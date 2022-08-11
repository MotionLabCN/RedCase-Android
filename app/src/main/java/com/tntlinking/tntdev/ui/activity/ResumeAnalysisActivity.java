package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ParseAnalysisApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;

import androidx.annotation.NonNull;

public final class ResumeAnalysisActivity extends AppActivity {
    private PDFView pdfView;
    private ImageView ivView;
    private Button btn_parse;

    private BaseDialog.Builder<BaseDialog.Builder<?>> builderBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.analysis_activity;
    }

    @Override
    protected void initView() {
        pdfView = findViewById(R.id.pdfView);
        ivView = findViewById(R.id.ivView);
        btn_parse = findViewById(R.id.btn_parse);

        builderBuilder = new BaseDialog.Builder<>(ResumeAnalysisActivity.this);
        builderBuilder.setContentView(R.layout.show_resume_dialog).setAnimStyle(BaseDialog.ANIM_SCALE);

        ImageView imageView = builderBuilder.findViewById(R.id.iv_gif);
        GlideApp.with(this)
                .load(R.drawable.resume_2)
                .into(imageView);
    }

    @Override
    protected void initData() {
        String url = getString("url");
        String fileName = getString("fileName");
        if (!TextUtils.isEmpty(url)) {
            if (url.contains(".pdf")) {
                pdfView.setVisibility(View.VISIBLE);
                ivView.setVisibility(View.GONE);
                getPdf(url);
            } else {
                pdfView.setVisibility(View.GONE);
                ivView.setVisibility(View.VISIBLE);
                setImageview(url);
            }

        }
        btn_parse.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(fileName)) {
                parseResume(fileName);
            }
        });
    }

    @SuppressLint({"WrongThread", "StaticFieldLeak"})
    private void getPdf(String url) {
        final InputStream[] input = new InputStream[1];
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                showDialog();
                try {
                    input[0] = new URL(url).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                pdfView.fromStream(input[0]).onLoad(nbPages -> hideDialog()).load();

            }
        }.execute();
    }

    // 根据返回的url 下载显示图片 因为每次返回的地址都是一样的，照片能容不一样而已
    public void setImageview(String url) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                byte[] bytes = response.body().bytes();
                //把byte字节组装成图片
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //数据请求成功后，在主线程中更新
                runOnUiThread(() -> {
                    //网络图片请求成功，更新到主线程的ImageView
                    ivView.setImageBitmap(bmp);
                });
            }
        });
    }

    @Override
    public void onLeftClick(View view) {
        super.onLeftClick(view);
        SPUtils.getInstance().put(AppConfig.RESUME_ANALYSIS, false);
    }

    private void parseResume(String fileName) {
        EasyHttp.post(this)
                .api(new ParseAnalysisApi().setFile(fileName))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {


//                        Intent intent = new Intent(ResumeAnalysisActivity.this, EnterDeveloperActivity.class);
//                        intent.putExtra(INTENT_KEY_DEVELOPER_INFO, data.getData());
//                        startActivity(intent);
//                        finish();

                        new BaseDialog.Builder<>(ResumeAnalysisActivity.this)
                                .setContentView(R.layout.show_resume_status_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    SPUtils.getInstance().put(AppConfig.RESUME_ANALYSIS, true);//简历解析成功

                                    checkDeveloper(data.getData());
                                    DeveloperInfoBean.getSingleton().setDeveloperInfoBean(data.getData());
                                    finish();
                                    dialog.dismiss();
                                }).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        new BaseDialog.Builder<>(ResumeAnalysisActivity.this)
                                .setContentView(R.layout.show_resume_status_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setImageDrawable(R.id.iv_gif, R.drawable.icon_resume_fail)
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    parseResume(fileName);
                                    dialog.dismiss();
                                }).show();

                    }

                    @Override
                    public void onStart(Call call) {
                        builderBuilder.show();
                    }

                    @Override
                    public void onEnd(Call call) {
//                        super.onEnd(call);
                        builderBuilder.dismiss();
                    }
                });
    }

    private int position1 = 0;
    private int position2 = 0;
    private int position3 = 0;
    public static String IS_RESUME = "is_resume";
    public static String IS_FIRST_RESUME = "is_first_resume";

    public void checkDeveloper(DeveloperInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(IS_RESUME, true);
        if (!isJumpBase(bean)) {
            intent.setClass(this, AddBaseInfoActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra(IS_FIRST_RESUME, true);

            startActivity(intent);
            return;
        }
        if (!isJumpCareer(bean)) {
            intent.setClass(this, AddCareerActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra(IS_FIRST_RESUME, true);
            startActivity(intent);
            return;
        }

        if (!isJumpEducation(bean)) {
            intent.setClass(this, AddEducationActivityNew.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra(IS_FIRST_RESUME, true);
            intent.putExtra("position", position1);
            startActivity(intent);
            return;
        }
        if (!isJumpWork(bean)) {
            intent.setClass(this, AddWorkActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra(IS_FIRST_RESUME, true);
            intent.putExtra("position", position2);
            startActivity(intent);
            return;
        }
        if (!isJumpProject(bean)) {
//            startActivity(AddProjectActivityNew.class);
            intent.setClass(this, AddProjectActivityNew.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra(IS_FIRST_RESUME, true);
            intent.putExtra("position", position3);
            startActivity(intent);
            return;
        }


    }


    // 基本资料全部没写就跳过 返回true，其他情况都要展示false
    public boolean isJumpBase(DeveloperInfoBean bean) {
        if (TextUtils.isEmpty(bean.getRealName()) &&
                TextUtils.isEmpty(bean.getBirthday()) &&
                TextUtils.isEmpty(bean.getProvinceName()) &&
                TextUtils.isEmpty(bean.getCityName()) &&
                TextUtils.isEmpty(bean.getAreasName()) &&
                TextUtils.isEmpty(bean.getRemoteWorkReasonStr())) {

            return true;
        } else {
            return false;
        }
    }

    // 职业资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpCareer(DeveloperInfoBean bean) {
        if (bean.getWorkModeDtoList().size() == 0 &&
                TextUtils.isEmpty(bean.getCareerDto().getCareerDirectionName()) &&
                TextUtils.isEmpty(bean.getCareerDto().getWorkYearsName()) &&
                TextUtils.isEmpty(bean.getCareerDto().getCurSalary())) {

            return true;
        } else {
            return false;
        }
    }

    // 教育资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpEducation(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        boolean mTag = true;
        if (educationDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < educationDtoList.size(); i++) {
                if (TextUtils.isEmpty(educationDtoList.get(i).getCollegeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getEducationName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getTrainingModeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getMajor()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolStartTime()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolEndTime())) {
                    position1 = i;

                    mTag = true;
                    break;
                } else {
                    position1 = i;
                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }

    // 工作资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpWork(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        boolean mTag = true;
        if (workExperienceDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < workExperienceDtoList.size(); i++) {
                if (TextUtils.isEmpty(workExperienceDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getPositionName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkStartTime()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkEndTime())) {
                    position2 = i;

                    mTag = true;
                    break;
                } else {
                    position2 = i;

                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }

    // 项目资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpProject(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
        boolean mTag = true;
        if (projectDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < projectDtoList.size(); i++) {
                if (TextUtils.isEmpty(projectDtoList.get(i).getProjectName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectStartDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectEndDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getPosition()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getDescription()) &&
                        projectDtoList.get(i).getProjectSkillList().size() == 0) {

                    position3 = i;

                    mTag = true;
                    break;
                } else {
                    position3 = i;

                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }
}

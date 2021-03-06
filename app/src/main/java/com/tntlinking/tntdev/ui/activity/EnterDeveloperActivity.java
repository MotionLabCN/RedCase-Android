package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.ParseResumeApi;
import com.tntlinking.tntdev.http.api.SubmitDeveloperApi;
import com.tntlinking.tntdev.http.api.UpdateAvatarApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.BitmapUtil;
import com.tntlinking.tntdev.other.FileSizeUtil;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.AddEducationAdapter;
import com.tntlinking.tntdev.ui.adapter.AddProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.AddWorkAdapter;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * ????????????????????????3
 */
public final class EnterDeveloperActivity extends AppActivity {
    private AppCompatButton mCommit;
    private ListView lv1, lv2, lv3;
    private ScrollView sv;
    private LinearLayout ll_add_base_info, ll_base_info, ll_add_career_info, ll_career_info;
    private TextView tv_edit_name;
    private TextView tv_edit_info;
    private TextView tv_edit_reason;
    private TextView tv_career_info;
    private TextView tv_career_info_work_year;
    private TextView tv_career_info_work_mode;
    private LinearLayout ll_add_education, ll_add_work, ll_add_project;
    private LinearLayout ll_progress;
    private LinearLayout ll_add_photo;
    private FrameLayout fl_add_photo;
    private TextView tv_photo_skills;
    private ImageView iv_photo_avatar;
    private TextView tv_welcome;
    private TextView tv_progress;
    private TextView tv_give_a_cue;
    private ImageView iv_progress;
    private ProgressBar progress_bar;
    private LinearLayout ll_import_resume;
    private TextView tv_add_base_info_tips;
    private TextView tv_add_career_info_tips;
    private TextView tv_add_education_tips;
    private TextView tv_add_worK_tips;
    private TextView tv_add_project_tips;
    public static final String INTENT_KEY_DEVELOPER_INFO = "DeveloperInfoBean";


    private AddEducationAdapter addEducationAdapter;
    private AddWorkAdapter addWorkAdapter;
    private AddProjectAdapter addProjectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.enter_developer_activity;
    }


    @Override
    protected void initView() {


        lv1 = findViewById(R.id.lv_1);
        lv2 = findViewById(R.id.lv_2);
        lv3 = findViewById(R.id.lv_3);
        sv = findViewById(R.id.sv);
        ll_add_base_info = findViewById(R.id.ll_add_base_info);
        ll_base_info = findViewById(R.id.ll_base_info);
        ll_add_career_info = findViewById(R.id.ll_add_career_info);
        ll_career_info = findViewById(R.id.ll_career_info);
        tv_edit_name = findViewById(R.id.tv_edit_name);
        tv_edit_info = findViewById(R.id.tv_edit_info);
        tv_edit_reason = findViewById(R.id.tv_edit_reason);
        tv_career_info = findViewById(R.id.tv_career_info);
        tv_career_info_work_year = findViewById(R.id.tv_career_info_work_year);
        tv_career_info_work_mode = findViewById(R.id.tv_career_info_work_mode);
        ll_add_education = findViewById(R.id.ll_add_education);
        ll_add_work = findViewById(R.id.ll_add_work);
        ll_add_project = findViewById(R.id.ll_add_project);
        ll_progress = findViewById(R.id.ll_progress);
        ll_add_photo = findViewById(R.id.ll_add_photo);
        fl_add_photo = findViewById(R.id.fl_add_photo);
        iv_photo_avatar = findViewById(R.id.iv_photo_avatar);
        tv_photo_skills = findViewById(R.id.tv_photo_skills);
        tv_welcome = findViewById(R.id.tv_welcome);
        tv_progress = findViewById(R.id.tv_progress);
        iv_progress = findViewById(R.id.iv_progress);
        progress_bar = findViewById(R.id.progress_bar);
        ll_import_resume = findViewById(R.id.ll_import_resume);
        tv_add_base_info_tips = findViewById(R.id.tv_add_base_info_tips);
        tv_add_career_info_tips = findViewById(R.id.tv_add_career_info_tips);
        tv_add_education_tips = findViewById(R.id.tv_add_education_tips);
        tv_add_worK_tips = findViewById(R.id.tv_add_worK_tips);
        tv_add_project_tips = findViewById(R.id.tv_add_project_tips);

        mCommit = findViewById(R.id.btn_commit);
        setOnClickListener(mCommit, ll_add_base_info, ll_base_info, ll_add_career_info, ll_career_info,
                ll_add_education, ll_add_work, ll_add_project, ll_add_photo, fl_add_photo, tv_photo_skills, ll_import_resume);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EnterDeveloperActivity.this, AddEducationActivityNew.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10006);
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EnterDeveloperActivity.this, AddWorkActivity.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10007);
            }
        });

        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EnterDeveloperActivity.this, AddProjectActivityNew.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10008);
            }
        });
    }


    @Override
    protected void initData() {// ???????????????????????????????????????????????????????????????????????????????????????
        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getRealName())) {
                setDeveloperInfo(bean);
            }
        } else {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);

            //?????????????????????
            String action = getIntent().getAction();//action
            String type = getIntent().getType();//??????
            //?????? /*&& "video/mp4".equals(type)*/
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                Uri uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
                //??????Uri??????????????????????????????????????????
                File file = UriUtils.uri2File(uri);

                //??????_???????????????
                String str1 = type.substring(0, type.indexOf("/"));
                String str2 = type.substring(str1.length() + 1, type.length());
                Log.d("str2", ">>>" + str2);
                EasyLog.print("======????????????==file=" + file);
                if (FileUtils.isFile(file) && str2.equals("pdf")) {
                    parseResume(file);
                } else {
                    toast("???????????????????????????PDF??????,????????????????????????");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
        getDeveloperDetail(developId);

//        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
//        if (bean != null) {
//            if (TextUtils.isEmpty(bean.getRealName())) {
//                setDeveloperInfo(bean);
//            }
//        } else {
//            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
//            getDeveloperDetail(developId);
//        }
        super.onResume();

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_add_photo:
            case R.id.fl_add_photo:

                ImageSelectActivity.start(this, data -> {
                    // ????????????
                    cropImageFile(new File(data.get(0)));
                });
                break;

            case R.id.tv_photo_skills: // https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/photography.md
//                startActivity(PhotoSkillsActivity.class);
                String service_guide = AppConfig.PHOTO_GUIDE_URL;
                intent.setClass(this, MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "????????????");
                startActivity(intent);
                break;
            case R.id.ll_add_base_info:
            case R.id.ll_base_info:
                intent.setClass(EnterDeveloperActivity.this, AddBaseInfoActivity.class);
                if (bean != null) {
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                }
                getActivity().startActivityForResult(intent, 10001);
                break;
            case R.id.ll_add_career_info:
            case R.id.ll_career_info:
                intent.setClass(EnterDeveloperActivity.this, AddCareerActivity.class);
                if (bean != null) {
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                }
                getActivity().startActivityForResult(intent, 10002);
                break;
            case R.id.ll_add_education:
                intent.setClass(EnterDeveloperActivity.this, AddEducationActivityNew.class);
                getActivity().startActivityForResult(intent, 10003);
                break;
            case R.id.ll_add_work:
                intent.setClass(EnterDeveloperActivity.this, AddWorkActivity.class);
                getActivity().startActivityForResult(intent, 10004);
                break;
            case R.id.ll_add_project:
                intent.setClass(EnterDeveloperActivity.this, AddProjectActivityNew.class);
                getActivity().startActivityForResult(intent, 10005);
                break;
            case R.id.btn_commit:
//                if (TextUtils.isEmpty(tv_edit_name.getText())) {
//                    toast("???????????????????????????????????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(tv_career_info.getText())) {
//                    toast("???????????????????????????????????????");
//                    return;
//                }
//                if (addEducationAdapter.getCount() == 0) {
//                    toast("???????????????????????????????????????");
//                    return;
//                }
//                if (addWorkAdapter.getCount() == 0) {
//                    toast("???????????????????????????????????????");
//                    return;
//                }
//                if (addProjectAdapter.getCount() == 0) {
//                    toast("???????????????????????????????????????");
//                    return;
//                }
//                submitDeveloper();

                checkCommit(bean);
                break;
            case R.id.ll_import_resume:
                startActivity(UploadResumeActivity.class);
                finish();
                break;
        }

    }


    public static List<ExperienceBean> sortList(List<ExperienceBean> listInAppxList) {
        Comparator<ExperienceBean> comparator = new Comparator<ExperienceBean>() {
            @Override
            public int compare(ExperienceBean details1, ExperienceBean details2) {
                if (details1.getType() > details2.getType())
                    return 1;
                else {
                    return -1;
                }
            }
        };
        //??????????????????????????????????????????
        Collections.sort(listInAppxList, comparator);
        return listInAppxList;
    }


    public static List<ExperienceBean> sortListForDate(List<ExperienceBean> listInAppxList) {
        Comparator<ExperienceBean> comparator = new Comparator<ExperienceBean>() {
            @Override
            public int compare(ExperienceBean o1, ExperienceBean o2) {
                if (o1.getType() == 4 && o2.getType() == 4) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getInSchoolStartTime()) < TimeUtil.getTimeLong("yyyy-MM", o2.getInSchoolStartTime()))
                        return 1;
                    else {
                        return -1;
                    }
                } else if (o1.getType() == 5 && o2.getType() == 5) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
                        return 1;
                    else {
                        return -1;
                    }
                } else if (o1.getType() == 6 && o2.getType() == 6) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
                        return 1;
                    else {
                        return -1;
                    }
                }
                return 1;


//                if (o1.getType() == 4 && o2.getType() == 4) {
//                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
//                        return 1;
//                    else {
//                        return -1;
//                    }
//                }
//                return 1;
            }
        };
        //??????????????????????????????????????????
        Collections.sort(listInAppxList, comparator);
        return listInAppxList;
    }

    /**
     * @param type
     * @param list
     * @return
     */
    public int getPosition(int type, List<ExperienceBean> list) {
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == type || list.get(i).getType() == type + 3) {
                position = i;
            }
        }
        return position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);
        }
    }

    private int progress = 0;
    private DeveloperInfoBean bean;

    @SuppressLint("SetTextI18n")
    public void getDeveloperDetail(int parentId) {
        EasyHttp.get(this)
                .api(new GetDeveloperDetailApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {
                        setDeveloperInfo(data.getData());
//                        progress = 0;
//                        bean = data.getData();
//                        String realName = bean.getRealName();
//                        int sex = bean.getSex();
//                        if (!TextUtils.isEmpty(bean.getAvatarUrl())) {
//                            ll_add_photo.setVisibility(View.GONE);
//                            fl_add_photo.setVisibility(View.VISIBLE);
//                            GlideApp.with(EnterDeveloperActivity.this)
//                                    .load(bean.getAvatarUrl())
//                                    .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
//                                    .into(iv_photo_avatar);
//                        } else {
//                            ll_add_photo.setVisibility(View.VISIBLE);
//                            fl_add_photo.setVisibility(View.GONE);
//                        }
//                        if (!TextUtils.isEmpty(realName)) {
//                            ll_base_info.setVisibility(View.VISIBLE);
//                            tv_edit_name.setText(realName);
//                            String mSex = sex == 0 ? "???" : "???";
//
//                            String nowTime = TimeUtil.getTimeString("yyyy-MM-dd");
//                            int age = Utils.getIntYear(nowTime) - Utils.getIntYear(bean.getBirthday());
//                            tv_edit_info.setText(mSex + " | " + age + "??? | " + bean.getProvinceName() + bean.getCityName() + bean.getAreasName());
//                            tv_edit_reason.setText(bean.getRemoteWorkReasonStr());
//
//                            progress++;
//                        }
//                        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
//                        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();
//                        if (workModeDtoList.size() != 0) {
//                            if (!TextUtils.isEmpty(careerDto.getCareerDirectionName())) {
//                                SPUtils.getInstance().put(AppConfig.CAREER_ID, bean.getCareerDirectionId() + "");
//
//                                ll_career_info.setVisibility(View.VISIBLE);
//                                tv_career_info.setText(careerDto.getCareerDirectionName());
//                                tv_career_info_work_year.setText(careerDto.getWorkYearsName() + " | ???????????????" + (Double.parseDouble(careerDto.getCurSalary()) / 1000) + "K");
//                                tv_career_info_work_mode.setText(workModeDtoList.get(0).getWorkDayModeName() + " | ???????????????" +
//                                        (Double.parseDouble(workModeDtoList.get(0).getLowestSalary()) / 1000) + "K"
//                                        + "-" + (Double.parseDouble(workModeDtoList.get(0).getHighestSalary()) / 1000) + "K");
//                                progress++;
//                            }
//                        }
//
//                        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
//                        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
//                        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
//                        if (educationDtoList.size() != 0) {
//                            progress++;
//                        }
//                        addEducationAdapter = new AddEducationAdapter(EnterDeveloperActivity.this, educationDtoList);
//                        lv1.setAdapter(addEducationAdapter);
//
//                        if (workExperienceDtoList.size() != 0) {
//                            progress++;
//                        }
//                        addWorkAdapter = new AddWorkAdapter(EnterDeveloperActivity.this, workExperienceDtoList);
//                        lv2.setAdapter(addWorkAdapter);
//                        if (projectDtoList.size() >= 1) {
//                            progress++;
//                        }
//                        addProjectAdapter = new AddProjectAdapter(EnterDeveloperActivity.this, projectDtoList);
//                        lv3.setAdapter(addProjectAdapter);
//                        if (projectDtoList.size() >= 2) {
//                            progress++;
//                        }
//                        if (projectDtoList.size() >= 3) {
//                            progress++;
//                        }
//                        if (projectDtoList.size() >= 4) {
//                            progress++;
//                        }
//                        if (projectDtoList.size() >= 5) {
//                            progress++;
//                        }
//                        if (projectDtoList.size() >= 6) {
//                            progress++;
//                        }
//                        if (projectDtoList.size() >= 7) {
//                            progress++;
//                        }
//
//
//                        ll_progress.setVisibility(View.VISIBLE);
//                        if (progress == 0) {
//                            ll_progress.setVisibility(View.GONE);
//                        } else if (progress == 1) {
//                            tv_progress.setText("\"???????????????2%???????????????????????????~\"");
//                            progress_bar.setProgress(2);
//                        } else if (progress == 2) {
//                            tv_progress.setText("\"???????????????5%??????????????????~\"");
//                            progress_bar.setProgress(5);
//                        } else if (progress == 3) {
//                            tv_progress.setText("\"???????????????10%????????????????????????~\"");
//                            progress_bar.setProgress(10);
//                        } else if (progress == 4) {
//                            tv_progress.setText("\"???????????????30%??????????????????????????????????????????~\"");
//                            progress_bar.setProgress(30);
//                        } else if (progress == 5) {
//                            tv_progress.setText("\"???????????????????????????60%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(60);
//                        } else if (progress == 6) {
//                            tv_progress.setText("\"???????????????????????????65%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(65);
//                        } else if (progress == 7) {
//                            tv_progress.setText("\"???????????????????????????70%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(70);
//                        } else if (progress == 8) {
//                            tv_progress.setText("\"???????????????????????????75%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(75);
//                        } else if (progress == 9) {
//                            tv_progress.setText("\"???????????????????????????80%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(80);
//                        } else if (progress == 10) {
//                            tv_progress.setText("\"???????????????????????????85%????????????????????????????????????????????????????????????????????????~\"");
//                            progress_bar.setProgress(85);
//                        } else {
//                            tv_progress.setText("\"???????????????????????????90%?????????????????????????????????\"");
//                            progress_bar.setProgress(90);
//                        }
//
//                        if (bean.getStatus() == 2) {
//                            tv_progress.setText("\"??????????????????????????????1-3???????????????????????????\"");
//                            iv_progress.setImageResource(R.drawable.icon_warning);
//                            progress_bar.setVisibility(View.GONE);
//                        } else if (bean.getStatus() == 3) {
//                            tv_welcome.setVisibility(View.GONE);
//                            ll_progress.setVisibility(View.GONE);
//                            mCommit.setVisibility(View.GONE);
//                        }
//                        sv.smoothScrollTo(0, 0);
                    }
                });
    }

    public void submitDeveloper() {
        EasyHttp.post(this)
                .api(new SubmitDeveloperApi())
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
//                        startActivity(SaveQRActivity.class);
//                        finish();
                        SPUtils.getInstance().put(AppConfig.RESUME_ANALYSIS, false);
                        toast("????????????");
                        onResume();
                    }
                });
    }

    /**
     * ????????????
     */
    private void cropImageFile(File sourceFile) {
//        ImageCropActivity.start(this, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {
//
//            @Override
//            public void onSucceed(Uri fileUri, String fileName) {
//                File outputFile;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    outputFile = new FileContentResolver(getActivity(), fileUri, fileName);
//                } else {
//                    try {
//                        outputFile = new File(new URI(fileUri.toString()));
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                        outputFile = new File(fileUri.toString());
//                    }
//                }
//                updateCropImage(outputFile, true);
//            }
//
//            @Override
//            public void onError(String details) {
//                // ????????????????????????????????????????????????
//                // ???????????????????????????????????????????????????
//                updateCropImage(sourceFile, false);
//            }
//        });

//        updateCropImage(sourceFile, false); // ????????????????????????
//        double fileSize = FileSizeUtil.getFileOrFilesSize(sourceFile, 3);
//        if (2 < fileSize && fileSize < 10) {//????????????2M ????????????????????????2M ????????????
//            File file = new File(BitmapUtil.compressImage(sourceFile.getAbsolutePath(), 90));
//            EasyLog.print("===1111====="+ FileUtils.getSize(file));
//            updateCropImage(file, false);
//        } else if (fileSize > 10) {
//            File file = new File(BitmapUtil.compressImage(sourceFile.getAbsolutePath(), 80));
//            EasyLog.print("===222====="+ FileUtils.getSize(file));
//
//            updateCropImage(file, false);
//        } else {
//            updateCropImage(sourceFile, false);
//        }

        double fileSize = FileSizeUtil.getFileOrFilesSize(sourceFile, 3);
        EasyLog.print("===FileUtils=111===" + FileUtils.getSize(sourceFile));
        if (fileSize > 2) {//????????????2M ????????????????????????2M ????????????
            File file = new File(BitmapUtil.compressImage(sourceFile.getAbsolutePath(), 90));
            EasyLog.print("===getSize=222===" + FileUtils.getSize(file));
            updateCropImage(file, false);
            toast("??????????????????==>>>" + FileUtils.getSize(file));
        } else {
            updateCropImage(sourceFile, false);
        }

    }

    /**
     * ????????????
     */
    private Uri mAvatarUrl;

    /**
     * ????????????????????????
     */
    private void updateCropImage(File file, boolean deleteFile) {
        ll_add_photo.setVisibility(View.GONE);
        fl_add_photo.setVisibility(View.VISIBLE);
        EasyHttp.post(this)
                .api(new UpdateAvatarApi()
                        .setFiles(file))
                .request(new HttpCallback<HttpData<String>>(this) {

                    @Override
                    public void onSucceed(HttpData<String> data) {

                        mAvatarUrl = Uri.parse(data.getData());
                        GlideApp.with(getActivity())
                                .load(mAvatarUrl)
                                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
                                .into(iv_photo_avatar);
                        if (deleteFile) {
                            file.delete();
                        }
                    }
                });
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    @SuppressLint("SetTextI18n")
    public void setDeveloperInfo(DeveloperInfoBean data) {

        progress = 0;
        bean = data;
        String realName = bean.getRealName();
        int sex = bean.getSex();
        if (!TextUtils.isEmpty(bean.getAvatarUrl())) {
            ll_add_photo.setVisibility(View.GONE);
            fl_add_photo.setVisibility(View.VISIBLE);
            GlideApp.with(EnterDeveloperActivity.this)
                    .load(bean.getAvatarUrl())
                    .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
                    .into(iv_photo_avatar);
        } else {
            ll_add_photo.setVisibility(View.VISIBLE);
            fl_add_photo.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(realName)) {
            ll_base_info.setVisibility(View.VISIBLE);
            tv_edit_name.setText(realName);
            String mSex = sex == 0 ? "???" : "???";

            String nowTime = TimeUtil.getTimeString("yyyy-MM-dd");
            int age = Utils.getIntYear(nowTime) - Utils.getIntYear(bean.getBirthday());

            if (bean.getProvinceName() == null) {
                tv_edit_info.setText(mSex + " | " + age + "???");
            } else {
                tv_edit_info.setText(mSex + " | " + age + "??? | " + bean.getProvinceName() + bean.getCityName() + bean.getAreasName());
            }
            tv_edit_reason.setText(bean.getRemoteWorkReasonStr());

            progress++;
        }
        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();
        if (workModeDtoList.size() != 0) {
            if (!TextUtils.isEmpty(careerDto.getCareerDirectionName())) {
                SPUtils.getInstance().put(AppConfig.CAREER_ID,careerDto.getCareerDirectionId());

                ll_career_info.setVisibility(View.VISIBLE);
                tv_career_info.setText(careerDto.getCareerDirectionName());
                tv_career_info_work_year.setText(careerDto.getWorkYearsName() + " | ???????????????" + (Double.parseDouble(Utils.isNullSalary(careerDto.getCurSalary())) / 1000) + "K");
                tv_career_info_work_mode.setText(workModeDtoList.get(0).getWorkDayModeName() + " | ???????????????" +
                        (Double.parseDouble(Utils.isNullSalary(workModeDtoList.get(0).getLowestSalary())) / 1000) + "K"
                        + "-" + (Double.parseDouble(Utils.isNullSalary(workModeDtoList.get(0).getHighestSalary())) / 1000) + "K");
                progress++;


            }
        }

        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
        if (educationDtoList.size() != 0) {
            progress++;
        }
        addEducationAdapter = new AddEducationAdapter(EnterDeveloperActivity.this, educationDtoList);
        lv1.setAdapter(addEducationAdapter);

        if (workExperienceDtoList.size() != 0) {
            progress++;
        }
        addWorkAdapter = new AddWorkAdapter(EnterDeveloperActivity.this, workExperienceDtoList);
        lv2.setAdapter(addWorkAdapter);
        if (projectDtoList.size() >= 1) {
            progress++;
        }
        addProjectAdapter = new AddProjectAdapter(EnterDeveloperActivity.this, projectDtoList);
        lv3.setAdapter(addProjectAdapter);
        if (projectDtoList.size() >= 2) {
            progress++;
        }
        if (projectDtoList.size() >= 3) {
            progress++;
        }
        if (projectDtoList.size() >= 4) {
            progress++;
        }
        if (projectDtoList.size() >= 5) {
            progress++;
        }
        if (projectDtoList.size() >= 6) {
            progress++;
        }
        if (projectDtoList.size() >= 7) {
            progress++;
        }


        EasyLog.print("=====progress==" + progress);
        ll_progress.setVisibility(View.VISIBLE);
        if (progress == 0) {
            ll_progress.setVisibility(View.GONE);
        } else if (progress == 1) {
            tv_progress.setText("\"???????????????2%???????????????????????????~\"");
            progress_bar.setProgress(2);
        } else if (progress == 2) {
            tv_progress.setText("\"???????????????5%??????????????????~\"");
            progress_bar.setProgress(5);
        } else if (progress == 3) {
            tv_progress.setText("\"???????????????10%????????????????????????~\"");
            progress_bar.setProgress(10);
        } else if (progress == 4) {
            tv_progress.setText("\"???????????????30%??????????????????????????????????????????~\"");
            progress_bar.setProgress(30);
        } else if (progress == 5) {
            tv_progress.setText("\"???????????????????????????60%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(60);
        } else if (progress == 6) {
            tv_progress.setText("\"???????????????????????????65%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(65);
        } else if (progress == 7) {
            tv_progress.setText("\"???????????????????????????70%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(70);
        } else if (progress == 8) {
            tv_progress.setText("\"???????????????????????????75%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(75);
        } else if (progress == 9) {
            tv_progress.setText("\"???????????????????????????80%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(80);
        } else if (progress == 10) {
            tv_progress.setText("\"???????????????????????????85%????????????????????????????????????????????????????????????????????????~\"");
            progress_bar.setProgress(85);
        } else {
            tv_progress.setText("\"???????????????????????????90%?????????????????????????????????\"");
            progress_bar.setProgress(90);
        }
        //??????????????????????????????
        boolean isResume = SPUtils.getInstance().getBoolean(AppConfig.RESUME_ANALYSIS, false);
        if (bean.getStatus() == 2) {
            tv_progress.setText("\"??????????????????????????????1-3???????????????????????????\"");
            iv_progress.setImageResource(R.drawable.icon_warning);
            progress_bar.setVisibility(View.GONE);
        } else if (bean.getStatus() == 3) {
            tv_welcome.setVisibility(View.GONE);
            ll_progress.setVisibility(View.GONE);

            if (isResume) {
                mCommit.setVisibility(View.VISIBLE);
            } else {
                mCommit.setVisibility(View.GONE);
            }
        }

        /**  -------------------------- ???????????????????????????????????? ------------------------------------ */
        if (TextUtils.isEmpty(bean.getRealName()) ||
                TextUtils.isEmpty(bean.getBirthday()) ||
                TextUtils.isEmpty(bean.getProvinceName()) ||
                TextUtils.isEmpty(bean.getCityName()) ||
                TextUtils.isEmpty(bean.getAreasName()) ||
                TextUtils.isEmpty(bean.getRemoteWorkReasonStr())) {
            tv_add_base_info_tips.setVisibility(View.VISIBLE);
        } else {
            tv_add_base_info_tips.setVisibility(View.INVISIBLE);
        }

        if (workModeDtoList.size() == 0 ||
                TextUtils.isEmpty(careerDto.getCareerDirectionName()) ||
                TextUtils.isEmpty(careerDto.getWorkYearsName()) ||
                TextUtils.isEmpty(careerDto.getCurSalary())) {
            tv_add_career_info_tips.setVisibility(View.VISIBLE);
        } else {
            tv_add_career_info_tips.setVisibility(View.INVISIBLE);
        }

        if (educationDtoList.size() == 0) {
            tv_add_education_tips.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < educationDtoList.size(); i++) {
                if (TextUtils.isEmpty(educationDtoList.get(i).getCollegeName()) ||
                        TextUtils.isEmpty(educationDtoList.get(i).getEducationName()) ||
                        TextUtils.isEmpty(educationDtoList.get(i).getTrainingModeName()) ||
                        TextUtils.isEmpty(educationDtoList.get(i).getMajor()) ||
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolStartTime()) ||
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolEndTime())) {

                    tv_add_education_tips.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tv_add_education_tips.setVisibility(View.INVISIBLE);
                }
            }
        }

        if (workExperienceDtoList.size() == 0) {
            tv_add_worK_tips.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < workExperienceDtoList.size(); i++) {
                if (TextUtils.isEmpty(workExperienceDtoList.get(i).getCompanyName()) ||
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getIndustryName()) ||
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getPositionName()) ||
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkStartTime()) ||
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkEndTime())) {

                    tv_add_worK_tips.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tv_add_worK_tips.setVisibility(View.INVISIBLE);
                }
            }
        }

        if (projectDtoList.size() == 0) {
            tv_add_project_tips.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < projectDtoList.size(); i++) {
                if (TextUtils.isEmpty(projectDtoList.get(i).getProjectName()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getIndustryName()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectStartDate()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectEndDate()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getPosition()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getCompanyName()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getIndustryName()) ||
                        TextUtils.isEmpty(projectDtoList.get(i).getDescription()) ||
                        projectDtoList.get(i).getProjectSkillList().size() == 0) {

                    tv_add_project_tips.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tv_add_project_tips.setVisibility(View.INVISIBLE);
                }
            }
        }
        sv.smoothScrollTo(0, 0);
    }

    /**
     * ?????????????????? ???????????????
     * 1???????????????????????? 2???????????????????????? 3???????????????????????? 4???????????????????????? 5????????????????????????
     *
     * @param bean
     */
    public void checkCommit(DeveloperInfoBean bean) {
        if (TextUtils.isEmpty(bean.getRealName()) ||
                TextUtils.isEmpty(bean.getBirthday()) ||
                TextUtils.isEmpty(bean.getProvinceName()) ||
                TextUtils.isEmpty(bean.getCityName()) ||
                TextUtils.isEmpty(bean.getAreasName()) ||
                TextUtils.isEmpty(bean.getRemoteWorkReasonStr())) {

            toast("?????????????????????????????????????????????");
            return;
        }

        if (bean.getWorkModeDtoList().size() == 0 ||
                TextUtils.isEmpty(bean.getCareerDto().getCareerDirectionName()) ||
                TextUtils.isEmpty(bean.getCareerDto().getWorkYearsName()) ||
                TextUtils.isEmpty(bean.getCareerDto().getCurSalary())) {

            toast("?????????????????????????????????????????????");
            return;
        }
        if (bean.getEducationDtoList().size() == 0) {
            toast("?????????????????????????????????????????????");
            return;
        } else {
            for (int i = 0; i < bean.getEducationDtoList().size(); i++) {
                if (TextUtils.isEmpty(bean.getEducationDtoList().get(i).getCollegeName()) ||
                        TextUtils.isEmpty(bean.getEducationDtoList().get(i).getEducationName()) ||
                        TextUtils.isEmpty(bean.getEducationDtoList().get(i).getTrainingModeName()) ||
                        TextUtils.isEmpty(bean.getEducationDtoList().get(i).getMajor()) ||
                        TextUtils.isEmpty(bean.getEducationDtoList().get(i).getInSchoolStartTime()) ||
                        TextUtils.isEmpty(bean.getEducationDtoList().get(i).getInSchoolEndTime())) {

                    toast("?????????????????????????????????????????????");
                    return;
                }
            }
        }
        if (bean.getWorkExperienceDtoList().size() == 0) {
            toast("?????????????????????????????????????????????");
            return;
        } else {
            for (int i = 0; i < bean.getWorkExperienceDtoList().size(); i++) {
                if (TextUtils.isEmpty(bean.getWorkExperienceDtoList().get(i).getCompanyName()) ||
                        TextUtils.isEmpty(bean.getWorkExperienceDtoList().get(i).getIndustryName()) ||
                        TextUtils.isEmpty(bean.getWorkExperienceDtoList().get(i).getPositionName()) ||
                        TextUtils.isEmpty(bean.getWorkExperienceDtoList().get(i).getWorkStartTime()) ||
                        TextUtils.isEmpty(bean.getWorkExperienceDtoList().get(i).getWorkEndTime())) {

                    toast("?????????????????????????????????????????????");
                    return;
                }
            }
        }

        if (bean.getProjectDtoList().size() == 0) {
            toast("?????????????????????????????????????????????");
            return;
        } else {
            for (int i = 0; i < bean.getProjectDtoList().size(); i++) {
                if (TextUtils.isEmpty(bean.getProjectDtoList().get(i).getProjectName()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getIndustryName()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getProjectStartDate()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getProjectEndDate()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getPosition()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getCompanyName()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getIndustryName()) ||
                        TextUtils.isEmpty(bean.getProjectDtoList().get(i).getDescription()) ||
                        bean.getProjectDtoList().get(i).getProjectSkillList().size() == 0) {

                    toast("?????????????????????????????????????????????");
                    return;
                }
            }
        }

        submitDeveloper();
    }

    /**
     * ??????????????????????????? ????????????
     *
     * @param file
     */
    private void parseResume(File file) {
        EasyHttp.post(this)
                .api(new ParseResumeApi().setFile(file))
                .request(new HttpCallback<HttpData<ParseResumeApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<ParseResumeApi.Bean> data) {
                        String url = data.getData().getUrl();
                        String fileName = data.getData().getFileName();
                        new BaseDialog.Builder<>(EnterDeveloperActivity.this)
                                .setContentView(R.layout.check_order_status_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "????????????")
                                .setText(R.id.tv_content, "?????????????????????????????????????????????")
                                .setText(R.id.btn_dialog_custom_cancel, "??????")
                                .setText(R.id.btn_dialog_custom_ok, "??????")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    Intent intent = new Intent(EnterDeveloperActivity.this, ResumeAnalysisActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("fileName", fileName);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }).show();
                    }
                });
    }
}
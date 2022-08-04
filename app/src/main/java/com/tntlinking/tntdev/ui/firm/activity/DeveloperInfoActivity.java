package com.tntlinking.tntdev.ui.firm.activity;

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
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
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
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.firm.adapter.DevEducationAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.DevProjectAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.DevWorkAdapter;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户信息填写页面3
 */
public final class DeveloperInfoActivity extends AppActivity {
    private LinearLayout ll_to_collect;
    private LinearLayout ll_to_sign;
    private AppCompatButton btn_to_interview;
    private ListView lv1, lv2, lv3;
    private ScrollView sv;
    private ImageView iv_avatar;
    private TextView tv_dev_name;
    private TextView tv_dev_info;
    private TextView tv_salary;
    public static final String INTENT_KEY_DEVELOPER_INFO = "DeveloperInfoBean";


    private DevEducationAdapter addEducationAdapter;
    private DevWorkAdapter addWorkAdapter;
    private DevProjectAdapter addProjectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.developer_info_activity;
    }


    @Override
    protected void initView() {
        lv1 = findViewById(R.id.lv_1);
        lv1 = findViewById(R.id.lv_1);
        lv2 = findViewById(R.id.lv_2);
        lv3 = findViewById(R.id.lv_3);
        sv = findViewById(R.id.sv);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_dev_name = findViewById(R.id.tv_dev_name);
        tv_dev_info = findViewById(R.id.tv_dev_info);
        tv_salary = findViewById(R.id.tv_salary);
        ll_to_collect = findViewById(R.id.ll_to_collect);
        ll_to_sign = findViewById(R.id.ll_to_sign);
        btn_to_interview = findViewById(R.id.btn_to_interview);

        setOnClickListener(ll_to_collect, ll_to_sign, btn_to_interview);

        GlideUtils.loadRoundCorners(this, R.drawable.update_app_top_bg, iv_avatar, (int) getResources().getDimension(R.dimen.dp_8));
    }


    @Override
    protected void initData() {// 一个是从简历解析传过来的，一个是进入页面接口请求显示数据的
        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getRealName())) {
                setDeveloperInfo(bean);
            }
        } else {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);

        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_to_collect:
                toast("收藏");
                break;
            case R.id.ll_to_sign:
                toast("去签约");
                startActivity(ContractDetailActivity.class);
                break;
            case R.id.btn_to_interview:

                new BaseDialog.Builder<>(this)
                        .setContentView(R.layout.to_add_service_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setOnClickListener(R.id.iv_close, (dialog, views) -> {
                            dialog.dismiss();
                        }).show();
                break;
        }

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
                        toast("提交成功");
                        onResume();
                    }
                });
    }


    /**
     * 简历解析页面跳转过来的，直接填充相关数据
     */
    @SuppressLint("SetTextI18n")
    public void setDeveloperInfo(DeveloperInfoBean data) {

        progress = 0;
        bean = data;
        String realName = bean.getRealName();


        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();


        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
        if (educationDtoList.size() != 0) {
            progress++;
        }
        addEducationAdapter = new DevEducationAdapter(DeveloperInfoActivity.this, educationDtoList);
        lv1.setAdapter(addEducationAdapter);

        if (workExperienceDtoList.size() != 0) {
            progress++;
        }
        addWorkAdapter = new DevWorkAdapter(DeveloperInfoActivity.this, workExperienceDtoList);
        lv2.setAdapter(addWorkAdapter);
        if (projectDtoList.size() >= 1) {
            progress++;
        }
        addProjectAdapter = new DevProjectAdapter(DeveloperInfoActivity.this, projectDtoList);
        lv3.setAdapter(addProjectAdapter);

        sv.smoothScrollTo(0, 0);
    }

}
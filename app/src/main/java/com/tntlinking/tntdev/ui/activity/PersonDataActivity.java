package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class PersonDataActivity extends AppActivity {

    private SettingBar mPersonDataIncome;
    private SettingBar mPersonDataInterview;
    private SettingBar mPersonDataSetting;
    private SettingBar person_data_private;
    private SettingBar person_data_deal;
    private SettingBar person_data_dev;
    private SettingBar person_data_about;
    private SettingBar person_data_recommend;
    private SettingBar person_data_service;
    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_sign_num;
    private TextView tv_profit_total;
    //    private TextView tv_status;
    //https://fuchsia-athlete-f65.notion.site/9f0df9c1265e4d00a99ac0591d390ac3
    //https://fuchsia-athlete-f65.notion.site/cfcae78c7aa643228502af8e9c6a0d17
    private String status_1 = "尚未完善入驻资料，请先完善";// 待完善
    private String status_2 = "入驻资料尚未完成审核，请稍后再试";// 待审核
    private String status_3 = "很遗憾入驻资料未通过审核，请先完善";// 审核失败

    //    private String status_1 ="尚未完善入驻资料，请先完善";// 审核成功
    @Override
    protected int getLayoutId() {
        return R.layout.persondata_activity;
    }

    @Override
    protected void initView() {
//        tv_status = findViewById(R.id.tv_status);
        mPersonDataIncome = findViewById(R.id.person_data_income);
        mPersonDataInterview = findViewById(R.id.person_data_interview);
        mPersonDataSetting = findViewById(R.id.person_data_setting);
        person_data_private = findViewById(R.id.person_data_private);
        person_data_deal = findViewById(R.id.person_data_deal);
        person_data_dev = findViewById(R.id.person_data_dev);
        person_data_about = findViewById(R.id.person_data_about);
        person_data_recommend = findViewById(R.id.person_data_recommend);
        person_data_service = findViewById(R.id.person_data_service);
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_sign_num = findViewById(R.id.tv_sign_num);
        tv_profit_total = findViewById(R.id.tv_profit_total);

        setOnClickListener(mPersonDataIncome, mPersonDataSetting, mPersonDataInterview,
                person_data_private, person_data_deal, person_data_dev, person_data_about, person_data_recommend, person_data_service);

//        String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME);
//        if (!TextUtils.isEmpty(name)) {
//            tv_avatar.setText(name);
//        } else {
//            tv_avatar.setBackground(getResources().getDrawable(R.drawable.dot_oval_blue));
//        }
    }

    @Override
    protected void initData() {
        getData();

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == person_data_dev) { // 入驻资料
            startActivity(EnterDeveloperActivity.class);
        } else if (view == person_data_service) {// 服务协议
//            showServiceDialog();
            if (mContractStatus == 0) {// 没有签约 才能点进去，签约了不能 点进去了
                startActivity(SignContactActivity.class);
            }

        } else if (view == mPersonDataIncome) {// 收益账单
            startActivity(IncomeListActivity.class);
        } else if (view == mPersonDataInterview) {// 面试设置
            startActivity(InterviewSettingActivity.class);
        } else if (view == person_data_recommend) {// 推荐有礼
            startActivity(InterviewActivity.class);
        } else if (view == person_data_private) {// 隐私政策
            BrowserActivity.start(getActivity(), AppConfig.PRIVATE_URL);
        } else if (view == person_data_deal) {// 用户协议
            BrowserActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
        } else if (view == mPersonDataSetting) {// 账户设置
            startActivity(PersonSettingActivity.class);
        } else if (view == person_data_about) {// 关于天天数链开发者
            startActivity(AboutAppActivity.class);
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    private String mStatus = "1"; //1  待完善 2  待审核 3  审核成功 4  审核失败
    private int mContractStatus = 0; //0  未签约 1 签约成功

    public void getData() {
        EasyHttp.get(this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        if (TextUtils.isEmpty(data.getData().getRealName())) {
                            tv_avatar.setText("朋友");
                        } else {
                            tv_avatar.setText(Utils.formatName(data.getData().getRealName()));
                        }
                        tv_name.setText(data.getData().getRealName());
                        tv_position.setText(data.getData().getCareerDirection());
                        tv_sign_num.setText(data.getData().getSignContractNum() + "次");
                        tv_profit_total.setText("¥" + data.getData().getProfitTotal());
                        mStatus = data.getData().getStatus();
                        mContractStatus = data.getData().getContractStatus();

                        person_data_service.setRightText(mContractStatus == 0 ? "未签约" : "已签约");
                        if (mStatus.equals("1")) {
//                            tv_status.setVisibility(View.VISIBLE);
//                            tv_status.setText("未入驻");
                            person_data_dev.setRightText("待完善");
                        } else if (mStatus.equals("2")) {
//                            tv_status.setVisibility(View.VISIBLE);
//                            tv_status.setText("审核中");
                            person_data_dev.setRightText("待审核");
                        } else if (mStatus.equals("3")) {
//                            tv_status.setVisibility(View.VISIBLE);
//                            tv_status.setText("已认证");
                            person_data_dev.setRightText("审核成功");
                        } else if (mStatus.equals("4")) {
//                            tv_status.setVisibility(View.VISIBLE);
//                            tv_status.setText("未通过");
                            person_data_dev.setRightText("审核失败");
                        }
                    }
                });
    }

    /**
     * 不同的状态点进服务协议的弹框
     */
    public void showServiceDialog() {
        if (mStatus.equals("1")) {
            new BaseDialog.Builder<>(PersonDataActivity.this)
                    .setContentView(R.layout.write_daily_delete_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "尚未完善入驻资料，请先完善。")
                    .setText(R.id.btn_dialog_custom_cancel, "取消")
                    .setText(R.id.btn_dialog_custom_ok, "去完善")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                        startActivity(EnterDeveloperActivity.class);
                        dialog.dismiss();
                    })
                    .show();
        } else if (mStatus.equals("2")) {
            new BaseDialog.Builder<>(PersonDataActivity.this)
                    .setContentView(R.layout.write_daily_delete_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "入驻资料尚未完成审核，请稍后再试。")
                    .setVisibility(R.id.btn_dialog_custom_cancel, View.GONE)
                    .setText(R.id.btn_dialog_custom_ok, "同意")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {


                        dialog.dismiss();
                    })
                    .show();
        } else if (mStatus.equals("3")) {
            startActivity(SignContactActivity.class);
        } else if (mStatus.equals("4")) {
            new BaseDialog.Builder<>(PersonDataActivity.this)
                    .setContentView(R.layout.write_daily_delete_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "很遗憾入驻资料未通过审核，请先完善。")
                    .setText(R.id.btn_dialog_custom_cancel, "取消")
                    .setText(R.id.btn_dialog_custom_ok, "去完善")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                        startActivity(EnterDeveloperActivity.class);
                        dialog.dismiss();
                    })
                    .show();
        }
    }
}
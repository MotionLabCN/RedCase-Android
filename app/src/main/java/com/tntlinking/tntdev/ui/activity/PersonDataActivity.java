package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetSignContractPDFApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


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
    private ScrollView scroll;
    private TitleBar title_bar;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        scroll = findViewById(R.id.scroll);
        title_bar = findViewById(R.id.title_bar);
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

        scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 0) {
                    title_bar.setBackgroundColor(getColor(R.color.transparent));
                } else if (scrollY > 0 && scrollY < SizeUtils.dp2px(200)) {
                    title_bar.setBackgroundColor(getColor(R.color.white));
                }
            }
        });
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
            showServiceDialog();
//            startActivity(SignContactActivity.class);
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

    private String mStatus = "1"; //入驻状态 1  待完善 2  待审核 3  审核成功 4  审核失败
    private int mContractStatus = 0; //签约状态 0, "待签约"，1, "签约中" 2, "签约成功" 3, "签约失败"

    public void getData() {
        EasyHttp.get(this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        SPUtils.getInstance().put(AppConfig.CAREER_ID, data.getData().getCareerDirectionId());

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
                        if (mContractStatus == 0) {
                            person_data_service.setRightText("待签约");
                        } else if (mContractStatus == 1) {
                            person_data_service.setRightText("签约中");
                        } else if (mContractStatus == 2) {
                            person_data_service.setRightText("签约成功");
                            getContactPDF();
                        } else if (mContractStatus == 3) {
                            person_data_service.setRightText("签约失败");
                        }

                        if (mStatus.equals("1")) {
                            person_data_dev.setRightText("待完善");
                            person_data_dev.setRightTextColor(getResources().getColor(R.color.color_hint_color));
                        } else if (mStatus.equals("2")) {
                            person_data_dev.setRightText("待审核");
                            person_data_dev.setRightTextColor(getResources().getColor(R.color.color_FB8B39));
                        } else if (mStatus.equals("3")) {
                            person_data_dev.setRightText("审核成功");
                            person_data_dev.setRightTextColor(getResources().getColor(R.color.color_hint_color));
                        } else if (mStatus.equals("4")) {
                            person_data_dev.setRightText("审核失败");
                            person_data_dev.setRightTextColor(getResources().getColor(R.color.color_F5313D));
                        }
                    }
                });
    }

    /**
     * 不同的状态点进服务协议的弹框
     */
    public void showServiceDialog() {
        if (mStatus.equals("1")) { // 入驻资料1  待完善 2  待审核 3  审核成功 4  审核失败
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
        } else if (mStatus.equals("2")) {// 入驻资料 2  待审核
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
        } else if (mStatus.equals("3")) {// 入驻资料3  审核成功
            if (mContractStatus == 0) {//待签约
                startActivity(SignContactActivity.class);
            } else if (mContractStatus == 1) {//签约中
                BrowserActivity.start(this, AppConfig.SIGN_CONTRACT_URL + "?developerId="
                        + SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID), "签约中");

            } else if (mContractStatus == 2) {//签约成功
                Intent intent = new Intent();
                intent.setClass(this, PDFViewActivity.class);
                intent.putExtra("pdf_url", PDFUrl);
                intent.putExtra("title", "合作协议");
                startActivity(intent);
            } else {//签约失败
                BrowserActivity.start(this, AppConfig.SIGN_CONTRACT_URL + "?developerId="
                        + SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID), "签约失败");
            }
        } else if (mStatus.equals("4")) {// 4  审核失败
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

    private String PDFUrl = "";

    public void getContactPDF() {
        EasyHttp.get(this)
                .api(new GetSignContractPDFApi())
                .request(new HttpCallback<HttpData<GetSignContractPDFApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetSignContractPDFApi.Bean> data) {
                        if (data.getData() != null) {
                            if (!TextUtils.isEmpty(data.getData().getPdfUrl())) {
                                PDFUrl = data.getData().getPdfUrl();
                            }
                        }

                    }
                });
    }
}
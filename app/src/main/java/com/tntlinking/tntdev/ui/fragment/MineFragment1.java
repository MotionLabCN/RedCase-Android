package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetHasRead;
import com.tntlinking.tntdev.http.api.GetSignContractPDFApi;
import com.tntlinking.tntdev.http.api.GetTaskStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.AboutAppActivity;
import com.tntlinking.tntdev.ui.activity.BrowserActivity;
import com.tntlinking.tntdev.ui.activity.BrowserPrivateActivity;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationTipsActivity;
import com.tntlinking.tntdev.ui.activity.HistoryOrderListActivity;
import com.tntlinking.tntdev.ui.activity.IncomeListActivity;
import com.tntlinking.tntdev.ui.activity.InterviewActivity;
import com.tntlinking.tntdev.ui.activity.InterviewSettingActivity;
import com.tntlinking.tntdev.ui.activity.JkBrowserActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.MessageListActivity;
import com.tntlinking.tntdev.ui.activity.PDFViewActivity;
import com.tntlinking.tntdev.ui.activity.PersonSettingActivity;
import com.tntlinking.tntdev.ui.activity.SignContactActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * desc   : 我的 Fragment
 */
public final class MineFragment1 extends TitleBarFragment<MainActivity> implements OnRefreshLoadMoreListener {
    private SmartRefreshLayout mRefreshLayout;

    //    private SettingBar mPersonDataIncome;
    private SettingBar mPersonDataInterview;
    private SettingBar mPersonDataSetting;
    private SettingBar person_data_private;
    private SettingBar person_data_deal;
    private SettingBar person_data_dev;
    private SettingBar person_data_about;
    private SettingBar person_data_recommend;
    private SettingBar person_data_service;
    private SettingBar person_data_evaluation;
    private SettingBar person_data_history;

    private TitleBar title_bar;
    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_sign_num;
    private TextView tv_profit_total;
    private ImageView iv_message;
    private View view_dot;
    private LinearLayout ll_income;

    public static MineFragment1 newInstance() {
        return new MineFragment1();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.persondata_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
//        tv_status = findViewById(R.id.tv_status);
//        mPersonDataIncome = findViewById(R.id.person_data_income);
        mPersonDataInterview = findViewById(R.id.person_data_interview);
        mPersonDataSetting = findViewById(R.id.person_data_setting);
        person_data_private = findViewById(R.id.person_data_private);
        person_data_deal = findViewById(R.id.person_data_deal);
        person_data_dev = findViewById(R.id.person_data_dev);
        person_data_about = findViewById(R.id.person_data_about);
        person_data_recommend = findViewById(R.id.person_data_recommend);
        person_data_service = findViewById(R.id.person_data_service);
        person_data_evaluation = findViewById(R.id.person_data_evaluation);
        person_data_history = findViewById(R.id.person_data_history);
        ll_income = findViewById(R.id.ll_income);

        ScrollView scroll = findViewById(R.id.scroll);
        title_bar = findViewById(R.id.title_bar);
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_sign_num = findViewById(R.id.tv_sign_num);
        tv_profit_total = findViewById(R.id.tv_profit_total);
        iv_message = findViewById(R.id.iv_message);
        view_dot = findViewById(R.id.view_dot);

        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        setOnClickListener(mPersonDataSetting, mPersonDataInterview,
                person_data_private, person_data_deal, person_data_dev, person_data_evaluation,
                person_data_about, person_data_recommend, person_data_service, person_data_history, iv_message, ll_income);


        scroll.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY <= 0) {
                title_bar.setBackgroundColor(getColor(R.color.transparent));
            } else if (scrollY < SizeUtils.dp2px(200)) {
                title_bar.setBackgroundColor(getColor(R.color.white));
            }
        });
    }

    @Override
    protected void initData() {
        getHasRead();
        getPersonData();
        getTaskStatus();
    }

    private void getDeveloperJkStatus() {
        EasyHttp.get(this)
                .api(new GetDeveloperJkStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperJkStatusApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<GetDeveloperJkStatusApi.Bean> data) {
                        if (mStatus.equals("1")) {
                            startActivity(EvaluationActivity.class);
                        } else if (data.getData() != null && data.getData().getUserPlanStatus() == 0) {
                            startActivity(EvaluationTipsActivity.class);
                        } else {
                            JkBrowserActivity.start(getActivity(), data.getData().getPlanUrl());

                        }

                    }
                });
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == person_data_dev) { // 入驻资料
            startActivity(EnterDeveloperActivity.class);
        } else if (view == person_data_service) {// 服务协议
//            showServiceDialog();
            startActivity(SignContactActivity.class);
        } else if (view == ll_income) {// 收益账单
            startActivity(IncomeListActivity.class);
        } else if (view == mPersonDataInterview) {// 面试设置
            startActivity(InterviewSettingActivity.class);
        } else if (view == person_data_recommend) {// 推荐有礼
            startActivity(InterviewActivity.class);
        } else if (view == person_data_private) {// 隐私政策
            BrowserPrivateActivity.start(getActivity(), AppConfig.PRIVATE_URL);
//            BrowserActivity.start(getActivity(), "https://talent-business.tntlinking.com/#/page/article?pageCode=5f537d5726744c0ab632f8379eeae3e5");
        } else if (view == person_data_deal) {// 用户协议
            BrowserPrivateActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
        } else if (view == mPersonDataSetting) {// 账户设置
            startActivity(PersonSettingActivity.class);
        } else if (view == person_data_about) {// 关于天天数链开发者
            startActivity(AboutAppActivity.class);
//            startActivity(FirmMainActivity.class);
//            ActivityManager.getInstance().finishAllActivities(FirmMainActivity.class);
        } else if (view == person_data_evaluation) {
            getDeveloperJkStatus();
        } else if (view == person_data_history) {// 历史订单
            startActivity(HistoryOrderListActivity.class);
        } else if (view == iv_message) {// 消息界面
            startActivity(MessageListActivity.class);
            view_dot.setVisibility(View.GONE);
        }

    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    /**
     * 是否有未读消息
     */
    public void getHasRead() {
        EasyHttp.get(this)
                .api(new GetHasRead())
                .request(new HttpCallback<HttpData<Boolean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        Boolean data1 = data.getData();
                        if (data.getData()) {
                            view_dot.setVisibility(View.VISIBLE);
                        } else {
                            view_dot.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 是否显示推荐有礼
     */
    public void getTaskStatus() {
        EasyHttp.get(this)
                .api(new GetTaskStatusApi())
                .request(new HttpCallback<HttpData<Boolean>>(this) {
                    @Override
                    public void onSucceed(HttpData<Boolean> data) { //返回true代表'推荐有礼'任务是开的，否则是关的
                        if (data.getData()) {
                            person_data_recommend.setVisibility(View.VISIBLE);
                        } else {
                            person_data_recommend.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private String mStatus = "1"; //入驻状态 1  待完善 2  待审核 3  审核成功 4  审核失败
    private int mContractStatus = 0; //签约状态 0, "待签约"，1, "签约中" 2, "签约成功" 3, "签约失败"

    public void getPersonData() {
        EasyHttp.get(this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        SPUtils.getInstance().put(AppConfig.CAREER_ID, data.getData().getCareerDirectionId());
                        mRefreshLayout.finishRefresh();
                        if (TextUtils.isEmpty(data.getData().getRealName())) {
                            tv_avatar.setText("朋友");
                            tv_name.setText("你好，新朋友");
                            tv_position.setText("尚未确认职业");
                        } else {
                            tv_avatar.setText(Utils.formatName(data.getData().getRealName()));
                            tv_name.setText(data.getData().getRealName());
                            tv_position.setText(data.getData().getCareerDirection());
                        }

                        tv_sign_num.setText(data.getData().getSignContractNum() + "次");
                        tv_profit_total.setText("¥" + Utils.formatMoney(data.getData().getProfitTotal()));

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

                        switch (mStatus) {
                            case "1":
                                person_data_dev.setRightText("待完善");
                                person_data_dev.setRightTextColor(getResources().getColor(R.color.color_hint_color));
                                break;
                            case "2":
                                person_data_dev.setRightText("待审核");
                                person_data_dev.setRightTextColor(getResources().getColor(R.color.color_FB8B39));
                                break;
                            case "3":
                                person_data_dev.setRightText("审核成功");
                                person_data_dev.setRightTextColor(getResources().getColor(R.color.color_hint_color));
                                break;
                            case "4":
                                person_data_dev.setRightText("审核失败");
                                person_data_dev.setRightTextColor(getResources().getColor(R.color.color_F5313D));
                                break;
                        }
                    }
                });
    }

    /**
     * 不同的状态点进服务协议的弹框
     */
    public void showServiceDialog() {
        switch (mStatus) {
            case "1":  // 入驻资料1  待完善 2  待审核 3  审核成功 4  审核失败
                new BaseDialog.Builder<>(getActivity())
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
                break;
            case "2": // 入驻资料 2  待审核
                new BaseDialog.Builder<>(getActivity())
                        .setContentView(R.layout.write_daily_delete_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title, "入驻资料尚未完成审核，请稍后再试。")
                        .setVisibility(R.id.btn_dialog_custom_cancel, View.GONE)
                        .setText(R.id.btn_dialog_custom_ok, "同意")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> dialog.dismiss())
                        .show();
                break;
            case "3": // 入驻资料3  审核成功
                if (mContractStatus == 0) {//待签约
                    startActivity(SignContactActivity.class);
                } else if (mContractStatus == 1) {//签约中
                    BrowserActivity.start(getActivity(), AppConfig.SIGN_CONTRACT_URL + "?developerId="
                            + SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID), "签约中");

                } else if (mContractStatus == 2) {//签约成功
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PDFViewActivity.class);
                    intent.putExtra("pdf_url", PDFUrl);
                    intent.putExtra("title", "合作协议");
                    startActivity(intent);
                } else {//签约失败
                    BrowserActivity.start(getActivity(), AppConfig.SIGN_CONTRACT_URL + "?developerId="
                            + SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID), "签约失败");
                }
                break;
            case "4": // 4  审核失败
                new BaseDialog.Builder<>(getActivity())
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
                break;

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

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        getPersonData();
//        getHasRead();
        initData();
    }
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

}
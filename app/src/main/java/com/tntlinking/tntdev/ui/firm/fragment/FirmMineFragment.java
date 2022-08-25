package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetFirmInfoApi;
import com.tntlinking.tntdev.http.api.GetSignContractPDFApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.AboutAppActivity;
import com.tntlinking.tntdev.ui.activity.BrowserPrivateActivity;
import com.tntlinking.tntdev.ui.activity.MessageListActivity;
import com.tntlinking.tntdev.ui.firm.activity.AccountManageActivity;
import com.tntlinking.tntdev.ui.firm.activity.AuditionMangeActivity;
import com.tntlinking.tntdev.ui.firm.activity.ChangeAdminActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmCertificationActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmManageActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMessageListActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmSettingActivity;
import com.tntlinking.tntdev.ui.firm.activity.MyCompanyActivity;
import com.tntlinking.tntdev.ui.firm.activity.TreatyOrderListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


/**
 * desc   : 企业端我的页面 Fragment
 */
public final class FirmMineFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {
    private SmartRefreshLayout mRefreshLayout;
    private SettingBar mPersonDataSetting;
    private SettingBar person_data_private;
    private SettingBar person_data_deal;
    private SettingBar person_data_dev;
    private SettingBar person_data_about;
    private SettingBar person_data_service;

    private LinearLayout ll_mine_audition;
    private LinearLayout ll_mine_contract;
    private LinearLayout ll_mine_account;
    private LinearLayout ll_mine_firm;

    private TitleBar title_bar;
    private ImageView iv_avatar;
    private TextView tv_name;
    private ImageView iv_message;

    private int mStatus = 1; //企业认证状态  1待审核 2审核中 3已认证 4审核失败
    private String mMobile;

    public static FirmMineFragment newInstance() {
        return new FirmMineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_mine_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {

        mPersonDataSetting = findViewById(R.id.person_data_setting);
        person_data_private = findViewById(R.id.person_data_private);
        person_data_deal = findViewById(R.id.person_data_deal);
        person_data_dev = findViewById(R.id.person_data_dev);
        person_data_about = findViewById(R.id.person_data_about);
        person_data_service = findViewById(R.id.person_data_service);

        ll_mine_audition = findViewById(R.id.ll_mine_audition);
        ll_mine_contract = findViewById(R.id.ll_mine_contract);
        ll_mine_account = findViewById(R.id.ll_mine_account);
        ll_mine_firm = findViewById(R.id.ll_mine_firm);


        ScrollView scroll = findViewById(R.id.scroll);
        title_bar = findViewById(R.id.title_bar);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        iv_message = findViewById(R.id.iv_message);

        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        setOnClickListener(iv_message, mPersonDataSetting, person_data_service, person_data_private, person_data_deal, person_data_dev,
                ll_mine_audition, ll_mine_contract, ll_mine_account, ll_mine_firm, person_data_about);


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
        getFirmInfo();
    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_message://消息
                startActivity(FirmMessageListActivity.class);
                break;
            case R.id.person_data_dev://身份切换
                intent.setClass(getActivity(), ChangeAdminActivity.class);
                intent.putExtra("isFirm", true);
                startActivity(intent);
                break;
            case R.id.person_data_service://企业认证
                if (mStatus == 2) {  // 2 审核中 不能离开更换公司
                    intent.setClass(getActivity(), MyCompanyActivity.class);
                    intent.putExtra("status", mStatus);
                    startActivity(intent);
                } else if (mStatus == 3) { // 3 审核通过 能离开和更换公司
                    startActivity(MyCompanyActivity.class);
                } else {
                    intent.setClass(getActivity(), FirmCertificationActivity.class);
                    intent.putExtra("mobile", mMobile);
                    startActivity(intent);
                }
                break;
            case R.id.ll_mine_audition://面试管理
                startActivity(AuditionMangeActivity.class);
                break;
            case R.id.ll_mine_contract://合约单
                startActivity(TreatyOrderListActivity.class);
                break;
            case R.id.ll_mine_account://账户管理
                startActivity(AccountManageActivity.class);
                break;
            case R.id.ll_mine_firm://企业管理
                startActivity(FirmManageActivity.class);
                break;
            case R.id.person_data_setting://账户设置
                startActivity(FirmSettingActivity.class);
                break;
            case R.id.person_data_private:// 隐私政策
                BrowserPrivateActivity.start(getActivity(), AppConfig.PRIVATE_URL);
                break;
            case R.id.person_data_deal:// 用户协议
                BrowserPrivateActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
                break;
            case R.id.person_data_about: // 关于天天数链开发者
//                startActivity(AboutAppActivity.class);

                intent.setClass(getActivity(), FirmCertificationActivity.class);
                intent.putExtra("mobile", mMobile);
                startActivity(intent);
                break;
        }

    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }


//    public void getData() {
//        EasyHttp.get(this)
//                .api(new GetDeveloperStatusApi())
//                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {
//
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
//                        SPUtils.getInstance().put(AppConfig.CAREER_ID, data.getData().getCareerDirectionId());
//                        mRefreshLayout.finishRefresh();
//                        if (TextUtils.isEmpty(data.getData().getRealName())) {
//                            tv_avatar.setText("朋友");
//                            tv_name.setText("你好，新朋友");
//
//                        } else {
//                            tv_avatar.setText(Utils.formatName(data.getData().getRealName()));
//                            tv_name.setText(data.getData().getRealName());
//
//                        }
//                    }
//                });
//    }


    private void getFirmInfo() {
        EasyHttp.get(this)
                .api(new GetFirmInfoApi())
                .request(new HttpCallback<HttpData<GetFirmInfoApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmInfoApi.Bean> data) {
                        mRefreshLayout.finishRefresh();
                        mStatus = data.getData().getStatus();
                        switch (data.getData().getStatus()) { //1待审核 2审核中 3已认证 4审核失败
                            case 1:
                                person_data_service.setRightText("去认证");
                                person_data_service.setRightTextColor(getResources().getColor(R.color.color_4850FF));
                                break;
                            case 2:
                                person_data_service.setRightText("审核中");
                                person_data_service.setRightTextColor(getResources().getColor(R.color.color_FB8B39));
                                break;
                            case 3:
                                person_data_service.setRightText("已认证");
                                person_data_service.setRightTextColor(getResources().getColor(R.color.color_7E89A3));
                                break;
                            case 4:
                                person_data_service.setRightText("不通过重新认证");
                                person_data_service.setRightTextColor(getResources().getColor(R.color.color_F5313D));
                                break;
                        }

                        if (TextUtils.isEmpty(data.getData().getRealName())) {
                            tv_name.setText("未命名");
                        } else {
                            tv_name.setText(data.getData().getRealName());
                        }
                        mMobile = data.getData().getMobile();
                        GlideUtils.loadCircle(getActivity(), data.getData().getAvatarUrl(), iv_avatar);
                    }
                });

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getFirmInfo();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

}
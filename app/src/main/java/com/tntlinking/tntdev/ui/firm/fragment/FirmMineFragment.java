package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
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
import com.tntlinking.tntdev.http.api.GetSignContractPDFApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.AboutAppActivity;
import com.tntlinking.tntdev.ui.activity.BrowserPrivateActivity;
import com.tntlinking.tntdev.ui.firm.activity.AccountManageActivity;
import com.tntlinking.tntdev.ui.firm.activity.AuditionMangeActivity;
import com.tntlinking.tntdev.ui.firm.activity.ChangeAdminActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmCertificationActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmManageActivity;
import com.tntlinking.tntdev.ui.firm.activity.TreatyOrderListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


/**
 * desc   : 首页 Fragment
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
    private TextView tv_avatar;
    private TextView tv_name;
    private ImageView iv_message;


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
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        iv_message = findViewById(R.id.iv_message);

        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        setOnClickListener(mPersonDataSetting,person_data_service, person_data_private, person_data_deal, person_data_dev,
                ll_mine_audition, ll_mine_contract, ll_mine_account, ll_mine_firm);


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
        getData();
    }


    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_data_dev://身份切换
                startActivity(ChangeAdminActivity.class);
                break;
            case R.id.person_data_service://企业认证
                startActivity(FirmCertificationActivity.class);
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
            case R.id.person_data_private:// 隐私政策
                BrowserPrivateActivity.start(getActivity(), AppConfig.PRIVATE_URL);
                break;
            case R.id.person_data_deal:// 用户协议
                BrowserPrivateActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
                break;
            case R.id.person_data_about: // 关于天天数链开发者
                startActivity(AboutAppActivity.class);
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

    private String mStatus = "1"; //入驻状态 1  待完善 2  待审核 3  审核成功 4  审核失败
    private int mContractStatus = 0; //签约状态 0, "待签约"，1, "签约中" 2, "签约成功" 3, "签约失败"

    public void getData() {
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

                        } else {
                            tv_avatar.setText(Utils.formatName(data.getData().getRealName()));
                            tv_name.setText(data.getData().getRealName());

                        }
                    }
                });
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
        getData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

}
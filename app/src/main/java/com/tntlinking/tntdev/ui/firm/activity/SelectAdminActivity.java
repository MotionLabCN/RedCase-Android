package com.tntlinking.tntdev.ui.firm.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetFirmInfoApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 角色切换页面
 */
public final class SelectAdminActivity extends AppActivity {
    private LinearLayout ll_firm;
    private LinearLayout ll_dev;


    @Override
    protected int getLayoutId() {
        return R.layout.select_admin_activity;
    }


    @Override
    protected void initView() {

        ll_firm = findViewById(R.id.ll_firm);
        ll_dev = findViewById(R.id.ll_dev);

        setOnClickListener(ll_firm, ll_dev);


    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override  //请求头loginRole ：Developer 开发者  Recruiter 企业者
    public void onClick(View view) {
        SPUtils.getInstance().put(AppConfig.HAS_SELECT_ROLE, true);
        switch (view.getId()) {
            case R.id.ll_firm://企业
//                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
//                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
//                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
//                startActivity(FirmMainActivity.class);
//                ActivityManager.getInstance().finishAllActivities();
                getFirmInfo();
                break;
            case R.id.ll_dev://开发者
//                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
//                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
//                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);
//                startActivity(MainActivity.class);
//                ActivityManager.getInstance().finishAllActivities();
                getDeveloperInfo();
                break;
        }

    }

    /**
     * 企业端
     */
    private void getFirmInfo() {
        EasyHttp.get(this)
                .api(new GetFirmInfoApi())
                .request(new HttpCallback<HttpData<GetFirmInfoApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmInfoApi.Bean> data) {
                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
                        SPUtils.getInstance().put(AppConfig.DEVELOP_MOBILE, data.getData().getMobile());

                        JPushInterface.setAlias(getActivity(), 1001, AppConfig.JPUSH_FIRM + data.getData().getId());
                        ActivityManager.getInstance().finishAllActivities();
                        startActivity(FirmMainActivity.class);
                    }
                });

    }

    /**
     * 开发者端
     */
    private void getDeveloperInfo() {
        EasyHttp.get(this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        // 1->待认证  2->待审核   3->审核成功 4->审核失败
                        SPUtils.getInstance().put(AppConfig.HAS_LOGIN, true);
                        SPUtils.getInstance().put(AppConfig.DEVELOP_MOBILE, data.getData().getMobile());
                        SPUtils.getInstance().put(AppConfig.DEVELOP_STATUS, data.getData().getStatus());
                        SPUtils.getInstance().put(AppConfig.DEVELOP_NAME, data.getData().getRealName());
                        SPUtils.getInstance().put(AppConfig.DEVELOPER_ID, data.getData().getId());

                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);

                        JPushInterface.setAlias(getActivity(), 1001, AppConfig.JPUSH_DEV + data.getData().getId());
                        ActivityManager.getInstance().finishAllActivities();
                        startActivity(MainActivity.class);

                    }
                });

    }


}
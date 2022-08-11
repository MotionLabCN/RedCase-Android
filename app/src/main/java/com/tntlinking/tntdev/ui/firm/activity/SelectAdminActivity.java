package com.tntlinking.tntdev.ui.firm.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.MainActivity;

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
        switch (view.getId()) {
            case R.id.ll_firm://企业
                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
                startActivity(FirmMainActivity.class);
                ActivityManager.getInstance().finishAllActivities();

                break;
            case R.id.ll_dev://开发者
                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);
                startActivity(MainActivity.class);
                ActivityManager.getInstance().finishAllActivities();

                break;
        }

    }


}
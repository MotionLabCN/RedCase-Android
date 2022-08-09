package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.http.EasyConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

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
            case R.id.ll_firm:
                EasyConfig.getInstance().addHeader("loginRole", "Developer");
                startActivity(FirmMainActivity.class);
                ActivityManager.getInstance().finishAllActivities();
                break;
            case R.id.ll_dev:
                EasyConfig.getInstance().addHeader("loginRole", "Recruiter");
                startActivity(FirmMainActivity.class);
                ActivityManager.getInstance().finishAllActivities();
                break;
        }

    }



}
package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
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
public final class ChangeAdminActivity extends AppActivity {
    private LinearLayout ll_firm;
    private LinearLayout ll_dev;

    private boolean isFirm;

    @Override
    protected int getLayoutId() {
        return R.layout.change_admin_activity;
    }

    @Override
    protected void initView() {

        ll_firm = findViewById(R.id.ll_firm);
        ll_dev = findViewById(R.id.ll_dev);
        setOnClickListener(ll_firm, ll_dev);
        ll_firm.setSelected(true);
        ll_dev.setSelected(false);
    }


    @Override
    protected void initData() {
        isFirm = getBoolean("isFirm");
        if (isFirm) {
            ll_firm.setSelected(true);
            ll_dev.setSelected(false);
        } else {
            ll_firm.setSelected(false);
            ll_dev.setSelected(true);
        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_firm:
                if (ll_firm.isSelected()) {
                    toast("目前已经是企业角色");
                } else {
                    checkDialog(true);
                }
                break;
            case R.id.ll_dev:
                if (ll_dev.isSelected()) {
                    toast("目前已经是开发者角色");
                } else {
                    checkDialog(false);
                }

                break;
        }

    }

    public void checkDialog(boolean isFirm) {
        String content = isFirm ? "切换成企业账户" : "切换成开发者账户";
        new BaseDialog.Builder<>(this)
                .setContentView(R.layout.check_order_status_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setText(R.id.tv_title, "身份切换")
                .setText(R.id.tv_content, content)
                .setText(R.id.btn_dialog_custom_cancel, "否")
                .setText(R.id.btn_dialog_custom_ok, "是")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                    if (isFirm) { // 企业账户
                        getFirmInfo();

                    } else {// 开发者账户

                        getDeveloperInfo();
                    }
                }).show();
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
                        ll_firm.setSelected(true);
                        ll_dev.setSelected(false);
                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
                        //删除极光推送别名
                        JPushInterface.deleteAlias(ChangeAdminActivity.this, 1001);

                        JPushInterface.setAlias(getActivity(), 1001, "ttsl_recruiter_" + data.getData().getId());
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

                        ll_firm.setSelected(false);
                        ll_dev.setSelected(true);
                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);
                        //删除极光推送别名
                        JPushInterface.deleteAlias(ChangeAdminActivity.this, 1001);

                        JPushInterface.setAlias(getActivity(), 1001, "ttsl_" + data.getData().getId());
                        ActivityManager.getInstance().finishAllActivities();
                        startActivity(MainActivity.class);

                    }
                });

    }

}
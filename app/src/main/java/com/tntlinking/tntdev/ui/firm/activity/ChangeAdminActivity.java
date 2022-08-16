package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
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
public final class ChangeAdminActivity extends AppActivity {
    private LinearLayout ll_firm;
    private LinearLayout ll_dev;


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

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_firm:
//                ll_firm.setSelected(true);
//                ll_dev.setSelected(false);
//                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
//                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
//                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
//                startActivity(FirmMainActivity.class);
//                ActivityManager.getInstance().finishAllActivities();
//                checkDialog(true);
                toast("目前已经是企业角色");
                break;
            case R.id.ll_dev:
//                ll_firm.setSelected(false);
//                ll_dev.setSelected(true);
//                SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
//                EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
//                SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);
//                startActivity(MainActivity.class);
//                ActivityManager.getInstance().finishAllActivities();
                checkDialog(false);
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
                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Recruiter");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Recruiter");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, true);
                        finish();
                    } else {// 开发者账户
                        ll_firm.setSelected(false);
                        ll_dev.setSelected(true);
                        SPUtils.getInstance().put(AppConfig.ACCESS_ROLE, "Developer");
                        EasyConfig.getInstance().addHeader(AppConfig.ACCESS_ROLE, "Developer");
                        SPUtils.getInstance().put(AppConfig.LOGIN_ROLE, false);
                        startActivity(MainActivity.class);
                        ActivityManager.getInstance().finishAllActivities();
                    }
                }).show();
    }

}
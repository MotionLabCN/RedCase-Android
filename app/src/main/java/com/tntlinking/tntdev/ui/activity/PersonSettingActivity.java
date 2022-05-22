package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.LogoutApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;

import androidx.appcompat.widget.AppCompatButton;

import cn.jpush.android.api.JPushInterface;

public final class PersonSettingActivity extends AppActivity {

    private AppCompatButton mOutLogin;
    private SettingBar mSettingPhone;
    private SettingBar mSettingCancelService;
    private static int sequence = 1;


    @Override
    protected int getLayoutId() {
        return R.layout.person_setting_activity;
    }

    @Override
    protected void initView() {

        mSettingPhone = findViewById(R.id.setting_phone);
        mSettingCancelService = findViewById(R.id.setting_cancel_service);
        mOutLogin = findViewById(R.id.btn_out_login);

        setOnClickListener(mSettingPhone, mSettingCancelService, mOutLogin);


    }

    @Override
    protected void initData() {


    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(AppConfig.DEVELOP_MOBILE, ""))) {
            String mobile = Utils.changPhoneNumber(SPUtils.getInstance().getString(AppConfig.DEVELOP_MOBILE));
            mSettingPhone.setRightText(mobile);
        }
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mSettingPhone) {
            startActivity(ChangePhoneActivity.class);
        } else if (view == mSettingCancelService) {
            startActivity(CancelServiceActivity.class);

        } else if (view == mOutLogin) {
            new BaseDialog.Builder<>(PersonSettingActivity.this)
                    .setContentView(R.layout.write_daily_delete_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "是否退出登录？")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        //删除极光推送别名
                        JPushInterface.deleteAlias(PersonSettingActivity.this, sequence++);
                        loginOut(dialog);
                    })
                    .show();

        }

    }


    public void loginOut(BaseDialog dialog) {
        // 退出登录
        EasyHttp.get(this)
                .api(new LogoutApi())
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        dialog.dismiss();
                        SPUtils.getInstance().clear();
                        EasyConfig.getInstance().removeHeader("Authorization");
                        startActivity(LoginActivity1.class);

                        // 进行内存优化，销毁除登录页之外的所有界面
                        ActivityManager.getInstance().finishAllActivities(LoginActivity1.class);
                    }
                });
    }
}
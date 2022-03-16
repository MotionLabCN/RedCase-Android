package com.tntlinking.tntdev.widget.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.LoginActivity2;
import com.umeng.commonsdk.debug.E;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMCustomInterface;
import com.umeng.umverify.view.UMAbstractPnsViewDelegate;
import com.umeng.umverify.view.UMAuthRegisterViewConfig;
import com.umeng.umverify.view.UMAuthRegisterXmlConfig;
import com.umeng.umverify.view.UMAuthUIConfig;

public class DialogBottomConfig extends BaseUIConfig {

    public DialogBottomConfig(Activity activity, UMVerifyHelper authHelper) {
        super(activity, authHelper);
    }

    @Override
    public void configAuthPage() {
        mAuthHelper.removeAuthRegisterXmlConfig();
        mAuthHelper.removeAuthRegisterViewConfig();
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        updateScreenSize(authPageOrientation);
        int dialogHeight = (int) (mScreenHeightDp * 0.5f);
        //sdk默认控件的区域是marginTop50dp
        int designHeight = dialogHeight - 50;
        int unit = designHeight / 10;
        String carrierName = mAuthHelper.getCurrentCarrierName();
        if (carrierName.equals("CMCC")){
            carrierName = "中国移动";
        }else if (carrierName.equals("CUCC")){
            carrierName = "中国联通";
        }else if (carrierName.equals("CTCC")){
            carrierName = "中国电信 ";
        }
        int logBtnHeight = (int) (unit * 1.2);
        mAuthHelper.addAuthRegistViewConfig("switch_msg", new UMAuthRegisterViewConfig.Builder()
                .setView(initSwitchView(170))
                .setRootViewId(UMAuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new UMCustomInterface() {
                    @Override
                    public void onClick(Context context) {
                        Intent pIntent = new Intent(mActivity, LoginActivity2.class);
                        mActivity.startActivity(pIntent);
                        mAuthHelper.quitLoginPage();
                    }
                })
                .build());
        mAuthHelper.addAuthRegisterXmlConfig(new UMAuthRegisterXmlConfig.Builder()
                .setLayout(R.layout.custom_port_dialog_action_bar, new UMAbstractPnsViewDelegate() {
                    @Override
                    public void onViewCreated(View view) {
                        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAuthHelper.quitLoginPage();
//                                mActivity.finish();
                            }
                        });
                    }
                })
                .build());

        mAuthHelper.setAuthUIConfig(new UMAuthUIConfig.Builder()
                .setPrivacyBefore("我已阅读并同意")
                .setAppPrivacyOne("《法律声明及隐私权限》", AppConfig.PRIVATE_URL)
                .setAppPrivacyTwo("《用户协议》", AppConfig.AGREEMENT_URL)
                .setAppPrivacyColor(Color.parseColor("#7E89A3"), Color.parseColor("#002E00"))
                .setProtocolGravity(Gravity.LEFT)
                .setWebViewStatusBarColor(Color.parseColor("#4850FF"))

                .setWebNavColor(Color.parseColor("#4850FF"))
                .setSwitchAccHidden(true)
                .setPrivacyState(false)
                .setCheckboxHidden(false)
                .setCheckedImgDrawable(mContext.getResources().getDrawable(R.drawable.icon_select))
                .setUncheckedImgDrawable(mContext.getResources().getDrawable(R.drawable.icon_no_select))


                .setLogoImgPath("mytel_app_launcher")
                .setLogoOffsetY(0)
                .setLogoWidth(42)
                .setLogoHeight(42)

                .setNumFieldOffsetY(10)
                .setNumberSize(32)
                .setNumberColor(Color.parseColor("#000000"))

                .setSloganText(carrierName+"提供认证服务")
                .setSloganOffsetY(unit * 2)
                .setSloganTextSizeDp(12)

                .setLogBtnText("本机号码一键登录")
                .setLogBtnTextColor(Color.WHITE)
                .setLogBtnBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_circle_selector))

                .setLogBtnOffsetY(100)
                .setLogBtnHeight(48)
                .setLogBtnMarginLeftAndRight(16)
                .setLogBtnTextSizeDp(15)
                .setLogBtnBackgroundPath("login_btn_bg")

                .setPageBackgroundPath("dialog_page_background")
                .setAuthPageActIn("in_activity", "out_activity")
                .setAuthPageActOut("in_activity", "out_activity")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setDialogHeight(dialogHeight)
                .setDialogBottom(true)
                .setScreenOrientation(authPageOrientation)
                .create());

    }
}

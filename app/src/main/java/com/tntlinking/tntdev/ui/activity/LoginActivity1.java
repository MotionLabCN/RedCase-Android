package com.tntlinking.tntdev.ui.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.BuildConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.OneClickLoginApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.dialog.LoginDialog;
import com.tntlinking.tntdev.widget.CustomVideoView;
import com.tntlinking.tntdev.widget.config.AuthPageConfig;
import com.tntlinking.tntdev.widget.config.BaseUIConfig;
import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMPreLoginResultListener;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

public final class LoginActivity1 extends AppActivity {

    private AppCompatCheckBox cb_deal;
    private boolean hasChecked = false;


    private ImageView mLogoView;


    private AppCompatButton mCommitView;
    private CustomVideoView customVideoView;

    private UMVerifyHelper umVerifyHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity1;
    }

    @Override
    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);
        mCommitView = findViewById(R.id.btn_login_commit);
        cb_deal = findViewById(R.id.cb_deal);
        customVideoView = findViewById(R.id.customVideoView);

        setOnClickListener(mCommitView);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_video);
        customVideoView.playVideo(uri);
        cb_deal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hasChecked = isChecked;
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        if (!BuildConfig.DEBUG) {
            sdkInit();
            mUIConfig = BaseUIConfig.init(this, umVerifyHelper);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean mBoolean = SPUtils.getInstance().getBoolean(AppConfig.DEAL_DIALOG, false);
                if (!mBoolean){
                    showDealDialog();
                }
            }
        }, 1000);

    }

    private AuthPageConfig mUIConfig;
    private UMTokenResultListener mCheckListener;
    private UMTokenResultListener mTokenResultListener;

    public void sdkInit() {
        mCheckListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                try {
                    Log.i("TAG", "checkEnvAvailable：" + s);
                    UMTokenRet pTokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_ERROR_ENV_CHECK_SUCCESS.equals(pTokenRet.getCode())) {
                        accelerateLoginPage(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
//                sdkAvailable = false;
                Log.e("TAG", "checkEnvAvailable：" + s);
                //终端环境检查失败之后 跳转到其他号码校验方式
            }
        };
        umVerifyHelper = UMVerifyHelper.getInstance(this, mCheckListener);
        umVerifyHelper.setAuthSDKInfo(AppConfig.APP_SECRET);
        umVerifyHelper.checkEnvAvailable(UMVerifyHelper.SERVICE_TYPE_LOGIN);
    }

    public void accelerateLoginPage(int timeout) {
        umVerifyHelper.accelerateLoginPage(timeout, new UMPreLoginResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                Log.e("TAG", "预取号成功: " + s);
            }

            @Override
            public void onTokenFailed(String s, String s1) {
                Log.e("TAG", "预取号失败：" + ", " + s1);
            }
        });
    }

    /**
     * 拉起授权页
     *
     * @param timeout 超时时间
     */
    public void getLoginToken(int timeout) {
        mUIConfig.configAuthPage();
        mTokenResultListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "唤起授权页成功：" + s);
                    }

                    if (UMResultCode.CODE_GET_TOKEN_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "获取token成功：" + s);
//                        token = tokenRet.getToken();
//                        getResultWithToken(token);
                        oneClickLogin(tokenRet.getToken());
                        mUIConfig.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onTokenFailed(String s) {
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    toast(tokenRet.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                umVerifyHelper.quitLoginPage();
                mUIConfig.release();
                //终端环境检查失败之后 跳转到其他号码校验方式
                startActivity(LoginActivity2.class);
            }
        };
        umVerifyHelper.setAuthListener(mTokenResultListener);
        umVerifyHelper.getLoginToken(this, timeout);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customVideoView != null) {
            Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.guide_video);
            customVideoView.playVideo(uri);
        }
    }

    @Override
    public void onRightClick(View view) {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCommitView) {
//            if (!hasChecked) {
//                toast("你还没有勾选协议");
//                return;
//            }

            if (BuildConfig.DEBUG) {
                startActivity(LoginActivity2.class);
            } else {
                getLoginToken(5000);
            }


        }
    }


    public void oneClickLogin(String token) {
        EasyHttp.post(this)
                .api(new OneClickLoginApi()
                        .setAuthorization(AppConfig.LOGIN_AUTHORIZATION)
                        .setLoginChannel("Developer_APP")
                        .setYouVerifyToken(token))
                .request(new HttpCallback<HttpData<OneClickLoginApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<OneClickLoginApi.Bean> data) {

                        umVerifyHelper.quitLoginPage();
                        SPUtils.getInstance().put(AppConfig.ACCESS_TOKEN, "Bearer " + data.getData().getAccessToken());
                        EasyConfig.getInstance().addHeader("Authorization", "Bearer " + data.getData().getAccessToken());

                        toLogin();
                    }
                });
    }


    private void toLogin() {
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


//                        SPUtils.getInstance().getBoolean(AppConfig.GUIDE_VIEW, true);
//                        startActivity(LoginActivityView.class);
//                        SPUtils.getInstance().put(AppConfig.GUIDE_VIEW, false);
//                        ActivityManager.getInstance().finishAllActivities();

                        startActivity(HomeStatusActivity.class);
                        ActivityManager.getInstance().finishAllActivities();
                    }
                });

    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDealDialog() {
        //进入页面就弹隐私条款，同意 后面的隐私条款默认被勾选
        BaseDialog.Builder<?> builder = new BaseDialog.Builder<>(LoginActivity1.this)
                .setContentView(R.layout.write_daily_delete_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setText(R.id.tv_title, "spannableStringBuilder")
                .setText(R.id.btn_dialog_custom_ok, "同意")
                .setText(R.id.btn_dialog_custom_cancel, "不同意")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> {
                    dialog.dismiss();
                    ActivityManager.getInstance().finishAllActivities();//退出程序
                })
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                    dialog.dismiss();
                    SPUtils.getInstance().put(AppConfig.DEAL_DIALOG, true);
                });

        TextView viewById = builder.findViewById(R.id.tv_title);
        SpanUtils.with(viewById).append("我已阅读并同意").setForegroundColor(getColor(R.color.color_text_color))
                .append("《隐私权限》").setClickSpan(new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.start(getActivity(), AppConfig.PRIVATE_URL);
            }
        }).setForegroundColor(getColor(R.color.color_text_color)).append("和")
                .setForegroundColor(getColor(R.color.color_text_color)).append("《用户协议》").setClickSpan(new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
            }
        }).setForegroundColor(getColor(R.color.color_text_color)).append("的全部条款，同意后可开始使用我们的服务。")
                .setForegroundColor(getColor(R.color.color_text_color)).create();

        builder.show();
    }
}
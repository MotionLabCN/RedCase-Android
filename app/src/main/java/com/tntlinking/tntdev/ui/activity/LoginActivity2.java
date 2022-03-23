package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.BuildConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetCodeApi;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.LoginApi2;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.manager.InputTextManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * desc   : 登录界面
 */
public final class LoginActivity2 extends AppActivity implements TextView.OnEditorActionListener {

    private EditText mPhoneView;
    private EditText mPasswordView;
    private AppCompatButton mCommitView;
    private CountdownView mCountdownView;
    private AppCompatCheckBox cb_deal;
    private TextView tv_deal;
    private boolean hasChecked = false;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCommitView = findViewById(R.id.btn_login_commit);
        mCountdownView = findViewById(R.id.cv_password_forget_countdown);
        cb_deal = findViewById(R.id.cb_deal);
        tv_deal = findViewById(R.id.tv_deal);

        setOnClickListener(mCountdownView, mCommitView);

        mPasswordView.setOnEditorActionListener(this);
        //我已阅读并同意 《隐私权限》和《用户协议》
        SpanUtils.with(tv_deal).append("我已阅读并同意").setForegroundColor(getColor(R.color.color_hint_color)).
                append("《隐私权限》").setClickSpan(new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.start(getActivity(), AppConfig.PRIVATE_URL);
            }
        }).setForegroundColor(getColor(R.color.color_text_color)).append("和")
                .setForegroundColor(getColor(R.color.color_hint_color)).append("《用户协议》").setClickSpan(new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {
                BrowserActivity.start(getActivity(), AppConfig.AGREEMENT_URL);
            }
        })
                .setForegroundColor(getColor(R.color.color_text_color)).create();

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();

        cb_deal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hasChecked = isChecked;
        });
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCountdownView) {
            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));

                toast(R.string.common_phone_input_error);
                return;
            }

            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());

            // 获取验证码
            EasyHttp.get(this)
                    .api(new GetCodeApi()
                            .setPhone(mPhoneView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(R.string.common_code_send_hint);
                            mCountdownView.start();
                        }
                    });


        } else if (view == mCommitView) {

            if (mPasswordView.getText().toString().length() != 6) {
                mPasswordView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                toast("验证码错误");
                return;
            }

            if (!hasChecked) {
                toast("你还没有勾选协议");
                return;
            }

            EasyHttp.post(this)
                    .api(new LoginApi2()
                            .setAuthorization(AppConfig.LOGIN_AUTHORIZATION)
                            .setGrant_type("sms_code")
                            .setScope("all")
                            .setLoginChannel("Developer_APP")
                            .setSmsCode(mPasswordView.getText().toString())
                            .setMobile(mPhoneView.getText().toString()))
                    .request(new HttpCallback<HttpData<LoginApi2.Bean>>(this) {

                        @Override
                        public void onSucceed(HttpData<LoginApi2.Bean> data) {
                            SPUtils.getInstance().put(AppConfig.ACCESS_TOKEN, "Bearer " + data.getData().getAccessToken());
                            EasyConfig.getInstance().addHeader("Authorization", "Bearer " + data.getData().getAccessToken());

                            toLogin();

                        }
                    });

        }

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


    /**
     * {@link TextView.OnEditorActionListener}
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView.isEnabled()) {
            // 模拟点击登录按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}
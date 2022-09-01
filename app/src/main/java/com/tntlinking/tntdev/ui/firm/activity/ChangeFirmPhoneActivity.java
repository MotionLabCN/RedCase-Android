package com.tntlinking.tntdev.ui.firm.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ChangeFirmPhoneApi;
import com.tntlinking.tntdev.http.api.ChangePhoneApi;
import com.tntlinking.tntdev.http.api.GetCodeApi;
import com.tntlinking.tntdev.http.api.GetCodeNewApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.InputTextManager;
import com.tntlinking.tntdev.other.AppConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;


public final class ChangeFirmPhoneActivity extends AppActivity
        implements TextView.OnEditorActionListener {


    private EditText mPhoneView;
    private EditText mPasswordView;
    private AppCompatButton mCommitView;
    private CountdownView mCountdownView;


    @Override
    protected int getLayoutId() {
        return R.layout.change_phone_activity;
    }

    @Override
    protected void initView() {

        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCountdownView = findViewById(R.id.cv_password_forget_countdown);
        mCommitView = findViewById(R.id.btn_commit);


        setOnClickListener(mCountdownView, mCommitView);

        mPasswordView.setOnEditorActionListener(this);

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();
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
                    .api(new GetCodeNewApi().setMobile(mPhoneView.getText().toString()))
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
            EasyHttp.get(this)
                    .api(new ChangeFirmPhoneApi()
                            .setMobile(mPhoneView.getText().toString())
                            .setCode(mPasswordView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {

                            SPUtils.getInstance().put(AppConfig.DEVELOP_MOBILE, mPhoneView.getText().toString());

                            finish();
                        }

                    });


        }

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
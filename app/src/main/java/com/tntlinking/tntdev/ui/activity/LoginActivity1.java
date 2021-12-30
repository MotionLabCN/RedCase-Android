package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

public final class LoginActivity1 extends AppActivity {

    private AppCompatCheckBox cb_deal;
    private boolean hasChecked = false;


    private ImageView mLogoView;


    private AppCompatButton mCommitView;


    @Override
    protected int getLayoutId() {
        return R.layout.login_activity1;
    }

    @Override
    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);
        mCommitView = findViewById(R.id.btn_login_commit);
        cb_deal = findViewById(R.id.cb_deal);

        setOnClickListener(mCommitView);


        cb_deal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hasChecked = isChecked;
        });


    }

    @Override
    protected void initData() {


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
            startActivity(LoginActivity2.class);

        }


    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}
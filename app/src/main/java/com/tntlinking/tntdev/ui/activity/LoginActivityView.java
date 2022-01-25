package com.tntlinking.tntdev.ui.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

import androidx.appcompat.widget.AppCompatButton;


public final class LoginActivityView extends AppActivity {
    private ImageView mLogoView;
    private AppCompatButton btn_commit_1;
    private AppCompatButton btn_commit_2;
    private AppCompatButton btn_commit_3;
    private LinearLayout mLoginView1;
    private LinearLayout mLoginView2;
    private LinearLayout mLoginView3;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_view;
    }

    @Override
    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);

        btn_commit_1 = findViewById(R.id.btn_commit_1);
        btn_commit_2 = findViewById(R.id.btn_commit_2);
        btn_commit_3 = findViewById(R.id.btn_commit_3);
        mLoginView1 = findViewById(R.id.ll_login_view_1);
        mLoginView2 = findViewById(R.id.ll_login_view_2);
        mLoginView3 = findViewById(R.id.ll_login_view_3);


        setOnClickListener(btn_commit_1, btn_commit_2, btn_commit_3);

    }

    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_commit_1) {
            btn_commit_1.setVisibility(View.GONE);
            mLoginView1.setVisibility(View.GONE);
            mLoginView2.setVisibility(View.VISIBLE);
            btn_commit_2.setVisibility(View.VISIBLE);
            mLogoView.setImageResource(R.drawable.bg_view_2);
        } else if (view == btn_commit_2) {
            btn_commit_2.setVisibility(View.GONE);
            mLoginView2.setVisibility(View.GONE);
            mLoginView3.setVisibility(View.VISIBLE);
            btn_commit_3.setVisibility(View.VISIBLE);
            mLogoView.setImageResource(R.drawable.bg_view_3);
        } else if (view == btn_commit_3) {
            startActivity(HomeStatusActivity.class);

            ActivityManager.getInstance().finishAllActivities(BaseInfoActivity1.class);
        }

    }

}
package com.tntlinking.tntdev.ui.activity;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.widget.CustomVideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

public final class LoginActivity1 extends AppActivity {

    private AppCompatCheckBox cb_deal;
    private boolean hasChecked = false;


    private ImageView mLogoView;


    private AppCompatButton mCommitView;
    private CustomVideoView customVideoView;


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

    @Override
    protected void initData() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
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
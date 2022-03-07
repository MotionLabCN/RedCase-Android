package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

import androidx.annotation.NonNull;

public final class PhotoSkillsActivity extends AppActivity {




    @Override
    protected int getLayoutId() {
        return R.layout.photo_skills_activity;
    }

    @Override
    protected void initView() {


        ;

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

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}
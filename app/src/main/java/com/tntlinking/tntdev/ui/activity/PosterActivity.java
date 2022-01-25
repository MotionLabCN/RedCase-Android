package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户审核失败页面
 */
public final class PosterActivity extends AppActivity {

    private AppCompatButton btn_next;

    @Override
    protected int getLayoutId() {
        return R.layout.poster_activity;
    }

    @Override
    protected void initView() {
        btn_next = findViewById(R.id.btn_next);


    }

    @Override
    protected void initData() {

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
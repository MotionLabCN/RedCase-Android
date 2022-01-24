package com.tntlinking.tntdev.ui.activity;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户审核失败页面
 */
public final class SaveQRActivity extends AppActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.save_qr_activity;
    }

    @Override
    protected void initView() {


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
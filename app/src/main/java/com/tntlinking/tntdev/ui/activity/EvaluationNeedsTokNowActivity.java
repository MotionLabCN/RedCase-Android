package com.tntlinking.tntdev.ui.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

public final class EvaluationNeedsTokNowActivity extends AppActivity {
    private AppCompatButton btn_out_evaluating;

    @Override
    protected int getLayoutId() {
        return R.layout.evaluationneedstoknow_activity;
    }

    @Override
    protected void initView() {
        btn_out_evaluating = findViewById(R.id.btn_out_evaluating);

    }

    @Override
    protected void initData() {
        setOnClickListener(btn_out_evaluating);

    }
    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_out_evaluating) { // 测评岗位页面
            startActivity(EvaluationListActivity.class);
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

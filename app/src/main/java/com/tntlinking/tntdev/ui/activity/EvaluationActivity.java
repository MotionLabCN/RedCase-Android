package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;


public final class EvaluationActivity extends AppActivity {

    private AppCompatButton btn_out_improve_data;

    @Override
    protected int getLayoutId() {
        return R.layout.ecaluation_activity1;
    }

    @Override
    protected void initView() {

        btn_out_improve_data = findViewById(R.id.btn_out_improve_data);


    }

    @Override
    protected void initData() {
        setOnClickListener(btn_out_improve_data);
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_out_improve_data) { // 入驻资料
            startActivity(EnterDeveloperActivity.class);
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
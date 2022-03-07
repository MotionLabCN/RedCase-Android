package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 *
 */
public final class SignStatusActivity extends AppActivity {


    private ImageView iv_sign_status;
    private TextView tv_sign_status;
    private AppCompatButton btn_next;

    @Override
    protected int getLayoutId() {
        return R.layout.sign_status_activity;
    }

    @Override
    protected void initView() {
        iv_sign_status = findViewById(R.id.iv_sign_status);
        tv_sign_status = findViewById(R.id.tv_sign_status);
        btn_next = findViewById(R.id.btn_next);


        setOnClickListener(btn_next);
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
        if (view == btn_next) {

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
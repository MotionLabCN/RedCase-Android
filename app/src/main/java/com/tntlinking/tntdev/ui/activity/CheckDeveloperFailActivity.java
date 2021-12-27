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
public final class CheckDeveloperFailActivity extends AppActivity {
    private AppCompatButton btn_commit;


    @Override
    protected int getLayoutId() {
        return R.layout.check_developer_fail_activity;
    }

    @Override
    protected void initView() {
        btn_commit = findViewById(R.id.btn_commit);


        setOnClickListener(btn_commit);
    }

    @Override
    protected void initData() {


    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_commit) {
            startActivity(BaseInfoActivity1.class);
            finish();
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
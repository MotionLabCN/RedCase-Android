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
public final class SaveQRActivity extends AppActivity {
    private TitleBar title_bar;
    private LinearLayout ll_commit_tips;
    private TextView tv_tips_1;
    private TextView tv_tips_2;

    @Override
    protected int getLayoutId() {
        return R.layout.save_qr_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        ll_commit_tips = findViewById(R.id.ll_commit_tips);
        tv_tips_1 = findViewById(R.id.tv_tips_1);
        tv_tips_2 = findViewById(R.id.tv_tips_2);

    }

    @Override
    protected void initData() {
        String contact = getString("contact");
        if (TextUtils.isEmpty(contact)) {

        } else {
            title_bar.setTitle("联系顾问");
            ll_commit_tips.setVisibility(View.GONE);
            tv_tips_2.setVisibility(View.GONE);
            tv_tips_1.setText("添加顾问");
        }

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
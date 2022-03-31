package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.bar.TitleBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 注销账户页面
 */
public final class SignStatusActivity extends AppActivity {


    private TitleBar title_bar;
    private ImageView iv_sign_status;
    private TextView tv_sign_status;
    private AppCompatButton btn_next;
    private String status = "";

    @Override
    protected int getLayoutId() {
        return R.layout.sign_status_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        iv_sign_status = findViewById(R.id.iv_sign_status);
        tv_sign_status = findViewById(R.id.tv_sign_status);
        btn_next = findViewById(R.id.btn_next);


        setOnClickListener(btn_next);
    }

    @Override
    protected void initData() {
        status = getString("status");
        if (status.equals("success")) {
            title_bar.setTitle("注销成功");
            tv_sign_status.setText("注销成功");
            btn_next.setText("重新开始");
            iv_sign_status.setImageResource(R.drawable.icon_cancel_success);
        } else {
            title_bar.setTitle("注销失败");
            tv_sign_status.setText("注销失败");
            btn_next.setText("联系顾问");
            iv_sign_status.setImageResource(R.drawable.icon_cancel_fail);
        }

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_next) {
            if (status.equals("success")) {  //重新开始
                SPUtils.getInstance().clear();
                startActivity(LoginActivity1.class);

                // 进行内存优化，销毁除登录页之外的所有界面
                ActivityManager.getInstance().finishAllActivities(LoginActivity1.class);
            } else {// 联系顾问
                Intent intent = new Intent();
                intent.setClass(this, SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
            }

        }

    }

}
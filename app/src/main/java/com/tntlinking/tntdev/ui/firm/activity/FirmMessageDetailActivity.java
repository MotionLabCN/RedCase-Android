package com.tntlinking.tntdev.ui.firm.activity;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

import androidx.annotation.RequiresApi;

/**
 * 冻结结果页面
 */
public final class FirmMessageDetailActivity extends AppActivity {

    private TextView tv_title;
    private TextView tv_tips;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_message_detail_activity;
    }

    @Override
    protected void initView() {

        tv_title = findViewById(R.id.tv_title);
        tv_tips = findViewById(R.id.tv_tips);

        String message = getString("message");
        if (!TextUtils.isEmpty(message)) {
            tv_tips.setText(message);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

    }

}
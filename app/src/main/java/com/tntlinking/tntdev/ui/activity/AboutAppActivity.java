package com.tntlinking.tntdev.ui.activity;

import android.widget.TextView;
import com.tntlinking.tntdev.BuildConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;

public final class AboutAppActivity extends AppActivity {
    private TextView tv_version;
    @Override
    protected int getLayoutId() {
        return R.layout.about_app_activity;
    }

    @Override
    protected void initView() {
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText("V"+ BuildConfig.VERSION_NAME);
    }

    @Override
    protected void initData() {}
}
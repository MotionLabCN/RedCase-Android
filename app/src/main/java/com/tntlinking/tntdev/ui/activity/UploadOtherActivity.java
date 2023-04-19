package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.glide.GlideApp;

public final class UploadOtherActivity extends AppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.upload_other_activity;
    }

    @Override
    protected void initView() {
        ImageView iv_show = findViewById(R.id.iv_show);

        GlideApp.with(this)
                .load(R.drawable.icon_resume)
                .into(iv_show);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mobile_upload:
                break;
            case R.id.ll_other_uploads:

                break;
        }
    }



}

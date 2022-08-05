package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;


import androidx.appcompat.widget.AppCompatButton;

/**
 * 推荐详情页面
 */
public final class FirmInfoActivity extends AppActivity {
    private LinearLayout ll_firm;
    private LinearLayout ll_dev;
    private AppCompatButton btn_modify;
    private AppCompatButton btn_contact;


    @Override
    protected int getLayoutId() {
        return R.layout.firm_info_activity;
    }


    @Override
    protected void initView() {

        btn_modify = findViewById(R.id.btn_modify);
        btn_contact = findViewById(R.id.btn_contact);

        setOnClickListener(btn_modify, btn_contact);


    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_modify:
                startActivity(SendPositionActivity.class);
                break;
            case R.id.btn_contact:

                new BaseDialog.Builder<>(this)
                        .setContentView(R.layout.bottom_dialog)
                        .setAnimStyle(BaseDialog.ANIM_BOTTOM)
                        //.setText(id, "我是预设置的文本")
                        .setOnClickListener(R.id.iv_close, (dialog, views) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_commit, (dialog, views) -> {

                        }).show();
                break;
        }

    }

}
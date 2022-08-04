package com.tntlinking.tntdev.ui.firm.activity;


import android.os.Build;
import android.view.View;

import com.hjq.widget.layout.SettingBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 企业认证页面
 */
public final class FirmCertificationActivity extends AppActivity {




    private AppCompatButton btn_commit;


    @Override
    protected int getLayoutId() {
        return R.layout.firm_certification_activity;
    }

    @Override
    protected void initView() {

        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(btn_commit);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_specialisations:

                break;

            case R.id.btn_commit:

                break;

        }

    }


}
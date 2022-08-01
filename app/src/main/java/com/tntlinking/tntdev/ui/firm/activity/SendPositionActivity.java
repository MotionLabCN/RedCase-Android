package com.tntlinking.tntdev.ui.firm.activity;


import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.SettingBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import java.util.List;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户信息填写页面2
 */
public final class SendPositionActivity extends AppActivity {

    private SettingBar info_specialisations;
    private SettingBar info_work_experience;


    private AppCompatButton btn_commit;



    @Override
    protected int getLayoutId() {
        return R.layout.send_position_activity;
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
package com.tntlinking.tntdev.ui.firm.activity;

import android.os.Build;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 发布职位信息
 */
public final class SendPositionSuccessActivity extends AppActivity {
    private TitleBar title_bar;
    private AppCompatButton btn_commit;

    @Override
    protected int getLayoutId() {
        return R.layout.send_position_success_activity;
    }

    @Override
    protected void initView() {

        title_bar = findViewById(R.id.title_bar);
        btn_commit = findViewById(R.id.btn_commit);


        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.getInstance().finishAllActivities();
                startActivity(FirmMainActivity.class);

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

    }


}
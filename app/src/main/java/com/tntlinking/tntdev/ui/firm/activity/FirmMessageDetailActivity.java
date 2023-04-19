package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.manager.ActivityManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 冻结结果页面
 */
public final class FirmMessageDetailActivity extends AppActivity {

    private TextView tv_title;
    private TextView tv_tips;
    private AppCompatButton btn_commit;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_message_detail_activity;
    }

    @Override
    protected void initView() {

        tv_title = findViewById(R.id.tv_title);
        tv_tips = findViewById(R.id.tv_tips);
        btn_commit = findViewById(R.id.btn_commit);

        String message = getString("message");
        int typeId = getInt("typeId");
        if (!TextUtils.isEmpty(message)) {
            tv_tips.setText(message);
        }

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirmMessageDetailActivity.this, SendPositionActivity.class);
                intent.putExtra("typeId", typeId);
                startActivity(intent);
                finish();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

    }

}
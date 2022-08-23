package com.tntlinking.tntdev.ui.firm.activity;

import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import androidx.annotation.RequiresApi;

/**
 * 冻结结果页面
 */
public final class FreezeMoneyStatusActivity extends AppActivity {
    private ImageView iv_status_icon;
    private TextView tv_status;
    private TextView tv_status_tips;

    @Override
    protected int getLayoutId() {
        return R.layout.freeze_money_status_activity;
    }

    @Override
    protected void initView() {

        iv_status_icon = findViewById(R.id.iv_status_icon);
        tv_status = findViewById(R.id.tv_status);
        tv_status_tips = findViewById(R.id.tv_status_tips);

        String freezeStatus = getString("freezeStatus");
        if (!TextUtils.isEmpty(freezeStatus)) {
            if (freezeStatus.equals("余额不足")) {
                iv_status_icon.setImageResource(R.drawable.icon_freeze_fail);
                tv_status.setText("冻结失败");
                tv_status_tips.setText("余额不足");
            } else {
                iv_status_icon.setImageResource(R.drawable.icon_freeze_success);
                tv_status.setText("冻结成功");
                tv_status_tips.setText("¥" + freezeStatus);
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

    }


}
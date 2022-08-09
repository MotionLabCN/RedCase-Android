package com.tntlinking.tntdev.ui.firm.activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

/**
 * 角色切换页面
 */
public final class ChangeAdminActivity extends AppActivity {
    private LinearLayout ll_firm;
    private LinearLayout ll_dev;


    @Override
    protected int getLayoutId() {
        return R.layout.change_admin_activity;
    }


    @Override
    protected void initView() {

        ll_firm = findViewById(R.id.ll_firm);
        ll_dev = findViewById(R.id.ll_dev);

        setOnClickListener(ll_firm, ll_dev);

        ll_firm.setSelected(true);
        ll_dev.setSelected(false);
    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_firm:
                ll_firm.setSelected(true);
                ll_dev.setSelected(false);

                break;
            case R.id.ll_dev:
                ll_firm.setSelected(false);
                ll_dev.setSelected(true);

                break;
        }

    }

}
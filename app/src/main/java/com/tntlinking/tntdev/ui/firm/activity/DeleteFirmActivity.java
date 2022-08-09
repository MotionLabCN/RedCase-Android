package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CancellationApi;
import com.tntlinking.tntdev.http.api.GetCancellationApi;
import com.tntlinking.tntdev.http.api.GetFirmConditionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.ui.activity.SignStatusActivity;
import com.tntlinking.tntdev.ui.adapter.CancelServiceAdapter;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

public final class DeleteFirmActivity extends AppActivity {

    private ListView list_view;
    private TextView tv_tips;
    private AppCompatButton mCommitView;
    private CancelServiceAdapter mAdapter;
    private LinearLayout ll_task_empty;

    @Override
    protected int getLayoutId() {
        return R.layout.delete_firm_activity;
    }

    @Override
    protected void initView() {

        list_view = findViewById(R.id.list_view);
        tv_tips = findViewById(R.id.tv_tips);
        ll_task_empty = findViewById(R.id.ll_task_empty);
        mCommitView = findViewById(R.id.btn_commit);


        setOnClickListener(mCommitView);


    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initData() {
        getCancelDescription();
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCommitView) {
            new BaseDialog.Builder<>(DeleteFirmActivity.this)
                    .setContentView(R.layout.write_daily_delete_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "确定注销账户吗？")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        cancellation(dialog);
                    })
                    .show();

        }

    }

    private boolean isFlag = true;

    private void getCancelDescription() {
        EasyHttp.get(this)
                .api(new GetFirmConditionApi())
                .request(new HttpCallback<HttpData<List<GetCancellationApi.ListBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetCancellationApi.ListBean>> data) {
                        if (data.getData().size() != 0) {
                            list_view.setVisibility(View.VISIBLE);
                            ll_task_empty.setVisibility(View.GONE);

                            mAdapter = new CancelServiceAdapter(DeleteFirmActivity.this, data.getData());
                            list_view.setAdapter(mAdapter);

                            for (GetCancellationApi.ListBean index : data.getData()) {
                                if (!index.isFlag()) {
                                    isFlag = false;
                                    break;
                                }
                            }
                            mCommitView.setEnabled(isFlag);
                        } else {
                            list_view.setVisibility(View.GONE);
                            ll_task_empty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        list_view.setVisibility(View.GONE);
                        ll_task_empty.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void cancellation(Dialog dialog) {
        EasyHttp.post(this)
                .api(new CancellationApi())
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        Intent intent = new Intent(DeleteFirmActivity.this, SignStatusActivity.class);
                        intent.putExtra("status", "success");
                        startActivity(intent);
                        dialog.dismiss();
                        SPUtils.getInstance().clear();
                        EasyConfig.getInstance().removeHeader("Authorization");
                        ActivityManager.getInstance().finishAllActivities(SignStatusActivity.class);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        Intent intent = new Intent(DeleteFirmActivity.this, SignStatusActivity.class);
                        intent.putExtra("status", "fail");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
    }
}
package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CancellationApi;
import com.tntlinking.tntdev.http.api.GetCancellationApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.CancelServiceAdapter;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

public final class CancelServiceActivity extends AppActivity {

    private ListView list_view;
    private TextView tv_tips;
    private AppCompatButton mCommitView;
    private CancelServiceAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.cancel_service_activity;
    }

    @Override
    protected void initView() {

        list_view = findViewById(R.id.list_view);
        tv_tips = findViewById(R.id.tv_tips);
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
            if (isFlag) {
                cancellation();
            } else {
                toast("您还有相关条件未通过，暂不能申请注销");
            }

        }

    }

    private boolean isFlag = true;

    private void getCancelDescription() {
        EasyHttp.get(this)
                .api(new GetCancellationApi())
                .request(new HttpCallback<HttpData<List<GetCancellationApi.ListBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetCancellationApi.ListBean>> data) {
                        if (data.getData().size() != 0) {
                            mAdapter = new CancelServiceAdapter(CancelServiceActivity.this, data.getData());
                            list_view.setAdapter(mAdapter);

                            for (GetCancellationApi.ListBean index : data.getData()) {
                                if (!index.isFlag()) {
                                    isFlag = false;
                                    break;
                                }
                            }
                        }
                    }

                });
    }

    private void cancellation() {
        EasyHttp.post(this)
                .api(new CancellationApi())
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        Intent intent = new Intent(CancelServiceActivity.this, SignStatusActivity.class);
                        intent.putExtra("status", "success");
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        Intent intent = new Intent(CancelServiceActivity.this, SignStatusActivity.class);
                        intent.putExtra("status", "fail");
                        startActivity(intent);
                    }
                });
    }
}
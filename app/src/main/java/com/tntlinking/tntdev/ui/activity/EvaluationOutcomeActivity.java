package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;

import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.EvaluationOutcomeListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class EvaluationOutcomeActivity extends AppActivity {
    private List<GetDeveloperJkStatusApi.Bean.stackInfoListBean> mStackInfoList = new ArrayList<>();

    private Button btn_out_evaluating;
    private RecyclerView rv_outcome_list;
    private String mPlanUrl;
    private int mUserPlanStatus;
    private LinearLayout ll_evaluating;
    private LinearLayout ll_default_page;
    /**
     * 获取布局 ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.outcome_activity;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        rv_outcome_list = findViewById(R.id.rv_outcome_list);
        btn_out_evaluating = findViewById(R.id.btn_out_evaluating);
        ll_evaluating = findViewById(R.id.ll_evaluating);
        ll_default_page = findViewById(R.id.ll_default_page);

        setOnClickListener(btn_out_evaluating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_outcome_list.setLayoutManager(layoutManager);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        getDeveloperJkStatus();
    }
    private void getDeveloperJkStatus() {
        EasyHttp.get(this)
                .api(new GetDeveloperJkStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperJkStatusApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<GetDeveloperJkStatusApi.Bean> data) {
                        if (data.getData().getPlanUrl()!=null){
                            mPlanUrl=data.getData().getPlanUrl();
                        }
                        if (data.getData().getUserPlanStatus() == 2) {
                            ll_evaluating.setVisibility(View.GONE);
                        }
                        if (!data.getData().getStackInfoList().isEmpty()){
                            mStackInfoList=data.getData().getStackInfoList();
                            EvaluationOutcomeListAdapter mEvaluationOutcomeListAdapter = new EvaluationOutcomeListAdapter(mStackInfoList);
                            rv_outcome_list.setAdapter(mEvaluationOutcomeListAdapter);
                        }else {
                            ll_default_page.setVisibility(View.VISIBLE);
                        }




                    }
                });
    }
    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_out_evaluating) {
            JkBrowserActivity.start(getActivity(), mPlanUrl);
        }
    }


}

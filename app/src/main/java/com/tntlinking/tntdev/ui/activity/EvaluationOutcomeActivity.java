package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;

import com.tntlinking.tntdev.ui.adapter.EvaluationOutcomeListAdapter;

import java.util.ArrayList;
import java.util.List;

public final class EvaluationOutcomeActivity extends AppActivity {
   private List<GetDeveloperJkStatusApi.Bean.stackInfoListBean> mStackInfoList = new ArrayList<>();

   private Button btn_out_evaluating;
   private RecyclerView rv_outcome_list;
   private String mPlanUrl;
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
      setOnClickListener(btn_out_evaluating);
      LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      rv_outcome_list.setLayoutManager(layoutManager);
   }

   /**
    * 初始化数据
    */
   @Override
   protected void initData() {
      mPlanUrl=getIntent().getStringExtra("PlanUrl");
      List<GetDeveloperJkStatusApi.Bean.stackInfoListBean> list = (List<GetDeveloperJkStatusApi.Bean.stackInfoListBean>) getIntent().getSerializableExtra("StackInfoList");
      if (list != null && list.size() > 0) {
         mStackInfoList=list;
      }
      if (mStackInfoList.size()>0){
         EvaluationOutcomeListAdapter mEvaluationOutcomeListAdapter = new EvaluationOutcomeListAdapter(mStackInfoList);
         rv_outcome_list.setAdapter(mEvaluationOutcomeListAdapter);
      }
   }

   @SuppressLint("NewApi")
   @SingleClick
   @Override
   public void onClick(View view) {
      if (view == btn_out_evaluating) { // 入驻资料
         JkBrowserActivity.start(getActivity(), mPlanUrl);
      }
   }


}

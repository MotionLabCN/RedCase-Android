package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;



import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;


public class EvaluationOutComeNullActivity extends AppActivity {

   private Button btn_out_evaluating;
   private String mPlanUrl;
   /**
    * 获取布局 ID
    */
   @Override
   protected int getLayoutId() {
      return R.layout.outcome_null_activity;
   }

   /**
    * 初始化控件
    */
   @Override
   protected void initView() {
      btn_out_evaluating = findViewById(R.id.btn_out_evaluating);
      setOnClickListener(btn_out_evaluating);
   }

   /**
    * 初始化数据
    */
   @Override
   protected void initData() {
      mPlanUrl=getIntent().getStringExtra("PlanUrl");

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

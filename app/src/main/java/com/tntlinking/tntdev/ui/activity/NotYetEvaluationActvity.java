package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;

public final class NotYetEvaluationActvity extends AppActivity {
   private Button btn_out_evaluate;

   /**
    * 获取布局 ID
    */
   @Override
   protected int getLayoutId() {
      return R.layout.notyetevaluation_activity;
   }

   /**
    * 初始化控件
    */
   @Override
   protected void initView() {
      btn_out_evaluate = findViewById(R.id.btn_out_evaluate);
      setOnClickListener(btn_out_evaluate);
   }
   @SuppressLint("NewApi")
   @SingleClick
   @Override
   public void onClick(View view) {
      if (view == btn_out_evaluate) { // 测评岗位列表
         startActivity(EvaluationListActivity.class);
      }
   }
   /**
    * 初始化数据
    */
   @Override
   protected void initData() {

   }
}

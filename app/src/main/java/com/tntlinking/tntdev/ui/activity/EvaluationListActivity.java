package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetAddEvaluationPlanApi;
import com.tntlinking.tntdev.http.api.JKSelectJobsApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.EvaluationListAdapter;

import java.util.ArrayList;
import java.util.List;

public final class EvaluationListActivity extends AppActivity {
   private final List<JKSelectJobsApi.Bean> jkSelectList = new ArrayList<>();
   private int mJKid;
   private RecyclerView rv_speciality_list;
   private Button btn_out_evaluating;

   /**
    * 获取布局 ID
    */
   @Override
   protected int getLayoutId() {
      return R.layout.evaluation_list_activity;
   }

   /**
    * 初始化控件
    */
   @Override
   protected void initView() {
      rv_speciality_list = findViewById(R.id.rv_speciality_list);
      btn_out_evaluating = findViewById(R.id.btn_out_evaluating);
       setOnClickListener(btn_out_evaluating);
      LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      rv_speciality_list.setLayoutManager(layoutManager);

   }
      private void getUndervaluation(){
         EasyHttp.post(this)
                 .api(new GetAddEvaluationPlanApi().setjobId(mJKid))
                 .request(new HttpCallback<HttpData<String>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<String> data) {
                        JkBrowserActivity.start(getActivity(), data.getData());

                    }

                     @Override
                     public void onFail(Exception e) {
                         super.onFail(e);

                     }
                 });
      }
   /**
    * 初始化数据
    */
   @Override
   protected void initData() {
      getSelectJobs();
   }

   private void getSelectJobs() {
      EasyHttp.get(this)
              .api(new JKSelectJobsApi())
              .request(new HttpCallback<HttpData<List<JKSelectJobsApi.Bean>>>(this) {

                 @SuppressLint("NotifyDataSetChanged")
                 @Override
                 public void onSucceed(HttpData<List<JKSelectJobsApi.Bean>> data) {
                    if (data.getData().size() > 0) {
                       jkSelectList.addAll(data.getData());
                       EvaluationListAdapter mEvaluationListAdapter = new EvaluationListAdapter(jkSelectList);
                       rv_speciality_list.setAdapter(mEvaluationListAdapter);
                        mEvaluationListAdapter.setonRecyclerViewItemClickListener((jkSelectJobsApi, position) -> {
                            mJKid =jkSelectJobsApi.getId();
                            //点击按钮,将当前点击的下标传进去,刷新适配器
                            mEvaluationListAdapter.setSelPos(position);
                            mEvaluationListAdapter.notifyDataSetChanged();
                        });
                    }
                 }

                 @Override
                 public void onFail(Exception e) {
                    super.onFail(e);
                 }
              });
   }

    @SuppressLint("NewApi")
    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_out_evaluating) { // 测评岗位列表
            getUndervaluation();
        }
    }
}

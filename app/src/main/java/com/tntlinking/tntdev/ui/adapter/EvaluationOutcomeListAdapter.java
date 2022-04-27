package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;

import java.util.List;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;


public class EvaluationOutcomeListAdapter extends RecyclerView.Adapter<EvaluationOutcomeListAdapter.ViewHolder> {
   private final List<GetDeveloperJkStatusApi.Bean.stackInfoListBean> mStackInfoList;
   static class ViewHolder extends RecyclerView.ViewHolder{
      TextView tv_skill;
      TextView tv_time;
      TextView tv_time_name;

      TextView tv_evaluation_end_time;
      TextView tv_number_of_name;
      TextView tv_self_rating;
      TextView tv_evaluation_level;
      ZzHorizontalProgressBar pb_self_rating;
      ZzHorizontalProgressBar pd_evaluating_grade;

      public ViewHolder(@NonNull View view) {
         super(view);
         tv_time_name = view.findViewById(R.id.tv_time_name);

         tv_skill = view.findViewById(R.id.tv_skill);
         tv_time = view.findViewById(R.id.tv_time);
         tv_evaluation_end_time = view.findViewById(R.id.tv_evaluation_end_time);
         tv_number_of_name = view.findViewById(R.id.tv_number_of_name);
         tv_self_rating = view.findViewById(R.id.tv_self_rating);
         tv_evaluation_level = view.findViewById(R.id.tv_evaluation_level);
         pb_self_rating = view.findViewById(R.id.pb_self_rating);
         pd_evaluating_grade = view.findViewById(R.id.pd_evaluating_grade);

      }
   }

   public EvaluationOutcomeListAdapter(List<GetDeveloperJkStatusApi.Bean.stackInfoListBean> mStackInfoList) {
      this.mStackInfoList = mStackInfoList;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outcome_list_item,parent,false);
      return new ViewHolder(view);
   }

   @SuppressLint({"RecyclerView", "SetTextI18n", "DefaultLocale"})
   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      GetDeveloperJkStatusApi.Bean.stackInfoListBean jkSelectJobsApi = mStackInfoList.get(position);
      holder.tv_skill.setText(jkSelectJobsApi.getStack());
      holder.tv_time.setText("答题时间:"+jkSelectJobsApi.getAnswerDuration()+"秒");
      holder.tv_evaluation_end_time.setText("测评结束时间:"+jkSelectJobsApi.getAnswerEndAt());
      holder.tv_number_of_name.setText(String.format("总数: %d  答对: %d 得分: %d", jkSelectJobsApi.getTotalQuestionCount(), jkSelectJobsApi.getCorrectQuestionCount(), jkSelectJobsApi.getScore()));
      holder.tv_self_rating.setText(jkSelectJobsApi.getSelfEvaluationGrade());
      holder.tv_evaluation_level.setText(jkSelectJobsApi.getEvaluationGrade());

      switch (jkSelectJobsApi.getSelfEvaluationGrade()) {
         case "了解":
            holder.pb_self_rating.setProgress(20);
            break;
         case "熟悉":
            holder.pb_self_rating.setProgress(40);
            break;
         case "掌握":
            holder.pb_self_rating.setProgress(60);
            break;
         case "精通":
            holder.pb_self_rating.setProgress(80);
            break;
         case "专家":
            holder.pb_self_rating.setProgress(100);
            break;
      }
      switch (jkSelectJobsApi.getEvaluationGrade()) {
         case "了解":
            holder.pd_evaluating_grade.setProgress(20);
            break;
         case "熟悉":
            holder.pd_evaluating_grade.setProgress(40);
            break;
         case "掌握":
            holder.pd_evaluating_grade.setProgress(60);
            break;
         case "精通":
            holder.pd_evaluating_grade.setProgress(80);
            break;
         case "专家":
            holder.pd_evaluating_grade.setProgress(100);
            break;
      }

   }
   @Override
   public int getItemCount(){
      return mStackInfoList.size();
   }




}
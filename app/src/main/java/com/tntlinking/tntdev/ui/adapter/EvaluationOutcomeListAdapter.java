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
      TextView text_skill;
      TextView text_time;
      TextView text_time_name;

      TextView evaluation_end_time;
      TextView number_of_name;
      TextView text_self_rating;
      TextView text_evaluation_level;
      ZzHorizontalProgressBar pb;
      ZzHorizontalProgressBar pb1;

      public ViewHolder(@NonNull View view) {
         super(view);
         text_time_name = view.findViewById(R.id.text_time_name);

         text_skill = view.findViewById(R.id.text_skill);
         text_time = view.findViewById(R.id.text_time);
         evaluation_end_time = view.findViewById(R.id.evaluation_end_time);
         number_of_name = view.findViewById(R.id.number_of_name);
         text_self_rating = view.findViewById(R.id.text_self_rating);
         text_evaluation_level = view.findViewById(R.id.text_evaluation_level);
         pb = view.findViewById(R.id.pd);
         pb1 = view.findViewById(R.id.pd1);

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
      holder.text_skill.setText(jkSelectJobsApi.getStack());
      holder.text_time.setText("答题时间:"+jkSelectJobsApi.getAnswerDuration()+"秒");
      holder.evaluation_end_time.setText("测评结束时间:"+jkSelectJobsApi.getAnswerEndAt());
      holder.number_of_name.setText(String.format("总数: %d  答对: %d 得分: %d", jkSelectJobsApi.getTotalQuestionCount(), jkSelectJobsApi.getCorrectQuestionCount(), jkSelectJobsApi.getScore()));
      holder.text_self_rating.setText(jkSelectJobsApi.getSelfEvaluationGrade());
      holder.text_evaluation_level.setText(jkSelectJobsApi.getEvaluationGrade());

      switch (jkSelectJobsApi.getSelfEvaluationGrade()) {
         case "了解":
            holder.pb.setProgress(20);
            break;
         case "熟悉":
            holder.pb.setProgress(40);
            break;
         case "掌握":
            holder.pb.setProgress(60);
            break;
         case "精通":
            holder.pb.setProgress(80);
            break;
         case "专家":
            holder.pb.setProgress(100);
            break;
      }
      switch (jkSelectJobsApi.getEvaluationGrade()) {
         case "了解":
            holder.pb1.setProgress(20);
            break;
         case "熟悉":
            holder.pb1.setProgress(40);
            break;
         case "掌握":
            holder.pb1.setProgress(60);
            break;
         case "精通":
            holder.pb1.setProgress(80);
            break;
         case "专家":
            holder.pb1.setProgress(100);
            break;
      }

   }
   @Override
   public int getItemCount(){
      return mStackInfoList.size();
   }




}
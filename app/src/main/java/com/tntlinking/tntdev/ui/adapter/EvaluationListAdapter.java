package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.JKSelectJobsApi;

import java.util.List;

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.ViewHolder> {
   private final List<JKSelectJobsApi.Bean> mFruitList;
   private onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
   /**
    * 定义选中的下标, 默认-1
    */
   private int selPos = -1;

   public void setSelPos(int selPos) {
      this.selPos = selPos;
   }

   static class ViewHolder extends RecyclerView.ViewHolder{
      ImageView iv_select;
      TextView tv_name;

      public ViewHolder(@NonNull View view) {
         super(view);
         iv_select = view.findViewById(R.id.iv_select);
         tv_name = view.findViewById(R.id.tv_name);
      }
   }
   public void setonRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
      this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
   }
   public EvaluationListAdapter(List<JKSelectJobsApi.Bean> mFruitList) {
      this.mFruitList = mFruitList;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speciality_list_item,parent,false);
      return new ViewHolder(view);
   }

   @SuppressLint("RecyclerView")
   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      JKSelectJobsApi.Bean jkSelectJobsApi = mFruitList.get(position);
      holder.iv_select.setSelected(selPos == holder.getAdapterPosition());

      holder.tv_name.setText(jkSelectJobsApi.getName());
      //绑定view的方法
      if (onRecyclerViewItemClickListener != null) {
         holder.itemView.setOnClickListener(v -> onRecyclerViewItemClickListener.onItemClick(jkSelectJobsApi,position));
      }
   }
   @Override
   public int getItemCount(){
      return mFruitList.size();
   }




}
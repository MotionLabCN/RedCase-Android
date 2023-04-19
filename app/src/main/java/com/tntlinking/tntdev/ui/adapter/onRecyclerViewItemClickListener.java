package com.tntlinking.tntdev.ui.adapter;



import com.tntlinking.tntdev.http.api.JKSelectJobsApi;

public interface onRecyclerViewItemClickListener {

 void onItemClick(JKSelectJobsApi.Bean jkSelectJobsApi, int position);
}

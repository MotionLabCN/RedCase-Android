package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.JobDetailsApi;

import java.util.List;

public class JobRequirementsAdapter extends RecyclerView.Adapter<JobRequirementsAdapter.FlexboxAdapterHolder> {
    Context mContext;
    private final List<String> mStringArrayList;

    public JobRequirementsAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mStringArrayList = list;
    }

    @NonNull
    @Override
    public FlexboxAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.require_labels_item, parent, false);
        return new FlexboxAdapterHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FlexboxAdapterHolder holder, final int position) {
        holder.tv_require_labels.setText(mStringArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return mStringArrayList.size();
    }

    public static class FlexboxAdapterHolder extends RecyclerView.ViewHolder {
        private final TextView tv_require_labels;

        public FlexboxAdapterHolder(View itemView) {
            super(itemView);
            tv_require_labels = itemView.findViewById(R.id.tv_require_labels);
        }
    }
}

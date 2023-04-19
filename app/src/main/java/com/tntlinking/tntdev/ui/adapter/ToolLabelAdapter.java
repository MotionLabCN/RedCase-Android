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

import java.util.List;

public class ToolLabelAdapter extends RecyclerView.Adapter<ToolLabelAdapter.FlexboxAdapterHolder> {
    Context mContext;
    private final List<String> mStringToolLabelArrayList;

    public ToolLabelAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mStringToolLabelArrayList = list;
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
        holder.tv_require_labels.setText(mStringToolLabelArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return mStringToolLabelArrayList.size();
    }

    public static class FlexboxAdapterHolder extends RecyclerView.ViewHolder {
        private final TextView tv_require_labels;

        public FlexboxAdapterHolder(View itemView) {
            super(itemView);
            tv_require_labels = itemView.findViewById(R.id.tv_require_labels);
        }
    }
}

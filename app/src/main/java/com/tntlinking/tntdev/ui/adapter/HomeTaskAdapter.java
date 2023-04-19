package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.other.Utils;

import java.util.List;


public final class HomeTaskAdapter extends BaseAdapter {

    private List<GetNewbieApi.Bean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public HomeTaskAdapter(Context context, List<GetNewbieApi.Bean> list) {
        this.mContext = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GetNewbieApi.Bean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<GetNewbieApi.Bean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_home_task_list, null);
            holder = new ViewHolder();
            holder.tv_task_name = convertView.findViewById(R.id.tv_task_name);
            holder.view_dot = convertView.findViewById(R.id.view_dot);
            holder.tv_remain_day = convertView.findViewById(R.id.tv_remain_day);
            holder.tv_task_description = convertView.findViewById(R.id.tv_task_description);
            holder.tv_rewardNum = convertView.findViewById(R.id.tv_rewardNum);
            holder.tv_do_task = convertView.findViewById(R.id.tv_do_task);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetNewbieApi.Bean item = mList.get(position);
        int taskStatus = item.getTaskStatus(); // 任务状态 0-未开始 1-进行中 2-已完成 3-已失效
        int rewardStatus = item.getRewardStatus();//	奖励发放状态：0-不满足 1-待发放 2-已发放
        holder.tv_task_name.setText(item.getTaskName());
        holder.tv_task_description.setText(item.getTaskShortDesc());
        holder.tv_rewardNum.setText(Utils.StripZeros(item.getRewardNum()) + "元");

        if (taskStatus == 0 || taskStatus == 1) { //做任务
            holder.tv_do_task.setText("做任务");
            holder.view_dot.setVisibility(View.VISIBLE);
            holder.tv_remain_day.setVisibility(View.VISIBLE);
            holder.tv_remain_day.setText("距失效剩余" + item.getRemainingDays() + "天");
        } else if (taskStatus == 2) {
            if (rewardStatus == 0 || rewardStatus == 1) {// 已完成
                holder.tv_do_task.setText("领奖励");

            } else if ( rewardStatus == 2){ //已领取
                holder.tv_do_task.setText("已领取");
                holder.tv_do_task.setTextColor(mContext.getResources().getColor(R.color.color_hint_color));
                holder.tv_do_task.setBackground(mContext.getResources().getDrawable(R.drawable.btn_gray_radius_20));
                holder.tv_do_task.setClickable(false);

            }
            holder.view_dot.setVisibility(View.GONE);
            holder.tv_remain_day.setVisibility(View.GONE);
        } else {// 已失效

            holder.tv_do_task.setText("已失效");
            holder.tv_do_task.setTextColor(mContext.getResources().getColor(R.color.color_hint_color));
            holder.tv_do_task.setBackground(mContext.getResources().getDrawable(R.drawable.btn_gray_radius_20));
            holder.tv_do_task.setClickable(false);

            holder.view_dot.setVisibility(View.GONE);
            holder.tv_remain_day.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_task_name;
        View view_dot;
        TextView tv_remain_day;
        TextView tv_task_description;
        TextView tv_rewardNum;
        TextView tv_do_task;


    }


}


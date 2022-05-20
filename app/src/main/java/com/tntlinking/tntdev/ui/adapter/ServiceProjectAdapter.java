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
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class ServiceProjectAdapter extends BaseAdapter {

    private List<AppListApi.Bean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ServiceProjectAdapter(Context context, List<AppListApi.Bean> list) {
        this.mContext = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<AppListApi.Bean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_app_list, null);
            holder = new ViewHolder();
            holder.tv_position_name = convertView.findViewById(R.id.tv_position_name);
            holder.tv_company_name = convertView.findViewById(R.id.tv_company_name);
            holder.tv_work_info = convertView.findViewById(R.id.tv_work_info);
            holder.ll_status = convertView.findViewById(R.id.ll_status);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.tv_type = convertView.findViewById(R.id.tv_type);
            holder.tv_interview = convertView.findViewById(R.id.tv_interview);
            holder.view_dot = convertView.findViewById(R.id.view_dot);
            holder.tv_write = convertView.findViewById(R.id.tv_write);
            holder.view_line = convertView.findViewById(R.id.view_line);
            holder.tv_order_id = convertView.findViewById(R.id.tv_order_id);
            holder.ll_detail = convertView.findViewById(R.id.ll_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppListApi.Bean item = mList.get(position);
        if (!TextUtils.isEmpty(item.getOrderNo())) { // 订单列表
            holder.tv_position_name.setText(item.getPositionName());
            holder.tv_company_name.setText(item.getCompanyName());
            holder.tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getWorkStartDate()) + "—" + Utils.getYearFromDate(item.getFinishDate()));
            holder.tv_status.setText(item.getStatusName());
            holder.view_line.setVisibility(View.VISIBLE);
            holder.tv_order_id.setVisibility(View.VISIBLE);
            holder.ll_detail.setVisibility(View.GONE);
            holder.tv_order_id.setText("订单号："+item.getOrderNo());
            if (item.getServiceName().equals("服务中")) { // 服务中
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_orange));
                holder.ll_status.setBackground(mContext.getDrawable(R.drawable.bg_orange_radius_3));
                holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tv_type.setBackground(mContext.getDrawable(R.drawable.btn_blue_radius_10));
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_fb839));
            } else { // 待服务
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_gray));
                holder.ll_status.setBackground(mContext.getDrawable(R.drawable.bg_gray_radius_3));
                holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.color_C1C6D2));
                holder.tv_type.setBackground(mContext.getDrawable(R.drawable.btn_gray_radius_10));
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.gray));
            }
        } else {// 面试邀约
            holder.tv_position_name.setText(item.getPositionName());
            holder.tv_company_name.setText(item.getCompanyName());
            holder.tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getInterviewStartDate()) + " " + Utils.getHoursAndMin(item.getInterviewStartDate()) + "—" + Utils.getHoursAndMin(item.getInterviewEndDate()));
            holder.view_line.setVisibility(View.GONE);
            holder.tv_order_id.setVisibility(View.GONE);
            holder.tv_write.setVisibility(View.GONE);
            holder.ll_detail.setVisibility(View.VISIBLE);

            if (item.getInterviewTimeType().equals("今日面试")) {
                holder.tv_status.setText(item.getInterviewTimeType());
                holder.tv_interview.setVisibility(View.VISIBLE);
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_green));
                holder.ll_status.setBackground(mContext.getDrawable(R.drawable.bg_green_radius_3));
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_5CE28A));
            } else if (!item.getInterviewTimeType().equals("今日面试")) {
                holder.tv_status.setText("等待面试");
                holder.tv_interview.setVisibility(View.VISIBLE);
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_red));
                holder.ll_status.setBackground(mContext.getDrawable(R.drawable.bg_red_radius_3));
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_F5313D));
            }else  if (TextUtils.isEmpty(item.getInterviewTimeType())) {
                holder.tv_interview.setVisibility(View.GONE);
//                holder.ll_status.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_position_name;
        TextView tv_company_name;
        TextView tv_work_info;
        LinearLayout ll_status;
        TextView tv_status;
        TextView tv_type;
        TextView tv_interview;
        View view_dot;
        TextView tv_write;
        View view_line;
        TextView tv_order_id;
        LinearLayout ll_detail;

    }


}


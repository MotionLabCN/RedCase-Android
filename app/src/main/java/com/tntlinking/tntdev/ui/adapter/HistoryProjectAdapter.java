package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class HistoryProjectAdapter extends BaseAdapter {

    private List<AppListApi.Bean> mList;
    private LayoutInflater layoutInflater;

    public HistoryProjectAdapter(Context context, List<AppListApi.Bean> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_history_list, null);
            holder = new ViewHolder();
            holder.tv_position_name = convertView.findViewById(R.id.tv_position_name);
            holder.tv_company_name = convertView.findViewById(R.id.tv_company_name);
            holder.tv_work_info = convertView.findViewById(R.id.tv_work_info);
            holder.tv_order_id = convertView.findViewById(R.id.tv_order_id);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppListApi.Bean item = mList.get(position);

        holder.tv_position_name.setText(item.getPositionName());
        holder.tv_company_name.setText(item.getCompanyName());
        holder.tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getWorkStartDate()) + "—" + Utils.getYearFromDate(item.getFinishDate()));
        holder.tv_order_id.setText("订单号："+item.getOrderNo());
        return convertView;
    }

    class ViewHolder {
        TextView tv_position_name;
        TextView tv_company_name;
        TextView tv_work_info;
        TextView tv_order_id;

    }


}


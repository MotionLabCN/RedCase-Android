package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppScheduleApi;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class AddWorkAdapter extends BaseAdapter {

    private List<DeveloperInfoBean.DeveloperWork> mList;
    private LayoutInflater layoutInflater;

    public AddWorkAdapter(Context context, List<DeveloperInfoBean.DeveloperWork> list) {
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

    public void setData(List<DeveloperInfoBean.DeveloperWork> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_tab_work, null);
            holder = new ViewHolder();
            holder.tv_work_name = convertView.findViewById(R.id.tv_work_name);
            holder.tv_work_position = convertView.findViewById(R.id.tv_work_position);
            holder.tv_work_time = convertView.findViewById(R.id.tv_work_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeveloperInfoBean.DeveloperWork bean = mList.get(position);
        holder.tv_work_name.setText(bean.getCompanyName());
        holder.tv_work_position.setText(bean.getPositionName());
        holder.tv_work_time.setText(bean.getWorkStartTime() + "-" + bean.getWorkEndTime());

        return convertView;
    }

    class ViewHolder {
        TextView tv_work_name;
        TextView tv_work_position;
        TextView tv_work_time;

    }


}


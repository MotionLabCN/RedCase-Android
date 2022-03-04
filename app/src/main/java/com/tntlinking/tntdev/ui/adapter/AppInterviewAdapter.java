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

import java.util.List;


public final class AppInterviewAdapter extends BaseAdapter {

    private List<AppScheduleApi.Bean> mList;
    private LayoutInflater layoutInflater;

    public AppInterviewAdapter(Context context, List<AppScheduleApi.Bean> provinceBeanList) {
        this.mList = provinceBeanList;
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

    public void setData(List<AppScheduleApi.Bean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_add_interview, null);
            holder = new ViewHolder();
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppScheduleApi.Bean bean = mList.get(position);
        if (bean != null) {
//            holder.tv_time.setText(bean.getScheduleDate());
            holder.tv_content.setText(bean.getTitle());
            String startDate = bean.getStartDate();
            String endDate = bean.getEndDate();

            if (bean.isFullDay()) {
                holder.tv_time.setText("全天");
            } else {
                holder.tv_time.setText(Utils.getHoursAndMin(startDate) + "-" + Utils.getHoursAndMin(endDate));

            }

        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_time;
        TextView tv_content;

    }


}


package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class AddEducationAdapter extends BaseAdapter {

    private List<DeveloperInfoBean.DeveloperEducation> mList;
    private LayoutInflater layoutInflater;

    public AddEducationAdapter(Context context,List<DeveloperInfoBean.DeveloperEducation> list) {
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

    public void setData(List<DeveloperInfoBean.DeveloperEducation> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_tab_education, null);
            holder = new ViewHolder();
            holder.tv_school_name = convertView.findViewById(R.id.tv_school_name);
            holder.tv_school_info = convertView.findViewById(R.id.tv_school_info);
            holder.tv_school_time = convertView.findViewById(R.id.tv_school_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeveloperInfoBean.DeveloperEducation bean = mList.get(position);
        holder.tv_school_name.setText(bean.getCollegeName());
        holder.tv_school_info.setText(bean.getEducationName()+" | "+bean.getTrainingModeName()+" | "+bean.getMajor());
        holder.tv_school_time.setText(bean.getInSchoolStartTime()+" - "+bean.getInSchoolEndTime());

        return convertView;
    }

    class ViewHolder {
        TextView tv_school_name;
        TextView tv_school_info;
        TextView tv_school_time;

    }


}


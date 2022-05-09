package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.ui.bean.PositionBean;

import java.util.List;

public class PositionRecommendationAdapter extends BaseAdapter {

    private List<PositionBean> mList;
    private final LayoutInflater layoutInflater;
    private Context mContext;
    public PositionRecommendationAdapter(Context context, List<PositionBean> list) {
        this.mContext = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PositionBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<PositionBean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PositionRecommendationAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.position_item, null);
            holder = new ViewHolder();
            holder.tv_position_name = convertView.findViewById(R.id.tv_position_name);
            holder.tv_recommend = convertView.findViewById(R.id.tv_recommend);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_professional_title = convertView.findViewById(R.id.tv_professional_title);
            holder.tv_company = convertView.findViewById(R.id.tv_company);
            holder.iv_recommend = convertView.findViewById(R.id.iv_recommend);

            convertView.setTag(holder);
        } else {
            holder = (PositionRecommendationAdapter.ViewHolder) convertView.getTag();
        }
        PositionBean item = mList.get(position);
        holder.tv_position_name.setText(item.getPosition_name());
        holder.tv_recommend.setText(item.getRecommend());
        holder.tv_salary.setText(item.getSalary());
        holder.tv_content.setText(item.getContent());
        holder.tv_name.setText(item.getName());
        holder.tv_professional_title.setText(item.getProfessional_title());
        holder.tv_company.setText(item.getCompany());
        if (item.isIcon_recommend()==true){
            holder.iv_recommend.setVisibility(View.VISIBLE);
        }else {
            holder.iv_recommend.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_position_name;
        TextView tv_recommend;
        TextView tv_salary;
        TextView tv_content;
        TextView tv_name;
        TextView tv_professional_title;
        TextView tv_company;
        ImageView iv_recommend;


    }


}


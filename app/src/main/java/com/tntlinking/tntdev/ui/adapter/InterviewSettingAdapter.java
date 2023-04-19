package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.InterviewListApi;
import com.tntlinking.tntdev.other.Utils;

import java.util.List;

/**
 * Created by Dan on 2021/12/10.
 */

public class InterviewSettingAdapter extends BaseAdapter {

    private List<InterviewListApi.Bean> provinceBeanList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public InterviewSettingAdapter(Context context, List<InterviewListApi.Bean> provinceBeanList) {
        this.provinceBeanList = provinceBeanList;
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return provinceBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return provinceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<InterviewListApi.Bean> list) {
        if (list.size() != 0) {
            this.provinceBeanList = list;
            notifyDataSetChanged();
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_interview_setting, null);
            holder = new ViewHolder();
            holder.ll_interview = convertView.findViewById(R.id.ll_interview);
            holder.text = convertView.findViewById(R.id.text);
            holder.view_dot =  convertView.findViewById(R.id.view_dot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InterviewListApi.Bean bean = provinceBeanList.get(position);
        if (bean != null) {
            if (bean.getScheduleDate().contains("-")) {
//                holder.view_dot.setVisibility(View.VISIBLE);
            }
            holder.text.setText(Utils.getDay(bean.getScheduleDate()));

            if (bean.getStatus()==2){ //1可参加/2不可参加/3部分时间可参加
                holder.view_dot.setVisibility(View.VISIBLE);
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.bg_red_radius_100));
            }else if (bean.getStatus()==3){
                holder.view_dot.setVisibility(View.VISIBLE);
                holder.view_dot.setBackground(mContext.getDrawable(R.drawable.bg_green_radius_100));
            }else {
                holder.view_dot.setVisibility(View.INVISIBLE);
            }

            if (bean.isSelect()){
                holder.ll_interview.setBackground(mContext.getDrawable(R.drawable.bg_gray_round_radius_8));
            }else {
                holder.ll_interview.setBackground(mContext.getDrawable(R.drawable.bg_trans_round_radius_8));
            }
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_interview;
        TextView text;
        View view_dot;
    }


}



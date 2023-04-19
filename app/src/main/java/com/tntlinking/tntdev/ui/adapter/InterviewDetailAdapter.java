package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppScheduleApi;
import com.tntlinking.tntdev.http.api.GetAppListDateApi;
import com.tntlinking.tntdev.other.Utils;

import java.util.List;


public final class InterviewDetailAdapter extends BaseAdapter {

    private List<GetAppListDateApi.Bean> mList;
    private LayoutInflater layoutInflater;

    public InterviewDetailAdapter(Context context, List<GetAppListDateApi.Bean> provinceBeanList) {
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

    public void setData(List<GetAppListDateApi.Bean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.top_interview_item, null);
            holder = new ViewHolder();
            holder.ll_status = convertView.findViewById(R.id.ll_status);
            holder.tv_interview_position = convertView.findViewById(R.id.tv_interview_position);
            holder.tv_interview_salary = convertView.findViewById(R.id.tv_interview_salary);
            holder.tv_interview_company = convertView.findViewById(R.id.tv_interview_company);
            holder.tv_interview_time = convertView.findViewById(R.id.tv_interview_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetAppListDateApi.Bean bean = mList.get(position);
        if (bean != null) {

            holder.ll_status.setVisibility(View.GONE);
            holder.tv_interview_position.setText(bean.getPositionName());
            double startPay = Double.parseDouble(bean.getStartPay()) / 1000;
            double endPay = Double.parseDouble(bean.getEndPay()) / 1000;
            holder.tv_interview_salary.setText(bean.getWorkDaysModeName() + "·" +
                    Utils.formatMoney(startPay) + "-" + Utils.formatMoney(endPay) + "k/月");
            holder.tv_interview_company.setText(bean.getCompanyName());
//            holder.tv_interview_time.setText(bean.getInterviewStartDate());

            String year = Utils.getYearFromDate(bean.getInterviewStartDate());
            String start = Utils.getHoursAndMin(bean.getInterviewStartDate());
            String end = Utils.getHoursAndMin(bean.getInterviewEndDate());
            holder.tv_interview_time.setText(year + " " + start + "-" + end);

        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout ll_status;
        TextView tv_interview_position;
        TextView tv_interview_salary;
        TextView tv_interview_company;
        TextView tv_interview_time;

    }


}


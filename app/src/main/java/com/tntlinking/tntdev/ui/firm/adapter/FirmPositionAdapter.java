package com.tntlinking.tntdev.ui.firm.adapter;

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
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.other.OnItemClickListener;


import java.util.List;


public final class FirmPositionAdapter extends BaseAdapter {

    private List<GetFirmPositionApi.Bean.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemSelectListener) {
        this.mOnItemClickListener = mOnItemSelectListener;
    }

    public FirmPositionAdapter(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
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

    public List<GetFirmPositionApi.Bean.ListBean> getData() {
        return mList;
    }

    public void setData(List<GetFirmPositionApi.Bean.ListBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_firm_position, null);
            holder = new ViewHolder();
            holder.tv_position = convertView.findViewById(R.id.tv_position);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);
            holder.view_dot = convertView.findViewById(R.id.view_dot);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.ll_status = convertView.findViewById(R.id.ll_status);
            holder.tv_company_name = convertView.findViewById(R.id.tv_company_name);
            holder.tv_position_desc = convertView.findViewById(R.id.tv_position_desc);
            holder.tv_reason = convertView.findViewById(R.id.tv_reason);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFirmPositionApi.Bean.ListBean item = mList.get(position);
        holder.tv_position.setText(item.getCareerDirection());
        holder.tv_salary.setText(item.getStartPay() + "-" + item.getEndPay());
        holder.tv_company_name.setText(item.getCareerDirection());
        holder.tv_position_desc.setText(item.getWorkDaysMode() + "-" + item.getEducation() + "-" + item.getWorkYears());


        holder.tv_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {

        TextView tv_position;
        View view_dot;
        TextView tv_status;
        LinearLayout ll_status;
        TextView tv_company_name;
        TextView tv_salary;
        TextView tv_position_desc;
        TextView tv_reason;

    }
}


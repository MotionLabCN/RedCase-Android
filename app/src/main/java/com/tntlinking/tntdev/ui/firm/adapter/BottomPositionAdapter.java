package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.other.Utils;

import java.util.List;


public final class BottomPositionAdapter extends BaseAdapter {

    private List<GetFirmPositionApi.Bean.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public BottomPositionAdapter(Context context) {
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
            convertView = layoutInflater.inflate(R.layout.item_bottom_position, null);
            holder = new ViewHolder();
            holder.tv_dev_position = convertView.findViewById(R.id.tv_dev_position);
            holder.tv_dev_info = convertView.findViewById(R.id.tv_dev_info);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFirmPositionApi.Bean.ListBean item = mList.get(position);
        holder.tv_dev_position.setText(item.getCareerDirection());
//        holder.tv_salary.setText(item.getStartPay() + "-" + item.getEndPay());
        String mStart = Utils.formatMoney(item.getStartPay() / 1000) + "k";
        String mEnd = Utils.formatMoney(item.getEndPay() / 1000) + "k";
        holder.tv_salary.setText(mStart + "-" + mEnd);
        holder.tv_dev_info.setText(item.getWorkDaysMode() + "-" + item.getEducation() + "-" + item.getWorkYears());


        return convertView;
    }

    class ViewHolder {
        TextView tv_dev_position;
        TextView tv_dev_info;
        TextView tv_salary;

    }

}


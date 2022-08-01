package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppListApi;

import java.util.List;


public final class FreezeAdapter extends BaseAdapter {

    private List<AppListApi.Bean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public FreezeAdapter(Context context, List<AppListApi.Bean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_freeze, null);
            holder = new ViewHolder();
            holder.tv_freeze_money = convertView.findViewById(R.id.tv_freeze_money);
            holder.tv_freeze_num = convertView.findViewById(R.id.tv_freeze_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppListApi.Bean item = mList.get(position);
        holder.tv_freeze_money.setText(item.getPositionName());
        holder.tv_freeze_num.setText(item.getCompanyName());


        return convertView;
    }

    class ViewHolder {
        TextView tv_freeze_money;
        TextView tv_freeze_num;
        TextView tv_freeze_time;


    }


}


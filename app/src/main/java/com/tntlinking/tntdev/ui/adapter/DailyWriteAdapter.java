package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetDailyListApi;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class DailyWriteAdapter extends BaseAdapter {

    private List<GetDailyListApi.ListBean> mList;
    private LayoutInflater layoutInflater;

    public DailyWriteAdapter(Context context) {
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
    public GetDailyListApi.ListBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<GetDailyListApi.ListBean> getData() {
        return mList;
    }

    public void setData(List<GetDailyListApi.ListBean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_write_daily, null);
            holder = new ViewHolder();
            holder.tv_tab_left = convertView.findViewById(R.id.tv_tab_left);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetDailyListApi.ListBean bean = mList.get(position);
        holder.tv_tab_left.setText(bean.getItem());

        return convertView;
    }

    class ViewHolder {
        TextView tv_tab_left;

    }


}


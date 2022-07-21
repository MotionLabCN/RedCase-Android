package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetHistoryListApi;
import java.util.List;


public final class HistoryDailyItemAdapter extends BaseAdapter {

    private List<GetHistoryListApi.Bean.CommonBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public HistoryDailyItemAdapter(Context context, List<GetHistoryListApi.Bean.CommonBean> list) {
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

    public void setData(List<GetHistoryListApi.Bean.CommonBean> list) {
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
        GetHistoryListApi.Bean.CommonBean bean = mList.get(position);
        holder.tv_tab_left.setText(bean.getItem());

        return convertView;
    }

    class ViewHolder {
        TextView tv_tab_left;

    }

}


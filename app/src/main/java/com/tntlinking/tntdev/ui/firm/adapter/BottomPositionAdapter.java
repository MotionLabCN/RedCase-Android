package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


public final class BottomPositionAdapter extends BaseAdapter {

    private List<String> mList;
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

    public List<String> getData() {
        return mList;
    }

    public void setData(List<String> list) {
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
        String item = mList.get(position);
        holder.tv_dev_position.setText(item);

        return convertView;
    }

    class ViewHolder {
        TextView tv_dev_position;
        TextView tv_dev_info;
        TextView tv_salary;

    }

}


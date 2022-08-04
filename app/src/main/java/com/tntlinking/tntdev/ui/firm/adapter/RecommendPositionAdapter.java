package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.other.GlideUtils;

import java.util.List;


public final class RecommendPositionAdapter extends BaseAdapter {

    private List<AppListApi.Bean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public RecommendPositionAdapter(Context context, List<AppListApi.Bean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_recommend_position, null);
            holder = new ViewHolder();
            holder.iv_avatar = convertView.findViewById(R.id.iv_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_position = convertView.findViewById(R.id.tv_position);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppListApi.Bean item = mList.get(position);
        holder.tv_name.setText(item.getPositionName());
        holder.tv_position.setText(item.getCompanyName());

        GlideUtils.loadRoundCorners(mContext, R.drawable.update_app_top_bg, holder.iv_avatar,
                (int) mContext.getResources().getDimension(R.dimen.dp_8));
        return convertView;
    }

    class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_position;
        TextView tv_salary;


    }


}


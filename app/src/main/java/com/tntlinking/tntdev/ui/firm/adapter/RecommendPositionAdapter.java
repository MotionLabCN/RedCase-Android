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
import com.tntlinking.tntdev.http.api.GetFirmRecommendsApi;
import com.tntlinking.tntdev.other.GlideUtils;

import java.util.List;


public final class RecommendPositionAdapter extends BaseAdapter {

    private List<GetFirmRecommendsApi.Bean.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public RecommendPositionAdapter(Context context) {
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

    public void setData(List<GetFirmRecommendsApi.Bean.ListBean> list) {
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
            holder.tv_position_desc = convertView.findViewById(R.id.tv_position_desc);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFirmRecommendsApi.Bean.ListBean item = mList.get(position);
        holder.tv_name.setText(item.getRealName());
        holder.tv_salary.setText(item.getExpectSalary());
        holder.tv_position_desc.setText(item.getCareerDirectionName() + "-" + item.getEducationName() + "-工作经验" + item.getWorkYearsName());
        GlideUtils.loadRoundCorners(mContext, item.getAvatarUrl(), holder.iv_avatar, (int) mContext.getResources().getDimension(R.dimen.dp_8));
        return convertView;
    }

    class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_position_desc;
        TextView tv_salary;


    }


}


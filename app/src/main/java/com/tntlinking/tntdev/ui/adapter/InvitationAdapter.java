package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.InvitationListApi;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import java.util.List;


public final class InvitationAdapter extends BaseAdapter {

    private List<InvitationListApi.Bean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public InvitationAdapter(Context context, List<InvitationListApi.Bean> list) {
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

    public void setData(List<InvitationListApi.Bean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_invitation, null);
            holder = new ViewHolder();
            holder.tv_phone = convertView.findViewById(R.id.tv_phone);
            holder.iv_red_packet = convertView.findViewById(R.id.iv_red_packet);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InvitationListApi.Bean bean = mList.get(position);
        holder.tv_phone.setText(bean.getDeveloperName());
        holder.tv_date.setText(Utils.getYearFromDate(bean.getCreateDate()));
        holder.iv_red_packet.setVisibility(View.GONE);
        if (bean.getDeveloperStatus() == 1) {
            holder.tv_phone.setText(bean.getDeveloperName() + "(已注册)");
        } else if (bean.getDeveloperStatus() == 2) {
            holder.tv_phone.setText(bean.getDeveloperName() + "(待审核)");
        } else if (bean.getDeveloperStatus() == 3) {
            holder.tv_phone.setText(bean.getDeveloperName() + "(已入驻)");
            holder.iv_red_packet.setVisibility(View.VISIBLE);
            if (bean.getRewardStatus() == 1) {// 0 不满足 1 待发放 1已发放
                holder.iv_red_packet.setImageResource(R.drawable.icon_red_packet);
            } else if (bean.getRewardStatus() == 2) {
                holder.iv_red_packet.setImageResource(R.drawable.icon_red_packet_open);
            }

        } else if (bean.getDeveloperStatus() == 4) {
            holder.tv_phone.setText(bean.getDeveloperName() + "(未通过)");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_phone;
        ImageView iv_red_packet;
        TextView tv_date;

    }


}


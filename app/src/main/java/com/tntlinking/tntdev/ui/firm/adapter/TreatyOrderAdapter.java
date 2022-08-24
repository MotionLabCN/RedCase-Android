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
import com.tntlinking.tntdev.http.api.GetFirmOrderListApi;
import com.tntlinking.tntdev.other.OnItemClickListener;

import java.util.List;


public final class TreatyOrderAdapter extends BaseAdapter {

    private List<GetFirmOrderListApi.Bean.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener mOnItemSelectListener) {
        this.listener = mOnItemSelectListener;
    }

    public TreatyOrderAdapter(Context context) {
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
    public GetFirmOrderListApi.Bean.ListBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<GetFirmOrderListApi.Bean.ListBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_treatry_order, null);
            holder = new ViewHolder();
            holder.tv_order_num = convertView.findViewById(R.id.tv_order_num);
            holder.tv_order_status = convertView.findViewById(R.id.tv_order_status);
            holder.tv_order_time = convertView.findViewById(R.id.tv_order_time);
            holder.tv_order_dev_name = convertView.findViewById(R.id.tv_order_dev_name);
            holder.tv_order_dev_position = convertView.findViewById(R.id.tv_order_dev_position);
            holder.tv_to_pay = convertView.findViewById(R.id.tv_to_pay);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFirmOrderListApi.Bean.ListBean item = mList.get(position);

        holder.tv_order_num.setText("订单号：" + item.getOrderNo());
        holder.tv_order_status.setText(item.getOrderStatusName());
        holder.tv_order_time.setText(item.getWorkStartDate());
        holder.tv_order_dev_name.setText(item.getRealName()+"("+item.getWorkDaysModeName()+")");
        holder.tv_order_dev_position.setText(item.getCareerDirectionName());
//        holder.tv_to_pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onItemClick(position);
//                }
//            }
//        });
        holder.tv_to_pay.setVisibility(item.getOrderStatus() == 1 ? View.VISIBLE : View.GONE);
        //1、待支付 2、待服务 3、服务中  4、待结算 5、已完成 6、已取消
        switch (item.getOrderStatus()) {
            case 1:// 1、待支付
            case 2:// 2、待服务
            case 5:// 5、已完成
                holder.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.color_FB8B39));
                break;
            case 3://3、服务中
                holder.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.color_F5313D));
                break;
            case 4://4、待结算
                holder.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.color_5CE28A));
                break;
            case 6://6、已取消
                holder.tv_order_status.setTextColor(mContext.getResources().getColor(R.color.color_b5bed3));
                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_order_num;
        TextView tv_order_status;
        TextView tv_order_time;
        TextView tv_order_dev_name;
        TextView tv_order_dev_position;
        TextView tv_to_pay;

    }

}


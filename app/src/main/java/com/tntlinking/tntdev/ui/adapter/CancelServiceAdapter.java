package com.tntlinking.tntdev.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetCancellationApi;

import java.util.List;


public final class CancelServiceAdapter extends BaseAdapter {

    private List<GetCancellationApi.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public CancelServiceAdapter(Context context, List<GetCancellationApi.ListBean> list) {
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

    public void setData(List<GetCancellationApi.ListBean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_cancel_service, null);
            holder = new ViewHolder();
            holder.tv_task_name = convertView.findViewById(R.id.tv_task_name);
            holder.tv_task_description = convertView.findViewById(R.id.tv_task_description);
            holder.iv_tips = convertView.findViewById(R.id.iv_tips);
            holder.tv_tips = convertView.findViewById(R.id.tv_tips);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetCancellationApi.ListBean bean = mList.get(position);
        holder.tv_task_name.setText(bean.getConditionTitle());
        holder.tv_task_description.setText(bean.getConditionDescribe());
        if (bean.isFlag()){
            holder.iv_tips.setImageResource(R.drawable.icon_cancel_true);
            holder.tv_tips.setText("已通过");
            holder.tv_tips.setTextColor(mContext.getResources().getColor(R.color.color_5CE28A));
        }else {
            holder.iv_tips.setImageResource(R.drawable.icon_cancel_false);
            holder.tv_tips.setText("未通过");
            holder.tv_tips.setTextColor(mContext.getResources().getColor(R.color.color_F5313D));
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_task_name;
        TextView tv_task_description;
        ImageView iv_tips;
        TextView tv_tips;

    }


}


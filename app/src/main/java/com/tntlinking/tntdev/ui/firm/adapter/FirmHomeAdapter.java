package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.other.GlideUtils;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


public final class FirmHomeAdapter extends BaseAdapter {

    private List<String> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public FirmHomeAdapter(Context context) {
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
            convertView = layoutInflater.inflate(R.layout.item_firm_home, null);
            holder = new ViewHolder();
            holder.iv_position_avatar = convertView.findViewById(R.id.iv_position_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_position = convertView.findViewById(R.id.tv_position);
            holder.view_dot = convertView.findViewById(R.id.view_dot);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.ll_status = convertView.findViewById(R.id.ll_status);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = mList.get(position);
        holder.tv_position.setText(item);

        GlideUtils.loadRoundCorners(mContext, R.drawable.update_app_top_bg, holder.iv_position_avatar,
                (int) mContext.getResources().getDimension(R.dimen.dp_8));
        return convertView;
    }

    class ViewHolder {
        ImageView iv_position_avatar;
        TextView tv_name;
        TextView tv_position;
        View view_dot;
        TextView tv_status;
        LinearLayout ll_status;
        TextView tv_salary;

    }


}


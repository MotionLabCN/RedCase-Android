package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetFavoriteDeveloperApi;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.List;


public final class CollectPositionAdapter extends BaseAdapter {

    private List<GetFavoriteDeveloperApi.Bean.ListBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public CollectPositionAdapter(Context context) {
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
    public GetFavoriteDeveloperApi.Bean.ListBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<GetFavoriteDeveloperApi.Bean.ListBean> getData() {
        return mList;
    }

    public void setData(List<GetFavoriteDeveloperApi.Bean.ListBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_collect_position, null);
            holder = new ViewHolder();
            holder.iv_position_avatar = convertView.findViewById(R.id.iv_position_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.view_dot = convertView.findViewById(R.id.view_dot);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.ll_status = convertView.findViewById(R.id.ll_status);
            holder.tv_all_day = convertView.findViewById(R.id.tv_all_day);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);
            holder.tv_position = convertView.findViewById(R.id.tv_position);
            holder.tag_flow_layout = convertView.findViewById(R.id.tag_flow_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFavoriteDeveloperApi.Bean.ListBean item = mList.get(position);
        GlideUtils.loadRoundCorners(mContext, item.getAvatarUrl(), holder.iv_position_avatar, (int) mContext.getResources().getDimension(R.dimen.dp_8));
        holder.tv_name.setText(item.getRealName());
        holder.tv_position.setText(item.getCareerDirectionName() + "·工作经验" + item.getWorkYears());
//        holder.tv_salary.setText(item.getExpectSalary() + "");
        holder.tv_salary.setText((Utils.formatMoney(item.getExpectSalary() / 1000) + "k/月"));
        holder.tv_all_day.setText(item.getWorkMode());

        //2 3 4 显示已签单  其他 显示待签单
        if (item.getStatus() == 2 || item.getStatus() == 3 || item.getStatus() == 4) {
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_5CE28A));
            holder.view_dot.setBackgroundResource(R.drawable.dot_oval_green);
            holder.ll_status.setBackgroundResource(R.drawable.bg_green_radius_3);
            holder.tv_status.setText("已签单");
        } else {
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_FB8B39));
            holder.view_dot.setBackgroundResource(R.drawable.dot_oval_orange);
            holder.ll_status.setBackgroundResource(R.drawable.bg_orange_radius_3);
            holder.tv_status.setText("待签单");

        }
        TagFirmAdapter adapter = new TagFirmAdapter(mContext, 2);
        holder.tag_flow_layout.setAdapter(adapter);
        //标签显示最多4个
        if (item.getSkillNameList() != null && item.getSkillNameList().size() != 0) {
            if (item.getSkillNameList().size() > 4) {
                List<String> strings = item.getSkillNameList().subList(0, 4);
                adapter.onlyAddAll(strings);
            } else {
                adapter.onlyAddAll(item.getSkillNameList());
            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_position_avatar;
        TextView tv_name;
        View view_dot;
        TextView tv_status;
        LinearLayout ll_status;
        TextView tv_all_day;
        TextView tv_salary;
        TextView tv_position;
        FlowTagLayout tag_flow_layout;
    }


}


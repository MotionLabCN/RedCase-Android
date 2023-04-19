package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetFirmDevApi;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.TagAdapter;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class FirmHomeAdapter extends BaseAdapter {

    private List<GetFirmDevApi.Bean.ListBean> mList;
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

    public List<GetFirmDevApi.Bean.ListBean> getData() {
        return mList;
    }

    public void setData(List<GetFirmDevApi.Bean.ListBean> list) {
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
            holder.iv_avatar = convertView.findViewById(R.id.iv_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_position = convertView.findViewById(R.id.tv_position);
            holder.tv_all_day = convertView.findViewById(R.id.tv_all_day);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);
            holder.tag_flow_layout = convertView.findViewById(R.id.tag_flow_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GetFirmDevApi.Bean.ListBean item = mList.get(position);

        GlideUtils.loadRoundCorners(mContext, item.getAvatarUrl(), holder.iv_avatar,
                (int) mContext.getResources().getDimension(R.dimen.dp_8));
        holder.tv_name.setText(item.getRealName());
        holder.tv_all_day.setText(item.getWorkDayModeName());
        holder.tv_salary.setText((Utils.formatMoney(item.getExpectSalary() / 1000) + "k/月"));
        holder.tv_position.setText(item.getCareerDirectionName() + "·工作经验" + item.getWorkYearsName() + "·" + item.getEducationName());

        TagFirmAdapter adapter = new TagFirmAdapter(mContext, 2);
        holder.tag_flow_layout.setAdapter(adapter);
        if (item.getSkillName().contains(",")) {
            String[] split = item.getSkillName().split(",");
            List<String> strings = Arrays.asList(split);
            if (strings.size() > 4) {
                adapter.onlyAddAll(strings.subList(0, 4));
            } else {
                adapter.onlyAddAll(strings);
            }

        } else {
            adapter.onlyAddAll(Arrays.asList(item.getSkillName()));
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_position;
        TextView tv_all_day;
        TextView tv_salary;
        FlowTagLayout tag_flow_layout;
    }

}


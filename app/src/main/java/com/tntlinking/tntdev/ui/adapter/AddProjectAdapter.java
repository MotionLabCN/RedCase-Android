package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.List;


public final class AddProjectAdapter extends BaseAdapter {

    private List<DeveloperInfoBean.DeveloperProject> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public AddProjectAdapter(Context context, List<DeveloperInfoBean.DeveloperProject> list) {
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

    public void setData(List<DeveloperInfoBean.DeveloperProject> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_tab_project, null);
            holder = new ViewHolder();
            holder.tv_project_name = convertView.findViewById(R.id.tv_project_name);
            holder.tv_project_time = convertView.findViewById(R.id.tv_project_time);
            holder.tv_character = convertView.findViewById(R.id.tv_character);
            holder.tv_company_name = convertView.findViewById(R.id.tv_company_name);
            holder.tv_description = convertView.findViewById(R.id.tv_description);
            holder.tag_flow_layout = convertView.findViewById(R.id.tag_flow_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DeveloperInfoBean.DeveloperProject bean = mList.get(position);
        holder.tv_project_name.setText(bean.getProjectName());
        holder.tv_project_time.setText(bean.getProjectStartDate() + "-" + bean.getProjectEndDate());
        holder.tv_character.setText(bean.getProjectStartDate()  + " | " + bean.getWorkModeName());
        holder.tv_company_name.setText(bean.getCompanyName() + " | " + bean.getIndustryName());
        holder.tv_description.setText(bean.getDescription());

        TagAdapter adapter = new TagAdapter(mContext);
        holder.tag_flow_layout.setAdapter(adapter);
        adapter.onlyAddAll(bean.getProjectSkillList());
        return convertView;
    }

    class ViewHolder {
        TextView tv_project_name;
        TextView tv_project_time;
        TextView tv_character;
        TextView tv_company_name;
        TextView tv_description;
        FlowTagLayout tag_flow_layout;

    }


}


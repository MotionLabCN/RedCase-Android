package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetDeveloperRecommendsApi;
import com.tntlinking.tntdev.other.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecommendationAdapter extends BaseAdapter {
    private final List<String> mStringArrayList = new ArrayList<>();
    private List<GetDeveloperRecommendsApi.Bean> mList;
    private final LayoutInflater layoutInflater;
    private final Context mContext;
    private String StartPay;
    private String EndPay;

    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemSelectListener) {
        this.mOnItemClickListener = mOnItemSelectListener;
    }
    public RecommendationAdapter(Context context, List<GetDeveloperRecommendsApi.Bean> list) {
        this.mContext = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GetDeveloperRecommendsApi.Bean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<GetDeveloperRecommendsApi.Bean> list) {
        if (list != null) {
            this.mList = list;
            notifyDataSetChanged();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendationAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.position_item, null);
            holder = new ViewHolder();
            holder.tv_position_name = convertView.findViewById(R.id.tv_position_name);
            holder.tv_work_pattern = convertView.findViewById(R.id.tv_work_pattern);
            holder.tv_job_skills_name = convertView.findViewById(R.id.tv_job_skills_name);
            holder.tv_job_skills_name2 = convertView.findViewById(R.id.tv_job_skills_name2);
            holder.tv_job_skills_name3 = convertView.findViewById(R.id.tv_job_skills_name3);
            holder.tv_academic_degree = convertView.findViewById(R.id.tv_academic_degree);
            holder.tv_work_experience = convertView.findViewById(R.id.tv_work_experience);
            holder.tv_recommend = convertView.findViewById(R.id.tv_recommend);
            holder.tv_salary = convertView.findViewById(R.id.tv_salary);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_professional_title = convertView.findViewById(R.id.tv_professional_title);
            holder.tv_company = convertView.findViewById(R.id.tv_company);
            holder.iv_recommend = convertView.findViewById(R.id.iv_recommend);
            holder.iv_close = convertView.findViewById(R.id.iv_close);
            holder.rv_job_requirements = convertView.findViewById(R.id.rv_job_requirements);
            convertView.setTag(holder);
        } else {
            holder = (RecommendationAdapter.ViewHolder) convertView.getTag();
        }
        GetDeveloperRecommendsApi.Bean item = mList.get(position);
        holder.tv_position_name.setText(item.getTitle());
        holder.tv_work_pattern.setText(item.getWorkDaysModeName());
        holder.tv_academic_degree.setText(item.getEducationName());
        holder.tv_work_experience.setText(item.getWorkYearsName());
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        double startPay = item.getStartPay() / 1000;
        StartPay = decimalFormat.format(startPay);
        double endPay = item.getEndPay() / 1000;
        EndPay = decimalFormat.format(endPay);
        holder.tv_salary.setText(StartPay + "-" + EndPay + "k/月");
        holder.tv_content.setText(item.getDescription());
        if (item.getCompanyRecruiterRealName() != null && item.getCompanyRecruiterRealName().length() > 2) {
            String RealName = item.getCompanyRecruiterRealName().substring(1);
            holder.tv_name.setText(RealName);
        } else {
            holder.tv_name.setText(item.getCompanyRecruiterRealName());
        }
        if (TextUtils.isEmpty(item.getCompanyRecruiterPosition())) {
            holder.tv_professional_title.setText(item.getCompanyRecruiterRealName());
        } else {
            holder.tv_professional_title.setText(item.getCompanyRecruiterRealName() + "·" + item.getCompanyRecruiterPosition());
        }
        holder.tv_company.setText(item.getCompanyName());
        if (item.getSelfRecommendStatus()) {
            holder.tv_recommend.setVisibility(View.VISIBLE);
        } else {
            holder.tv_recommend.setVisibility(View.GONE);
        }
        if (item.getRecommendByOperate()) {
            holder.iv_recommend.setVisibility(View.VISIBLE);
        } else {
            holder.iv_recommend.setVisibility(View.GONE);
        }
        //将正常的manager替换为FlexboxLayoutManager
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        holder.rv_job_requirements.setLayoutManager(layoutManager);
        mStringArrayList.clear();
        mStringArrayList.addAll(item.getSkillNames());
        JobRequirementsAdapter adapter = new JobRequirementsAdapter(mContext, mStringArrayList);
        holder.rv_job_requirements.setAdapter(adapter);

        holder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        return convertView;
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    static class ViewHolder {
        TextView tv_position_name;
        TextView tv_work_pattern;
        TextView tv_academic_degree;
        TextView tv_work_experience;
        TextView tv_recommend;
        TextView tv_salary;
        TextView tv_content;
        TextView tv_name;
        TextView tv_professional_title;
        TextView tv_company;
        TextView tv_job_skills_name;
        TextView tv_job_skills_name2;
        TextView tv_job_skills_name3;
        ImageView iv_recommend;
        ImageView iv_close;
        RecyclerView rv_job_requirements;
    }


}


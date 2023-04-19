package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class AppListAdapter extends AppAdapter<AppListApi.Bean> {
    public static final int APP_LIST_TAB = 1;
    public static final int APP_HISTORY_LIST_TAB = 2;
    public static final int ADD_HISTORY_TAB = 3;
    public static final int ADD_HISTORY_TAB_EMPTY = 4;

    public Context mContext;

    public AppListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        return getData().get(position).getType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case APP_LIST_TAB:
                return new Tab1ViewHolder();
            case ADD_HISTORY_TAB:
                return new Tab3ViewHolder();
            case APP_HISTORY_LIST_TAB:
                return new Tab2ViewHolder();
            case ADD_HISTORY_TAB_EMPTY:
                return new Tab4ViewHolder();
            default:
                throw new IllegalArgumentException("are you ok?");
        }
    }


    /**
     * 主页工作list
     */
    private final class Tab1ViewHolder extends ViewHolder {
        private final TextView tv_position_name;
        private final TextView tv_company_name;
        private final TextView tv_work_info;
        private final LinearLayout ll_status;
        private final TextView tv_status;
        private final TextView tv_type;
        private final TextView tv_interview;
        private final View view_dot;

        private Tab1ViewHolder() {
            super(R.layout.item_app_list);
            tv_position_name = findViewById(R.id.tv_position_name);
            tv_company_name = findViewById(R.id.tv_company_name);
            tv_work_info = findViewById(R.id.tv_work_info);
            ll_status = findViewById(R.id.ll_status);
            tv_status = findViewById(R.id.tv_status);
            tv_type = findViewById(R.id.tv_type);
            tv_interview = findViewById(R.id.tv_interview);
            view_dot = findViewById(R.id.view_dot);

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            AppListApi.Bean item = getItem(position);
            if (!TextUtils.isEmpty(item.getServiceName())) { // 订单列表
                tv_position_name.setText(item.getPositionName());
                tv_company_name.setText(item.getCompanyName());
                tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getWorkStartDate()) + "—" + Utils.getYearFromDate(item.getFinishDate()));
                tv_status.setText(item.getServiceName());
                tv_type.setText("写日报");
                if (item.getServiceName().equals("服务中")) { // 服务中
                    view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_orange));
                    ll_status.setBackground(mContext.getDrawable(R.drawable.bg_orange_radius_3));
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_type.setBackground(mContext.getDrawable(R.drawable.btn_blue_radius_10));
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.color_fb839));
                } else { // 待服务
                    view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_gray));
                    ll_status.setBackground(mContext.getDrawable(R.drawable.bg_gray_radius_3));
                    tv_type.setTextColor(mContext.getResources().getColor(R.color.color_C1C6D2));
                    tv_type.setBackground(mContext.getDrawable(R.drawable.btn_gray_radius_10));
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.gray));
                }
            } else {// 面试邀约
                tv_position_name.setText(item.getPositionName());
                tv_company_name.setText(item.getCompanyName());
                tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getInterviewStartDate()) + " " + Utils.getHoursAndMin(item.getInterviewStartDate()) + "—" + Utils.getHoursAndMin(item.getInterviewEndDate()));
                tv_type.setText("详情");
                if (item.getInterviewTimeType().equals("今日面试")) {
                    tv_status.setText(item.getInterviewTimeType());
                    tv_interview.setVisibility(View.VISIBLE);
                    view_dot.setBackground(mContext.getDrawable(R.drawable.dot_oval_blue));
                    ll_status.setBackground(mContext.getDrawable(R.drawable.bg_white_radius_3));
                    tv_status.setTextColor(mContext.getResources().getColor(R.color.main_color));
                } else {
                    tv_interview.setVisibility(View.GONE);
                    ll_status.setVisibility(View.GONE);
                }
            }

        }
    }

    /**
     * 主页历史工作list
     */
    private final class Tab2ViewHolder extends ViewHolder {

        private final TextView tv_position_name;
        private final TextView tv_company_name;
        private final TextView tv_work_info;

        private Tab2ViewHolder() {
            super(R.layout.item_history_list);
            tv_position_name = findViewById(R.id.tv_position_name);
            tv_company_name = findViewById(R.id.tv_company_name);
            tv_work_info = findViewById(R.id.tv_work_info);

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {

            AppListApi.Bean item = getItem(position);
            tv_position_name.setText(item.getPositionName());
            tv_company_name.setText(item.getCompanyName());
            tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getWorkStartDate()) + "—" + Utils.getYearFromDate(item.getFinishDate()));


        }

    }

    /**
     * 主页历史工作tab
     */
    private final class Tab3ViewHolder extends ViewHolder {
        private Tab3ViewHolder() {
            super(R.layout.item_history_tab);
        }

        @Override
        public void onBindView(int position) {


        }

    }

    /**
     * 主页历史工作tab 空白页面
     */
    private final class Tab4ViewHolder extends ViewHolder {
        private Tab4ViewHolder() {
            super(R.layout.item_history_tab_empty);
        }

        @Override
        public void onBindView(int position) {


        }

    }

}
package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class HistoryOrderAdapter extends AppAdapter<AppListApi.Bean> {

    public HistoryOrderAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_position_name;
        private final TextView tv_company_name;
        private final TextView tv_work_info;
        private final TextView tv_order_id;


        private ViewHolder() {
            super(R.layout.item_history_list);
            tv_position_name = findViewById(R.id.tv_position_name);
            tv_company_name = findViewById(R.id.tv_company_name);
            tv_work_info = findViewById(R.id.tv_work_info);
            tv_order_id = findViewById(R.id.tv_order_id);


        }

        @Override
        public void onBindView(int position) {
            AppListApi.Bean item = getItem(position);

            tv_position_name.setText(item.getPositionName());
            tv_company_name.setText(item.getCompanyName());
            tv_work_info.setText(item.getWorkDaysModeName() + " | " + Utils.getYearFromDate(item.getWorkStartDate()) + "—" + Utils.getYearFromDate(item.getFinishDate()));
            tv_order_id.setText("订单号：" + item.getOrderNo());
        }
    }
}
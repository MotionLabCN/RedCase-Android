package com.tntlinking.tntdev.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.developerBillDetailApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class IncomeDetailAdapter extends AppAdapter<developerBillDetailApi.Bean.ListBean> {

    public IncomeDetailAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_order_id;
        private final TextView tv_service_time;
        private final TextView tv_service_days;
        private final TextView tv_service_account;
        private final TextView tv_withhold_amount;
        private final TextView tv_withhold_reason;
        private final TextView tv_actual_money;

        private ViewHolder() {
            super(R.layout.income_detail_item);

            tv_order_id = findViewById(R.id.tv_order_id);
            tv_service_time = findViewById(R.id.tv_service_time);
            tv_service_days = findViewById(R.id.tv_service_days);
            tv_service_account = findViewById(R.id.tv_service_account);
            tv_withhold_amount = findViewById(R.id.tv_withhold_amount);
            tv_withhold_reason = findViewById(R.id.tv_withhold_reason);
            tv_actual_money = findViewById(R.id.tv_actual_money);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            developerBillDetailApi.Bean.ListBean item = getItem(position);
            tv_order_id.setText(item.getOrderNo());
            tv_service_time.setText(Utils.getYearFromDate(item.getWorkStartDate()) + " — " + Utils.getYearFromDate(item.getFinishDate()));
            tv_service_days.setText(item.getDays() + "个工作日");
            tv_service_account.setText("¥"+item.getTotalAmount());
            if (item.getRefundMoney()>0){
                tv_withhold_amount.setText("+¥"+item.getRefundMoney());
            }else {
                tv_withhold_amount.setText("-¥"+Math.abs(item.getRefundMoney()));
            }

            if (TextUtils.isEmpty(item.getRefundReason())) {
                tv_withhold_reason.setText("-");
            } else {
                tv_withhold_reason.setText(item.getRefundReason());
            }
            tv_actual_money.setText("¥"+item.getActualMoney());
        }
    }


}
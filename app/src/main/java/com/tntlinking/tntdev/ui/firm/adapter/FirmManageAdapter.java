package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class FirmManageAdapter extends AppAdapter<developerBillListApi.Bean.ListBean> {

    public FirmManageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_create_time;
        private final TextView tv_serviceMoney;
        private final TextView tv_deductMoney;
        private final TextView tv_personalTax;
        private final TextView tv_actualMoney;


        private ViewHolder() {
            super(R.layout.item_firm_manage);
            tv_create_time = findViewById(R.id.tv_create_time);
            tv_serviceMoney = findViewById(R.id.tv_serviceMoney);
            tv_deductMoney = findViewById(R.id.tv_deductMoney);
            tv_personalTax = findViewById(R.id.tv_personalTax);
            tv_actualMoney = findViewById(R.id.tv_actualMoney);

        }

        @Override
        public void onBindView(int position) {
            developerBillListApi.Bean.ListBean item = getItem(position);
            tv_create_time.setText(Utils.getYearFromDate(item.getCreateDate()));
            tv_serviceMoney.setText("¥"+item.getServiceMoney());
        }
    }
}
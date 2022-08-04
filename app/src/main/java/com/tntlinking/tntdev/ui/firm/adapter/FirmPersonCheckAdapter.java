package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.developerBillListApi;

import androidx.annotation.NonNull;


public final class FirmPersonCheckAdapter extends AppAdapter<developerBillListApi.Bean.ListBean> {

    public FirmPersonCheckAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_serviceMoney;
        private final TextView tv_deductMoney;
        private final TextView tv_personalTax;
        private final TextView tv_actualMoney;


        private ViewHolder() {
            super(R.layout.item_firm_person_check);
            tv_serviceMoney = findViewById(R.id.tv_serviceMoney);
            tv_deductMoney = findViewById(R.id.tv_deductMoney);
            tv_personalTax = findViewById(R.id.tv_personalTax);
            tv_actualMoney = findViewById(R.id.tv_actualMoney);

        }

        @Override
        public void onBindView(int position) {
            developerBillListApi.Bean.ListBean item = getItem(position);
            tv_serviceMoney.setText("¥"+item.getServiceMoney());
        }
    }
}
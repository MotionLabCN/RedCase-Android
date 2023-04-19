package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.FirmMemberToBeAuditedApi;

import androidx.annotation.NonNull;


public final class FirmPersonCheckAdapter extends AppAdapter<FirmMemberToBeAuditedApi.Bean.ListBean> {

    public FirmPersonCheckAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_name;
        private final TextView tv_position;
        private final TextView tv_mobile;


        private ViewHolder() {
            super(R.layout.item_firm_person_check);
            tv_name = findViewById(R.id.tv_name);
            tv_position = findViewById(R.id.tv_position);
            tv_mobile = findViewById(R.id.tv_mobile);

        }

        @Override
        public void onBindView(int position) {
            FirmMemberToBeAuditedApi.Bean.ListBean item = getItem(position);
            tv_name.setText(item.getRealName());
            tv_position.setText(item.getPositionName());
            tv_mobile.setText(item.getMobile());
        }
    }
}
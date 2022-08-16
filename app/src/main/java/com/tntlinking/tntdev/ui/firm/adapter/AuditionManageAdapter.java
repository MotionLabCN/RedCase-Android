package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetFirmInterviewListApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class AuditionManageAdapter extends AppAdapter<GetFirmInterviewListApi.Bean.ListBean> {

    public AuditionManageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_create_time;
        private final TextView tv_time;
        private final TextView tv_position;
        private final TextView tv_name;
        private final TextView tv_link;


        private ViewHolder() {
            super(R.layout.item_audition_manage);
            tv_create_time = findViewById(R.id.tv_create_time);
            tv_time = findViewById(R.id.tv_time);
            tv_position = findViewById(R.id.tv_position);
            tv_name = findViewById(R.id.tv_name);
            tv_link = findViewById(R.id.tv_link);



        }

        @Override
        public void onBindView(int position) {
            GetFirmInterviewListApi.Bean.ListBean item = getItem(position);
            tv_create_time.setText(item.getDayType());
            tv_time.setText(Utils.getYearFromDate(item.getInterviewStartDate()));
            tv_position.setText(Utils.getYearFromDate(item.getTitle()));
            tv_name.setText(Utils.getYearFromDate(item.getRealName()));
            tv_link.setText(Utils.getYearFromDate(item.getMeetingUrl()));

        }
    }
}
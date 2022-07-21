package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetHistoryListApi;
import com.tntlinking.tntdev.other.Utils;

import java.util.List;

import androidx.annotation.NonNull;


public final class HistoryDailyAdapter extends AppAdapter<GetHistoryListApi.Bean> {

    public HistoryDailyAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {
        private final TextView tv_time;
        private final ListView lv_1;
        private final ListView lv_2;
        private final ListView lv_3;
        private final ListView lv_4;


        private ViewHolder() {
            super(R.layout.item_history_daily_list);
            tv_time = findViewById(R.id.tv_time);
            lv_1 = findViewById(R.id.lv_1);
            lv_2 = findViewById(R.id.lv_2);
            lv_3 = findViewById(R.id.lv_3);
            lv_4 = findViewById(R.id.lv_4);


        }

        @Override
        public void onBindView(int position) {
            GetHistoryListApi.Bean item = getItem(position);
            tv_time.setText(item.getDateOf());
            List<GetHistoryListApi.Bean.CommonBean> done = item.getDone();
            List<GetHistoryListApi.Bean.CommonBean> running = item.getRunning();
            List<GetHistoryListApi.Bean.CommonBean> future = item.getFuture();
            List<GetHistoryListApi.Bean.CommonBean> help = item.getHelp();
            HistoryDailyItemAdapter doneAdapter = new HistoryDailyItemAdapter(getContext(), done);
            HistoryDailyItemAdapter runningAdapter = new HistoryDailyItemAdapter(getContext(), running);
            HistoryDailyItemAdapter futureAdapter = new HistoryDailyItemAdapter(getContext(), future);
            HistoryDailyItemAdapter helpAdapter = new HistoryDailyItemAdapter(getContext(), help);
            lv_1.setAdapter(doneAdapter);
            lv_2.setAdapter(runningAdapter);
            lv_3.setAdapter(futureAdapter);
            lv_4.setAdapter(helpAdapter);
        }
    }
}
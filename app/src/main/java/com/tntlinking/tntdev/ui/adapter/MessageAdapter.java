package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetMessageListApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.other.Utils;

import androidx.annotation.NonNull;


public final class MessageAdapter extends AppAdapter<GetMessageListApi.Bean.ListBean> {

    public MessageAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_message;
        private final TextView tv_message_time;

        private ViewHolder() {
            super(R.layout.item_message_list);
            tv_message = findViewById(R.id.tv_message);
            tv_message_time = findViewById(R.id.tv_message_time);
        }

        @Override
        public void onBindView(int position) {
            GetMessageListApi.Bean.ListBean item = getItem(position);
            tv_message.setText(item.getMessageStr());
            tv_message_time.setText(item.getCreateTime());


        }
    }
}
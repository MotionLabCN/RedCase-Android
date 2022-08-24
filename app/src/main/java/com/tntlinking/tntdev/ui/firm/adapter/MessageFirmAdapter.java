package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetFirmMessageListApi;

import androidx.annotation.NonNull;


public final class MessageFirmAdapter extends AppAdapter<GetFirmMessageListApi.Bean.ListBean> {

    public MessageFirmAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_message_content;
        private final TextView tv_message_time;
        private final TextView tv_message_status;
        private final View view_line;

        private ViewHolder() {
            super(R.layout.item_message_list);
            tv_message_content = findViewById(R.id.tv_message_content);
            tv_message_time = findViewById(R.id.tv_message_time);
            tv_message_status = findViewById(R.id.tv_message_status);
            view_line = findViewById(R.id.view_line);
        }

        @Override
        public void onBindView(int position) {
            GetFirmMessageListApi.Bean.ListBean item = getItem(position);
            tv_message_content.setText(item.getMessageStr());
            tv_message_time.setText(item.getSendTime());
            switch (item.getMessageType()) {
                case 5:
                case 6:
                    view_line.setVisibility(View.VISIBLE);
                    tv_message_status.setVisibility(View.VISIBLE);
                    tv_message_status.setText("查看修改建议");
                    break;
                case 7:
                    view_line.setVisibility(View.VISIBLE);
                    tv_message_status.setVisibility(View.VISIBLE);
                    tv_message_status.setText("去面试");
                    break;
                default:
                    view_line.setVisibility(View.GONE);
                    tv_message_status.setVisibility(View.GONE);
                    break;
            }


        }
    }
}
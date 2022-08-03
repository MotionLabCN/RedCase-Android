package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetMessageListApi;

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
            GetMessageListApi.Bean.ListBean item = getItem(position);
            tv_message_content.setText(item.getMessageStr());
            tv_message_time.setText(item.getCreateTime());
            switch (item.getMessageType()) {// 2 面试邀约 3 面试提醒 4面试取消 5 入驻通过 6 入驻未通过
                case 2:
                case 3:
                    view_line.setVisibility(View.VISIBLE);
                    tv_message_status.setVisibility(View.VISIBLE);
                    tv_message_status.setText("面试详情");
                    break;
                case 4:
                    break;
                case 5:
                    view_line.setVisibility(View.VISIBLE);
                    tv_message_status.setVisibility(View.VISIBLE);
                    tv_message_status.setText("查看职位");
                    break;
                case 6:
                    view_line.setVisibility(View.VISIBLE);
                    tv_message_status.setVisibility(View.VISIBLE);
                    tv_message_status.setText("去修改");
                    break;
                default:
                    view_line.setVisibility(View.GONE);
                    tv_message_status.setVisibility(View.GONE);
                    break;
            }


        }
    }
}
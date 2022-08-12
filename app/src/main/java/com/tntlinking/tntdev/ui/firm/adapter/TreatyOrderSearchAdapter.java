package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetFirmOrderListApi;
import androidx.annotation.NonNull;


public final class TreatyOrderSearchAdapter extends AppAdapter<GetFirmOrderListApi.Bean.ListBean> {

    public TreatyOrderSearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final TextView tv_order_num;
        private final TextView tv_order_status;
        private final TextView tv_order_time;
        private final TextView tv_order_dev_name;
        private final TextView tv_order_dev_position;
        private final TextView  tv_to_pay;

        private ViewHolder() {
            super(R.layout.item_treatry_order);
            tv_order_num = findViewById(R.id.tv_order_num);
            tv_order_status = findViewById(R.id.tv_order_status);
            tv_order_time = findViewById(R.id.tv_order_time);
            tv_order_dev_name = findViewById(R.id.tv_order_dev_name);
            tv_order_dev_position = findViewById(R.id.tv_order_dev_position);
            tv_to_pay = findViewById(R.id.tv_to_pay);
        }

        @Override
        public void onBindView(int position) {
            GetFirmOrderListApi.Bean.ListBean item = getItem(position);

            tv_order_num.setText("订单号：" + item.getOrderNo());
            tv_order_status.setText(item.getOrderStatusName());
            tv_order_time.setText(item.getWorkStartDate());
            tv_order_dev_name.setText(item.getRealName());
            tv_order_dev_position.setText(item.getCareerDirectionName());
            tv_to_pay.setVisibility(item.getOrderStatus() == 1 ? View.VISIBLE : View.GONE);
            switch (item.getOrderStatus()) {
                case 1:// 1、待支付
                case 2:// 2、待服务
                case 5:// 5、已完成
                    tv_order_status.setTextColor(getResources().getColor(R.color.color_FB8B39));
                    break;
                case 3://3、服务中
                    tv_order_status.setTextColor(getResources().getColor(R.color.color_F5313D));
                    break;
                case 4://4、待结算
                    tv_order_status.setTextColor(getResources().getColor(R.color.color_5CE28A));
                    break;
                case 6://6、已取消
                   tv_order_status.setTextColor(getResources().getColor(R.color.color_b5bed3));
                    break;
            }
        }
    }
}
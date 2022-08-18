package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateOrderApi;
import com.tntlinking.tntdev.other.Utils;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约支付页面
 */
public final class ContractPayActivity extends AppActivity {
    private TextView tv_work_money;
    private TextView tv_work_time_1;
    private TextView tv_work_time_money_1;
    private TextView tv_work_time_2;
    private TextView tv_work_time_money_2;
    private LinearLayout ll_date_1;
    private LinearLayout ll_date_2;
    private TextView tv_work_service_money;
    private TextView tv_work_freeze_money;
    private AppCompatButton btn_commit;


    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_activity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        tv_work_money = findViewById(R.id.tv_work_money);
        tv_work_time_1 = findViewById(R.id.tv_work_time_1);
        tv_work_time_money_1 = findViewById(R.id.tv_work_time_money_1);
        tv_work_time_2 = findViewById(R.id.tv_work_time_2);
        tv_work_time_money_2 = findViewById(R.id.tv_work_time_money_2);
        ll_date_1 = findViewById(R.id.ll_date_1);
        ll_date_2 = findViewById(R.id.ll_date_2);
        tv_work_freeze_money = findViewById(R.id.tv_work_freeze_money);
        tv_work_service_money = findViewById(R.id.tv_work_service_money);
        btn_commit = findViewById(R.id.btn_commit);

        CreateOrderApi.Bean bean = getSerializable("PayInfoBean");
        CreateOrderApi.Bean.PayInfoBean payInfo = bean.getPayInfo();
        if (payInfo.getPreOrderList().size() != 0) {
            if (payInfo.getPreOrderList().size() == 1) {
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean0 = payInfo.getPreOrderList().get(0);
                tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                tv_work_time_money_1.setText(Utils.formatMoney(preBean0.getServiceMoney() + ""));
                ll_date_2.setVisibility(View.GONE);
            } else if (payInfo.getPreOrderList().size() == 2) {
                ll_date_2.setVisibility(View.VISIBLE);
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean0 = payInfo.getPreOrderList().get(0);
                tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                tv_work_time_money_1.setText(Utils.formatMoney(preBean0.getServiceMoney() + ""));
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean1 = payInfo.getPreOrderList().get(1);
                tv_work_time_2.setText(preBean1.getWorkStartDate() + "-" + preBean1.getWorkEndDate());
                tv_work_time_money_2.setText(Utils.formatMoney(preBean1.getServiceMoney() + ""));

            }

            tv_work_money.setText(Utils.formatMoney(payInfo.getTotalAmount() + ""));
            tv_work_service_money.setText(Utils.formatMoney(payInfo.getServiceAmount() + ""));
            tv_work_freeze_money.setText(Utils.formatMoney(payInfo.getFreezeAmount() + ""));
        }
        setOnClickListener(btn_commit);

    }


    @Override
    protected void initData() {


    }

    @Override
    public void onLeftClick(View view) {
        showDialog();
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                Intent intent = new Intent(this, ContractPayCodeActivity.class);
                CreateOrderApi.Bean bean = getSerializable("PayInfoBean");
                intent.putExtra("orderIds", bean.getOrderIds());
                startActivity(intent);
                break;

        }

    }

    public void showDialog() {
        new BaseDialog.Builder<>(getActivity())
                .setContentView(R.layout.check_order_status_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setText(R.id.tv_title, "订单支付")
                .setText(R.id.tv_content, "您有订单还未支付，是否退出！")
                .setText(R.id.btn_dialog_custom_cancel, "取消支付")
                .setText(R.id.btn_dialog_custom_ok, "继续支付")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (dialog, views) -> {
                    dialog.dismiss();
                    finish();
                })
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                    startActivity(ContractPayCodeActivity.class);
                }).show();
    }




}
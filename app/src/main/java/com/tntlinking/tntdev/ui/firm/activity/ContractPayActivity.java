package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateOrderApi;
import com.tntlinking.tntdev.http.api.PreOrderInfoApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import java.util.List;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约支付页面
 */
public final class ContractPayActivity extends AppActivity {
    private TextView tv_count_down_time;
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
    private String orderIds;

    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_activity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        tv_count_down_time = findViewById(R.id.tv_count_down_time);
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

        setOnClickListener(btn_commit);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        int orderId = getInt("orderId"); //从合约单 列表页面点击过来的
        orderIds = orderId + "";
        if (orderId != 0) {
            getOrderInfo(orderId);
        } else {//从合约单支付详情页面过来的
            CreateOrderApi.Bean bean = getSerializable("PayInfoBean");
            orderIds = bean.getOrderIds();
            CreateOrderApi.Bean.PayInfoBean payInfo = bean.getPayInfo();
            if (payInfo.getPreOrderList().size() != 0) {
                if (payInfo.getPreOrderList().size() == 1) {
                    CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean0 = payInfo.getPreOrderList().get(0);
                    tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                    tv_work_time_money_1.setText("¥" + Utils.formatMoney(preBean0.getServiceMoney() + ""));
                    ll_date_2.setVisibility(View.GONE);
                } else if (payInfo.getPreOrderList().size() == 2) {
                    ll_date_2.setVisibility(View.VISIBLE);
                    CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean0 = payInfo.getPreOrderList().get(0);
                    tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                    tv_work_time_money_1.setText("¥" + Utils.formatMoney(preBean0.getServiceMoney() + ""));
                    CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean1 = payInfo.getPreOrderList().get(1);
                    tv_work_time_2.setText(preBean1.getWorkStartDate() + "-" + preBean1.getWorkEndDate());
                    tv_work_time_money_2.setText("¥" + Utils.formatMoney(preBean1.getServiceMoney() + ""));

                }

                tv_work_money.setText("¥" + Utils.formatMoney(payInfo.getTotalAmount() + ""));
                tv_work_service_money.setText("¥" + Utils.formatMoney(payInfo.getServiceAmount() + ""));
                tv_work_freeze_money.setText("¥" + Utils.formatMoney(payInfo.getFreezeAmount() + ""));
                btn_commit.setText("支付¥" + Utils.formatMoney(payInfo.getFreezeAmount() + ""));
            }

            timeStemp = Integer.valueOf(bean.getTime());
            getCountDownTime();
        }

    }

    @Override
    public void onLeftClick(View view) {
        showTipsDialog();
    }

    @Override
    public void onBackPressed() {
        showTipsDialog();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                Intent intent = new Intent(this, ContractPayCodeActivity.class);
                intent.putExtra("orderIds", orderIds);
                startActivity(intent);
                break;

        }

    }

    public void showTipsDialog() {
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


    //获取订单详情
    public void getOrderInfo(int orderId) {
        EasyHttp.get(this)
                .api(new PreOrderInfoApi().setOrderId(orderId))
                .request(new HttpCallback<HttpData<PreOrderInfoApi.Bean>>(this) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<PreOrderInfoApi.Bean> data) {
                        PreOrderInfoApi.Bean bean = data.getData();
                        List<PreOrderInfoApi.Bean.PreOrderListBean> preOrderList = bean.getPreOrderList();
                        if (preOrderList != null && preOrderList.size() != 0) {
                            if (preOrderList.size() == 1) {
                                PreOrderInfoApi.Bean.PreOrderListBean preBean0 = preOrderList.get(0);
                                tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                                tv_work_time_money_1.setText("¥" + Utils.formatMoney(preBean0.getServiceMoney() + ""));
                                ll_date_2.setVisibility(View.GONE);
                            } else if (preOrderList.size() == 2) {
                                PreOrderInfoApi.Bean.PreOrderListBean preBean0 = preOrderList.get(0);
                                ll_date_2.setVisibility(View.VISIBLE);
                                tv_work_time_1.setText(preBean0.getWorkStartDate() + "-" + preBean0.getWorkEndDate());
                                tv_work_time_money_1.setText("¥" + Utils.formatMoney(preBean0.getServiceMoney() + ""));
                                PreOrderInfoApi.Bean.PreOrderListBean preBean1 = preOrderList.get(1);
                                tv_work_time_2.setText(preBean1.getWorkStartDate() + "-" + preBean1.getWorkEndDate());
                                tv_work_time_money_2.setText("¥" + Utils.formatMoney(preBean1.getServiceMoney() + ""));

                            }
                            tv_work_money.setText("¥" + Utils.formatMoney(data.getData().getTotalAmount() + ""));
                            tv_work_service_money.setText("¥" + Utils.formatMoney(data.getData().getServiceAmount() + ""));
                            tv_work_freeze_money.setText("¥" + Utils.formatMoney(data.getData().getFreezeAmount() + ""));
                            btn_commit.setText("支付¥" + Utils.formatMoney(data.getData().getFreezeAmount() + ""));
                        }

                        timeStemp = Integer.valueOf(bean.getTime());
                        getCountDownTime();
                    }
                });
    }

    //24小时换算成毫秒
    private int timeStemp = 86400000;
    private CountDownTimer timer;


    private void getCountDownTime() {

        timer = new CountDownTimer(timeStemp, 1000) {
            @Override
            public void onTick(long l) {

                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                tv_count_down_time.setText("支付剩余时间  " + hour + ":" + minute + ":" + second);
            }

            @Override
            public void onFinish() {
                finish();
                //倒计时为0时执行此方法

            }
        };

        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.onFinish();
    }
}
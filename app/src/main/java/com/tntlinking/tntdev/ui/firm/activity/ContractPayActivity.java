package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateOrderApi;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约支付页面
 */
public final class ContractPayActivity extends AppActivity {
    private TextView tv_work_money;
    private TextView tv_work_time_start;
    private TextView tv_work_time_start_money;
    private TextView tv_work_time_end;
    private TextView tv_work_time_end_money;
    private TextView tv_work_service_money;
    private TextView tv_work_freeze_money;
    private AppCompatButton btn_commit;


    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_activity;
    }


    @Override
    protected void initView() {

        tv_work_money = findViewById(R.id.tv_work_money);
        tv_work_time_start = findViewById(R.id.tv_work_time_start);
        tv_work_time_start_money = findViewById(R.id.tv_work_time_start_money);
        tv_work_time_end = findViewById(R.id.tv_work_time_end);
        tv_work_time_end_money = findViewById(R.id.tv_work_time_end_money);
        tv_work_freeze_money = findViewById(R.id.tv_work_freeze_money);
        tv_work_service_money = findViewById(R.id.tv_work_service_money);
        btn_commit = findViewById(R.id.btn_commit);

        CreateOrderApi.Bean bean = getSerializable("PayInfoBean");
        CreateOrderApi.Bean.PayInfoBean payInfo = bean.getPayInfo();
        if (payInfo.getPreOrderList().size() != 0) {
            if (payInfo.getPreOrderList().size() == 1) {
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preOrderListBean = payInfo.getPreOrderList().get(0);

                tv_work_time_start.setText(preOrderListBean.getWorkStartDate() + "-" + preOrderListBean.getWorkEndDate());
                tv_work_time_start_money.setText(preOrderListBean.getServiceMoney() + "");
                tv_work_time_end.setVisibility(View.GONE);
                tv_work_time_end_money.setVisibility(View.GONE);
            } else if (payInfo.getPreOrderList().size() == 2) {
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean1 = payInfo.getPreOrderList().get(0);
                tv_work_time_start.setText(preBean1.getWorkStartDate() + "-" + preBean1.getWorkEndDate());
                tv_work_time_start_money.setText(preBean1.getServiceMoney() + "");
                CreateOrderApi.Bean.PayInfoBean.PreOrderListBean preBean2 = payInfo.getPreOrderList().get(1);
                tv_work_time_end.setText(preBean2.getWorkStartDate() + "-" + preBean2.getWorkEndDate());
                tv_work_time_end_money.setText(preBean2.getServiceMoney() + "");

            }
            tv_work_money.setText(payInfo.getTotalAmount() + "");
            tv_work_service_money.setText(payInfo.getServiceAmount() + "");
            tv_work_freeze_money.setText(payInfo.getFreezeAmount() + "");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);
        }
    }


    @SuppressLint("SetTextI18n")
    public void getDeveloperDetail(int parentId) {
        EasyHttp.get(this)
                .api(new GetDeveloperDetailApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {


                    }
                });
    }


}
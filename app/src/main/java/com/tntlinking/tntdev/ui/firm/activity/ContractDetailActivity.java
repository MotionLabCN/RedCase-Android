package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateOrderApi;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.PostCalculateApi;
import com.tntlinking.tntdev.http.api.PreOrderInfoApi;
import com.tntlinking.tntdev.http.api.SendPositionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约单页面
 */
public final class ContractDetailActivity extends AppActivity {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_work_mode;
    private TextView tv_work_salary;
    private LinearLayout ll_work_time;
    private TextView tv_work_time;
    private TextView tv_work_money;
    private TextView tv_work_time_1;
    private TextView tv_work_time_money_1;
    private TextView tv_work_time_2;
    private TextView tv_work_time_money_2;
    private TextView tv_work_service_money;
    private TextView tv_work_freeze_money;
    private LinearLayout ll_date_1;
    private LinearLayout ll_date_2;
    private AppCompatButton btn_create;

    private int positionId;
    private int developerId;
    private String selectTime;

    @Override
    protected int getLayoutId() {
        return R.layout.contract_detail_activity;
    }


    @Override
    protected void initView() {

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_work_mode = findViewById(R.id.tv_work_mode);
        tv_work_salary = findViewById(R.id.tv_work_salary);
        ll_work_time = findViewById(R.id.ll_work_time);
        tv_work_time = findViewById(R.id.tv_work_time);
        tv_work_money = findViewById(R.id.tv_work_money);
        tv_work_time_1 = findViewById(R.id.tv_work_time_1);
        tv_work_time_money_1 = findViewById(R.id.tv_work_time_money_1);
        tv_work_time_2 = findViewById(R.id.tv_work_time_2);
        tv_work_time_money_2 = findViewById(R.id.tv_work_time_money_2);
        tv_work_freeze_money = findViewById(R.id.tv_work_freeze_money);
        tv_work_service_money = findViewById(R.id.tv_work_service_money);
        ll_date_1 = findViewById(R.id.ll_date_1);
        ll_date_2 = findViewById(R.id.ll_date_2);
        btn_create = findViewById(R.id.btn_create);

        setOnClickListener(ll_work_time, btn_create);


    }


    @Override
    protected void initData() {
        positionId = getInt("positionId");
        developerId = getInt("developerId");
        String name = getString("name");
        String avatarUrl = getString("avatarUrl");

        GlideUtils.loadRoundCorners(this, avatarUrl, iv_avatar, (int) getResources().getDimension(R.dimen.dp_8));

        tv_name.setText(name);
        String nowTime = TimeUtil.getTimeString("yyyy-MM-dd");
        tv_work_time.setText(nowTime);
        EasyConfig.getInstance().addHeader("loginRole", "Recruiter");
        postCalculate(nowTime);

        if (getInt("orderId") != 0) {
            getOrderInfo(getInt("orderId"));
        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_work_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        selectTime = year + "-" + Utils.formatDate(month) + "-" + day;
                        tv_work_time.setText(selectTime);

                        postCalculate(selectTime);
                    }

                }).show();
                break;
            case R.id.btn_create:

                createOrder(selectTime);
                break;
        }

    }

    //订单前置计算
    public void postCalculate(String date) {
        EasyHttp.post(this)
                .api(new PostCalculateApi()
                        .setDeveloperId(developerId)
                        .setPositionId(positionId)
                        .setWorkStartDate(date))
                .request(new HttpCallback<HttpData<PostCalculateApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<PostCalculateApi.Bean> data) {
                        List<PostCalculateApi.Bean.PreListBean> preList = data.getData().getPreList();
                        if (preList != null && preList.size() != 0) {
                            if (preList.size() == 1) {
                                PostCalculateApi.Bean.PreListBean bean = preList.get(0);
                                tv_work_time_1.setText(bean.getWorkStartDate() + "-" + bean.getWorkEndDate());
                                tv_work_time_money_1.setText(Utils.formatMoney(bean.getServiceMoney() + ""));
                                ll_date_2.setVisibility(View.GONE);
                            } else if (preList.size() == 2) {
                                PostCalculateApi.Bean.PreListBean bean1 = preList.get(0);
                                ll_date_2.setVisibility(View.VISIBLE);
                                tv_work_time_1.setText(bean1.getWorkStartDate() + "-" + bean1.getWorkEndDate());
                                tv_work_time_money_1.setText(Utils.formatMoney(bean1.getServiceMoney() + ""));
                                PostCalculateApi.Bean.PreListBean bean2 = preList.get(1);
                                tv_work_time_2.setText(bean2.getWorkStartDate() + "-" + bean2.getWorkEndDate());
                                tv_work_time_money_2.setText(Utils.formatMoney(bean2.getServiceMoney() + ""));

                            }
                            tv_work_money.setText(Utils.formatMoney(data.getData().getTotalAmount() + ""));
                            tv_work_service_money.setText(Utils.formatMoney(data.getData().getServiceAmount() + ""));
                            tv_work_freeze_money.setText(Utils.formatMoney(data.getData().getFreezeAmount() + ""));
                        }
                    }
                });
    }


    //订单创建
    public void createOrder(String date) {
        EasyHttp.post(this)
                .api(new CreateOrderApi()
                        .setDeveloperId(developerId)
                        .setPositionId(positionId)
                        .setWorkStartDate(date))
                .request(new HttpCallback<HttpData<CreateOrderApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<CreateOrderApi.Bean> data) {
                        CreateOrderApi.Bean bean = data.getData();
                        Intent intent = new Intent(ContractDetailActivity.this, ContractPayActivity.class);
                        intent.putExtra("PayInfoBean", bean);
                        startActivity(intent);
                    }
                });
    }


    //获取订单详情
    public void getOrderInfo(int orderId) {
        EasyHttp.get(this)
                .api(new PreOrderInfoApi().setOrderId(orderId))
                .request(new HttpCallback<HttpData<PreOrderInfoApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<PreOrderInfoApi.Bean> data) {


                    }
                });
    }

}
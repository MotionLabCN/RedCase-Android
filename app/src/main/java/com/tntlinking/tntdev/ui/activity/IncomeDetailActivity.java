package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.developerBillDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.IncomeDetailAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;

import java.util.List;

/**
 * 账单明细详情页面
 */
public final class IncomeDetailActivity extends AppActivity {

    private WrapRecyclerView mRecyclerView;
    private IncomeDetailAdapter mAdapter;
    private TextView tv_work_time;
    private TextView tv_pay_time;
    private TextView tv_service_money;
    private TextView tv_personalTax;
    private TextView tv_order_count;

    @Override
    protected int getLayoutId() {
        return R.layout.icome_detail_activity;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_status_list);
        mAdapter = new IncomeDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        View headerView = mRecyclerView.addHeaderView(R.layout.item_income_detail_header);
        tv_work_time = headerView.findViewById(R.id.tv_work_time);
        tv_pay_time = headerView.findViewById(R.id.tv_pay_time);
        tv_service_money = headerView.findViewById(R.id.tv_service_money);
        tv_personalTax = headerView.findViewById(R.id.tv_personalTax);
        tv_order_count = headerView.findViewById(R.id.tv_order_count);
        headerView.setOnClickListener(v -> toast("点击了头部"));
//
//        TextView footerView = mRecyclerView.addFooterView(R.layout.picker_item);
//        footerView.setText("我是尾部");
//        footerView.setOnClickListener(v -> toast("点击了尾部"));

//        mRefreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void initData() {
        String orderId = getString("orderId");
        getBillDetail(orderId);
    }


    @SingleClick
    @Override
    public void onClick(View view) {


    }

    private void getBillDetail(String orderId) {
        EasyHttp.get(this)
                .api(new developerBillDetailApi().setDeveloperBillId(orderId))
                .request(new HttpCallback<HttpData<developerBillDetailApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<developerBillDetailApi.Bean> data) {
                        if (data.getData() != null) {

                            developerBillDetailApi.Bean bean = data.getData();
                            tv_work_time.setText(bean.getTimeStart() + " —— " + bean.getTimeEnd());
                            tv_pay_time.setText(Utils.getYearFromDate(bean.getCreateDate()));
                            tv_service_money.setText("¥"+bean.getActualMoney());
                            tv_personalTax.setText("¥"+bean.getPersonalTax());
                            tv_order_count.setText(bean.getOrderCounts());

                            List<developerBillDetailApi.Bean.ListBean> orders = data.getData().getOrders();
                            mAdapter.setData(orders);
                        }

                    }
                });
    }


}
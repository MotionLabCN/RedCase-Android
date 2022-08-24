package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetFirmOrderListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.OnItemClickListener;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.ui.firm.activity.ContractDetailActivity;
import com.tntlinking.tntdev.ui.firm.activity.ContractPayActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.adapter.TreatyOrderAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   :我的tab页面  合约单 Fragment
 */
public final class TreatyOrderFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;
    private LinearLayout ll_daily;
    private SmartRefreshLayout mRefreshLayout;
    private TreatyOrderAdapter mAdapter;


    private String orderStatus = "";
    private int mPageNum = 1;
    private static final String INTENT_KEY_POSITION = "position";

    private List<GetFirmOrderListApi.Bean.ListBean> mList = new ArrayList<>();

    public static TreatyOrderFragment newInstance(String url) {
        TreatyOrderFragment fragment = new TreatyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_KEY_POSITION, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_position_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);
        ll_daily = findViewById(R.id.ll_daily);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);


        mAdapter = new TreatyOrderAdapter(getActivity());

        lv_1.setAdapter(mAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.getItem(position).getOrderStatus() == 1) {
                    String workStartDate = mAdapter.getItem(position).getWorkStartDate();
                    long workTime = TimeUtil.getTimeLong("yyyy-MM-dd", workStartDate);
                    long nowTime = TimeUtil.getTimeLong();

                    if (workTime > nowTime) { // 开始时间大于当前时间跳转到支付页面
                        Intent intent = new Intent(getActivity(), ContractPayActivity.class);
                        intent.putExtra("orderId", mAdapter.getItem(position).getId());
                        startActivity(intent);
                    } else {// 开始时间小于当前时间 跳转到修改合约单页面
                        Intent intent = new Intent(getActivity(), ContractDetailActivity.class);
                        intent.putExtra("orderId", mAdapter.getItem(position).getId());
                        startActivity(intent);
                    }

                }

            }
        });


    }

    @Override
    protected void initData() {
        orderStatus = getString(INTENT_KEY_POSITION);
        GetDeveloperList(orderStatus, mPageNum);
    }



    /**
     * 获取职业方向
     */
    public void GetDeveloperList(String orderStatus, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmOrderListApi().setOrderStatus(orderStatus).setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetFirmOrderListApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmOrderListApi.Bean> data) {

                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                } else {
                                    mList.clear();
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishRefresh();
                            } else {
                                if (pageNum == data.getData().getPageNum()) { //当前pageNum 是否等于后台传过来的当前页pagenum 数
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }

                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPageNum = 1;
        GetDeveloperList(orderStatus, mPageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        GetDeveloperList(orderStatus, mPageNum);
    }


}
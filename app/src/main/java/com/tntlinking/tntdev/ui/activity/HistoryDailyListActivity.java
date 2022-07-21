package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetHistoryListApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.HistoryDailyAdapter;
import com.tntlinking.tntdev.ui.adapter.HistoryOrderAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public final class HistoryDailyListActivity extends AppActivity implements OnRefreshLoadMoreListener {

    private TitleBar title_bar;
    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private HistoryDailyAdapter mAdapter;
    private int pageNum = 1;
    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.message_list_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);
        title_bar.setTitle("历史日报");
        mAdapter = new HistoryDailyAdapter(this);

        mRecyclerView.setAdapter(mAdapter);


        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        orderId = getString("orderId");
    }

    @Override
    protected void initData() {
        getList(orderId);
    }


    /**
     * 获取历史服务list
     */
    private void getList(String orderId) {
        EasyHttp.get(this)
                .api(new GetHistoryListApi().setOrderId(orderId).setPageNum(1).setPageSize(20))
                .request(new HttpCallback<HttpData<List<GetHistoryListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetHistoryListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            mAdapter.setData(data.getData());

                        }
                    }

                });
    }


    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getList(orderId);
//        postDelayed(() -> {
//            mAdapter.clearData();
//            mAdapter.setData(analogData());
//            mRefreshLayout.finishRefresh();
//        }, 1000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }


}
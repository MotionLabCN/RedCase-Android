package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.base.BaseAdapter;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.IncomeAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 账单明细列表页面
 */
public final class IncomeListActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener {


    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private IncomeAdapter mAdapter;
    private List<developerBillListApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.icomelist_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new IncomeAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

//        TextView headerView = mRecyclerView.addHeaderView(R.layout.picker_item);
//        headerView.setText("我是头部");
//        headerView.setOnClickListener(v -> toast("点击了头部"));
//
//        TextView footerView = mRecyclerView.addFooterView(R.layout.picker_item);
//        footerView.setText("我是尾部");
//        footerView.setOnClickListener(v -> toast("点击了尾部"));

        mRefreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void initData() {

//        mAdapter.setData(analogData());
        getBillList(pageNum);
    }


    @SingleClick
    @Override
    public void onClick(View view) {


    }

    private void getBillList(int pageNum) {
        EasyHttp.get(this)
                .api(new developerBillListApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<developerBillListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<developerBillListApi.Bean> data) {
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
                                if (pageNum == Integer.valueOf(data.getData().getPageNum())) { //当前pageNum 是否等于后台传过来的当前页pagenum 数
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }

                    }
                });
    }


    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getCount(); i < mAdapter.getCount() + 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    /**
     * {@link BaseAdapter.OnItemClickListener}
     *
     * @param recyclerView RecyclerView对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

        Intent intent = new Intent(this, IncomeDetailActivity.class);
        intent.putExtra("orderId", mAdapter.getItem(position).getId());
        startActivity(intent);
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getBillList(pageNum);
//        postDelayed(() -> {
//            mAdapter.clearData();
//            mAdapter.setData(analogData());
//            mRefreshLayout.finishRefresh();
//        }, 1000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getBillList(pageNum);
//        postDelayed(() -> {
//            mAdapter.addData(analogData());
//            mRefreshLayout.finishLoadMore();
//
//            mAdapter.setLastPage(mAdapter.getCount() >= 100);
//            mRefreshLayout.setNoMoreData(mAdapter.isLastPage());
//        }, 1000);
    }
}
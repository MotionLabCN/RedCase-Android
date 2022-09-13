package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmInterviewHistoryApi;
import com.tntlinking.tntdev.http.api.GetFirmInterviewListApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.AuditionHistoryAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.AuditionManageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 历史面试列表页面
 */
public final class AuditionHistoryListActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {

    private TitleBar title_bar;
    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private AuditionHistoryAdapter mAdapter;
    private List<GetFirmInterviewListApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.audition_manage_history_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new AuditionHistoryAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnChildClickListener(R.id.tv_look_audition, this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

    }

    @Override
    protected void initData() {
        getFirmInterviewHistoryList(pageNum);
    }


    @SingleClick
    @Override
    public void onClick(View view) {

    }

    public void getFirmInterviewHistoryList(int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmInterviewHistoryApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetFirmInterviewListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmInterviewListApi.Bean> data) {
                        mRefreshLayout.setEnableLoadMore(true);
                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                    mRefreshLayout.setEnableLoadMore(false);
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
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Intent intent = new Intent(this, FirmAuditionDetailActivity.class);
        intent.putExtra("name", mAdapter.getItem(position).getRealName());
        intent.putExtra("time", mAdapter.getItem(position).getInterviewStartDate());
        intent.putExtra("position", mAdapter.getItem(position).getTitle());
        intent.putExtra("code", mAdapter.getItem(position).getMeetingCode());
        intent.putExtra("url", mAdapter.getItem(position).getMeetingUrl());
        startActivity(intent);

    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
        intent.putExtra("developerId", mAdapter.getItem(position).getDeveloperId());
        intent.putExtra("from", "audition_manage");
        startActivity(intent);
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getFirmInterviewHistoryList(pageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getFirmInterviewHistoryList(pageNum);
    }


}
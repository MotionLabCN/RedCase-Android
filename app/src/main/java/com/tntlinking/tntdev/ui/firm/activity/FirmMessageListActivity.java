package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmMessageListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.MessageFirmAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 企业消息列表
 */
public final class FirmMessageListActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener {


    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private MessageFirmAdapter mAdapter;
    private List<GetFirmMessageListApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.message_list_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new MessageFirmAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        mRefreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void initData() {

        getMessageList(pageNum);
    }

    //获取消息列表
    private void getMessageList(int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmMessageListApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetFirmMessageListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmMessageListApi.Bean> data) {
                        List<GetFirmMessageListApi.Bean.ListBean> listBean = data.getData().getList();
                        if (listBean.size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                } else {
                                    mList.clear();
                                    mList.addAll(listBean);
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
     * {@link BaseAdapter.OnItemClickListener}
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        final GetFirmMessageListApi.Bean.ListBean item = mAdapter.getItem(position);

        Intent intent = new Intent();
        switch (item.getMessageType()) {// 5 6 消息详情 7面试详情
            case 5:
            case 6:
                intent.setClass(getActivity(), FirmMessageDetailActivity.class);
                intent.putExtra("message", item.getMessageStr());
                intent.putExtra("typeId", item.getTypeId());
                startActivity(intent);
                break;
            case 7:
                intent.setClass(getActivity(), FirmAuditionDetailActivity.class);
                intent.putExtra("interviewId", item.getTypeId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getMessageList(pageNum);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getMessageList(pageNum);

    }
}
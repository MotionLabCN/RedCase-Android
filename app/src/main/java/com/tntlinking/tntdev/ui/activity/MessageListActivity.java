package com.tntlinking.tntdev.ui.activity;

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
import com.tntlinking.tntdev.http.api.GetMessageListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public final class MessageListActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener {


    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private MessageAdapter mAdapter;
    private List<GetMessageListApi.Bean.ListBean> mList = new ArrayList<>();
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

        mAdapter = new MessageAdapter(this);
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
                .api(new GetMessageListApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetMessageListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetMessageListApi.Bean> data) {
                        List<GetMessageListApi.Bean.ListBean> listBean = data.getData().getList();
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
     *
     * @param recyclerView RecyclerView对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        final GetMessageListApi.Bean.ListBean item = mAdapter.getItem(position);

        Intent intent = new Intent();
        switch (item.getMessageType()) {// 2 面试邀约 3 面试提醒 4面试取消 5 入驻通过 6 入驻未通过
            case 2:
                break;
            case 3:
                intent.setClass(getActivity(), AuditionDetailActivity.class);
                intent.putExtra("interviewId", item.getId());
                startActivity(intent);
                break;
            case 4:
                break;
            case 5:
                intent.setClass(getActivity(), MainActivity.class);
                intent.putExtra("interviewId", item.getId());
                startActivity(intent);
                break;
            case 6:
                intent.setClass(getActivity(), EnterDeveloperActivity.class);
                intent.putExtra("interviewId", item.getId());
                startActivity(intent);
                break;

        }
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getMessageList(pageNum);
//        postDelayed(() -> {
//            mAdapter.clearData();
//            mAdapter.setData(analogData());
//            mRefreshLayout.finishRefresh();
//        }, 1000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getMessageList(pageNum);
//        postDelayed(() -> {
//            mAdapter.addData(analogData());
//            mRefreshLayout.finishLoadMore();
//
//            mAdapter.setLastPage(mAdapter.getCount() >= 100);
//            mRefreshLayout.setNoMoreData(mAdapter.isLastPage());
//        }, 1000);
    }
}
package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetFirmRecommendsApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.adapter.RecommendPositionAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   : 服务 Fragment
 */
public final class RecommendPositionFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;
    private LinearLayout ll_daily;
    private SmartRefreshLayout mRefreshLayout;


    private List<GetFirmRecommendsApi.Bean.ListBean> mList = new ArrayList<>();
    private RecommendPositionAdapter mAdapter;

    private int mPageNum = 1;
    private int positionId=227;

    private static final String INTENT_KEY_POSITION = "position";


    public static RecommendPositionFragment newInstance(String url) {
        RecommendPositionFragment fragment = new RecommendPositionFragment();
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

        mAdapter = new RecommendPositionAdapter(getActivity());


        lv_1.setAdapter(mAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


    }

    @Override
    protected void initData() {
        getRecommendList(positionId, mPageNum);
    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:
//                Intent intent = new Intent(getActivity(), HistoryDailyListActivity.class);
//                intent.putExtra("orderId", orderId);
//                startActivity(intent);
                break;
        }
    }


    /**
     * 获取在服务企业list
     */
    private void getRecommendList(int position, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmRecommendsApi().setPositionId(position).setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFirmRecommendsApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmRecommendsApi.Bean> data) {
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
                                if (pageNum == data.getData().getPageNum()) {
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    /**
     * 获取在服务企业list
     */
    private void getRecommendSelfList(int position, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmRecommendsApi().setPositionId(position).setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFirmRecommendsApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmRecommendsApi.Bean> data) {
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
                                if (pageNum == data.getData().getPageNum()) {
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPageNum = 1;
        getRecommendList(positionId, 1);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        getRecommendList(positionId, mPageNum);
    }


}
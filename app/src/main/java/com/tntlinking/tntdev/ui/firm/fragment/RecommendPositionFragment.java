package com.tntlinking.tntdev.ui.firm.fragment;

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
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetFirmRecommendsApi;
import com.tntlinking.tntdev.http.api.GetFirmSelfRecommendsApi;
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


    private List<GetFirmRecommendsApi.Bean.ListBean> mList1 = new ArrayList<>();
    private List<GetFirmRecommendsApi.Bean.ListBean> mList2 = new ArrayList<>();
    private RecommendPositionAdapter mAdapter;

    private int mPageNum = 1;

    private static final String POSITION = "position";
    private static final String POSITION_ID = "position_id";
    private int mPosition = 1;// 1 是推荐 2 是自荐
    private int mPositionId = 0;

    public static RecommendPositionFragment newInstance(int position, int positionId) {
        RecommendPositionFragment fragment = new RecommendPositionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putInt(POSITION_ID, positionId);
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
        mPosition = getInt(POSITION);
        mPositionId = getInt(POSITION_ID);
        EasyLog.print("=====mPosition===" + mPosition);
        EasyLog.print("=====mPositionId===" + mPositionId);
        if (mPosition == 1) {
            getRecommendList(mPositionId, mPageNum);
        } else {
            getRecommendSelfList(mPositionId, mPageNum);
        }

    }


    /**
     * 获取推荐列表
     */
    private void getRecommendList(int position, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmRecommendsApi().setPositionId(position).setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFirmRecommendsApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmRecommendsApi.Bean> data) {
                        if (data.getData().getList() != null && data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                } else {
                                    mList1.clear();
                                    mList1.addAll(data.getData().getList());
                                    mAdapter.setData(mList1);
                                }
                                mRefreshLayout.finishRefresh();
                            } else {
                                if (pageNum == data.getData().getPageNum()) {
                                    mList1.addAll(data.getData().getList());
                                    mAdapter.setData(mList1);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    /**
     * 获取自荐列表
     */
    private void getRecommendSelfList(int position, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmSelfRecommendsApi().setPositionId(position).setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFirmRecommendsApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmRecommendsApi.Bean> data) {
                        if (data.getData().getList() != null && data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                } else {
                                    mList2.clear();
                                    mList2.addAll(data.getData().getList());
                                    mAdapter.setData(mList2);
                                }
                                mRefreshLayout.finishRefresh();
                            } else {
                                if (pageNum == data.getData().getPageNum()) {
                                    mList2.addAll(data.getData().getList());
                                    mAdapter.setData(mList2);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
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
        if (mPosition == 1) {
            getRecommendList(mPositionId, mPageNum);
        } else {
            getRecommendSelfList(mPositionId, mPageNum);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        if (mPosition == 1) {
            getRecommendList(mPositionId, mPageNum);
        } else {
            getRecommendSelfList(mPositionId, mPageNum);
        }
    }


}
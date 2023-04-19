package com.tntlinking.tntdev.ui.firm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetFirmDevApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.activity.DeveloperInfoActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.adapter.FirmHomeAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   : 服务 Fragment
 */
public final class PositionFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private FirmHomeAdapter mAdapter;
    private static final String INTENT_KEY_POSITION = "position";
    private List<GetFirmDevApi.Bean.ListBean> mList = new ArrayList<>();
    private int mId;
    private int mPageNum = 1;


    public static PositionFragment newInstance(int id) {
        PositionFragment fragment = new PositionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_KEY_POSITION, id);
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



        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);


        mAdapter = new FirmHomeAdapter(getActivity());
        lv_1.setAdapter(mAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
                intent.putExtra("developerId", mList.get(position).getId());
                startActivity(intent);

            }
        });


    }

    @Override
    protected void initData() {
        mId = getInt(INTENT_KEY_POSITION);
        GetDeveloperList(mId, mPageNum);

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

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:

                break;
        }
    }


    /**
     * 获取职业方向
     */
    public void GetDeveloperList(int orderId, int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmDevApi().setOrderId(orderId).setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetFirmDevApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmDevApi.Bean> data) {
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
        GetDeveloperList(mId, 1);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        GetDeveloperList(mId, mPageNum);
    }


}
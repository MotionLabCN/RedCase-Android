package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.OnItemClickListener;
import com.tntlinking.tntdev.ui.firm.activity.FirmInfoActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.activity.RecommendPositionActivity;
import com.tntlinking.tntdev.ui.firm.activity.SendPositionActivity;
import com.tntlinking.tntdev.ui.firm.adapter.FirmPositionAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * desc   : 发布职位fragment
 */
public final class FirmPositionFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {
    private MyListView lv_1;
    private LinearLayout ll_empty;
    private LinearLayout ll_daily;
    private SmartRefreshLayout mRefreshLayout;
    private AppCompatButton btn_commit;


    private List<GetFirmPositionApi.Bean.ListBean> mList = new ArrayList<>();
    private FirmPositionAdapter mAdapter;
    private int mPageNum = 1;

    public static FirmPositionFragment newInstance() {
        return new FirmPositionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_send_position_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);
        ll_daily = findViewById(R.id.ll_daily);
        btn_commit = findViewById(R.id.btn_commit);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        mAdapter = new FirmPositionAdapter(getActivity());
        lv_1.setAdapter(mAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetFirmPositionApi.Bean.ListBean item = mAdapter.getItem(position);

                if (item.getAuditStatus() == 1) {//0 是审核中,1 是通过， 2 是未通过
                    Intent intent = new Intent(getActivity(), RecommendPositionActivity.class);
                    intent.putExtra("position_bean", item);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), FirmInfoActivity.class);
                    intent.putExtra("position_bean", item);
                    startActivity(intent);
                }
            }
        });
//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                GetFirmPositionApi.Bean.ListBean item = mAdapter.getItem(position);
//                Intent intent = new Intent(getActivity(), FirmInfoActivity.class);
//                intent.putExtra("position_bean", item);
//                startActivity(intent);
//            }
//        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(SendPositionActivity.class);
            }
        });

    }

    @Override
    protected void initData() {
        getAppList(mPageNum);
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

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }


    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:


                break;
        }
    }


    /**
     * 获取在服务企业list //2 待服务，3 服务中
     */
    private void getAppList(int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmPositionApi().setStatus(1).setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFirmPositionApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFirmPositionApi.Bean> data) {
                        mRefreshLayout.setEnableLoadMore(true);

                        if (data.getData().getList() != null && data.getData().getList().size() >= 0) {
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

                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
                            mRefreshLayout.setEnableLoadMore(false);
                            mRefreshLayout.finishRefresh();
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
        getAppList(1);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        getAppList(mPageNum);
    }


}
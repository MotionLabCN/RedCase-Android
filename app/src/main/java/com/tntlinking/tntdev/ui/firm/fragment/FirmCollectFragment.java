package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.HistoryDailyListActivity;
import com.tntlinking.tntdev.ui.firm.activity.DeveloperInfoActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.adapter.CollectPositionAdapter;
import com.tntlinking.tntdev.widget.MyListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;


/**
 * desc   :企业收藏页面
 */
public final class FirmCollectFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;

    private SmartRefreshLayout mRefreshLayout;


    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private CollectPositionAdapter mAdapter;


    private String orderId;

    private static final String INTENT_KEY_POSITION = "position";


    public static FirmCollectFragment newInstance() {

        return new FirmCollectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_collect_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new CollectPositionAdapter(getActivity());


        lv_1.setAdapter(mAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(DeveloperInfoActivity.class);
            }
        });


    }

    @Override
    protected void initData() {
        mAdapter.setData(analogData());
//        getAppList();
    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:
                Intent intent = new Intent(getActivity(), HistoryDailyListActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                break;
        }
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

    /**
     * 获取在服务企业list //2 待服务，3 服务中
     */
    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(3))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
//                        if (data.getData().size() > 0) {
//                            ll_empty.setVisibility(View.GONE);
//
//
//                            mServiceList.addAll(data.getData());
//                            mAdapter.setData(mServiceList);
//                            orderId = mServiceList.get(0).getId();// 服务项目只会有一个
//
//                        } else {
//                            ll_empty.setVisibility(View.VISIBLE);
//
//                        }
//                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        mServiceList.clear();
//        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
//        if (status.equals("3")) {
//            getAppList();
//        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }


}
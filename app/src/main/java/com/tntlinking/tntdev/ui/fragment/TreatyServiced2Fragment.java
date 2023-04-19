package com.tntlinking.tntdev.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
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
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.ui.activity.AuditionDetailActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.WriteDailyActivity;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   : 服务 Fragment
 */
public final class TreatyServiced2Fragment extends TitleBarFragment<MainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private LinearLayout ll_history;

    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private ServiceProjectAdapter mServiceAdapter;


    private HomeChangeListener listener;

    public void setListener(HomeChangeListener listener) {
        this.listener = listener;
    }
    public static TreatyServiced2Fragment newInstance() {
        return new TreatyServiced2Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_serviced_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);
        ll_history = findViewById(R.id.ll_history);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        mServiceAdapter = new ServiceProjectAdapter(getActivity(), mServiceList);


        lv_1.setAdapter(mServiceAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AppListApi.Bean item = (AppListApi.Bean) mServiceAdapter.getItem(position);
//                if (!TextUtils.isEmpty(item.getServiceName()) && item.getServiceName().equals("服务中")) {
//
//                    Intent intent = new Intent(getActivity(), WriteDailyActivity.class);
//                    intent.putExtra("orderId", item.getId());
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getActivity(), AuditionDetailActivity.class);
//                    intent.putExtra("interviewId", item.getId());
//                    startActivity(intent);
//                }

            }
        });


    }

    @Override
    protected void initData() {
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
        if (status.equals("3")) {
            getAppList();
        }
    }


    /**
     * 获取在服务企业list //2 待服务，3 服务中
     */
    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(2))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
//                            ll_empty.setVisibility(View.GONE);

                            mServiceList.addAll(data.getData());
                            mServiceAdapter.setData(mServiceList);

                        } else {
//                            ll_empty.setVisibility(View.VISIBLE);
                        }
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mServiceList.clear();
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
        if (status.equals("3")) {
            getAppList();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }


}
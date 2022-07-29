package com.tntlinking.tntdev.ui.firm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.CreateDailyApi;
import com.tntlinking.tntdev.http.api.DeleteDailyApi;
import com.tntlinking.tntdev.http.api.GetDailyListApi;
import com.tntlinking.tntdev.http.api.UpdateDailyApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.ui.activity.HistoryDailyListActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.adapter.DailyWriteAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.ui.dialog.AddTagDialog;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.fragment.BrowserFragment;
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
    private LinearLayout ll_daily;
    private SmartRefreshLayout mRefreshLayout;
    private LinearLayout ll_history;



    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private ServiceProjectAdapter mServiceAdapter;


    private String orderId;

    private static final String INTENT_KEY_POSITION = "position";


    public static PositionFragment newInstance(String url) {
        PositionFragment fragment = new PositionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_KEY_POSITION, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_fragment1;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);
        ll_daily = findViewById(R.id.ll_daily);
        ll_history = findViewById(R.id.ll_history);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        setOnClickListener(ll_history);
        mServiceAdapter = new ServiceProjectAdapter(getActivity(), mServiceList);


        lv_1.setAdapter(mServiceAdapter);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


    }

    @Override
    protected void initData() {
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
        String string = getString(INTENT_KEY_POSITION);
        EasyLog.print("===position==="+string);
        if (status.equals("3")) {
            getAppList();
        } else {
            toast("您还没有认证");
            ll_empty.setVisibility(View.VISIBLE);
            ll_daily.setVisibility(View.GONE);
        }

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
     * 获取在服务企业list //2 待服务，3 服务中
     */
    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(3))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            ll_empty.setVisibility(View.GONE);
                            ll_daily.setVisibility(View.VISIBLE);

                            mServiceList.addAll(data.getData());
                            mServiceAdapter.setData(mServiceList);
                            orderId = mServiceList.get(0).getId();// 服务项目只会有一个

                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
                            ll_daily.setVisibility(View.GONE);
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
package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.InterviewDetailActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.WriteDailyActivity;
import com.tntlinking.tntdev.ui.adapter.HistoryProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   : 服务 Fragment
 */
public final class TreatyFragment extends TitleBarFragment<MainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private MyListView lv_2;
    private LinearLayout ll_empty;
    private LinearLayout ll_history_empty;
    private TextView tv_footer;
    private SmartRefreshLayout mRefreshLayout;

    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private List<AppListApi.Bean> mHistoryList = new ArrayList<>();

    private int appSize = 0; //工作请求列表size
    private int interSize = 0; //面试请求列表size
    private int historySize = 0;//历史记录列表size

    private ServiceProjectAdapter mServiceAdapter;
    private HistoryProjectAdapter mHistoryAdapter;

    public static TreatyFragment newInstance() {
        return new TreatyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);
        lv_2 = findViewById(R.id.lv_2);
        ll_empty = findViewById(R.id.ll_empty);
        ll_history_empty = findViewById(R.id.ll_history_empty);
        tv_footer = findViewById(R.id.tv_footer);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

        mServiceAdapter = new ServiceProjectAdapter(getActivity(), mServiceList);
        mHistoryAdapter = new HistoryProjectAdapter(getActivity(), mHistoryList);
        lv_1.setAdapter(mServiceAdapter);
        lv_2.setAdapter(mHistoryAdapter);
        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppListApi.Bean item = (AppListApi.Bean) mServiceAdapter.getItem(position);
                if (!TextUtils.isEmpty(item.getServiceName()) && item.getServiceName().equals("服务中")) {

                    Intent intent = new Intent(getActivity(), WriteDailyActivity.class);
                    intent.putExtra("orderId", item.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), InterviewDetailActivity.class);
                    intent.putExtra("interviewId", item.getId());
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData() {
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");

        if (status.equals("3")) {
            getAppList();
        } else {
            toast("您还没有认证");
            ll_empty.setVisibility(View.VISIBLE);
        }
    }

    @SingleClick
    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }


    /**
     * 获取在服务企业list
     */
    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            mServiceList.addAll(data.getData());
                        }
                        getInterviewAppList();
                        appSize = data.getData().size();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getHistoryList();
                        appSize = 0;
                    }
                });
    }

    /**
     * 获取面试邀约list
     */
    @SuppressLint("CheckResult")
    private void getInterviewAppList() {
        EasyHttp.get(this)
                .api(new AppListInterviewApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        interSize = data.getData().size();
                        if (data.getData().size() > 0) {
                            mServiceList.addAll(data.getData());
                        }
                        getHistoryList();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getHistoryList();
                        interSize = 0;
                        if (appSize + interSize == 0) {
                            ll_empty.setVisibility(View.VISIBLE);
                        } else {
                            ll_empty.setVisibility(View.GONE);
                        }
                    }
                });


    }


    /**
     * 获取历史服务list
     */
    private void getHistoryList() {
        EasyHttp.get(this)
                .api(new HistoryListApi().setOrderData("2018-10-10"))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {

                            //无服务项目（包含面试邀约）且无历史服务项目  显示平台介绍和新手任务
                            if (appSize + interSize == 0) {
                                ll_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_empty.setVisibility(View.GONE);
                            }
                            mServiceAdapter.setData(mServiceList);

                            mHistoryList.addAll(data.getData());
                            mHistoryAdapter.setData(mHistoryList);
                            tv_footer.setVisibility(View.VISIBLE);
                            ll_history_empty.setVisibility(View.GONE);
                        } else {
                            //无服务项目（包含面试邀约）但有历史服务项目  显示暂无工作和历史项目

                            if (appSize + interSize == 0) {
                                ll_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_empty.setVisibility(View.GONE);
                            }
                            mServiceAdapter.setData(mServiceList);

                            ll_history_empty.setVisibility(View.VISIBLE);
                            tv_footer.setVisibility(View.GONE);
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
        mHistoryList.clear();
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
        if (status.equals("3")) {
            getAppList();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }


}
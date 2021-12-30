package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseAdapter;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.adapter.AppListAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 工作主页面
 */
public final class HomeWorkActivity extends AppActivity {
    private TextView tv_avatar;
    private AppListAdapter mAdapter;
    private RecyclerView rv_app_list;
    private LinearLayout ll_empty;
    private TextView tv_refresh;
    private int appSize = 0; //工作请求列表size
    private int interSize = 0; //面试请求列表size
    private int historySize = 0;//历史记录列表size

    @Override
    protected int getLayoutId() {
        return R.layout.work_activity;
    }

    @Override
    protected void initView() {
        tv_avatar = findViewById(R.id.tv_avatar);
        rv_app_list = findViewById(R.id.rv_app_list);
        ll_empty = findViewById(R.id.ll_empty);
        tv_refresh = findViewById(R.id.tv_refresh);
        String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME);
        if (!TextUtils.isEmpty(name)) {
            tv_avatar.setText(name);
        } else {
            tv_avatar.setBackground(getResources().getDrawable(R.drawable.dot_oval_blue));
        }
        setOnClickListener(tv_avatar, tv_refresh);

        mAdapter = new AppListAdapter(this);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {  // 要在setapder之前设置监听事件
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                AppListApi.Bean item = mAdapter.getItem(position);
                if (item.getType() == 1) {
                    if (!TextUtils.isEmpty(item.getServiceName())) {

                        Intent intent = new Intent(HomeWorkActivity.this, WriteDailyActivity.class);
                        intent.putExtra("orderId", item.getId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(HomeWorkActivity.this, InterviewDetailActivity.class);
                        intent.putExtra("interviewId", item.getId());
                        startActivity(intent);
                    }

                } else if (item.getType() == 2) {

                }
            }
        });
        rv_app_list.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
//        getHistoryList();
        getAppList();

    }


    @Override
    public void onRightClick(View view) {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == tv_avatar) {
            startActivity(PersonDataActivity.class);
        } else if (view == tv_refresh) {
            getAppList();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast("刷新成功");
                }
            }, 600);
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    private List<AppListApi.Bean> mList = new ArrayList<>();

    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(1);
                                mList.add(bean);
                            }
//                            getHistoryList();
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

    @SuppressLint("CheckResult")
    private void getInterviewAppList() {
        EasyHttp.get(this)
                .api(new AppListInterviewApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(1);
                                mList.add(bean);
                            }

                        }
                        getHistoryList();
                        interSize = data.getData().size();

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

    private void getHistoryList() {
        EasyHttp.get(this)
                .api(new HistoryListApi().setOrderData("2018-10-10"))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            AppListApi.Bean appBean = new AppListApi.Bean();
                            appBean.setType(3);
                            mList.add(appBean);

                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(2);
                                mList.add(bean);
                            }
                        } else { //历史记录空白页面

                            if (appSize + interSize != 0) {
                                AppListApi.Bean appBean = new AppListApi.Bean();
                                appBean.setType(4);
                                mList.add(appBean);
                            } else if (appSize + interSize == 0) {
                                ll_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_empty.setVisibility(View.GONE);
                            }
                        }

                        mAdapter.setData(mList);
                    }
                });
    }


}
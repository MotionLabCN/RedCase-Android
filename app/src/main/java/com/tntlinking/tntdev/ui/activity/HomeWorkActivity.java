package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
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
    private LinearLayout ll_title;
    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_status;
    private AppListAdapter mAdapter;
    private RecyclerView rv_app_list;
    private LinearLayout ll_empty;
    private TextView tv_refresh;
    private LinearLayout ll_status;
    private TextView tv_status_refresh;
    private int appSize = 0; //工作请求列表size
    private int interSize = 0; //面试请求列表size
    private int historySize = 0;//历史记录列表size
    private ImageView iv_interview;
    private LinearLayout ll_cooperation;
    private LinearLayout ll_service;
    private LinearLayout ll_question;
    private LinearLayout ll_contact;

    String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME, "朋友");
    @Override
    protected int getLayoutId() {
        return R.layout.work_activity;
    }

    @Override
    protected void initView() {
        ll_title = findViewById(R.id.ll_title);
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_status = findViewById(R.id.tv_status);
        rv_app_list = findViewById(R.id.rv_app_list);
        ll_empty = findViewById(R.id.ll_empty);
        tv_refresh = findViewById(R.id.tv_refresh);
        ll_status = findViewById(R.id.ll_status);
        tv_status_refresh = findViewById(R.id.tv_status_refresh);
        iv_interview = findViewById(R.id.iv_interview);
        ll_cooperation = findViewById(R.id.ll_cooperation);
        ll_service = findViewById(R.id.ll_service);
        ll_question = findViewById(R.id.ll_question);
        ll_contact = findViewById(R.id.ll_contact);

        tv_avatar.setText(name);
//        if (!TextUtils.isEmpty(name)) {
//            tv_avatar.setText(name);
//        } else {
//            tv_avatar.setBackground(getResources().getDrawable(R.drawable.dot_oval_blue));
//        }
        ImmersionBar.setTitleBar(this, ll_title);
        setOnClickListener(tv_avatar, iv_interview, tv_refresh, tv_status_refresh, ll_cooperation, ll_service, ll_question, ll_contact);

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

        int status = getInt(AppConfig.DEVELOP_STATUS, 0);
        if (status == 2) {
//            ll_status.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.VISIBLE);
        } else {
            ll_status.setVisibility(View.GONE);
            getAppList();
        }

        if (status == 3) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("已认证");
            tv_name.setText("你好," + name);
        } else {
            tv_status.setVisibility(View.GONE);
            tv_name.setText("你好,新朋友");
        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_avatar:
                startActivity(PersonDataActivity.class);
                break;
            case R.id.iv_interview:
                startActivity(InterviewActivity.class);
                break;
            case R.id.ll_cooperation:
                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/recruit_guide.pdf");
                break;
            case R.id.ll_service:
                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md");
                break;
            case R.id.ll_question:
                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/faq_guide.md");
                break;
            case R.id.ll_contact:
                Intent intent = new Intent();
                intent.setClass(this,SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
                break;
            case R.id.tv_refresh:
                getAppList();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast("刷新成功");
                    }
                }, 600);
                break;
            case R.id.tv_status_refresh:
//                getStatus();
                break;


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
                        EasyLog.print("=======222222=====");
                        interSize = 0;
                        if (appSize + interSize == 0) {
                            ll_empty.setVisibility(View.VISIBLE);
                            EasyLog.print("=======33333=====");
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
                                EasyLog.print("=======44444=====");
                            } else if (appSize + interSize == 0) {
                                ll_empty.setVisibility(View.VISIBLE);
                                EasyLog.print("=======5555=====");
                            } else {
                                ll_empty.setVisibility(View.GONE);
                            }
                        }

                        mAdapter.setData(mList);
                    }
                });
    }


    public void getStatus() {
        EasyHttp.get(HomeWorkActivity.this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(HomeWorkActivity.this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        // 1->待认证  2->待审核   3->审核成功 4->审核失败
                        SPUtils.getInstance().put(AppConfig.DEVELOP_STATUS, data.getData().getStatus());
                        SPUtils.getInstance().put(AppConfig.DEVELOP_NAME, data.getData().getRealName());
                        tv_avatar.setText(data.getData().getRealName());
                        if (data.getData().getStatus().equals("1")) {
                            startActivity(LoginActivityView.class);
                            finish();
                        } else if (data.getData().getStatus().equals("3")) {
                            ll_status.setVisibility(View.GONE);
                            getAppList();
                        } else if (data.getData().getStatus().equals("2")) {
//                            startActivity(CheckDeveloperActivity.class);
                        } else {
                            startActivity(CheckDeveloperFailActivity.class);
                            finish();
                        }

                    }
                });
    }

}

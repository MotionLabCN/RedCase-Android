package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationTipsActivity;
import com.tntlinking.tntdev.ui.activity.JkBrowserActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.SaveQRActivity;
import com.tntlinking.tntdev.ui.activity.SignContactActivity;
import com.tntlinking.tntdev.ui.adapter.HomeTaskAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ActiveTaskFragment extends TitleBarFragment<MainActivity> {
    private HomeTaskAdapter mTaskAdapter;
    private final List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    private HomeChangeListener listener;
    private MyListView lv_task;
    public void setListener(HomeChangeListener listener) {
        this.listener = listener;
    }
    public static ActiveTaskFragment newInstance() {
        return new ActiveTaskFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.active_task_fragment;
    }

    @Override
    protected void initView() {
        lv_task = findViewById(R.id.lv_task);
        mTaskAdapter = new HomeTaskAdapter(getActivity(), mTaskList);
        lv_task.setAdapter(mTaskAdapter);

        lv_task.setOnItemClickListener((parent, view, position, id) -> {
            GetNewbieApi.Bean item = mTaskAdapter.getItem(position);
            if (item.getTaskId() == 2) { //入驻任务
                if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //做任务
                    startActivity(EnterDeveloperActivity.class);
                } else if (item.getTaskStatus() == 2) {
                    if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// 已完成
                        startActivity(SaveQRActivity.class);
                    }
                }
            } else if (item.getTaskId() == 4) {//即可测评
                getDeveloperJkStatus();
            } else {//签订协议任务
                startActivity(SignContactActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        getNewbie();
    }

    public void getNewbie() {
        EasyHttp.get(this)
                .api(new GetNewbieApi())
                .request(new HttpCallback<HttpData<List<GetNewbieApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetNewbieApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            mTaskList.clear();
                            mTaskList.addAll(data.getData());
                            mTaskAdapter.setData(mTaskList);
                            lv_task.post(new Runnable() {
                                @Override
                                public void run() {
                                    int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                    lv_task.measure(0, hMeasureSpec);
                                    listener.onDataChanged(lv_task.getMeasuredHeight());
                                }
                            });
                        }
                    }
                });
    }


    private void getDeveloperJkStatus() {
        EasyHttp.get(this)
                .api(new GetDeveloperJkStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperJkStatusApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<GetDeveloperJkStatusApi.Bean> data) {
                        //1->待认证  2->待审核   3->审核成功 4->审核失败
                        String mStatus = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
                        if (mStatus.equals("1")) {
                            startActivity(EvaluationActivity.class);
                        } else if (data.getData() != null && data.getData().getUserPlanStatus() == 0) {
                            startActivity(EvaluationTipsActivity.class);
                        } else {
                            JkBrowserActivity.start(getActivity(), data.getData().getPlanUrl());

                        }

                    }
                });
    }

}

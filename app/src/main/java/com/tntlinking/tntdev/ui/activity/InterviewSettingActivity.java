package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppScheduleApi;
import com.tntlinking.tntdev.http.api.GetAppListDateApi;
import com.tntlinking.tntdev.http.api.InterviewListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.InterviewDetailAdapter;
import com.tntlinking.tntdev.ui.adapter.InterviewSettingAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 面试日程设置界面
 */
public final class InterviewSettingActivity extends AppActivity {
    private TextView tv_year_month;
    private LinearLayout ll_add;
    private LinearLayout ll_empty;
    private GridView mGridView;
    private ListView mListView;
    private InterviewSettingAdapter mAdapter;
    private String[] mWeek = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    private List<InterviewListApi.Bean> mList = new ArrayList<>();
//    private AppInterviewAdapter interviewAdapter;
    private InterviewDetailAdapter interviewAdapter;
//    private List<AppScheduleApi.Bean> interviewList = new ArrayList<>();
    private List<GetAppListDateApi.Bean> interviewList = new ArrayList<>();
    public static final String INTENT_KEY_INTERVIEW = "key_add_interview";
    private String mToday = TimeUtils.millis2String(TimeUtils.getNowMills(), "yyyy-MM-dd");// 默认获取当天日程

    @Override
    protected int getLayoutId() {
        return R.layout.interview_setting_activity;
    }

    @Override
    protected void initView() {
        tv_year_month = findViewById(R.id.tv_year_month);
        mGridView = findViewById(R.id.grid_view);
        ll_add = findViewById(R.id.ll_add);
        ll_empty = findViewById(R.id.ll_empty);
        mListView = findViewById(R.id.rv_app_list);

        String nowTime = TimeUtils.millis2String(TimeUtils.getNowMills(), "yyyy/MM");
        tv_year_month.setText(nowTime);
        mAdapter = new InterviewSettingAdapter(this, mList);
        mGridView.setAdapter(mAdapter);
        setOnClickListener(ll_add);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getScheduleDate().contains("-")) {

                    for (InterviewListApi.Bean bean : mList) {
                        if (bean.isSelect()) {
                            bean.setSelect(false);
                        }
                    }
                    mList.get(position).setSelect(true);
                    mAdapter.setData(mList);

//                    getSchedule(mList.get(position).getScheduleDate());
                    mToday = mList.get(position).getScheduleDate();

                    getInterviewDate(mList.get(position).getScheduleDate());
                }
            }
        });

        interviewAdapter = new InterviewDetailAdapter(this, interviewList);
        mListView.setAdapter(interviewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(InterviewSettingActivity.this, AddScheduleActivity.class);
//                AppScheduleApi.Bean bean = interviewList.get(position);
//                intent.putExtra(INTENT_KEY_INTERVIEW, bean);
//                intent.putExtra("scheduleDate", bean.getScheduleDate());
//                getActivity().startActivityForResult(intent, 10002);

                Intent intent = new Intent(InterviewSettingActivity.this, AuditionDetailActivity.class);
                intent.setClass(getActivity(), AuditionDetailActivity.class);
                GetAppListDateApi.Bean bean = interviewList.get(position);
                intent.putExtra("interviewId", bean.getId());
                getActivity().startActivity(intent);

            }
        });

    }

    @Override
    protected void initData() {
        getWeekList();
//        getSchedule(mToday);
        getInterviewDate(mToday);
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == ll_add) {
            Intent intent = new Intent(InterviewSettingActivity.this, AddScheduleActivity.class);
            intent.putExtra("scheduleDate", mToday);
            getActivity().startActivityForResult(intent, 10001);
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    /**
     * 获取日历列表
     */
    private void getWeekList() {
        mList.clear();
        EasyHttp.get(this)
                .api(new InterviewListApi())
                .request(new HttpCallback<HttpData<List<InterviewListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<InterviewListApi.Bean>> data) {
                        List<InterviewListApi.Bean> dataBean = data.getData();
                        if (dataBean != null && dataBean.size() != 0) {
                            for (int i = 0; i < mWeek.length; i++) {
                                InterviewListApi.Bean bean = new InterviewListApi.Bean();
                                bean.setScheduleDate(mWeek[i]);
                                mList.add(bean);
                            }
                            for (InterviewListApi.Bean bean : dataBean) {
                                if (bean.getScheduleDate().equals(mToday)) {
                                    bean.setSelect(true);
                                }
                            }
                            mList.addAll(dataBean);
                            mAdapter.setData(mList);

                        }
                    }
                });

    }

    /**
     * 获取某天日程
     * @param date
     */
    private void getSchedule(String date) {
        EasyHttp.get(this)
                .api(new AppScheduleApi().setData(date))
                .request(new HttpCallback<HttpData<List<AppScheduleApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppScheduleApi.Bean>> data) {
//                        if (data.getData() != null && data.getData().size() != 0) {
//                            interviewList.clear();
//                            interviewList.addAll(data.getData());
//                            interviewAdapter.setData(interviewList);
//                        } else {
//                            interviewList.clear();
//                            interviewAdapter.setData(interviewList);
//
//                        }
                    }
                });
    }

    /**
     * 指定时间的面试列表
     * @param date
     */
    private void getInterviewDate(String date) {
        EasyHttp.get(this)
                .api(new GetAppListDateApi().setInterviewDate(date))
                .request(new HttpCallback<HttpData<List<GetAppListDateApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetAppListDateApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            ll_empty.setVisibility(View.GONE);
                            interviewList.clear();
                            interviewList.addAll(data.getData());
                            interviewAdapter.setData(interviewList);

                        } else {
                            interviewList.clear();
                            interviewAdapter.setData(interviewList);
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10001) {// 新增日程
                getSchedule(mToday);
            } else if (requestCode == 10002) {// 删除和更新日程

                getSchedule(mToday);

            }
        }
        getWeekList();
    }
}

package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ClipboardUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetInterviewDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import androidx.annotation.NonNull;

/**
 * 面试详情页面
 */
public final class InterviewDetailActivity extends AppActivity {

    private TextView tv_interview_position;
    private TextView tv_interview_company;
    private TextView tv_interview_mode;
    private TextView tv_interview_way;
    private TextView tv_interview_time;
    private TextView tv_interview_id;
    private TextView tv_interview_url;


    @Override
    protected int getLayoutId() {
        return R.layout.interview_detail_activity;
    }

    @Override
    protected void initView() {
        tv_interview_position = findViewById(R.id.tv_interview_position);
        tv_interview_company = findViewById(R.id.tv_interview_company);
        tv_interview_mode = findViewById(R.id.tv_interview_mode);
        tv_interview_way = findViewById(R.id.tv_interview_way);
        tv_interview_time = findViewById(R.id.tv_interview_time);
        tv_interview_id = findViewById(R.id.tv_interview_id);
        tv_interview_url = findViewById(R.id.tv_interview_url);

        setOnClickListener(tv_interview_url, tv_interview_id);

    }

    @Override
    protected void initData() {
        String interviewId = getString("interviewId");
        EasyHttp.get(this)
                .api(new GetInterviewDetailApi()
                        .setInterviewId(interviewId))
                .request(new HttpCallback<HttpData<GetInterviewDetailApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetInterviewDetailApi.Bean> data) {

                        GetInterviewDetailApi.Bean bean = data.getData();
                        tv_interview_position.setText(bean.getPositionName());
                        tv_interview_company.setText(bean.getCompanyName());
                        tv_interview_mode.setText(bean.getWorkDaysModeName());
                        tv_interview_way.setText(bean.getInterviewWayName());
                        String interviewStartDate = bean.getInterviewStartDate();
                        String interviewEndDate = bean.getInterviewEndDate();
                        String year = Utils.getYearFromDate(interviewStartDate);
                        String startTime = Utils.getHoursAndMin(interviewStartDate);
                        String endTime = Utils.getHoursAndMin(interviewEndDate);
                        tv_interview_time.setText(year + " " + startTime + "-" + endTime);
                        tv_interview_id.setText(bean.getMeetingCode());
                        tv_interview_url.setText(bean.getMeetingUrl());

                    }
                });
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == tv_interview_url) {
            BrowserActivity.start(getActivity(), tv_interview_url.getText().toString());
        } else if (view == tv_interview_id) {
            ClipboardUtils.copyText(tv_interview_id.getText());
            toast("复制成功");
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}
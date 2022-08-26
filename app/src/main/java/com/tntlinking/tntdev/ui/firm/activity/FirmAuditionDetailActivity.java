package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetAuditionDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.BrowserActivity;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 面试详情页面
 */
public final class FirmAuditionDetailActivity extends AppActivity {
    private AppCompatButton mCommit;
    private TextView tv_time;
    private TextView tv_position;
    private TextView tv_name;
    private TextView tv_meeting_code;

    private String meetingUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_audition_detail_activity;
    }


    @Override
    protected void initView() {
        tv_time = findViewById(R.id.tv_time);
        tv_position = findViewById(R.id.tv_position);
        tv_name = findViewById(R.id.tv_name);
        tv_meeting_code = findViewById(R.id.tv_meeting_code);

        mCommit = findViewById(R.id.btn_commit);
        setOnClickListener(mCommit);
    }


    @Override
    protected void initData() {
        int interviewId = getInt("interviewId");

        if (interviewId == 0) {//从面试列表过来的，
            String name = getString("name");
            String position = getString("position");
            String time = getString("time");
            String code = getString("code");

            tv_time.setText(time);
            tv_position.setText(position);
            tv_name.setText(name);
            tv_meeting_code.setText(code);
        } else { //从消息页面过来的，
            getDetail(interviewId);
        }

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_commit:
                if (!TextUtils.isEmpty(meetingUrl)) {
                    BrowserActivity.start(getActivity(), meetingUrl);
                }
                break;
        }

    }

    /**
     * @param id 获取会议详情
     */
    public void getDetail(int id) {
        EasyHttp.get(this)
                .api(new GetAuditionDetailApi().setInterviewId(id))
                .request(new HttpCallback<HttpData<GetAuditionDetailApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetAuditionDetailApi.Bean> data) {
                        GetAuditionDetailApi.Bean bean = data.getData();
                        if (bean != null) {
                            tv_time.setText(bean.getInterviewStartDate());
                            tv_position.setText(bean.getPositionName());
                            tv_name.setText(bean.getDeveloperName());
                            tv_meeting_code.setText(bean.getMeetingCode());
                            meetingUrl = bean.getMeetingUrl();
                        }
                    }
                });
    }
}
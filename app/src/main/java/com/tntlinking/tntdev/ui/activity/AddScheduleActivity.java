package com.tntlinking.tntdev.ui.activity;


import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AddScheduleApi;
import com.tntlinking.tntdev.http.api.AppScheduleApi;
import com.tntlinking.tntdev.http.api.DeleteScheduleApi;
import com.tntlinking.tntdev.http.api.UpdateScheduleApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.dialog.TimeSelectDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

import static com.tntlinking.tntdev.ui.activity.InterviewSettingActivity.INTENT_KEY_INTERVIEW;


/**
 * 添加日程页面
 */
public final class AddScheduleActivity extends AppActivity {
    private TitleBar title_bar;//
    private EditText et_schedule;//项目名称
    private TextView schedule_in_time;
    private TextView schedule_end_time;
    private SwitchButton sb_find_switch;
    private AppCompatButton btn_delete;
    private AppCompatButton btn_commit;

    private String mInTime;
    private String mEndTime;
    private boolean isFullDay = false;
    private String mScheduleDate = "";
    private AppScheduleApi.Bean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.add_schedule_activity;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        et_schedule = findViewById(R.id.et_schedule);
        sb_find_switch = findViewById(R.id.sb_find_switch);
        schedule_in_time = findViewById(R.id.schedule_in_time);
        schedule_end_time = findViewById(R.id.schedule_end_time);
        btn_delete = findViewById(R.id.btn_delete);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(schedule_in_time, schedule_end_time, btn_delete, btn_commit);
        sb_find_switch.setColor(getColor(R.color.main_color),getColor(R.color.main_color));
        sb_find_switch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton button, boolean checked) {
                isFullDay = checked;
            }
        });
    }


    @Override
    protected void initData() {
        mScheduleDate = getString("scheduleDate");
        bean = getSerializable(INTENT_KEY_INTERVIEW);

        if (bean != null) {
            if (TextUtils.isEmpty(bean.getTitle())) {
                btn_delete.setVisibility(View.GONE);

            } else {
                title_bar.setTitle("编辑日程");
                btn_delete.setVisibility(View.VISIBLE);
                et_schedule.setText(bean.getTitle());
                sb_find_switch.setChecked(bean.isFullDay());
                schedule_in_time.setText(Utils.getTimeFromDate(bean.getStartDate()));
                schedule_end_time.setText(Utils.getTimeFromDate(bean.getEndDate()));

                isFullDay = bean.isFullDay();
                mInTime = Utils.getHoursAndMin(bean.getStartDate());
                mEndTime =Utils.getHoursAndMin(bean.getEndDate());
            }
        }

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_in_time:
                new TimeSelectDialog.Builder(this).setTitle("选择时间").setIgnoreSecond().setListener(new TimeSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int hours, int minute, int second) {
                        mInTime = Utils.formatDate(hours) + ":" + Utils.formatDate(minute);
//
                        schedule_in_time.setText(hours + ":" + minute);

                    }
                }).show();
                break;
            case R.id.schedule_end_time:

                new TimeSelectDialog.Builder(this).setTitle("选择时间").setIgnoreSecond().setListener(new TimeSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int hours, int minute, int second) {
                        mEndTime = Utils.formatDate(hours) + ":" + Utils.formatDate(minute);

                        schedule_end_time.setText(hours + ":" + minute);

                    }
                }).show();
                break;
            case R.id.btn_delete:
                deleteSchedule();
                break;
            case R.id.btn_commit:
                String title = et_schedule.getText().toString();
                String in_time = schedule_in_time.getText().toString();
                String end_time = schedule_end_time.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    toast("没有输入项目名称");
                    return;
                }
                if (!in_time.contains(":")) {
                    toast("没有选择开始时间");
                    return;
                }
                if (!end_time.contains(":")) {
                    toast("没有选择结束时间");
                    return;
                }
                if (btn_delete.getVisibility() == View.GONE) {
                    addSchedule();
                } else {
                    updateSchedule();
                }

                break;
        }
    }


    /**
     * s删除日程
     */
    public void deleteSchedule() {
        EasyHttp.post(this)
                .api(new DeleteScheduleApi()
                        .setScheduleId(bean.getId()))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        setResult(RESULT_OK);
                        finish();

                    }
                });
    }


    /**
     * 新增日程
     */
    public void addSchedule() {
        if (!TextUtils.isEmpty(mScheduleDate)) {
            EasyHttp.post(this)
                    .api(new AddScheduleApi()
                            .setStartDate(mScheduleDate + " " + mInTime + ":00")
                            .setEndDate(mScheduleDate + " " + mEndTime + ":00")
                            .setTitle(et_schedule.getText().toString())
                            .setFullDay(isFullDay)
                            .setScheduleDate(mScheduleDate)
                    )
                    .request(new HttpCallback<HttpData<Void>>(this) {
                        @Override
                        public void onSucceed(HttpData<Void> data) {

                            setResult(RESULT_OK);
                            finish();

                        }
                    });
        }
    }

    /**
     * 修改日程
     */
    public void updateSchedule() {
        EasyHttp.post(this)
                .api(new UpdateScheduleApi()
                        .setStartDate(mScheduleDate + " " + mInTime + ":00")
                        .setEndDate(mScheduleDate + " " + mEndTime + ":00")
                        .setTitle(et_schedule.getText().toString())
                        .setFullDay(isFullDay)
                        .setScheduleDate(mScheduleDate)
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

}
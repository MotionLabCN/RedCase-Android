package com.tntlinking.tntdev.ui.activity;


import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout ll_time;

    private String mInTime;
    private String mEndTime;
    private boolean isFullDay = false; //默认半日
    private String mScheduleDate = "";
    private AppScheduleApi.Bean bean;
    private int inTimeData = 0;
    private int endTimeData = 0;

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
        ll_time = findViewById(R.id.ll_time);
        schedule_in_time = findViewById(R.id.schedule_in_time);
        schedule_end_time = findViewById(R.id.schedule_end_time);
        btn_delete = findViewById(R.id.btn_delete);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(schedule_in_time, schedule_end_time, btn_delete, btn_commit);
        sb_find_switch.setColor(getColor(R.color.main_color), getColor(R.color.main_color));
        sb_find_switch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton button, boolean checked) {
                isFullDay = checked;
                if (isFullDay) {
                    ll_time.setVisibility(View.GONE);
                } else {
                    ll_time.setVisibility(View.VISIBLE);
                }
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
                schedule_in_time.setText(Utils.getHoursAndMin(bean.getStartDate()));
                schedule_end_time.setText(Utils.getHoursAndMin(bean.getEndDate()));

                isFullDay = bean.isFullDay();
                mInTime = Utils.getHoursAndMin(bean.getStartDate());
                mEndTime = Utils.getHoursAndMin(bean.getEndDate());

                if (isFullDay) {
                    ll_time.setVisibility(View.GONE);
                } else {
                    ll_time.setVisibility(View.VISIBLE);
                }
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
                        inTimeData = hours + minute;

                        mInTime = Utils.formatDate(hours) + ":" + Utils.formatDate(minute);
                        schedule_in_time.setText(hours + ":" + minute);

                    }
                }).show();
                break;
            case R.id.schedule_end_time:

                new TimeSelectDialog.Builder(this).setTitle("选择时间").setIgnoreSecond().setListener(new TimeSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int hours, int minute, int second) {
                        endTimeData = hours + minute;
                        if (endTimeData < inTimeData) {
                            toast("结束时间不能小于开始时间");
                            return;
                        }
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

                if (!isFullDay) {
                    if (!in_time.contains(":")) {
                        toast("没有选择开始时间");
                        return;
                    }
                    if (!end_time.contains(":")) {
                        toast("没有选择结束时间");
                        return;
                    }
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
     * 新增日程
     */
    public void addSchedule() {
        if (!TextUtils.isEmpty(mScheduleDate)) {
            AddScheduleApi addScheduleApi;
            if (isFullDay) {
                addScheduleApi = new AddScheduleApi()
                        .setTitle(et_schedule.getText().toString())
                        .setFullDay(isFullDay)
                        .setScheduleDate(mScheduleDate);
            } else {
                addScheduleApi = new AddScheduleApi()
                        .setStartDate(mScheduleDate + " " + mInTime + ":00")
                        .setEndDate(mScheduleDate + " " + mEndTime + ":00")
                        .setTitle(et_schedule.getText().toString())
                        .setFullDay(isFullDay)
                        .setScheduleDate(mScheduleDate);
            }
            EasyHttp.post(this)
                    .api(addScheduleApi)
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
                        .setId(bean.getId())
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

}
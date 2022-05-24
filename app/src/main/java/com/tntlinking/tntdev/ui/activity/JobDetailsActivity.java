package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.JobDetailsApi;
import com.tntlinking.tntdev.http.api.SelfReCommendApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.JobRequirementsAdapter;
import com.tntlinking.tntdev.ui.adapter.ToolLabelAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends AppActivity {
    private RecyclerView rv_job_requirements;
    private RecyclerView rv_tool_label;
    private final List<String> mStringArrayList = new ArrayList<>();
    private final List<String> mStringToolLabelArrayList = new ArrayList<>();
    private TextView tv_position_name;
    private TextView tv_salary;
    private TextView tv_service_mode;
    private TextView tv_work_experience;
    private TextView tv_academic_degree;
    private TextView tv_total_number_of_people;
    private TextView tv_content;
    private TextView tv_name;
    private TextView tv_professional_title;
    private TextView tv_company_size;
    private AppCompatButton btn_recommend_oneself;
    private String mPositionId;
    private String StartPay;
    private String EndPay;

    @Override
    protected int getLayoutId() {
        return R.layout.job_details_activity;
    }

    @Override
    protected void initView() {
        tv_content = findViewById(R.id.tv_content);
        rv_job_requirements = findViewById(R.id.rv_job_requirements);
        rv_tool_label = findViewById(R.id.rv_tool_label);
        tv_position_name = findViewById(R.id.tv_position_name);
        tv_salary = findViewById(R.id.tv_salary);
        tv_service_mode = findViewById(R.id.tv_service_mode);
        tv_work_experience = findViewById(R.id.tv_work_experience);
        tv_academic_degree = findViewById(R.id.tv_academic_degree);
        tv_total_number_of_people = findViewById(R.id.tv_total_number_of_people);
        tv_name = findViewById(R.id.tv_name);
        tv_professional_title = findViewById(R.id.tv_professional_title);
        tv_company_size = findViewById(R.id.tv_company_size);
        btn_recommend_oneself = findViewById(R.id.btn_recommend_oneself);
        tv_content.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
        //将正常的manager替换为FlexboxLayoutManager
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        rv_job_requirements.setLayoutManager(layoutManager);
        FlexboxLayoutManager layoutManager1 = new FlexboxLayoutManager(this);
        layoutManager1.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        rv_tool_label.setLayoutManager(layoutManager1);


    }

    private class OnTvGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            tv_content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            final String newText = autoSplitText(tv_content);
            if (!TextUtils.isEmpty(newText)) {
                tv_content.setText(newText);
            }
        }
    }

    @Override
    public void onLeftClick(View view) {
        setResult(1);
        finish();
    }

    @Override
    protected void initData() {
        mPositionId = getString("positionId");
        getJobDetails(mPositionId);
        setOnClickListener(btn_recommend_oneself);

    }

    private void getJobDetails(String mPositionId) {
        EasyHttp.get(this)
                .api(new JobDetailsApi().setpositionId(mPositionId))
                .request(new HttpCallback<HttpData<JobDetailsApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<JobDetailsApi.Bean> data) {
                        if (data.getData() != null) {
                            if (data.getData().getSelfRecommendStatus()) {
                                btn_recommend_oneself.setText("自荐成功");

                                btn_recommend_oneself.setEnabled(false);
                                btn_recommend_oneself.setBackgroundResource(R.drawable.button_grey_circle_selector);
                            }
                            tv_position_name.setText(data.getData().getTitle());
                            DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                            double startPay = data.getData().getStartPay() / 1000;
                            StartPay = decimalFormat.format(startPay);
                            double endPay = data.getData().getEndPay() / 1000;
                            EndPay = decimalFormat.format(endPay);
                            tv_salary.setText(StartPay + "-" + EndPay + "k/月");
                            tv_service_mode.setText(data.getData().getWorkDaysModeName());
                            tv_work_experience.setText(data.getData().getWorkYearsName());
                            tv_academic_degree.setText(data.getData().getEducationName());
                            tv_total_number_of_people.setText(data.getData().getRecruitCount() + "人");
                            tv_content.setText(data.getData().getDescription());
                            tv_name.setText(data.getData().getCompanyIndustryName());
                            tv_professional_title.setText(data.getData().getCompanyName());
                            tv_company_size.setText(data.getData().getCompanyIndustryName() + "·" + data.getData().getCompanyPersonSizeName());
                            mStringArrayList.clear();
                            mStringArrayList.addAll(data.getData().getSkillNames());
                            mStringToolLabelArrayList.clear();
                            mStringToolLabelArrayList.addAll(data.getData().getCompanyTeamToolsDescNames());
                            JobRequirementsAdapter adapter = new JobRequirementsAdapter(JobDetailsActivity.this, mStringArrayList);
                            rv_job_requirements.setAdapter(adapter);
                            ToolLabelAdapter toolLabelAdapter = new ToolLabelAdapter(JobDetailsActivity.this, mStringToolLabelArrayList);
                            rv_tool_label.setAdapter(toolLabelAdapter);
                        }


                    }
                });
    }

    private String autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_recommend_oneself) { // 立即推荐
            self_recommend();

        }

    }

    private void self_recommend() {
        EasyHttp.post(this)
                .api(new SelfReCommendApi().setpositionId(mPositionId))
                .request(new HttpCallback<HttpData<Boolean>>(this) {

                    @SuppressLint({"SetTextI18n", "ResourceType"})
                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        if (data.getData()) {
                            ToastUtils.show("自荐成功");
                            btn_recommend_oneself.setText("自荐成功");
                            btn_recommend_oneself.setBackgroundResource(R.drawable.button_grey_circle_selector);
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }

                });
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}

package com.tntlinking.tntdev.ui.activity;

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
import com.hjq.toast.ToastUtils;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.ui.adapter.JobRequirementsAdapter;
import com.tntlinking.tntdev.ui.adapter.ToolLabelAdapter;
import com.tntlinking.tntdev.ui.bean.PositionBean;

import java.util.ArrayList;
import java.util.Arrays;

public class JobDetailsActivity extends AppActivity {
    private RecyclerView rv_job_requirements;
    private RecyclerView rv_tool_label;
    private final String[] arr= {"Java","C++","HTM5","CSS","MVC"};
    private final String[] arr1= {"微信","钉钉","企业微信"};
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

        tv_content.setText("1.负责产品的核心功能和模块的代码编写2.负责数据平台的搭建和管理3.具备良好的团队精神和沟通表达能力，协同开发，保证项目质量与进度");

        tv_content.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
        //将正常的manager替换为FlexboxLayoutManager
        FlexboxLayoutManager layoutManager =new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        // layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        // layoutManager.setAlignItems(AlignItems.CENTER);
        rv_job_requirements.setLayoutManager(layoutManager);
        FlexboxLayoutManager layoutManager1 =new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置水平方向。也可以设置垂直方向
        rv_tool_label.setLayoutManager(layoutManager1);

        ArrayList<String> list = new ArrayList<>(Arrays.asList(arr));
        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(arr1));
        JobRequirementsAdapter adapter =new JobRequirementsAdapter(this,list);
        rv_job_requirements.setAdapter(adapter);
        ToolLabelAdapter toolLabelAdapter=new ToolLabelAdapter(this,list2);
        rv_tool_label.setAdapter(toolLabelAdapter);

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
    protected void initData() {

        PositionBean positionBean = (PositionBean) getIntent().getSerializableExtra("Position");
        tv_position_name.setText(positionBean.getPosition_name());
        tv_salary.setText(positionBean.getSalary());
//        tv_service_mode.setText(positionBean.getSalary());
//        tv_work_experience.setText(positionBean.getSalary());
//        tv_academic_degree.setText(positionBean.getSalary());
//        tv_total_number_of_people.setText(positionBean.getSalary());
        tv_content.setText(positionBean.getContent());
        tv_name.setText(positionBean.getName());
        tv_professional_title.setText(positionBean.getCompany());
//        tv_company_size.setText(positionBean.getProfessional_title());
        setOnClickListener(btn_recommend_oneself);

    }
    private String autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
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
        if (view == btn_recommend_oneself) { // 入驻资料
            ToastUtils.show("自荐成功");
            btn_recommend_oneself.setText("自荐成功");
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

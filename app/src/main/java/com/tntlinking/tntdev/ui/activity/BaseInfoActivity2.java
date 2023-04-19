package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户信息填写页面2
 */
public final class BaseInfoActivity2 extends AppActivity {

    private SettingBar info_specialisations;
    private SettingBar info_work_experience;
    private TextView tv_all_day;
    private TextView tv_half_day;
    private ClearEditText et_salary;
    private ClearEditText et_expect_salary_low;
    private ClearEditText et_expect_salary_high;


    private AppCompatButton mNext;

    private int mCareerDirectionId = 1;    //职业方向id
    private int mWorkYearsId = 1;    //工作年限id
    private String mCurSalary = "1";    //当前薪资
    private int mWorkDay = 1; // 1 全日 2 半日
    private String mExpectSalary = "1"; // 期望薪资

    private SendDeveloperBean postBean = SendDeveloperBean.getSingleton();

    @Override
    protected int getLayoutId() {
        return R.layout.baseinfo_activity_2;
    }

    @Override
    protected void initView() {

        info_specialisations = findViewById(R.id.info_specialisations);
        info_work_experience = findViewById(R.id.info_work_experience);
        tv_all_day = findViewById(R.id.tv_all_day);
        tv_half_day = findViewById(R.id.tv_half_day);
        et_salary = findViewById(R.id.et_salary);
        et_expect_salary_low = findViewById(R.id.et_expect_salary_low);
        et_expect_salary_high = findViewById(R.id.et_expect_salary_high);
        mNext = findViewById(R.id.btn_next);

        setOnClickListener(info_specialisations, info_work_experience, tv_all_day, tv_half_day, mNext);


    }

    private List<GetDictionaryApi.DictionaryBean> mSpecialisationsList;
    private List<GetDictionaryApi.DictionaryBean> mWorkList;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        mSpecialisationsList = getDictionaryList("6");//职业方向
        mWorkList = getDictionaryList("4");//工作经验

//        String dev = SPUtils.getInstance().getString("dev");
//        SendDeveloperBean postBean = GsonUtils.fromJson(dev, SendDeveloperBean.class);
        if (!TextUtils.isEmpty(postBean.getCareerDirection())) {
            info_specialisations.setLeftText(postBean.getCareerDirection());
            info_work_experience.setLeftText(postBean.getWorkYears());
            et_salary.setText(postBean.getCurSalary());
            et_expect_salary_low.setText(postBean.getLowestSalary());
            et_expect_salary_high.setText(postBean.getHighestSalary());
            if (postBean.getWorkDayMode() == 1) {
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.white));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_white_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.color_hint_color));
            } else {
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_white_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.color_hint_color));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.white));
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_specialisations:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择职业方向")
                        .setList(mSpecialisationsList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {
                        info_specialisations.setLeftText(mSpecialisationsList.get(type).getName());


                        postBean.setCareerDirection(mSpecialisationsList.get(type).getName());
                        postBean.setCareerDirectionId(mSpecialisationsList.get(type).getId());

                    }
                }).show();
                break;
            case R.id.info_work_experience:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择工作经验")
                        .setList(mWorkList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {
                        info_work_experience.setLeftText(mWorkList.get(type).getName());

                        postBean.setWorkYears(mWorkList.get(type).getName());
                        postBean.setWorkYearsId(mWorkList.get(type).getId());
                    }
                }).show();
                break;
            case R.id.tv_all_day:  // 1 全日 2 半日
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.white));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_white_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.color_hint_color));
                postBean.setWorkDayMode(1);
                break;
            case R.id.tv_half_day:
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_white_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.color_hint_color));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.white));
                postBean.setWorkDayMode(2);
                break;

            case R.id.btn_next:


                if (TextUtils.isEmpty(postBean.getCareerDirection())) {
                    toast("没选择专业方向");
                    return;
                }
                if (TextUtils.isEmpty(postBean.getWorkYears())) {
                    toast("没选择工作经验");
                    return;
                }
                if (TextUtils.isEmpty(et_salary.getText().toString())) {
                    toast("没有输入当前薪资");
                    return;
                }
                if (TextUtils.isEmpty(et_expect_salary_low.getText().toString())) {
                    toast("没有输入期望最低薪资");
                    return;
                }
                if (TextUtils.isEmpty(et_expect_salary_high.getText().toString())) {
                    toast("没有输入期望最高薪资");
                    return;
                }

                Integer low = Integer.valueOf(et_expect_salary_low.getText().toString());
                Integer high = Integer.valueOf(et_expect_salary_high.getText().toString());
                if (high < low) {
                    et_expect_salary_high.setText("");
                    toast("期望最高薪资不能低于最低薪资");
                    return;
                }
                postBean.setCurSalary(et_salary.getText().toString());
                postBean.setLowestSalary(et_expect_salary_low.getText().toString());
                postBean.setHighestSalary(et_expect_salary_high.getText().toString());

                Intent intent = new Intent(this, BaseInfoActivity3.class);
//                intent.putExtra("postBean", postBean);
                startActivity(intent);

                break;

        }

    }

    /**
     * (1->行业 || 2->人员规模 || 3->常用协作工具 || 4->经验要求 || 5->学历要求 6->职业方向
     * || 7->培养方式 || 8->远程工作原因 || 9->职业状态 || 10->工作方式 || 11 ->面试方式)
     */
    public List<GetDictionaryApi.DictionaryBean> getDictionaryList(String parentId) {
        List<GetDictionaryApi.DictionaryBean> mList = new ArrayList();
        EasyHttp.get(this)
                .api(new GetDictionaryApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<List<GetDictionaryApi.DictionaryBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetDictionaryApi.DictionaryBean>> data) {
                        if (!data.getData().isEmpty()) {
                            mList.addAll(data.getData());

                        }
                    }
                });

        return mList;
    }

}
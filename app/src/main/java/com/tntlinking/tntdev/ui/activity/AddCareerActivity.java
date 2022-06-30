package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.UpdateBasicInfoApi;
import com.tntlinking.tntdev.http.api.UpdateCareerApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;

/**
 * 用户信息填写页面2
 */
public final class AddCareerActivity extends AppActivity {

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


    private int careerDirectionId = 0;
    private int workYearsId = 0;
    private String curSalary = "";
    private int workDayMode = 1;// 1 全日 2 半日  //默认全日
    private String lowestSalary = "";
    private String highestSalary = "";

    @Override
    protected int getLayoutId() {
        return R.layout.career_activity;
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

        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();
        if (bean != null) {
            if (!TextUtils.isEmpty(careerDto.getCareerDirectionName())) {

//                info_specialisations.setLeftText(careerDto.getCareerDirectionName());
//                info_work_experience.setLeftText(careerDto.getWorkYearsName());
//                et_salary.setText(careerDto.getCurSalary());
//                et_expect_salary_low.setText(workModeDtoList.get(0).getLowestSalary());
//                et_expect_salary_high.setText(workModeDtoList.get(0).getHighestSalary());

                info_specialisations.setLeftText(TextUtils.isEmpty(careerDto.getCareerDirectionName()) ? "职业方向" : careerDto.getCareerDirectionName());
                info_work_experience.setLeftText(TextUtils.isEmpty(careerDto.getWorkYearsName()) ? "工作经验" : careerDto.getWorkYearsName());
                et_salary.setText(TextUtils.isEmpty(careerDto.getCurSalary()) ? "当前薪资(元/月)" : careerDto.getCurSalary());
                et_expect_salary_low.setText(TextUtils.isEmpty(workModeDtoList.get(0).getLowestSalary()) ? "最低服务价格(元/月)" : workModeDtoList.get(0).getLowestSalary());
                et_expect_salary_high.setText(TextUtils.isEmpty(workModeDtoList.get(0).getHighestSalary()) ? "最高服务价格(元/月)" : workModeDtoList.get(0).getHighestSalary());

                if (workModeDtoList.get(0).getWorkDayMode() == 1) {
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

                careerDirectionId = careerDto.getCareerDirectionId();
                workYearsId = careerDto.getWorkYearsId();
                curSalary = careerDto.getCurSalary();
                workDayMode = workModeDtoList.get(0).getWorkDayMode();
                lowestSalary = workModeDtoList.get(0).getLowestSalary();
                highestSalary = workModeDtoList.get(0).getHighestSalary();
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

                        careerDirectionId = mSpecialisationsList.get(type).getId();
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

                        workYearsId = mWorkList.get(type).getId();
                    }
                }).show();
                break;
            case R.id.tv_all_day:  // 1 全日 2 半日
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.white));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_white_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.color_hint_color));

                workDayMode = 1;
                break;
            case R.id.tv_half_day:
                tv_all_day.setBackground(getResources().getDrawable(R.drawable.bg_white_left_radius_4));
                tv_all_day.setTextColor(getColor(R.color.color_hint_color));
                tv_half_day.setBackground(getResources().getDrawable(R.drawable.bg_blue_right_radius_4));
                tv_half_day.setTextColor(getColor(R.color.white));

                workDayMode = 2;
                break;

            case R.id.btn_next:
                curSalary = Utils.StripZeros(et_salary.getText().toString());
                lowestSalary = Utils.StripZeros(et_expect_salary_low.getText().toString());
                highestSalary = Utils.StripZeros(et_expect_salary_high.getText().toString());

                if (careerDirectionId == 0||TextUtils.isEmpty(info_specialisations.getLeftText())||info_specialisations.getLeftText().equals("职业方向")) {
                    toast("没选择专业方向");
                    return;
                }
                if (workYearsId == 0||TextUtils.isEmpty(info_work_experience.getLeftText())||info_work_experience.getLeftText().equals("工作经验")) {
                    toast("没选择工作经验");
                    return;
                }
                if (TextUtils.isEmpty(curSalary)) {
                    toast("没有输入当前薪资");
                    return;
                }
                if (TextUtils.isEmpty(lowestSalary)) {
                    toast("没有输入期望最低薪资");
                    return;
                }
                if (TextUtils.isEmpty(highestSalary)) {
                    toast("没有输入期望最高薪资");
                    return;
                }

                Integer low = Integer.valueOf(lowestSalary);
                Integer high = Integer.valueOf(highestSalary);
                if (high < low) {
                    et_expect_salary_high.setText("");
                    toast("期望最高薪资不能低于最低薪资");
                    return;
                }

                updateCareer();
                break;

        }

    }

    public void updateCareer() {
        EasyHttp.post(this)
                .api(new UpdateCareerApi()
                        .setCareerDirectionId(careerDirectionId)
                        .setCurSalary(Double.parseDouble(curSalary))
                        .setHighestSalary(Double.parseDouble(highestSalary))
                        .setLowestSalary(Double.parseDouble(lowestSalary))
                        .setWorkDayMode(workDayMode)
                        .setWorkYearsId(workYearsId))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        SPUtils.getInstance().put(AppConfig.CAREER_ID, careerDirectionId+"");//保存职业方向 后面获取标签需要用到
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
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
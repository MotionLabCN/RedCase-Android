package com.tntlinking.tntdev.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.DeleteEducationApi;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.AddEducationApi;
import com.tntlinking.tntdev.http.api.UpdateEducationApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;


/**
 * 添加教育经历
 */
public final class AddEducationActivityNew extends AppActivity {
    private AppCompatTextView tv_title;
    private ClearEditText et_info_school_name;
    private SettingBar info_education;
    private ClearEditText et_info_major;//专业
    private TextView info_school_in_time;
    private TextView info_school_end_time;
    private SettingBar info_training_method;
    private AppCompatButton btn_delete;
    private AppCompatButton btn_commit;


    private int educationId = 1;
    private int training_methodId = 1;
    private String school_name = "";
    private String education = "";
    private String major = "";
    private String in_time = "";
    private String end_time = "";
    private String training = "";

    private int position = 0;
    private int id = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.add_education_activity;
    }


    @Override
    protected void initView() {

        tv_title = findViewById(R.id.tv_title);
        et_info_school_name = findViewById(R.id.et_info_school_name);
        info_education = findViewById(R.id.info_education);
        et_info_major = findViewById(R.id.et_info_major);

        info_school_in_time = findViewById(R.id.info_school_in_time);
        info_school_end_time = findViewById(R.id.info_school_end_time);
        info_training_method = findViewById(R.id.info_training_method);
        btn_delete = findViewById(R.id.btn_delete);
        btn_commit = findViewById(R.id.btn_commit);
        setOnClickListener(info_education, info_school_in_time, info_school_end_time, info_training_method, btn_delete, btn_commit);

    }

    private List<GetDictionaryApi.DictionaryBean> mEducationList;
    private List<GetDictionaryApi.DictionaryBean> mTrainingList;

    @Override
    protected void initData() {
        mEducationList = getDictionaryList("5");//学历
        mTrainingList = getDictionaryList("7");//培养方式

        position = getInt("position", 0);
        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (bean != null) {
            DeveloperInfoBean.DeveloperEducation developerEducation = bean.getEducationDtoList().get(position);
            if (bean.getEducationDtoList().size() != 0) {
                if (TextUtils.isEmpty(developerEducation.getCollegeName())) {
                    btn_delete.setVisibility(View.GONE);
                    tv_title.setText("添加教育经历");
                } else {
                    btn_delete.setVisibility(View.VISIBLE);
                    tv_title.setText("编辑教育经历");

                    et_info_school_name.setText(developerEducation.getCollegeName());
                    info_education.setLeftText(developerEducation.getEducationName());
                    info_school_in_time.setText(developerEducation.getInSchoolStartTime());
                    info_school_end_time.setText(developerEducation.getInSchoolEndTime());
                    et_info_major.setText(developerEducation.getMajor());
                    info_training_method.setLeftText(developerEducation.getTrainingModeName());


                    school_name = developerEducation.getCollegeName();
                    education = developerEducation.getEducationName();
                    major = developerEducation.getMajor();
                    in_time = developerEducation.getInSchoolStartTime();
                    end_time = developerEducation.getInSchoolEndTime();
                    training = developerEducation.getTrainingModeName();
                    id = developerEducation.getId();
                }
            }
        }

    }


    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_education:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择学历")
                        .setList(mEducationList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {

                        info_education.setLeftText(mEducationList.get(type).getName());
                        educationId = mEducationList.get(type).getId();

                        education = mEducationList.get(type).getName();
                    }
                }).show();
                break;

            case R.id.info_school_in_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month);

                        info_school_in_time.setText(mInTime);
                        in_time = mInTime;
                    }

                }).show();
                break;
            case R.id.info_school_end_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mEndTime = year + "-" + Utils.formatDate(month);

                        info_school_end_time.setText(mEndTime);
                        end_time = mEndTime;
                    }

                }).show();

                break;
            case R.id.info_training_method:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择培养方式")
                        .setList(mTrainingList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {

                        info_training_method.setLeftText(mTrainingList.get(type).getName());
                        training_methodId = mTrainingList.get(type).getId();
                        training = mTrainingList.get(type).getName();
                    }
                }).show();
                break;
            case R.id.btn_delete:
//                setResult(RESULT_OK, new Intent().putExtra("position", position));
//                finish();
                deleteEducation(id);
                break;
            case R.id.btn_commit:
                school_name = et_info_school_name.getText().toString();
                major = et_info_major.getText().toString();

                if (TextUtils.isEmpty(school_name)) {
                    toast("没有输入院校名称");
                    return;
                }
                if (TextUtils.isEmpty(education)) {
                    toast("没选择学历");
                    return;
                }
                if (TextUtils.isEmpty(major)) {
                    toast("没有输入专业");
                    return;
                }
                if (TextUtils.isEmpty(in_time)) {
                    toast("没有选择入学时间");
                    return;
                }
                if (TextUtils.isEmpty(end_time)) {
                    toast("没有选择毕业时间");
                    return;
                }
                if (TextUtils.isEmpty(training)) {
                    toast("没选择培养方式");
                    return;
                }

                if (btn_delete.getVisibility() == View.VISIBLE) {
                    updateEducation(id);
                } else {
                    addEducation();
                }

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

    public void addEducation() {
        EasyHttp.post(this)
                .api(new AddEducationApi()
                        .setCollegeName(school_name)
                        .setEducationId(educationId)
                        .setMajor(major)
                        .setInSchoolStartTime(in_time + "-01")
                        .setInSchoolEndTime(end_time + "-01")
                        .setTrainingMode(training_methodId))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    /**
     * 修改教育信息
     *
     * @param id
     */
    public void updateEducation(int id) {
        EasyHttp.put(this)
                .api(new UpdateEducationApi()
                        .setId(id)
                        .setCollegeName(school_name)
                        .setEducationId(educationId)
                        .setMajor(major)
                        .setInSchoolStartTime(in_time + "-01")
                        .setInSchoolEndTime(end_time + "-01")
                        .setTrainingMode(training_methodId))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


    /**
     * 删除教育信息
     *
     * @param id
     */
    public void deleteEducation(int id) {
        EasyHttp.delete(this)
                .api(new DeleteEducationApi()
                        .setEducationId(id))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


}
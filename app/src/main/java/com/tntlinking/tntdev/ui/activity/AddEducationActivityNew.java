package com.tntlinking.tntdev.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_FIRST_RESUME;
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_RESUME;


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
    private String schoolName = "";
    private String educationName = "";
    private String major = "";
    private String in_time = "";
    private String end_time = "";
    private String trainingName = "";

    private int position = 0;
    private int mId = 0;// 教育条目 id
    private DeveloperInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.add_education_activity;
    }

    private DeveloperInfoBean singleton = DeveloperInfoBean.getSingleton();

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
        mBean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (mBean != null) {
            DeveloperInfoBean.DeveloperEducation developerEducation = mBean.getEducationDtoList().get(position);
            if (mBean.getEducationDtoList().size() != 0) {
                if (TextUtils.isEmpty(developerEducation.getCollegeName())) {
//                    btn_delete.setVisibility(View.GONE);
                    tv_title.setText("添加教育经历");
                    btn_delete.setText("保存");
                    btn_commit.setText("保存并添加下一条");
                } else {
//                    btn_delete.setVisibility(View.VISIBLE);

                    //1 是直接点击添加的，2 是上传简历解析 没有id 也是算添加 不算编辑
                    if (developerEducation.getId() == 0) {
                        tv_title.setText("添加教育经历");
                        btn_delete.setText("保存");
                        btn_commit.setText("保存并添加下一条");
                    } else {
                        tv_title.setText("编辑教育经历");
                        btn_delete.setText("删除");
                        btn_commit.setText("保存");
                    }

//                    et_info_school_name.setText(developerEducation.getCollegeName());
//                    info_education.setLeftText(developerEducation.getEducationName());
//                    info_school_in_time.setText(developerEducation.getInSchoolStartTime());
//                    info_school_end_time.setText(developerEducation.getInSchoolEndTime());
//                    et_info_major.setText(developerEducation.getMajor());
//                    info_training_method.setLeftText(developerEducation.getTrainingModeName());

                    et_info_school_name.setText(TextUtils.isEmpty(developerEducation.getCollegeName()) ? "" : developerEducation.getCollegeName());
                    info_education.setLeftText(TextUtils.isEmpty(developerEducation.getEducationName()) ? "学历" : developerEducation.getEducationName());
                    info_school_in_time.setText(TextUtils.isEmpty(developerEducation.getInSchoolStartTime()) ? "选择入学时间" : developerEducation.getInSchoolStartTime());
                    info_school_end_time.setText(TextUtils.isEmpty(developerEducation.getInSchoolEndTime()) ? "选择毕业时间" : developerEducation.getInSchoolEndTime());
                    et_info_major.setText(TextUtils.isEmpty(developerEducation.getMajor()) ? "" : developerEducation.getMajor());
                    info_training_method.setLeftText(TextUtils.isEmpty(developerEducation.getTrainingModeName()) ? "培养方式" : developerEducation.getTrainingModeName());

                    sYearMonth = Utils.dateToStamp(developerEducation.getInSchoolStartTime());
                    eYearMonth = Utils.dateToStamp(developerEducation.getInSchoolEndTime());

                    schoolName = developerEducation.getCollegeName();
                    educationName = developerEducation.getEducationName();
                    educationId = developerEducation.getEducationId();
                    major = developerEducation.getMajor();
                    in_time = developerEducation.getInSchoolStartTime();
                    end_time = developerEducation.getInSchoolEndTime();
                    trainingName = developerEducation.getTrainingModeName();
                    training_methodId = developerEducation.getTrainingMode();
                    mId = developerEducation.getId();
                }

                if (getBoolean(IS_RESUME)) {
                    btn_commit.setText("下一步");
                    btn_delete.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onLeftClick(View view) {
        super.onLeftClick(view);
        if (getBoolean(IS_FIRST_RESUME)) {
            Intent intent = new Intent(this, EnterDeveloperActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
            startActivity(intent);
            ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
        }
    }

    private long sYearMonth;// 开始时间戳
    private long eYearMonth;// 结束时间戳

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

                                educationName = mEducationList.get(type).getName();
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

                        sYearMonth = Utils.dateToStamp(mInTime);

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

                        eYearMonth = Utils.dateToStamp(mEndTime);

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
                                trainingName = mTrainingList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.btn_delete:
//                setResult(RESULT_OK, new Intent().putExtra("position", position));
//                finish();

                if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                    schoolName = et_info_school_name.getText().toString();
                    major = et_info_major.getText().toString();

                    if (TextUtils.isEmpty(schoolName)) {
                        toast("没有输入院校名称");
                        return;
                    }
                    if (TextUtils.isEmpty(educationName)) {
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
                    if (sYearMonth > eYearMonth) {
                        toast("入学时间不能大于毕业时间");
                        info_school_in_time.setText("选择入学时间");
                        info_school_end_time.setText("选择毕业时间");
                        in_time = "";
                        end_time = "";
                        return;
                    }
                    if (TextUtils.isEmpty(trainingName)) {
                        toast("没选择培养方式");
                        return;
                    }
                    addEducation(true);
                } else {
                    new BaseDialog.Builder<>(this)
                            .setContentView(R.layout.write_daily_delete_dialog)
                            .setAnimStyle(BaseDialog.ANIM_SCALE)
                            .setText(R.id.tv_title, "是否确认删除？")
                            .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                            .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                                deleteEducation(mId, dialog);
                            })
                            .show();

                }
                break;
            case R.id.btn_commit:
                schoolName = et_info_school_name.getText().toString();
                major = et_info_major.getText().toString();

                if (TextUtils.isEmpty(schoolName)) {
                    toast("没有输入院校名称");
                    return;
                }
                if (TextUtils.isEmpty(educationName)) {
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
                if (sYearMonth > eYearMonth) {
                    toast("入学时间不能大于毕业时间");
                    info_school_in_time.setText("选择入学时间");
                    info_school_end_time.setText("选择毕业时间");
                    in_time = "";
                    end_time = "";
                    return;
                }
                if (TextUtils.isEmpty(trainingName)) {
                    toast("没选择培养方式");
                    return;
                }

                if (btn_commit.getText().equals("完成")) {
                    Intent intent = new Intent(this, EnterDeveloperActivity.class);
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
                    startActivity(intent);
                    ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
                } else {
                    if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                        addEducation(false);
                    } else {
                        updateEducation(mId);
                    }
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

    /**
     * 添加 和修改教育信息  id传null或者传0 是新增，其他是更新修改
     */
    public void addEducation(boolean isBack) {
        EasyHttp.post(this)
                .api(new UpdateEducationApi()
                        .setId(null)
                        .setCollegeName(schoolName)
                        .setEducationId(educationId)
                        .setMajor(major)
                        .setInSchoolStartTime(in_time + "-01")
                        .setInSchoolEndTime(end_time + "-01")
                        .setTrainingMode(training_methodId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperEducation>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperEducation> data) {
                        if (getBoolean(IS_RESUME)) {
//                            DeveloperInfoBean.DeveloperEducation developerEducation = mBean.getEducationDtoList().get(position);
//                            developerEducation.setId(data.getData().getId());
//                            developerEducation.setCollegeName(school_name);
//                            developerEducation.setEducationName(education);
//                            developerEducation.setTrainingModeName(training);
//                            developerEducation.setMajor(major);
//                            developerEducation.setInSchoolStartTime(in_time);
//                            developerEducation.setInSchoolEndTime(end_time);
//
//                            checkDeveloper(mBean);


                            DeveloperInfoBean.DeveloperEducation dEducation = singleton.getEducationDtoList().get(position);
                            dEducation.setId(data.getData().getId());
                            dEducation.setCollegeName(schoolName);
                            dEducation.setEducationName(educationName);
                            dEducation.setEducationId(educationId);
                            dEducation.setMajor(major);
                            dEducation.setInSchoolStartTime(in_time);
                            dEducation.setInSchoolEndTime(end_time);
                            dEducation.setTrainingMode(training_methodId);
                            dEducation.setTrainingModeName(trainingName);

                            checkDeveloper(singleton);
                        } else if (isBack) {
                            setResult(RESULT_OK);
                            finish();

                            toast("保存成功");
                        } else {
                            et_info_school_name.setText("");
                            et_info_school_name.setHint("院校名称");
                            info_education.setLeftText("学历");
                            info_school_in_time.setText("选择入学时间");
                            info_school_end_time.setText("选择毕业时间");
                            et_info_major.setText("");
                            et_info_major.setHint("专业");
                            info_training_method.setLeftText("培养方式");

                            schoolName = "";
                            educationName = "";
                            major = "";
                            in_time = "";
                            end_time = "";
                            trainingName = "";
                            mId = 0;

                            toast("保存成功");
                        }


                    }
                });
    }

    /**
     * 添加 和修改教育信息  id传null或者传0 是新增，其他是更新修改
     *
     * @param id
     */
    public void updateEducation(int id) {
        EasyHttp.post(this)
                .api(new UpdateEducationApi()
                        .setId(id + "")
                        .setCollegeName(schoolName)
                        .setEducationId(educationId)
                        .setMajor(major)
                        .setInSchoolStartTime(in_time + "-01")
                        .setInSchoolEndTime(end_time + "-01")
                        .setTrainingMode(training_methodId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperEducation>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperEducation> data) {
                        if (!getBoolean(IS_RESUME)) {
                            setResult(RESULT_OK);
                            finish();
                        } else {

//                            DeveloperInfoBean.DeveloperEducation developerEducation = mBean.getEducationDtoList().get(position);
//                            developerEducation.setCollegeName(school_name);
//                            developerEducation.setEducationName(education);
//                            developerEducation.setTrainingModeName(training);
//                            developerEducation.setMajor(major);
//                            developerEducation.setInSchoolStartTime(in_time);
//                            developerEducation.setInSchoolEndTime(end_time);
//
//
//                            checkDeveloper(mBean);

                            DeveloperInfoBean.DeveloperEducation dEducation = singleton.getEducationDtoList().get(position);
                            dEducation.setId(data.getData().getId());
                            dEducation.setCollegeName(schoolName);
                            dEducation.setEducationName(educationName);
                            dEducation.setEducationId(educationId);
                            dEducation.setMajor(major);
                            dEducation.setInSchoolStartTime(in_time);
                            dEducation.setInSchoolEndTime(end_time);
                            dEducation.setTrainingMode(training_methodId);
                            dEducation.setTrainingModeName(trainingName);

                            checkDeveloper(singleton);
                        }
                    }
                });
    }


    /**
     * 删除教育信息
     *
     * @param id
     * @param dialog
     */
    public void deleteEducation(int id, BaseDialog dialog) {
        EasyHttp.delete(this)
                .api(new DeleteEducationApi()
                        .setEducationId(id))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


    private int position2 = 0;
    private int position3 = 0;

    public void checkDeveloper(DeveloperInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(IS_RESUME, true);

        if (!isJumpEducation(bean)) {
            setNextData(bean);
            return;
        }
        if (!isJumpWork(bean)) {
            intent.setClass(this, AddWorkActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra("position", position2);
            startActivity(intent);
            return;
        }
        if (!isJumpProject(bean)) {
            intent.setClass(this, AddProjectActivityNew.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra("position", position3);
            startActivity(intent);
            return;
        } else {
            btn_commit.setText("完成");
        }

    }

    // 教育资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpEducation(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        boolean mTag = true;
        if (educationDtoList.size() == 0) {
            return true;
        } else {// +1 跳到下一个教育经历集合 因为当前position 已经补充完整，跳该position下一个位置
            if (position + 1 >= educationDtoList.size()) {
                return true;
            }
            for (int i = position + 1; i < educationDtoList.size(); i++) {
                if (TextUtils.isEmpty(educationDtoList.get(i).getCollegeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getEducationName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getTrainingModeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getMajor()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolStartTime()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolEndTime())) {
                    position = i;
                    mTag = true;
                    break;
                } else {
                    position = i;
                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }


    // 工作资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpWork(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        boolean mTag = true;
        if (workExperienceDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < workExperienceDtoList.size(); i++) {
                if (TextUtils.isEmpty(workExperienceDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getPositionName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkStartTime()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkEndTime())) {
                    position2 = i;

                    mTag = true;
                    break;
                } else {
                    position2 = i;

                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }


    // 项目资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpProject(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
        boolean mTag = true;
        if (projectDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < projectDtoList.size(); i++) {
                if (TextUtils.isEmpty(projectDtoList.get(i).getProjectName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectStartDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectEndDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getPosition()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getDescription()) &&
                        projectDtoList.get(i).getProjectSkillList().size() == 0) {

                    position3 = i;

                    mTag = true;
                    break;
                } else {
                    position3 = i;

                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }


    public void setNextData(DeveloperInfoBean bean) {

        if (bean != null) {
            DeveloperInfoBean.DeveloperEducation developerEducation = bean.getEducationDtoList().get(position);
            if (bean.getEducationDtoList().size() != 0) {

                tv_title.setText("编辑教育经历");
                btn_commit.setText("下一步");
                btn_delete.setVisibility(View.GONE);

                et_info_school_name.setText(TextUtils.isEmpty(developerEducation.getCollegeName()) ? "" : developerEducation.getCollegeName());
                info_education.setLeftText(TextUtils.isEmpty(developerEducation.getEducationName()) ? "学历" : developerEducation.getEducationName());
                info_school_in_time.setText(TextUtils.isEmpty(developerEducation.getInSchoolStartTime()) ? "选择入学时间" : developerEducation.getInSchoolStartTime());
                info_school_end_time.setText(TextUtils.isEmpty(developerEducation.getInSchoolEndTime()) ? "选择毕业时间" : developerEducation.getInSchoolEndTime());
                et_info_major.setText(TextUtils.isEmpty(developerEducation.getMajor()) ? "" : developerEducation.getMajor());
                info_training_method.setLeftText(TextUtils.isEmpty(developerEducation.getTrainingModeName()) ? "培养方式" : developerEducation.getTrainingModeName());

                sYearMonth = Utils.dateToStamp(developerEducation.getInSchoolStartTime());
                eYearMonth = Utils.dateToStamp(developerEducation.getInSchoolEndTime());

                schoolName = developerEducation.getCollegeName();
                educationName = developerEducation.getEducationName();
                educationId = developerEducation.getEducationId();
                major = developerEducation.getMajor();
                in_time = developerEducation.getInSchoolStartTime();
                end_time = developerEducation.getInSchoolEndTime();
                trainingName = developerEducation.getTrainingModeName();
                training_methodId = developerEducation.getTrainingMode();
                mId = developerEducation.getId();
            }


        }

    }
}
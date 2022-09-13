package com.tntlinking.tntdev.ui.firm.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;

import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.http.api.GetPositionInfoApi;
import com.tntlinking.tntdev.http.api.GetPositionOriginalApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.api.SendPositionApi;
import com.tntlinking.tntdev.http.api.UpdatePositionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.AddProjectTagActivityNew;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.tntlinking.tntdev.ui.dialog.GenderSelectDialog;
import com.tntlinking.tntdev.ui.dialog.IndustrySelectDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 发布职位信息
 */
public final class SendPositionActivity extends AppActivity {
    private TitleBar title_bar;
    private SettingBar position_career_id;
    private SettingBar position_skill_id;
    private SettingBar position_education_id;
    private SettingBar position_training_mode_id;
    private SettingBar position_work_year_id;
    private SettingBar position_industry_id;
    private ClearEditText et_position_name;
    private ClearEditText et_expect_salary_low;
    private ClearEditText et_expect_salary_high;
    private ClearEditText et_recruit_count;
    private ClearEditText et_description;
    private AppCompatButton btn_commit;

    private List<GetDictionaryApi.DictionaryBean> mCompanyList;//所属行业
    private List<GetDictionaryApi.DictionaryBean> mCareerIdList;//职业id
    private List<GetDictionaryApi.DictionaryBean> mWorkYearsIdList;//工作经验
    private List<GetDictionaryApi.DictionaryBean> mEducationList;//学历
    private List<GetDictionaryApi.DictionaryBean> mTrainingList;//培养方式


    private int careerDirectionId = 0; //职业方向id
    private String careerDirectionName = "";
    private int workYearsId = 0;//工作年限id
    private String workYearsName = "";
    private int educationId = 0;//学历id
    private String educationName = "";//学历
    private int trainingId = 0;//培养方式id
    private String trainingName = "";//培养方式
    private int industryMandatory = -1;//行业强制性
    private String industryMandatoryName = "";
    private String major = "";//专业名字

    private String careerPosition = "";
    private String startPay = "";
    private String endPay = "";
    private String mCount = "0";
    private String mDescription;
    private int mId;
    private int mDeveloperId;//从开发者页面传过来的，

    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();
    private List<Integer> mTagIntList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.send_position_activity;
    }

    @Override
    protected void initView() {

        title_bar = findViewById(R.id.title_bar);
        position_career_id = findViewById(R.id.position_career_id);
        position_skill_id = findViewById(R.id.position_skill_id);
        position_education_id = findViewById(R.id.position_education_id);
        position_training_mode_id = findViewById(R.id.position_training_mode_id);
        position_work_year_id = findViewById(R.id.position_work_year_id);
        position_industry_id = findViewById(R.id.position_industry_id);
        et_position_name = findViewById(R.id.et_position_name);
        et_expect_salary_low = findViewById(R.id.et_expect_salary_low);
        et_expect_salary_high = findViewById(R.id.et_expect_salary_high);
        et_recruit_count = findViewById(R.id.et_recruit_count);
        et_description = findViewById(R.id.et_description);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(position_career_id, position_skill_id, position_education_id, position_training_mode_id,
                position_work_year_id, position_industry_id, btn_commit);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        mDeveloperId = getInt("developerId");
//        mCompanyList = getDictionaryList("1");//所属行业
        mWorkYearsIdList = getDictionaryList("4");//工作经验
        mEducationList = getDictionaryList("5");//学历
        mCareerIdList = getDictionaryList("6");//职业方向
        mTrainingList = getDictionaryList("7");//培养方式
        int typeId = getInt("typeId");
        GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");
        GetPositionOriginalApi.Bean beanIds = getSerializable("position_bean_ids");
        if (bean != null) {
            title_bar.setTitle("修改职位");
            btn_commit.setText("修改职位");

            position_career_id.setLeftText(bean.getCareerDirection());
            et_position_name.setText(bean.getTitle());
            position_education_id.setLeftText(bean.getEducation());
            position_training_mode_id.setLeftText(bean.getTrainingMode());
            position_work_year_id.setLeftText(bean.getWorkYears());
            position_industry_id.setLeftText(bean.getIndustryMandatory() ? "是" : "否");
            et_expect_salary_low.setText(bean.getStartPay() + "");
            et_expect_salary_high.setText(bean.getEndPay() + "");
            et_recruit_count.setText(bean.getRecruitCount() + "");
            et_description.setText(bean.getDescription());

            if (bean.getSkills() != null && bean.getSkills().size() > 0) {
                StringBuilder sb = new StringBuilder();
                mTagIntList.clear();
                mSelectList.clear();
                for (String i : bean.getSkills()) {
                    sb.append(i);
                    sb.append(",");
                }
                for (int i = 0; i < beanIds.getSkillIds().size(); i++) {
                    GetTagListApi.Bean.ChildrenBean childrenBean = new GetTagListApi.Bean.ChildrenBean();
                    childrenBean.setId(beanIds.getSkillIds().get(i));
                    mSelectList.add(childrenBean);
                    mTagIntList.add(beanIds.getSkillIds().get(i));
                }
                for (int i = 0; i < bean.getSkills().size(); i++) {
                    mSelectList.get(i).setSkillName(bean.getSkills().get(i));
                }
                position_skill_id.setLeftText(sb.toString());
            }


            careerDirectionName = bean.getCareerDirection();
            careerDirectionId = beanIds.getCareerDirectionId();
            workYearsName = bean.getWorkYears();
            workYearsId = beanIds.getWorkYearsId();
            educationName = bean.getEducation();
            educationId = beanIds.getEducationId();
            trainingName = bean.getTrainingMode();
            trainingId = beanIds.getTrainingModeId();
            industryMandatory = beanIds.getIndustryMandatory();

            careerPosition = bean.getTitle();
            startPay = bean.getStartPay() + "";
            endPay = bean.getEndPay() + "";
            mCount = bean.getRecruitCount() + "";
            mDescription = bean.getDescription();

            mId = beanIds.getId();
        } else if (typeId != 0) {
            getPositionInfo(typeId);
        }
    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.position_career_id:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择职业方向")
                        .setList(mCareerIdList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                position_career_id.setLeftText(mCareerIdList.get(type).getName());
                                careerDirectionId = mCareerIdList.get(type).getId();

                                careerDirectionName = mCareerIdList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.position_skill_id:
                Intent intents = new Intent(this, AddProjectTagActivityNew.class);

                if (mSelectList.size() != 0) {
                    intents.putExtra("list", (Serializable) mSelectList);
                }
                getActivity().startActivityForResult(intents, 1001);
                break;
            case R.id.position_education_id:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择学历")
                        .setList(mEducationList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                position_education_id.setLeftText(mEducationList.get(type).getName());
                                educationId = mEducationList.get(type).getId();

                                educationName = mEducationList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.position_training_mode_id:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择培养方式")
                        .setList(mTrainingList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                position_training_mode_id.setLeftText(mTrainingList.get(type).getName());
                                trainingId = mTrainingList.get(type).getId();

                                trainingName = mTrainingList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.position_work_year_id:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择工作经验")
                        .setList(mWorkYearsIdList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                position_work_year_id.setLeftText(mWorkYearsIdList.get(type).getName());
                                workYearsId = mWorkYearsIdList.get(type).getId();

                                workYearsName = mWorkYearsIdList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.position_industry_id:
                new GenderSelectDialog.Builder(this)
                        .setTitle("是否行业匹配")
                        .setList("否", "是").setListener(new GenderSelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                // 0 否 1 是
                                industryMandatoryName = type == 0 ? "否" : "是";
                                industryMandatory = type;
                                position_industry_id.setLeftText(industryMandatoryName);
                            }
                        }).show();
                break;
            case R.id.btn_commit:
                careerPosition = et_position_name.getText().toString();
                startPay = et_expect_salary_low.getText().toString();
                endPay = et_expect_salary_high.getText().toString();
                mCount = et_recruit_count.getText().toString();
                mDescription = et_description.getText().toString();

                if (careerDirectionId == 0) {
                    toast("你还没有选择职业方向");
                    return;
                }
                if (TextUtils.isEmpty(careerPosition)) {
                    toast("职位名称未填写");
                    return;
                }
                if (mTagIntList.size() == 0) {
                    toast("你还没有选择职业技能");
                    return;
                }
                if (educationId == 0) {
                    toast("你还没有选择学历");
                    return;
                }
                if (trainingId == 0) {
                    toast("你还没有选择培养方式");
                    return;
                }
                if (workYearsId == 0) {
                    toast("你还没有选择工作年限");
                    return;
                }
                if (TextUtils.isEmpty(startPay)) {
                    toast("最低服务价格未填写");
                    return;
                }
                if (TextUtils.isEmpty(endPay)) {
                    toast("最高服务价格未填写");
                    return;
                }
                if (Double.parseDouble(startPay) > Double.parseDouble(endPay)) {
                    toast("最低服务价格不能高于最高服务价格");
                    return;
                }
                if (industryMandatory == -1) {
                    toast("你还没有选择行业匹配");
                    return;
                }
                if (TextUtils.isEmpty(mCount)) {
                    toast("需求数量未填写");
                    return;
                }
                if (TextUtils.isEmpty(mDescription)) {
                    toast("职位描述未填写");
                    return;
                }

                if (btn_commit.getText().equals("发布职位")) {
                    submitDeveloper();
                } else {
                    updateDeveloper();
                }
                break;

        }

    }

    /**
     * 发布职位
     */
    public void submitDeveloper() {
        EasyHttp.post(this)
                .api(new SendPositionApi()
                        .setCareerDirectionId(careerDirectionId)
                        .setTitle(careerPosition)
                        .setDescription(mDescription)
                        .setRecruitCount(mCount)
                        .setWorkOperId(301)
                        .setWorkYearsId(workYearsId)
                        .setEducationId(educationId)
                        .setTrainingModeId(trainingId)
                        .setWorkDaysMode(1)
                        .setStartPay(startPay)
                        .setEndPay(endPay)
                        .setIndustryMandatory(industryMandatory)
                        .setSkillIds(mTagIntList))
                .request(new HttpCallback<HttpData<Integer>>(this) {

                    @Override
                    public void onSucceed(HttpData<Integer> data) {
//                        toast("提交成功");
//                        startActivity(SendPositionSuccessActivity.class);
//                        finish();

                        int positionId = data.getData();
                        Intent intent = new Intent();
                        intent.setClass(SendPositionActivity.this, SendPositionSuccessActivity.class);
                        intent.putExtra("developerId", mDeveloperId);
                        intent.putExtra("positionId", positionId);
                        startActivity(intent);
                        finish();

                    }
                });
    }

    /**
     * 更新职位
     */
    public void updateDeveloper() {
        EasyHttp.put(this)
                .api(new UpdatePositionApi()
                        .setId(mId)
                        .setCareerDirectionId(careerDirectionId)
                        .setTitle(careerPosition)
                        .setDescription(mDescription)
                        .setRecruitCount(mCount)
                        .setWorkOperId(301)
                        .setWorkYearsId(workYearsId)
                        .setEducationId(educationId)
                        .setTrainingModeId(trainingId)
                        .setWorkDaysMode(1)
                        .setStartPay(startPay)
                        .setEndPay(endPay)
                        .setIndustryMandatory(industryMandatory)
                        .setSkillIds(mTagIntList))
                .request(new HttpCallback<HttpData<Integer>>(this) {

                    @Override
                    public void onSucceed(HttpData<Integer> data) {
//                        toast("提交成功");
//                        startActivity(SendPositionSuccessActivity.class);
//                        finish();

//                        int positionId = data.getData();
                        Intent intent = new Intent();
                        intent.setClass(SendPositionActivity.this, SendPositionSuccessActivity.class);
                        intent.putExtra("developerId", mDeveloperId);
//                        intent.putExtra("positionId", positionId);
                        startActivity(intent);
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
                            if (parentId.equals("1")) {
                                SPUtils.getInstance().put("industry", GsonUtils.toJson(data.getData()));
                            }
                        }
                    }
                });

        return mList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                List<GetTagListApi.Bean.ChildrenBean> list = (List<GetTagListApi.Bean.ChildrenBean>) data.getSerializableExtra("list");

                mSelectList.clear();
                mSelectList.addAll(list);
                mTagIntList.clear();
                if (list != null && list.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (GetTagListApi.Bean.ChildrenBean i : list) {
                        sb.append(i.getSkillName());
                        sb.append(",");
                        mTagIntList.add(i.getId());
                    }

                    position_skill_id.setLeftText(sb.toString());
                }

            }
        }
    }

    private void getPositionInfo(int positionId) {
        EasyHttp.get(this)
                .api(new GetPositionInfoApi().setPositionId(positionId))
                .request(new HttpCallback<HttpData<GetPositionInfoApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetPositionInfoApi.Bean> data) {
                        GetPositionInfoApi.Bean bean = data.getData();
                        title_bar.setTitle("修改职位");
                        btn_commit.setText("修改职位");

                        position_career_id.setLeftText(bean.getCareerDirection());
                        et_position_name.setText(bean.getTitle());
                        position_education_id.setLeftText(bean.getEducation());
                        position_training_mode_id.setLeftText(bean.getTrainingMode());
                        position_work_year_id.setLeftText(bean.getWorkYears());
                        position_industry_id.setLeftText(bean.getIndustryMandatory() ? "是" : "否");
                        et_expect_salary_low.setText(bean.getStartPay() * 1000 + "");
                        et_expect_salary_high.setText(bean.getEndPay() * 1000 + "");
                        et_recruit_count.setText(bean.getRecruitCount() + "");
                        et_description.setText(bean.getDescription());

                        if (bean.getSkills() != null && bean.getSkills().size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            mTagIntList.clear();
                            mSelectList.clear();
                            for (String i : bean.getSkills()) {
                                sb.append(i);
                                sb.append(",");
                            }
                            for (int i = 0; i < bean.getSkillsList().size(); i++) {
                                GetTagListApi.Bean.ChildrenBean childrenBean = new GetTagListApi.Bean.ChildrenBean();
                                childrenBean.setId(bean.getSkillsList().get(i).getId());
                                mSelectList.add(childrenBean);
                                mTagIntList.add(bean.getSkillsList().get(i).getId());
                            }
                            for (int i = 0; i < bean.getSkills().size(); i++) {
                                mSelectList.get(i).setSkillName(bean.getSkills().get(i));
                            }
                            position_skill_id.setLeftText(sb.toString());
                        }


                        careerDirectionName = bean.getCareerDirection();
                        careerDirectionId = bean.getCareerDirectionId();
                        workYearsName = bean.getWorkYears();
                        workYearsId = bean.getWorkYearsId();
                        educationName = bean.getEducation();
                        educationId = bean.getEducationId();
                        trainingName = bean.getTrainingMode();
                        trainingId = bean.getTrainingModeId();

                        if (bean.getIndustryMandatory()) {
                            industryMandatory = 1;
                        } else {
                            industryMandatory = 0;
                        }

                        careerPosition = bean.getTitle();
                        startPay = bean.getStartPay() * 1000 + "";
                        endPay = bean.getEndPay() * 1000 + "";
                        mCount = bean.getRecruitCount() + "";
                        mDescription = bean.getDescription();

                        mId = bean.getId();
                    }
                });
    }
}
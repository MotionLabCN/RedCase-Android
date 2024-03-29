package com.tntlinking.tntdev.ui.firm.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import com.blankj.utilcode.util.GsonUtils;
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
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.api.SendPositionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.AddProjectTagActivityNew;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
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
    private int educationId = 1;//学历id
    private String educationName = "";//学历
    private int trainingId = 1;//培养方式id
    private String trainingName = "";//培养方式
    private int industryId;//行业id
    private String industryName = "";
    private String major = "";//专业名字

    private String careerPosition = "";
    private String startPay = "";
    private String endPay = "";
    private String mCount = "0";
    private String mDescription;


    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();
    private List<Integer> mTagIntList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.send_position_activity;
    }

    @Override
    protected void initView() {

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
        mCompanyList = getDictionaryList("1");//所属行业
        mWorkYearsIdList = getDictionaryList("4");//工作经验
        mEducationList = getDictionaryList("5");//学历
        mCareerIdList = getDictionaryList("6");//职业方向
        mTrainingList = getDictionaryList("7");//培养方式
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
                new IndustrySelectDialog.Builder(this).setTitle("选择所在行业")
                        .setListener(new IndustrySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, GetDictionaryApi.DictionaryBean bean, GetDictionaryApi.ChildrenBean childrenBean) {
                                position_industry_id.setLeftText(bean.getName() + "-" + childrenBean.getName());
                                industryId = childrenBean.getId();

                                industryName = bean.getName() + "-" + childrenBean.getName();
                            }
                        }).show();
                break;
            case R.id.btn_commit:
                careerPosition = et_position_name.getText().toString();
                startPay = et_expect_salary_low.getText().toString();
                endPay = et_expect_salary_high.getText().toString();
                mCount = et_recruit_count.getText().toString();
                mDescription = et_description.getText().toString();
                submitDeveloper();
                break;

        }

    }

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
                        .setIndustryMandatory(industryId)
                        .setSkillIds(mTagIntList))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
//                        startActivity(SaveQRActivity.class);
//                        finish();
//                        SPUtils.getInstance().put(AppConfig.RESUME_ANALYSIS, false);
                        toast("提交成功");

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
}
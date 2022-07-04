package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AddProjectApi;
import com.tntlinking.tntdev.http.api.DeleteProjectApi;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.api.UpdateProjectApi;
import com.tntlinking.tntdev.http.api.UpdateWorkApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.tntlinking.tntdev.ui.dialog.IndustrySelectDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;


/**
 * 添加项目经历
 */
public final class AddProjectActivityNew extends AppActivity {

    private AppCompatTextView tv_title;
    private ClearEditText et_project_name;//项目名称
    private TextView info_project_in_time;//
    private TextView info_project_end_time;//
    private ClearEditText et_project_position;//职位角色
    private SettingBar info_project_work_mode;//职业状态
    private ClearEditText et_project_company_name;//公司名字
    private SettingBar info_project_industry; //所在行业
    private SettingBar info_project_skill;
    private ClearEditText et_project_description;
    private AppCompatButton btn_delete;
    private AppCompatButton btn_commit;

    private static final String INTENT_KEY_PROJECT = "key_project";
    private int workModeId;//职业状态id
    private int industryId;//行业id

    private String project_name = "";
    private String in_time = "";
    private String end_time = "";
    private String project_position = "";
    private String work_mode = "";
    private String company_name = "";
    private String industry = "";
    private String project_skill = "";
    private String description = "";
    private int mId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.add_project_activity;
    }


    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_title);
        et_project_name = findViewById(R.id.et_project_name);
        info_project_in_time = findViewById(R.id.info_project_in_time);
        info_project_end_time = findViewById(R.id.info_project_end_time);
        et_project_position = findViewById(R.id.et_project_position);
        info_project_work_mode = findViewById(R.id.info_project_work_mode);
        et_project_company_name = findViewById(R.id.et_project_company_name);
        info_project_industry = findViewById(R.id.info_project_industry);
        info_project_skill = findViewById(R.id.info_project_skill);
        et_project_description = findViewById(R.id.et_project_description);
        btn_delete = findViewById(R.id.btn_delete);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(info_project_work_mode, info_project_in_time, info_project_end_time,
                info_project_industry, info_project_skill, btn_delete, btn_commit);

    }

    private List<GetDictionaryApi.DictionaryBean> mWorkStatusList;
    private List<GetDictionaryApi.DictionaryBean> mCompanyList;

    private int position = 0;

    @Override
    protected void initData() {
        mWorkStatusList = getDictionaryList("9");//职业状态
        mCompanyList = getDictionaryList("1");//所属行业

        position = getInt("position", 0);
        DeveloperInfoBean bean = getSerializable(INTENT_KEY_DEVELOPER_INFO);

        if (bean != null) {
            DeveloperInfoBean.DeveloperProject developerProject = bean.getProjectDtoList().get(position);
            if (bean.getProjectDtoList().size() != 0) {
//                if (TextUtils.isEmpty(developerProject.getProjectName())) {//判断是否有项目名字，没有就不显示
                if (developerProject.getId() == 0) {//判断是否有项目名字，没有就不显示
//                    btn_delete.setVisibility(View.GONE);
                    tv_title.setText("添加项目经历");
                    btn_delete.setText("保存");
                    btn_commit.setText("保存并添加下一条");
                } else {
//                    btn_delete.setVisibility(View.VISIBLE);
                    tv_title.setText("编辑项目经历");
                    btn_delete.setText("删除");
                    btn_commit.setText("保存");

//                    et_project_name.setText(developerProject.getProjectName());
//                    info_project_in_time.setText(developerProject.getProjectStartDate());
//                    info_project_end_time.setText(developerProject.getProjectEndDate());
//                    et_project_position.setText(developerProject.getPosition());
////                    info_project_work_mode.setLeftText(developerProject.getWorkModeName());
//                    et_project_company_name.setText(developerProject.getCompanyName());
//                    info_project_industry.setLeftText(developerProject.getIndustryName());
//                    et_project_description.setText(developerProject.getDescription());


                    et_project_name.setText(TextUtils.isEmpty(developerProject.getProjectName()) ? "" : developerProject.getProjectName());
                    info_project_in_time.setText(TextUtils.isEmpty(developerProject.getProjectStartDate()) ? "选择开始时间" : developerProject.getProjectStartDate());
                    info_project_end_time.setText(TextUtils.isEmpty(developerProject.getProjectEndDate()) ? "选择结束时间" : developerProject.getProjectEndDate());
                    et_project_position.setText(TextUtils.isEmpty(developerProject.getPosition()) ? "" : developerProject.getPosition());
//                    info_project_work_mode.setLeftText(developerProject.getWorkModeName());
                    et_project_company_name.setText(TextUtils.isEmpty(developerProject.getCompanyName()) ? "" : developerProject.getCompanyName());
                    info_project_industry.setLeftText(TextUtils.isEmpty(developerProject.getIndustryName()) ? "所在行业" : developerProject.getIndustryName());
                    et_project_description.setText(TextUtils.isEmpty(developerProject.getDescription()) ? "" : developerProject.getDescription());

                    sYearMonth = Utils.splitYearMonth(developerProject.getProjectStartDate());
                    eYearMonth = Utils.splitYearMonth(developerProject.getProjectEndDate());

                    if (developerProject.getProjectSkillList() != null && developerProject.getProjectSkillList().size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        mTagIntList.clear();
                        mSelectList.clear();
                        for (GetTagListApi.Bean.ChildrenBean i : developerProject.getProjectSkillList()) {
                            sb.append(i.getSkillName());
                            sb.append(",");
                            mTagIntList.add(i.getId());
                            mSelectList.add(i);
                        }
                        info_project_skill.setLeftText(sb.toString());
                    }

                    project_name = developerProject.getProjectName();
                    in_time = developerProject.getProjectStartDate();
                    end_time = developerProject.getProjectEndDate();
                    project_position = developerProject.getPosition();
                    work_mode = developerProject.getWorkModeName();
                    workModeId = developerProject.getWorkMode();
                    company_name = developerProject.getCompanyName();
                    industry = developerProject.getIndustryName();
                    industryId = developerProject.getIndustryId();
                    description = developerProject.getDescription();
                    mId = developerProject.getId();
                }
            }
        }
    }

    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();
    private List<Integer> mTagIntList = new ArrayList<>();

    private int sYearMonth;// 开始时间年，开始时间月
    private int eYearMonth;// 结束时间年，结束时间月

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_project_work_mode:
                if (mWorkStatusList.size() == 0) {
                    return;
                }
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择职业状态")
                        .setList(mWorkStatusList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {

                                info_project_work_mode.setLeftText(mWorkStatusList.get(type).getName());
                                workModeId = mWorkStatusList.get(type).getId();
                                work_mode = mWorkStatusList.get(type).getName();
                            }
                        }).show();
                break;
            case R.id.info_project_in_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month);

                        info_project_in_time.setText(mInTime);
                        in_time = mInTime;
                        Long timeLong = TimeUtil.getTimeLong("yyyy-MM", mInTime);
                        EasyLog.print("===timeLong==" + timeLong);

                        sYearMonth = year + month;
                    }

                }).show();
                break;
            case R.id.info_project_end_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mEndTime = year + "-" + Utils.formatDate(month);
                        info_project_end_time.setText(mEndTime);
                        end_time = mEndTime;

                        eYearMonth = year + month;
                    }

                }).show();
                break;

            case R.id.info_project_industry:
                new IndustrySelectDialog.Builder(this).setTitle("选择所在行业")
                        .setListener(new IndustrySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, GetDictionaryApi.DictionaryBean bean, GetDictionaryApi.ChildrenBean childrenBean) {

                                info_project_industry.setLeftText(bean.getName() + "-" + childrenBean.getName());
                                industryId = childrenBean.getId();
                                industry = bean.getName() + "-" + childrenBean.getName();

                                EasyLog.print("===industryId==" + industryId + "======" + childrenBean.getName());
                            }
                        }).show();
                break;
            case R.id.info_project_skill:
                Intent intents = new Intent(AddProjectActivityNew.this, AddProjectTagActivityNew.class);
//                ExperienceBean beans = getSerializable(INTENT_KEY_PROJECT);
//                if (beans != null) {
//                    intents.putExtra("list", (Serializable) beans.getSkillList());
//                } else if (mSelectList.size() != 0) {
//                    intents.putExtra("list", (Serializable) mSelectList);
//                }
                if (mSelectList.size() != 0) {
                    intents.putExtra("list", (Serializable) mSelectList);
                }
                getActivity().startActivityForResult(intents, 1001);
                break;
            case R.id.btn_delete:

                if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                    project_name = et_project_name.getText().toString();
                    project_position = et_project_position.getText().toString();
                    company_name = et_project_company_name.getText().toString();
                    description = et_project_description.getText().toString();
                    project_skill = info_project_skill.getLeftText().toString();

                    if (TextUtils.isEmpty(project_name)) {
                        toast("没有输入项目名称");
                        return;
                    }
                    if (TextUtils.isEmpty(in_time)) {
                        toast("没有选择开始时间");
                        return;
                    }
                    if (TextUtils.isEmpty(end_time)) {
                        toast("没有选择结束时间");
                        return;
                    }
                    if (sYearMonth > eYearMonth) {
                        toast("项目开始时间不能大于结束时间");
                        info_project_in_time.setText("选择开始时间");
                        info_project_end_time.setText("选择结束时间");
                        in_time = "";
                        end_time = "";
                        return;
                    }
                    if (TextUtils.isEmpty(project_position)) {
                        toast("没有输入担任角色");
                        return;
                    }
//                    if (TextUtils.isEmpty(work_mode)) {
//                        toast("没选职业状态");
//                        return;
//                    }
                    if (TextUtils.isEmpty(company_name)) {
                        toast("没有输入所属公司");
                        return;
                    }

                    if (TextUtils.isEmpty(industry)) {
                        toast("没选择所在行业");
                        return;
                    }
                    if (!project_skill.contains(",")) {
                        toast("没选择使用技能");
                        return;
                    }
                    if (TextUtils.isEmpty(description)) {
                        toast("没有输入项目描述");
                        return;
                    }
                    addProject(true);
                } else {
                    new BaseDialog.Builder<>(this)
                            .setContentView(R.layout.write_daily_delete_dialog)
                            .setAnimStyle(BaseDialog.ANIM_SCALE)
                            .setText(R.id.tv_title, "是否确认删除？")
                            .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                            .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                                deleteProject(mId, dialog);
                            })
                            .show();
                }
                break;
            case R.id.btn_commit:
                project_name = et_project_name.getText().toString();
                project_position = et_project_position.getText().toString();
                company_name = et_project_company_name.getText().toString();
                description = et_project_description.getText().toString();
                project_skill = info_project_skill.getLeftText().toString();

                if (TextUtils.isEmpty(project_name)) {
                    toast("没有输入项目名称");
                    return;
                }
                if (TextUtils.isEmpty(in_time)) {
                    toast("没有选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(end_time)) {
                    toast("没有选择结束时间");
                    return;
                }
                if (sYearMonth > eYearMonth) {
                    toast("项目开始时间不能大于结束时间");
                    info_project_in_time.setText("选择开始时间");
                    info_project_end_time.setText("选择结束时间");
                    in_time = "";
                    end_time = "";
                    return;
                }
                if (TextUtils.isEmpty(project_position)) {
                    toast("没有输入担任角色");
                    return;
                }
//                if (TextUtils.isEmpty(work_mode)) {
//                    toast("没选职业状态");
//                    return;
//                }
                if (TextUtils.isEmpty(company_name)) {
                    toast("没有输入所属公司");
                    return;
                }

                if (TextUtils.isEmpty(industry)) {
                    toast("没选择所在行业");
                    return;
                }
                if (!project_skill.contains(",")) {
                    toast("没选择使用技能");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    toast("没有输入项目描述");
                    return;
                }

                if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                    addProject(false);
                } else {
                    updateProject(mId);
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

                    info_project_skill.setLeftText(sb.toString());
                }

            }
        }
    }


    public void addProject(boolean isBack) {
        EasyHttp.post(this)
                .api(new AddProjectApi()
                        .setProjectName(project_name)
                        .setProjectStartDate(in_time + "-01")
                        .setProjectEndDate(end_time + "-01")
                        .setPosition(project_position)
                        .setCompanyName(company_name)
                        .setWorkModeId(workModeId)
                        .setIndustryId(industryId)
                        .setDescription(description)
                        .setSkillIdList(mTagIntList))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        if (isBack) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            et_project_name.setText("");
                            et_project_name.setHint("项目名称");
                            info_project_in_time.setText("选择开始时间");
                            info_project_end_time.setText("选择结束时间");
                            et_project_position.setText("");
                            et_project_position.setHint("担任角色");
//                            info_project_work_mode.setLeftText("职业状态");
                            et_project_company_name.setText("");
                            et_project_company_name.setHint("所属公司");
                            info_project_industry.setLeftText("所在行业");
                            et_project_description.setText("");
                            et_project_description.setHint("项目描述");
                            info_project_skill.setLeftText("使用技能");

                            mTagIntList.clear();
                            mSelectList.clear();

                            project_name = "";
                            in_time = "";
                            end_time = "";
                            project_position = "";
                            work_mode = "";
                            workModeId = 0;
                            company_name = "";
                            industry = "";
                            industryId = 0;
                            description = "";
                            mId = 0;
                        }

                        toast("保存成功");

                    }
                });
    }

    public void updateProject(int id) {
        EasyHttp.put(this)
                .api(new UpdateProjectApi()
                        .setProjectId(id)
                        .setProjectName(project_name)
                        .setProjectStartDate(in_time + "-01")
                        .setProjectEndDate(end_time + "-01")
                        .setPosition(project_position)
                        .setCompanyName(company_name)
                        .setWorkModeId(workModeId)
                        .setIndustryId(industryId)
                        .setDescription(description)
                        .setSkillIdList(mTagIntList))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        EasyLog.print("====updateProject===");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    public void deleteProject(int id, BaseDialog dialog) {
        EasyHttp.delete(this)
                .api(new DeleteProjectApi()
                        .setProjectId(id))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}
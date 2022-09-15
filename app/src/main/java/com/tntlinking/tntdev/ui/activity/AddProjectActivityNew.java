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
import com.tntlinking.tntdev.manager.ActivityManager;
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
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_FIRST_RESUME;
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_RESUME;


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

    private String projectName = "";
    private String in_time = "";
    private String end_time = "";
    private String project_position = "";
    private String work_mode = "";
    private String companyName = "";
    private String industryName = "";
    private String project_skill = "";
    private String description = "";
    private int mId = 0;
    private DeveloperInfoBean singleton = DeveloperInfoBean.getSingleton();

    //初始填充数据，判断是否对提交自己进行更改 ，没有更改不走接口
    private String projectName1 = "";
    private String in_time1 = "";
    private String end_time1 = "";
    private String companyName1 = "";
    private String project_position1 = "";
    private int industryId1;//行业id
    private String project_skill1 = "";
    private String description1 = "";

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
    private DeveloperInfoBean mBean;

    @Override
    protected void initData() {
        mWorkStatusList = getDictionaryList("9");//职业状态
        mCompanyList = getDictionaryList("1");//所属行业

        position = getInt("position", 0);
        mBean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (mBean != null) {
            DeveloperInfoBean.DeveloperProject developerProject = mBean.getProjectDtoList().get(position);
            if (mBean.getProjectDtoList().size() != 0) {
//                if (TextUtils.isEmpty(developerProject.getProjectName())) {//判断是否有项目名字，没有就不显示
                if (TextUtils.isEmpty(developerProject.getProjectName())) {//判断是否有项目名字，没有就不显示
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

                    sYearMonth = Utils.dateToStamp(developerProject.getProjectStartDate());
                    eYearMonth = Utils.dateToStamp(developerProject.getProjectEndDate());

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

                    projectName = developerProject.getProjectName();
                    in_time = developerProject.getProjectStartDate();
                    end_time = developerProject.getProjectEndDate();
                    project_position = developerProject.getPosition();
                    work_mode = developerProject.getWorkModeName();
                    workModeId = developerProject.getWorkMode();
                    companyName = developerProject.getCompanyName();
                    industryName = developerProject.getIndustryName();
                    industryId = developerProject.getIndustryId();
                    description = developerProject.getDescription();
                    mId = developerProject.getId();

                    projectName1 = developerProject.getProjectName();
                    in_time1 = developerProject.getProjectStartDate();
                    end_time1 = developerProject.getProjectEndDate();
                    project_position1 = developerProject.getPosition();
                    companyName1 = developerProject.getCompanyName();
                    industryId1 = developerProject.getIndustryId();
                    description1 = developerProject.getDescription();
                    project_skill1 = info_project_skill.getLeftText().toString();
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
//        super.onLeftClick(view);
//        if (getBoolean(IS_FIRST_RESUME)) {
//            Intent intent = new Intent(this, EnterDeveloperActivity.class);
//            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
//            startActivity(intent);
//            ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
//        }
        backToDialog();
    }

    @Override
    public void onBackPressed() {
        backToDialog();
    }


    public void backToDialog() {
        if (getBoolean(IS_FIRST_RESUME)) {
            new BaseDialog.Builder<>(this)
                    .setContentView(R.layout.check_order_status_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "简历解析")
                    .setText(R.id.tv_content, "是否返回到简历解析页面")
                    .setText(R.id.btn_dialog_custom_cancel, "否")
                    .setText(R.id.btn_dialog_custom_ok, "是")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        Intent intent = new Intent(this, EnterDeveloperActivity.class);
                        intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
                        startActivity(intent);
                        ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
                    }).show();
        } else {
            finish();
        }
    }

    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();
    private List<Integer> mTagIntList = new ArrayList<>();

    private long sYearMonth;// 开始时间年，开始时间月
    private long eYearMonth;// 结束时间年，结束时间月

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

                        sYearMonth = Utils.dateToStamp(mInTime);
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

                        eYearMonth = Utils.dateToStamp(mEndTime);
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
                                industryName = bean.getName() + "-" + childrenBean.getName();

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
                    projectName = et_project_name.getText().toString();
                    project_position = et_project_position.getText().toString();
                    companyName = et_project_company_name.getText().toString();
                    description = et_project_description.getText().toString();
                    project_skill = info_project_skill.getLeftText().toString();

                    if (TextUtils.isEmpty(projectName)) {
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
                    if (TextUtils.isEmpty(companyName)) {
                        toast("没有输入所属公司");
                        return;
                    }

                    if (TextUtils.isEmpty(industryName)) {
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
//                    addProject(true);
                    if (getInt("status") == 3) {
                        new BaseDialog.Builder<>(this)
                                .setContentView(R.layout.write_daily_delete_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "修改简历需要重新提交审核")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    addProject(true);
                                })
                                .show();
                    } else {
                        addProject(true);
                    }
                } else {

                    if (mBean.getStatus() == 3) {
                        new BaseDialog.Builder<>(this)
                                .setContentView(R.layout.write_daily_delete_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "修改简历需要重新提交审核")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    deleteProject(mId, dialog);
                                })
                                .show();
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
                }
                break;
            case R.id.btn_commit:
                projectName = et_project_name.getText().toString();
                project_position = et_project_position.getText().toString();
                companyName = et_project_company_name.getText().toString();
                description = et_project_description.getText().toString();
                project_skill = info_project_skill.getLeftText().toString();

                if (TextUtils.isEmpty(projectName)) {
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
                if (TextUtils.isEmpty(companyName)) {
                    toast("没有输入所属公司");
                    return;
                }

                if (TextUtils.isEmpty(industryName)) {
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


//                if (btn_commit.getText().equals("完成")) {
//                    Intent intent = new Intent(this, EnterDeveloperActivity.class);
//                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
//                    startActivity(intent);
//                    ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);

//                } else {
//                    if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
//                        addProject(false);
//                    } else {
//                        updateProject(mId);
//                    }
//                }
                if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
//                    addProject(false);
                    if (getInt("status") == 3) {
                        new BaseDialog.Builder<>(this)
                                .setContentView(R.layout.write_daily_delete_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "修改简历需要重新提交审核")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    addProject(true);
                                })
                                .show();
                    } else {
                        addProject(true);
                    }
                } else {
//                    updateProject(mId);
                    if (projectName1.equals(projectName1) && in_time.equals(in_time1) && end_time.equals(end_time1)
                            && companyName.equals(companyName1) && project_position.equals(project_position1) && industryId == industryId1
                            && project_skill.equals(project_skill1) && description.equals(description1)) {


                        toast("暂无修改");
                        return;
                    }
                    if (mBean.getStatus() == 3) {
                        new BaseDialog.Builder<>(this)
                                .setContentView(R.layout.write_daily_delete_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "修改简历需要重新提交审核")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    updateProject(mId);
                                })
                                .show();

                    } else {
                        updateProject(mId);
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
                .api(new UpdateProjectApi()
                        .setId(null)
                        .setProjectName(projectName)
                        .setProjectStartDate(in_time + "-01")
                        .setProjectEndDate(end_time + "-01")
                        .setPosition(project_position)
                        .setCompanyName(companyName)
                        .setWorkMode(workModeId)
                        .setIndustryId(industryId)
                        .setDescription(description)
                        .setSkillIdList(mTagIntList))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperProject>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperProject> data) {
                        if (getBoolean(IS_RESUME)) {

//                            DeveloperInfoBean.DeveloperProject developerProject = mBean.getProjectDtoList().get(position);
//                            developerProject.setId(data.getData().getId());
//                            developerProject.setProjectName(project_name);
//                            developerProject.setIndustryName(industry);
//                            developerProject.setProjectStartDate(in_time);
//                            developerProject.setProjectEndDate(end_time);
//                            developerProject.setPosition(project_position);
//                            developerProject.setCompanyName(company_name);
//                            developerProject.setDescription(description);
//                            developerProject.setProjectSkillList(mSelectList);
//                            checkDeveloper(mBean);

                            DeveloperInfoBean.DeveloperProject developerProject = singleton.getProjectDtoList().get(position);
                            developerProject.setId(data.getData().getId());
                            developerProject.setProjectName(projectName);
                            developerProject.setIndustryName(industryName);
                            developerProject.setIndustryId(industryId);
                            developerProject.setProjectStartDate(in_time);
                            developerProject.setProjectEndDate(end_time);
                            developerProject.setPosition(project_position);
                            developerProject.setCompanyName(companyName);
                            developerProject.setDescription(description);
                            developerProject.setProjectSkillList(mSelectList);
                            checkDeveloper(singleton);


                        } else if (isBack) {
                            setResult(RESULT_OK);
                            finish();

                            toast(R.string.sava_success);
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

                            projectName = "";
                            in_time = "";
                            end_time = "";
                            project_position = "";
                            work_mode = "";
                            workModeId = 0;
                            companyName = "";
                            industryName = "";
                            industryId = 0;
                            description = "";
                            mId = 0;

                            toast(R.string.sava_success);
                        }

//                        toast("保存成功");

                    }
                });
    }

    public void updateProject(int id) {
        EasyHttp.post(this)
                .api(new UpdateProjectApi()
                        .setId(id + "")
                        .setProjectName(projectName)
                        .setProjectStartDate(in_time + "-01")
                        .setProjectEndDate(end_time + "-01")
                        .setPosition(project_position)
                        .setCompanyName(companyName)
                        .setWorkMode(workModeId)
                        .setIndustryId(industryId)
                        .setDescription(description)
                        .setSkillIdList(mTagIntList))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperProject>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperProject> data) {
                        if (!getBoolean(IS_RESUME)) {
                            setResult(RESULT_OK);
                            finish();
                        } else {

//                            DeveloperInfoBean.DeveloperProject developerProject = mBean.getProjectDtoList().get(position);
//                            developerProject.setProjectName(project_name);
//                            developerProject.setIndustryName(industryName);
//                            developerProject.setProjectStartDate(in_time);
//                            developerProject.setProjectEndDate(end_time);
//                            developerProject.setPosition(project_position);
//                            developerProject.setCompanyName(company_name);
//                            developerProject.setDescription(description);
//                            developerProject.setProjectSkillList(mSelectList);
//                            checkDeveloper(mBean);

                            DeveloperInfoBean.DeveloperProject developerProject = singleton.getProjectDtoList().get(position);
                            developerProject.setId(data.getData().getId());
                            developerProject.setProjectName(projectName);
                            developerProject.setIndustryName(industryName);
                            developerProject.setIndustryId(industryId);
                            developerProject.setProjectStartDate(in_time);
                            developerProject.setProjectEndDate(end_time);
                            developerProject.setPosition(project_position);
                            developerProject.setCompanyName(companyName);
                            developerProject.setDescription(description);
                            developerProject.setProjectSkillList(mSelectList);
                            checkDeveloper(singleton);


                        }
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


    public void checkDeveloper(DeveloperInfoBean bean) {
        if (!isJumpProject(bean)) {
            setNextData(bean);
        } else {
//            btn_commit.setText("完成");

        }

    }


    // 项目资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpProject(DeveloperInfoBean bean) {
        if (btn_commit.getText().equals("完成")) {
            Intent intent = new Intent(AddProjectActivityNew.this, EnterDeveloperActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
            startActivity(intent);
            ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
        }
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
        boolean mTag = true;
        if (projectDtoList.size() == 0) {
            return true;
        } else {// +1 跳到下一个教育经历集合 因为当前position 已经补充完整，跳该position下一个位置
            if (position + 1 >= projectDtoList.size()) {
                return true;
            }
            for (int i = position + 1; i < projectDtoList.size(); i++) {
                if (TextUtils.isEmpty(projectDtoList.get(i).getProjectName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectStartDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getProjectEndDate()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getPosition()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(projectDtoList.get(i).getDescription()) &&
                        projectDtoList.get(i).getProjectSkillList().size() == 0) {

                    position = i;
                    mTag = true;
                    break;
                } else {
                    position = i;
                    if (position + 1 == projectDtoList.size()) {
                        btn_commit.setText("完成");
                    } else {
                        btn_commit.setText("下一步");
                    }
                    mTag = false;
                    break;
                }
            }
            return mTag;
        }

    }


    public void setNextData(DeveloperInfoBean bean) {
        if (bean != null) {
            DeveloperInfoBean.DeveloperProject developerProject = bean.getProjectDtoList().get(position);

            if (bean.getProjectDtoList().size() != 0) {
                tv_title.setText("编辑项目经历");
//                btn_commit.setText("下一步");

                btn_delete.setVisibility(View.GONE);

                et_project_name.setText(TextUtils.isEmpty(developerProject.getProjectName()) ? "" : developerProject.getProjectName());
                info_project_in_time.setText(TextUtils.isEmpty(developerProject.getProjectStartDate()) ? "选择开始时间" : developerProject.getProjectStartDate());
                info_project_end_time.setText(TextUtils.isEmpty(developerProject.getProjectEndDate()) ? "选择结束时间" : developerProject.getProjectEndDate());
                et_project_position.setText(TextUtils.isEmpty(developerProject.getPosition()) ? "" : developerProject.getPosition());
                et_project_company_name.setText(TextUtils.isEmpty(developerProject.getCompanyName()) ? "" : developerProject.getCompanyName());
                info_project_industry.setLeftText(TextUtils.isEmpty(developerProject.getIndustryName()) ? "所在行业" : developerProject.getIndustryName());
                et_project_description.setText(TextUtils.isEmpty(developerProject.getDescription()) ? "" : developerProject.getDescription());

                sYearMonth = Utils.dateToStamp(developerProject.getProjectStartDate());
                eYearMonth = Utils.dateToStamp(developerProject.getProjectEndDate());

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

                projectName = developerProject.getProjectName();
                in_time = developerProject.getProjectStartDate();
                end_time = developerProject.getProjectEndDate();
                project_position = developerProject.getPosition();
                work_mode = developerProject.getWorkModeName();
                workModeId = developerProject.getWorkMode();
                companyName = developerProject.getCompanyName();
                industryName = developerProject.getIndustryName();
                industryId = developerProject.getIndustryId();
                description = developerProject.getDescription();
                mId = developerProject.getId();
            }

        }
    }
}
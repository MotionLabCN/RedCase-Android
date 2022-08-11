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
import com.tntlinking.tntdev.http.api.AddWorkApi;
import com.tntlinking.tntdev.http.api.DeleteWorkApi;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.api.UpdateWorkApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.IndustrySelectDialog;

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
public final class AddWorkActivity extends AppActivity {

    private AppCompatTextView tv_title;
    private TextView info_work_in_time;//
    private TextView info_work_end_time;//
    private ClearEditText et_work_position;//职位角色
    private ClearEditText et_work_company_name;//公司名字
    private SettingBar info_work_industry; //所在行业
    private AppCompatButton btn_delete;
    private AppCompatButton btn_commit;

    private int industryId;//行业id
    private String in_time = "";
    private String end_time = "";
    private String project_position = "";
    private String companyName = "";
    private String industryName = "";

    private int mId = 0;

    private DeveloperInfoBean singleton = DeveloperInfoBean.getSingleton();
    @Override
    protected int getLayoutId() {
        return R.layout.add_work_activity;
    }


    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_title);
        et_work_company_name = findViewById(R.id.et_work_company_name);
        info_work_in_time = findViewById(R.id.info_work_in_time);
        info_work_end_time = findViewById(R.id.info_work_end_time);
        et_work_position = findViewById(R.id.et_work_position);
        info_work_industry = findViewById(R.id.info_work_industry);
        btn_delete = findViewById(R.id.btn_delete);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(info_work_in_time, info_work_end_time,
                info_work_industry, btn_delete, btn_commit);

    }


    private List<GetDictionaryApi.DictionaryBean> mCompanyList;

    private int position = 0;
    private DeveloperInfoBean mBean;

    @Override
    protected void initData() {

        mCompanyList = getDictionaryList("1");//所属行业


        position = getInt("position", 0);
        mBean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (mBean != null) {
            DeveloperInfoBean.DeveloperWork developerWork = mBean.getWorkExperienceDtoList().get(position);
            if (mBean.getWorkExperienceDtoList().size() != 0) {
                if (TextUtils.isEmpty(developerWork.getCompanyName())) {
//                    btn_delete.setVisibility(View.GONE);
                    btn_delete.setText("保存");
                    btn_commit.setText("保存并添加下一条");
                    tv_title.setText("添加工作经历");
                } else {
//                    btn_delete.setVisibility(View.VISIBLE);
                    tv_title.setText("编辑工作经历");
                    btn_delete.setText("删除");
                    btn_commit.setText("保存");

//                    et_work_company_name.setText(developerWork.getCompanyName());
//                    info_work_industry.setLeftText(developerWork.getIndustryName());
//                    et_work_position.setText(developerWork.getPositionName());
//                    info_work_in_time.setText(developerWork.getWorkStartTime());
//                    info_work_end_time.setText(developerWork.getWorkEndTime());

                    et_work_company_name.setText(TextUtils.isEmpty(developerWork.getCompanyName()) ? "" : developerWork.getCompanyName());
                    info_work_industry.setLeftText(TextUtils.isEmpty(developerWork.getIndustryName()) ? "所在行业" : developerWork.getIndustryName());
                    et_work_position.setText(TextUtils.isEmpty(developerWork.getPositionName()) ? "" : developerWork.getPositionName());
                    info_work_in_time.setText(TextUtils.isEmpty(developerWork.getWorkStartTime()) ? "选择开始时间" : developerWork.getWorkStartTime());
                    info_work_end_time.setText(TextUtils.isEmpty(developerWork.getWorkEndTime()) ? "选择结束时间" : developerWork.getWorkEndTime());

                    sYearMonth = Utils.dateToStamp(developerWork.getWorkStartTime());
                    eYearMonth = Utils.dateToStamp(developerWork.getWorkEndTime());


                    companyName = developerWork.getCompanyName();
                    project_position = developerWork.getPositionName();
                    in_time = developerWork.getWorkStartTime();
                    end_time = developerWork.getWorkEndTime();
                    industryName = developerWork.getIndustryName();
                    industryId = developerWork.getIndustryId();
                    mId = developerWork.getId();
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

    private long sYearMonth;
    private long eYearMonth;

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.info_work_in_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month);

                        info_work_in_time.setText(mInTime);
                        in_time = mInTime;

                        sYearMonth = Utils.dateToStamp(mInTime);

                    }

                }).show();
                break;
            case R.id.info_work_end_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mEndTime = year + "-" + Utils.formatDate(month);

                        info_work_end_time.setText(mEndTime);
                        end_time = mEndTime;
                        eYearMonth = Utils.dateToStamp(mEndTime);

                    }

                }).show();
                break;

            case R.id.info_work_industry:

                new IndustrySelectDialog.Builder(this).setTitle("选择所在行业")
                        .setListener(new IndustrySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, GetDictionaryApi.DictionaryBean bean, GetDictionaryApi.ChildrenBean childrenBean) {

                                info_work_industry.setLeftText(bean.getName() + "-" + childrenBean.getName());
                                industryId = childrenBean.getId();
                                industryName = bean.getName() + "-" + childrenBean.getName();

                                EasyLog.print("===industryId==" + industryId + "======" + childrenBean.getName());
                            }
                        }).show();
                break;

            case R.id.btn_delete:

                if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                    project_position = et_work_position.getText().toString();
                    companyName = et_work_company_name.getText().toString();

                    if (TextUtils.isEmpty(in_time)) {
                        toast("没有选择开始时间");
                        return;
                    }
                    if (TextUtils.isEmpty(end_time)) {
                        toast("没有选择结束时间");
                        return;
                    }
                    if (sYearMonth > eYearMonth) {
                        toast("工作开始时间不能大于结束时间");
                        info_work_in_time.setText("选择开始时间");
                        info_work_end_time.setText("选择结束时间");
                        in_time = "";
                        end_time = "";
                        return;
                    }
                    if (TextUtils.isEmpty(project_position)) {
                        toast("没有输入担任角色");
                        return;
                    }

                    if (TextUtils.isEmpty(companyName)) {
                        toast("没有输入所属公司");
                        return;
                    }

                    if (TextUtils.isEmpty(industryName)) {
                        toast("没选择所在行业");
                        return;
                    }

                    addWork(true);
                } else {

                    new BaseDialog.Builder<>(this)
                            .setContentView(R.layout.write_daily_delete_dialog)
                            .setAnimStyle(BaseDialog.ANIM_SCALE)
                            .setText(R.id.tv_title, "是否确认删除？")
                            .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                            .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                                deleteWork(mId, dialog);
                            })
                            .show();
                }
                break;
            case R.id.btn_commit:
                project_position = et_work_position.getText().toString();
                companyName = et_work_company_name.getText().toString();


                if (TextUtils.isEmpty(in_time)) {
                    toast("没有选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(end_time)) {
                    toast("没有选择结束时间");
                    return;
                }
                if (sYearMonth > eYearMonth) {
                    toast("工作开始时间不能大于结束时间");
                    info_work_in_time.setText("选择开始时间");
                    info_work_end_time.setText("选择结束时间");
                    in_time = "";
                    end_time = "";
                    return;
                }
                if (TextUtils.isEmpty(project_position)) {
                    toast("没有输入担任角色");
                    return;
                }

                if (TextUtils.isEmpty(companyName)) {
                    toast("没有输入所属公司");
                    return;
                }

                if (TextUtils.isEmpty(industryName)) {
                    toast("没选择所在行业");
                    return;
                }

                if (btn_commit.getText().equals("完成")) {
                    Intent intent = new Intent(this, EnterDeveloperActivity.class);
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
                    startActivity(intent);
                    ActivityManager.getInstance().finishAllActivities(EnterDeveloperActivity.class, MainActivity.class);
                } else {
                    if (mId == 0) { // 0 添加教育  不等于0 是编辑教育
                        addWork(false);
                    } else {
                        updateWork(mId);
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

                }

            }
        }
    }

    /**
     * 添加工作经历
     *
     * @param isBack
     */
    public void addWork(boolean isBack) {
        EasyHttp.post(this)
                .api(new UpdateWorkApi()
                        .setId(null)
                        .setCompanyName(companyName)
                        .setIndustryId(industryId)
                        .setPositionName(project_position)
                        .setWorkStartTime(in_time + "-01")
                        .setWorkEndTime(end_time + "-01"))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperWork>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperWork> data) {
                        if (getBoolean(IS_RESUME)) {
//                            DeveloperInfoBean.DeveloperWork developerWork = mBean.getWorkExperienceDtoList().get(position);
//                            developerWork.setId(data.getData().getId());
//                            developerWork.setCompanyName(company_name);
//                            developerWork.setIndustryName(industry);
//                            developerWork.setPositionName(project_position);
//                            developerWork.setWorkStartTime(in_time);
//                            developerWork.setWorkEndTime(end_time);

//                            checkDeveloper(mBean);

                            DeveloperInfoBean.DeveloperWork developerWork = singleton.getWorkExperienceDtoList().get(position);
                            developerWork.setId(data.getData().getId());
                            developerWork.setCompanyName(companyName);
                            developerWork.setIndustryId(industryId);
                            developerWork.setIndustryName(industryName);
                            developerWork.setPositionName(project_position);
                            developerWork.setWorkStartTime(in_time);
                            developerWork.setWorkEndTime(end_time);
                            checkDeveloper(singleton);
                        } else if (isBack) {
                            setResult(RESULT_OK);
                            finish();

                            toast(R.string.sava_success);
                        } else {
                            et_work_company_name.setText("");
                            info_work_industry.setLeftText("所在行业");
                            et_work_position.setText("");
                            info_work_in_time.setText("选择开始时间");
                            info_work_end_time.setText("选择结束时间");

                            companyName = "";
                            project_position = "";
                            in_time = "";
                            end_time = "";
                            industryName = "";
                            industryId = 0;
                            mId = 0;

                            toast("保存成功");
                        }


                    }
                });
    }

    /**
     * 更新工作经历
     *
     * @param id
     */
    public void updateWork(int id) {
        EasyHttp.post(this)
                .api(new UpdateWorkApi()
                        .setId(id + "")
                        .setCompanyName(companyName)
                        .setIndustryId(industryId)
                        .setPositionName(project_position)
                        .setWorkStartTime(in_time + "-01")
                        .setWorkEndTime(end_time + "-01"))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperWork>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperWork> data) {
                        if (!getBoolean(IS_RESUME)) {
                            setResult(RESULT_OK);
                            finish();
                        } else {

//                            DeveloperInfoBean.DeveloperWork developerWork = mBean.getWorkExperienceDtoList().get(position);
//                            developerWork.setCompanyName(company_name);
//                            developerWork.setIndustryName(industry);
//                            developerWork.setPositionName(project_position);
//                            developerWork.setWorkStartTime(in_time);
//                            developerWork.setWorkEndTime(end_time);
//
//                            checkDeveloper(mBean);

                            DeveloperInfoBean.DeveloperWork developerWork = singleton.getWorkExperienceDtoList().get(position);
                            developerWork.setId(data.getData().getId());
                            developerWork.setCompanyName(companyName);
                            developerWork.setIndustryId(industryId);
                            developerWork.setIndustryName(industryName);
                            developerWork.setPositionName(project_position);
                            developerWork.setWorkStartTime(in_time);
                            developerWork.setWorkEndTime(end_time);
                            checkDeveloper(singleton);
                        }
                    }
                });
    }

    public void deleteWork(int id, BaseDialog dialog) {
        EasyHttp.delete(this)
                .api(new DeleteWorkApi()
                        .setWorkExperienceId(id))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }


    private int position3 = 0;

    public void checkDeveloper(DeveloperInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(IS_RESUME, true);
        if (!isJumpWork(bean)) {
            setNextData(bean);
            return;
        }
        if (!isJumpProject(bean)) {
            intent.setClass(this, AddProjectActivityNew.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra("position", position3);
            startActivity(intent);
        } else {
            btn_commit.setText("完成");
        }

    }


    // 工作资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpWork(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        boolean mTag = true;
        if (workExperienceDtoList.size() == 0) {
            return true;
        } else {// +1 跳到下一个教育经历集合 因为当前position 已经补充完整，跳该position下一个位置
            if (position + 1 >= workExperienceDtoList.size()) {
                return true;
            }
            for (int i = position + 1; i < workExperienceDtoList.size(); i++) {
                if (TextUtils.isEmpty(workExperienceDtoList.get(i).getCompanyName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getIndustryName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getPositionName()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkStartTime()) &&
                        TextUtils.isEmpty(workExperienceDtoList.get(i).getWorkEndTime())) {
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
            DeveloperInfoBean.DeveloperWork developerWork = bean.getWorkExperienceDtoList().get(position);
            if (bean.getWorkExperienceDtoList().size() != 0) {

                tv_title.setText("编辑工作经历");
                btn_commit.setText("下一步");
                btn_delete.setVisibility(View.GONE);


                et_work_company_name.setText(TextUtils.isEmpty(developerWork.getCompanyName()) ? "" : developerWork.getCompanyName());
                info_work_industry.setLeftText(TextUtils.isEmpty(developerWork.getIndustryName()) ? "所在行业" : developerWork.getIndustryName());
                et_work_position.setText(TextUtils.isEmpty(developerWork.getPositionName()) ? "" : developerWork.getPositionName());
                info_work_in_time.setText(TextUtils.isEmpty(developerWork.getWorkStartTime()) ? "选择开始时间" : developerWork.getWorkStartTime());
                info_work_end_time.setText(TextUtils.isEmpty(developerWork.getWorkEndTime()) ? "选择结束时间" : developerWork.getWorkEndTime());

                sYearMonth = Utils.dateToStamp(developerWork.getWorkStartTime());
                eYearMonth = Utils.dateToStamp(developerWork.getWorkEndTime());


                companyName = developerWork.getCompanyName();
                project_position = developerWork.getPositionName();
                in_time = developerWork.getWorkStartTime();
                end_time = developerWork.getWorkEndTime();
                industryName = developerWork.getIndustryName();
                industryId = developerWork.getIndustryId();
                mId = developerWork.getId();
            }


        }


    }

}
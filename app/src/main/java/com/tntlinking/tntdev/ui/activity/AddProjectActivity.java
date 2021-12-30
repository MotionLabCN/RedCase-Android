package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.ui.dialog.IndustrySelectDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;


/**
 * 添加项目经历
 */
public final class AddProjectActivity extends AppActivity {

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
    private int workMode;//职业状态id
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

    @Override
    protected int getLayoutId() {
        return R.layout.add_project_activity;
    }


    @Override
    protected void initView() {
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
        ExperienceBean bean = getSerializable(INTENT_KEY_PROJECT);
        if (bean != null) {
            if (TextUtils.isEmpty(bean.getProjectName())) {
                btn_delete.setVisibility(View.GONE);
            } else {
                btn_delete.setVisibility(View.VISIBLE);
            }
            et_project_name.setText(bean.getProjectName());
            info_project_in_time.setText(bean.getProjectStartDate());
            info_project_end_time.setText(bean.getProjectEndDate());
            et_project_position.setText(bean.getPosition());
            info_project_work_mode.setLeftText(bean.getmWorkMode());
            et_project_company_name.setText(bean.getCompanyName());
            info_project_industry.setLeftText(bean.getIndustry());
            et_project_description.setText(bean.getDescription());

            if (bean.getSkillList() != null && bean.getSkillList().size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (GetTagListApi.Bean.ChildrenBean i : bean.getSkillList()) {
                    sb.append(i.getSkillName());
                    sb.append(",");
                }
                info_project_skill.setLeftText(sb.toString());
            }
            project_name = bean.getProjectName();
            in_time = bean.getProjectStartDate();
            end_time = bean.getProjectEndDate();
            project_position = bean.getPosition();
            work_mode = bean.getmWorkMode();
            company_name = bean.getCompanyName();
            industry = bean.getIndustry();
            description = bean.getDescription();
        }
    }

    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();
    private List<Integer> mTagIntList = new ArrayList<>();

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_project_work_mode:
                if (mWorkStatusList.size()==0){
                    return;
                }
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择职业状态")
                        .setList(mWorkStatusList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {

                        info_project_work_mode.setLeftText(mWorkStatusList.get(type).getName());
                        workMode = mWorkStatusList.get(type).getId();
                        work_mode = mWorkStatusList.get(type).getName();
                    }
                }).show();
                break;
            case R.id.info_project_in_time:
                new DateSelectDialog.Builder(this).setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month);

                        info_project_in_time.setText(mInTime);
                        in_time = mInTime;
                        Long timeLong = TimeUtil.getTimeLong("yyyy-MM", mInTime);
                        EasyLog.print("===timeLong=="+timeLong);
                    }

                }).show();
                break;
            case R.id.info_project_end_time:
                new DateSelectDialog.Builder(this).setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mEndTime = year + "-" + Utils.formatDate(month);

                        info_project_end_time.setText(mEndTime);
                        end_time = mEndTime;
                    }

                }).show();
                break;

            case R.id.info_project_industry:
//                new DictionarySelectDialog.Builder(this)
//                        .setTitle("选择所在行业")
//                        .setList(mCompanyList).setListener(new DictionarySelectDialog.OnListener() {
//                    @Override
//                    public void onSelected(BaseDialog dialog, int type) {
//
//                        info_project_industry.setLeftText(mCompanyList.get(type).getName());
//
//                        industryId = mCompanyList.get(type).getId();
//                        industry = mCompanyList.get(type).getName();
//                    }
//                }).show();

                new IndustrySelectDialog.Builder(this).setTitle("选择所在行业")
                        .setListener(new IndustrySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, GetDictionaryApi.DictionaryBean bean, GetDictionaryApi.ChildrenBean childrenBean) {

                                info_project_industry.setLeftText(bean.getName() + "-" + childrenBean.getName());
                                industryId = childrenBean.getId();
                                industry = bean.getName() + "-" + childrenBean.getName();
                            }
                        }).show();
                break;
            case R.id.info_project_skill:
                Intent intents = new Intent(AddProjectActivity.this, AddProjectTagActivity.class);
                ExperienceBean beans = getSerializable(INTENT_KEY_PROJECT);
                if (beans != null) {
                    intents.putExtra("list", (Serializable) beans.getSkillList());
                } else if (mSelectList.size() != 0) {
                    intents.putExtra("list", (Serializable) mSelectList);
                }
                getActivity().startActivityForResult(intents, 1001);
                break;
            case R.id.btn_delete:
                setResult(RESULT_OK, new Intent().putExtra("position", position));
                finish();
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
                if (TextUtils.isEmpty(project_position)) {
                    toast("没有输入担任角色");
                    return;
                }
                if (TextUtils.isEmpty(work_mode)) {
                    toast("没选职业状态");
                    return;
                }
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
                Intent intent = new Intent();
                ExperienceBean bean = new ExperienceBean();
                bean.setProjectName(et_project_name.getText().toString());
                bean.setProjectStartDate(info_project_in_time.getText().toString());
                bean.setProjectEndDate(info_project_end_time.getText().toString());
                bean.setPosition(et_project_position.getText().toString());
                bean.setmWorkMode(info_project_work_mode.getLeftText().toString());
                bean.setWorkMode(workMode);
                bean.setCompanyName(et_project_company_name.getText().toString());
                bean.setIndustry(info_project_industry.getLeftText().toString());
                bean.setIndustryId(industryId);
                bean.setSkillList(mSelectList);
                bean.setSkillIdList(mTagIntList);

                bean.setDescription(et_project_description.getText().toString());
//                setResult(RESULT_OK, new Intent().putExtra(INTENT_KEY_PROJECT, bean));
//                finish();
                intent.putExtra(INTENT_KEY_PROJECT, bean);
                if (position == 0) {
                    setResult(RESULT_OK, intent);
                } else {
                    intent.putExtra("position", position);
                    setResult(10006, intent);
                }
                finish();
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

}
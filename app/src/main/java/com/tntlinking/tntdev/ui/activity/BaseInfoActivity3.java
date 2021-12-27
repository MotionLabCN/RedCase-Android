package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.view.View;
import com.hjq.base.BaseAdapter;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.SendDeveloperApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.ui.adapter.AddExperienceAdapter;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 用户信息填写页面3
 */
public final class BaseInfoActivity3 extends AppActivity {
    private AppCompatButton mBack;
    private AppCompatButton mCommit;

    private String mSchoolName = "";// 学校名字
    private String mInTime = "";// 入校时间
    private String mEndTime = "";// 毕业时间
    private String mEducations = "";// 学历
    private String mSpecialty = "";// 专业
    private String mTrainingMethod = "";// 教育方式
    private String mCompanyName = "";// 公司名字
    private static final String INTENT_KEY_EDUCATION = "key_education";
    private static final String INTENT_KEY_PROJECT = "key_project";
    private AddExperienceAdapter mAdapter;
    private RecyclerView mRecyclerview;


    @Override
    protected int getLayoutId() {
        return R.layout.baseinfo_activity_3;
    }

    @Override
    protected void initView() {

        mBack = findViewById(R.id.btn_back);
        mCommit = findViewById(R.id.btn_commit);
        mRecyclerview = findViewById(R.id.rv_home_tab);

        setOnClickListener(mBack, mCommit);

        mAdapter = new AddExperienceAdapter(this);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                if (mList.get(position).getType() == 1) {
                    Intent intent = new Intent(BaseInfoActivity3.this, AddEducationActivity.class);
                    getActivity().startActivityForResult(intent, 10001);

                } else if (mList.get(position).getType() == 3) {
                    Intent intent = new Intent(BaseInfoActivity3.this, AddEducationActivity.class);

                    ExperienceBean bean = mAdapter.getData().get(position);
                    intent.putExtra(INTENT_KEY_EDUCATION, bean);
                    intent.putExtra("position", position);
                    getActivity().startActivityForResult(intent, 10002);

                } else if (mList.get(position).getType() == 2) {
                    Intent intent = new Intent(BaseInfoActivity3.this, AddProjectActivity.class);
                    getActivity().startActivityForResult(intent, 10004);

                } else if (mList.get(position).getType() == 4) {
                    Intent intent = new Intent(BaseInfoActivity3.this, AddProjectActivity.class);

                    ExperienceBean bean = mAdapter.getData().get(position);
                    intent.putExtra(INTENT_KEY_PROJECT, bean);
                    intent.putExtra("position", position);
                    getActivity().startActivityForResult(intent, 10005);

                }
            }
        });
        mRecyclerview.setAdapter(mAdapter);


    }

    private List<ExperienceBean> mList = new ArrayList<>();

    private SendDeveloperBean postBean = SendDeveloperBean.getSingleton();

    @Override
    protected void initData() {
        mList.add(new ExperienceBean(1));
        mList.add(new ExperienceBean(2));

        mAdapter.setData(mList);
//        mAdapter.setData(sortList(mList));

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mBack) {
            finish();

        } else if (view == mCommit) {

            List<ExperienceBean> data = mAdapter.getData();
            List<SendDeveloperApi.DeveloperEducation> educationList = postBean.getDeveloperEducationParamList();
            List<SendDeveloperApi.DeveloperProject> projectList = postBean.getDeveloperProjectParamList();

            for (ExperienceBean bean : data) {
                if (bean.getType() == 3) {
                    SendDeveloperApi.DeveloperEducation education = new SendDeveloperApi.DeveloperEducation();
                    education.setCollegeName(bean.getCollegeName());
                    education.setEducationId(bean.getEducationId());
                    education.setInSchoolStartTime(bean.getInSchoolStartTime() + "-01");
                    education.setInSchoolEndTime(bean.getInSchoolEndTime() + "-01");
                    education.setMajor(bean.getMajor());
                    education.setTrainingMode(bean.getTrainingMode());

                    educationList.add(education);
                } else if (bean.getType() == 4) {
                    SendDeveloperApi.DeveloperProject project = new SendDeveloperApi.DeveloperProject();

                    project.setCompanyName(bean.getCompanyName());
                    project.setDescription(bean.getDescription());
                    project.setIndustryId(bean.getIndustryId());
                    project.setPosition(bean.getPosition());
                    project.setProjectStartDate(bean.getProjectStartDate() + "-01");
                    project.setProjectEndDate(bean.getProjectEndDate() + "-01");
                    project.setProjectName(bean.getProjectName());
                    project.setSkillIdList(bean.getSkillIdList());
                    project.setWorkMode(bean.getWorkMode());

                    projectList.add(project);
                }
            }

            if (educationList.size()==0){
                toast("你还没有添加教育经历");
                return;
            }
            if (projectList.size()==0){
                toast("你还没有添加工作经历");
                return;
            }

            EasyHttp.post(this)
                    .api(new SendDeveloperApi()
                            .setRealName(postBean.getRealName())
                            .setSex(postBean.getSex())
                            .setworkYears(postBean.getWorkYears())
                            .setBirthday(postBean.getBirthday())
                            .setProvinceId(postBean.getProvinceId())
                            .setCityId(postBean.getCityId())
                            .setAreasId(postBean.getAreasId())
                            .setRemoteWorkReason(postBean.getRemoteWorkReason())
                            .setCareerDirectionId(postBean.getCareerDirectionId())
                            .setWorkDayMode(postBean.getWorkDayMode())
                            .setCurSalary(postBean.getCurSalary())
                            .setLowestSalary(postBean.getLowestSalary())
                            .setHighestSalary(postBean.getHighestSalary())
                            .setWorkYearsId(postBean.getWorkYearsId())
                            .setDeveloperEducationParamList(postBean.getDeveloperEducationParamList())
                            .setDeveloperProjectParamList(postBean.getDeveloperProjectParamList())
                    )

                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            startActivity(CheckDeveloperActivity.class);
                            ActivityManager.getInstance().finishAllActivities(CheckDeveloperActivity.class);
                        }
                    });

        }

    }


    public static List<ExperienceBean> sortList(List<ExperienceBean> listInAppxList) {
        Comparator<ExperienceBean> comparator = new Comparator<ExperienceBean>() {
            @Override
            public int compare(ExperienceBean details1, ExperienceBean details2) {
                if (details1.getType() > details2.getType())
                    return 1;
                else {
                    return -1;
                }
            }
        };
        //这里就会自动根据规则进行排序
        Collections.sort(listInAppxList, comparator);
        return listInAppxList;
    }

    /**
     * @param type
     * @param list
     * @return
     */
    public int getPosition(int type, List<ExperienceBean> list) {
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == type || list.get(i).getType() == type + 2) {
                position = i;
            }
        }
        return position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10001) {// 添加教育经历
                ExperienceBean bean = (ExperienceBean) data.getSerializableExtra(INTENT_KEY_EDUCATION);
                bean.setType(3);
                int index = getPosition(1, mAdapter.getData());
                mAdapter.addItem(index + 1, bean);


            } else if (requestCode == 10002) {// 删除教育经历
                int position = data.getIntExtra("position", 0);
                mAdapter.removeItem(position);

            } else if (requestCode == 10004) {// 添加工作经历
                ExperienceBean bean = (ExperienceBean) data.getSerializableExtra(INTENT_KEY_PROJECT);
                bean.setType(4);
                int index = getPosition(2, mAdapter.getData());
                mAdapter.addItem(index + 1, bean);

            } else if (requestCode == 10005) {// 删除工作经历
                int position = data.getIntExtra("position", 0);
                mAdapter.removeItem(position);

            }
        } else if (resultCode == 10003) {// 修改教育经历
            ExperienceBean bean = (ExperienceBean) data.getSerializableExtra(INTENT_KEY_EDUCATION);
            bean.setType(3);

            int position = data.getIntExtra("position", 0);
            mAdapter.removeItem(position);
            mAdapter.addItem(position, bean);
        } else if (resultCode == 10006) {//修改工作经历
            ExperienceBean bean = (ExperienceBean) data.getSerializableExtra(INTENT_KEY_PROJECT);
            bean.setType(4);

            int position = data.getIntExtra("position", 0);
            mAdapter.removeItem(position);
            mAdapter.addItem(position, bean);

        }
    }
}
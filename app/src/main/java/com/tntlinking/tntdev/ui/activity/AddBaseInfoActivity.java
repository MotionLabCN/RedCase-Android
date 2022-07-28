package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
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
import com.tntlinking.tntdev.http.api.UpdateBasicInfoApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.tntlinking.tntdev.ui.dialog.GenderSelectDialog;
import com.tntlinking.tntdev.ui.dialog.ProvinceSelectDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

import static com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity.INTENT_KEY_DEVELOPER_INFO;
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_FIRST_RESUME;
import static com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity.IS_RESUME;


/**
 * 用户信息填写页面1
 */
public final class AddBaseInfoActivity extends AppActivity {
    private ClearEditText mInfoName;
    private SettingBar mInfoGender;
    private SettingBar mInfoBirth;
    private SettingBar mInfoAddress;
    private SettingBar mInfoReason;
    private AppCompatButton btn_commit;


    private String realName;
    private int sex = -1;
    private String birthday = "";
    private int provinceId = 0;
    private int cityId = 0;
    private int areaId = 0;
    private int workReasonId = 0;

    private DeveloperInfoBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.baseinfo_activity;
    }

    @Override
    protected void initView() {
        mInfoName = findViewById(R.id.et_name);
        mInfoGender = findViewById(R.id.info_gender);
        mInfoBirth = findViewById(R.id.info_birth);
        mInfoAddress = findViewById(R.id.info_address);
        mInfoReason = findViewById(R.id.info_reason);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(mInfoGender, mInfoBirth, mInfoAddress, mInfoReason, btn_commit);
    }

    private List<GetDictionaryApi.DictionaryBean> mDictionaryList;

    @Override
    protected void initData() {
        mDictionaryList = getDictionaryList("8");//获取远程办公原因list

        if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {
            GetProvince();
        }
        mBean = getSerializable(INTENT_KEY_DEVELOPER_INFO);
        if (mBean != null) {
            if (!TextUtils.isEmpty(mBean.getRealName())) {
                mInfoName.setText(mBean.getRealName());
                if (mBean.getSex() == 0) {
                    mInfoGender.setLeftText("男");
                } else {
                    mInfoGender.setLeftText("女");
                }
                mInfoBirth.setLeftText(TextUtils.isEmpty(mBean.getBirthday()) ? "出生日期" : mBean.getBirthday());
                if (mBean.getProvinceName() != null) {
                    mInfoAddress.setLeftText(mBean.getProvinceName() + "-" + mBean.getCityName() + "-" + mBean.getAreasName());
                }
                mInfoReason.setLeftText(TextUtils.isEmpty(mBean.getRemoteWorkReasonStr()) ? "远程办公原因" : mBean.getRemoteWorkReasonStr());

                realName = mBean.getRealName();
                sex = mBean.getSex();
                birthday = mBean.getBirthday();
                provinceId = mBean.getProvinceId();
                cityId = mBean.getCityId();
                areaId = mBean.getAreasId();
                workReasonId = mBean.getRemoteWorkReason();
            }

            if (getBoolean(IS_RESUME)) {
                btn_commit.setText("下一步");
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
            ActivityManager.getInstance().finishAllActivities();
        }
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_gender:
                new GenderSelectDialog.Builder(this)
                        .setTitle("选择性别")
                        .setList("男", "女").setListener(new GenderSelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                if (type == 0) {
                                    mInfoGender.setLeftText("男");
                                } else {
                                    mInfoGender.setLeftText("女");
                                }
                                sex = type;
                            }
                        }).show();


                break;
            case R.id.info_birth:
                new DateSelectDialog.Builder(this).setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {

                        String mBirthday = year + "-" + Utils.formatDate(month) + "-" + Utils.formatDate(day);
                        mInfoBirth.setLeftText(mBirthday);
                        birthday = mBirthday;
                    }

                }).show();
                break;
            case R.id.info_address:
                if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {

                    return;
                }

                new ProvinceSelectDialog.Builder(this).setTitle("选择所在地").setListener(new ProvinceSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, GetProvinceApi.ProvinceBean province,
                                           GetProvinceApi.AreaBean city, GetProvinceApi.CityBean area) {
                        // 省 市 区
                        mInfoAddress.setLeftText(province.getRegionName() + "-" + city.getRegionName() + "-" + area.getRegionName());

                        provinceId = province.getId();
                        cityId = city.getId();
                        areaId = area.getId();
                    }
                }).show();

                break;
            case R.id.info_reason:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择原因")
                        .setList(mDictionaryList).setListener(new DictionarySelectDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int type) {
                                mInfoReason.setLeftText(mDictionaryList.get(type).getName());
                                workReasonId = mDictionaryList.get(type).getId();
                            }
                        }).show();
                break;

            case R.id.btn_commit:
                String name = mInfoName.getText().toString();
                if (TextUtils.isEmpty(name) && name.length() < 2) {
                    toast("没有输入用户名或者输入长度不够");
                    return;
                }
                if (sex == -1) {
                    toast("没选择性别");
                    return;
                }
                if (TextUtils.isEmpty(birthday)) {
                    toast("没选择出生年龄");
                    return;
                }   //所在地
                if (provinceId == 0 || TextUtils.isEmpty(mInfoAddress.getLeftText()) || mInfoAddress.getLeftText().equals("所在地")) {
                    toast("没选择地区");
                    return;
                }

                if (workReasonId == 0 || TextUtils.isEmpty(mInfoReason.getLeftText()) || mInfoReason.getLeftText().equals("远程办公原因")) {
                    toast("没选择办公原因");
                    return;
                }
                realName = name;

                if (btn_commit.getText().equals("完成")) {
                    Intent intent = new Intent(this, EnterDeveloperActivity.class);
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, mBean);
                    startActivity(intent);
                    ActivityManager.getInstance().finishAllActivities();
                } else {
                    updateBasicInfo();
                }
                break;
        }
    }


    public void updateBasicInfo() {
        EasyHttp.post(this)
                .api(new UpdateBasicInfoApi()
                        .setDeveloperId(89)
                        .setRealName(realName)
                        .setBirthday(birthday)
                        .setProvinceId(provinceId)
                        .setAreasId(areaId)
                        .setCityId(cityId)
                        .setSex(sex)
                        .setRemoteWorkReason(workReasonId))
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        if (!getBoolean(IS_RESUME)) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            checkDeveloper(getSerializable(INTENT_KEY_DEVELOPER_INFO));
                        }

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


    public void GetProvince() {
        EasyHttp.get(this)
                .api(new GetProvinceApi())
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        if (!data.getData().isEmpty()) {

                            SPUtils.getInstance().put("province", GsonUtils.toJson(data.getData()));
                        }
                    }
                });
    }


    private int position1 = 0;
    private int position2 = 0;
    private int position3 = 0;


    public void checkDeveloper(DeveloperInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(IS_RESUME, true);

        if (!isJumpCareer(bean)) {
            intent.setClass(this, AddCareerActivity.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            startActivity(intent);
            return;
        }

        if (!isJumpEducation(bean)) {
            intent.setClass(this, AddEducationActivityNew.class);
            intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
            intent.putExtra("position", position1);
            startActivity(intent);
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


    // 职业资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpCareer(DeveloperInfoBean bean) {
        if (bean.getWorkModeDtoList().size() == 0 &&
                TextUtils.isEmpty(bean.getCareerDto().getCareerDirectionName()) &&
                TextUtils.isEmpty(bean.getCareerDto().getWorkYearsName()) &&
                TextUtils.isEmpty(bean.getCareerDto().getCurSalary())) {

            return true;
        } else {
            return false;
        }
    }

    // 教育资料全部没写就跳过，返回true，其他情况都要展示false
    public boolean isJumpEducation(DeveloperInfoBean bean) {
        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        boolean mTag = true;
        if (educationDtoList.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < educationDtoList.size(); i++) {
                if (TextUtils.isEmpty(educationDtoList.get(i).getCollegeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getEducationName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getTrainingModeName()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getMajor()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolStartTime()) &&
                        TextUtils.isEmpty(educationDtoList.get(i).getInSchoolEndTime())) {
                    position1 = i;

                    mTag = true;
                    break;
                } else {
                    position1 = i;
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

}
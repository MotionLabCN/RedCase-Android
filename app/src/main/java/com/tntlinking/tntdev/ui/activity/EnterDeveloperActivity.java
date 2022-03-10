package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.FileContentResolver;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.SubmitDeveloperApi;
import com.tntlinking.tntdev.http.api.UpdateAvatarApi;
import com.tntlinking.tntdev.http.api.UpdateImageApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.AddEducationAdapter;
import com.tntlinking.tntdev.ui.adapter.AddProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.AddWorkAdapter;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户信息填写页面3
 */
public final class EnterDeveloperActivity extends AppActivity {
    private AppCompatButton mCommit;
    private ListView lv1, lv2, lv3;
    private ScrollView sv;
    private LinearLayout ll_add_base_info, ll_base_info, ll_add_career_info, ll_career_info;
    private TextView tv_edit_name;
    private TextView tv_edit_info;
    private TextView tv_edit_reason;
    private TextView tv_career_info;
    private TextView tv_career_info_work_year;
    private TextView tv_career_info_work_mode;
    private LinearLayout ll_add_education, ll_add_work, ll_add_project;
    private LinearLayout ll_progress;
    private LinearLayout ll_add_photo;
    private FrameLayout fl_add_photo;
    private TextView tv_photo_skills;
    private ImageView iv_photo_avatar;
    private TextView tv_welcome;
    private TextView tv_progress;
    private ImageView iv_progress;
    private ProgressBar progress_bar;

    public static final String INTENT_KEY_DEVELOPER_INFO = "DeveloperInfoBean";


    private AddEducationAdapter addEducationAdapter;
    private AddWorkAdapter addWorkAdapter;
    private AddProjectAdapter addProjectAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.enter_developer_activity;
    }


    @Override
    protected void initView() {


        lv1 = findViewById(R.id.lv_1);
        lv2 = findViewById(R.id.lv_2);
        lv3 = findViewById(R.id.lv_3);
        sv = findViewById(R.id.sv);
        ll_add_base_info = findViewById(R.id.ll_add_base_info);
        ll_base_info = findViewById(R.id.ll_base_info);
        ll_add_career_info = findViewById(R.id.ll_add_career_info);
        ll_career_info = findViewById(R.id.ll_career_info);
        tv_edit_name = findViewById(R.id.tv_edit_name);
        tv_edit_info = findViewById(R.id.tv_edit_info);
        tv_edit_reason = findViewById(R.id.tv_edit_reason);
        tv_career_info = findViewById(R.id.tv_career_info);
        tv_career_info_work_year = findViewById(R.id.tv_career_info_work_year);
        tv_career_info_work_mode = findViewById(R.id.tv_career_info_work_mode);
        ll_add_education = findViewById(R.id.ll_add_education);
        ll_add_work = findViewById(R.id.ll_add_work);
        ll_add_project = findViewById(R.id.ll_add_project);
        ll_progress = findViewById(R.id.ll_progress);
        ll_add_photo = findViewById(R.id.ll_add_photo);
        fl_add_photo = findViewById(R.id.fl_add_photo);
        iv_photo_avatar = findViewById(R.id.iv_photo_avatar);
        tv_photo_skills = findViewById(R.id.tv_photo_skills);
        tv_welcome = findViewById(R.id.tv_welcome);
        tv_progress = findViewById(R.id.tv_progress);
        iv_progress = findViewById(R.id.iv_progress);
        progress_bar = findViewById(R.id.progress_bar);

        mCommit = findViewById(R.id.btn_commit);
        setOnClickListener(mCommit, ll_add_base_info, ll_base_info, ll_add_career_info, ll_career_info,
                ll_add_education, ll_add_work, ll_add_project, ll_add_photo, fl_add_photo, tv_photo_skills);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(EnterDeveloperActivity.this, AddEducationActivityNew.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10006);
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EnterDeveloperActivity.this, AddWorkActivity.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10007);
            }
        });

        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(EnterDeveloperActivity.this, AddProjectActivityNew.class);
                intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                intent.putExtra("position", position);
                getActivity().startActivityForResult(intent, 10008);
            }
        });
    }


    @Override
    protected void initData() {
        int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
        getDeveloperDetail(developId);

    }

    @Override
    protected void onResume() {
        int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
        getDeveloperDetail(developId);

        super.onResume();

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_add_photo:
            case R.id.fl_add_photo:

                ImageSelectActivity.start(this, data -> {
                    // 裁剪头像
                    cropImageFile(new File(data.get(0)));
                });
                break;

            case R.id.tv_photo_skills: // https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/photography.md
//                startActivity(PhotoSkillsActivity.class);
                String service_guide = AppConfig.PHOTO_GUIDE_URL;
                intent.setClass(this, MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "拍照技巧");
                startActivity(intent);
                break;
            case R.id.ll_add_base_info:
            case R.id.ll_base_info:
                intent.setClass(EnterDeveloperActivity.this, AddBaseInfoActivity.class);
                if (bean != null) {
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                }
                getActivity().startActivityForResult(intent, 10001);
                break;
            case R.id.ll_add_career_info:
            case R.id.ll_career_info:
                intent.setClass(EnterDeveloperActivity.this, AddCareerActivity.class);
                if (bean != null) {
                    intent.putExtra(INTENT_KEY_DEVELOPER_INFO, bean);
                }
                getActivity().startActivityForResult(intent, 10002);
                break;
            case R.id.ll_add_education:
                intent.setClass(EnterDeveloperActivity.this, AddEducationActivityNew.class);
                getActivity().startActivityForResult(intent, 10003);
                break;
            case R.id.ll_add_work:
                intent.setClass(EnterDeveloperActivity.this, AddWorkActivity.class);
                getActivity().startActivityForResult(intent, 10004);
                break;
            case R.id.ll_add_project:
                intent.setClass(EnterDeveloperActivity.this, AddProjectActivityNew.class);
                getActivity().startActivityForResult(intent, 10005);
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(tv_edit_name.getText())) {
                    toast("您还没有请填写基本信息");
                    return;
                }
                if (TextUtils.isEmpty(tv_career_info.getText())) {
                    toast("您还没有请填写职业信息");
                    return;
                }
                if (addEducationAdapter.getCount() == 0) {
                    toast("您还没有请填写教育经历");
                    return;
                }
                if (addWorkAdapter.getCount() == 0) {
                    toast("您还没有请填写工作经历");
                    return;
                }
                if (addProjectAdapter.getCount() == 0) {
                    toast("您还没有请填写项目经历");
                    return;
                }
                submitDeveloper();
                break;

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


    public static List<ExperienceBean> sortListForDate(List<ExperienceBean> listInAppxList) {
        Comparator<ExperienceBean> comparator = new Comparator<ExperienceBean>() {
            @Override
            public int compare(ExperienceBean o1, ExperienceBean o2) {
                if (o1.getType() == 4 && o2.getType() == 4) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getInSchoolStartTime()) < TimeUtil.getTimeLong("yyyy-MM", o2.getInSchoolStartTime()))
                        return 1;
                    else {
                        return -1;
                    }
                } else if (o1.getType() == 5 && o2.getType() == 5) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
                        return 1;
                    else {
                        return -1;
                    }
                } else if (o1.getType() == 6 && o2.getType() == 6) {
                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
                        return 1;
                    else {
                        return -1;
                    }
                }
                return 1;


//                if (o1.getType() == 4 && o2.getType() == 4) {
//                    if (TimeUtil.getTimeLong("yyyy-MM", o1.getProjectStartDate()) < TimeUtil.getTimeLong("yyyy-MM", o2.getProjectStartDate()))
//                        return 1;
//                    else {
//                        return -1;
//                    }
//                }
//                return 1;
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
            if (list.get(i).getType() == type || list.get(i).getType() == type + 3) {
                position = i;
            }
        }
        return position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);
        }
    }

    private int progress = 0;
    private DeveloperInfoBean bean;

    @SuppressLint("SetTextI18n")
    public void getDeveloperDetail(int parentId) {


        EasyHttp.get(this)
                .api(new GetDeveloperDetailApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {
                        progress = 0;
                        bean = data.getData();
                        String realName = bean.getRealName();
                        int sex = bean.getSex();
                        if (!TextUtils.isEmpty(bean.getAvatarUrl())) {
                            ll_add_photo.setVisibility(View.GONE);
                            fl_add_photo.setVisibility(View.VISIBLE);
                            GlideApp.with(EnterDeveloperActivity.this)
                                    .load(bean.getAvatarUrl())
                                    .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
                                    .into(iv_photo_avatar);
                        } else {
                            ll_add_photo.setVisibility(View.VISIBLE);
                            fl_add_photo.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(realName)) {
                            ll_base_info.setVisibility(View.VISIBLE);
                            tv_edit_name.setText(realName);
                            String mSex = sex == 0 ? "男" : "女";

                            String nowTime = TimeUtil.getTimeString("yyyy-MM-dd");
                            int age = Utils.getIntYear(nowTime) - Utils.getIntYear(bean.getBirthday());
                            tv_edit_info.setText(mSex + " | " + age + "岁 | " + bean.getProvinceName() + bean.getCityName() + bean.getAreasName());
                            tv_edit_reason.setText(bean.getRemoteWorkReasonStr());

                            progress++;
                        }
                        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
                        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();
                        if (workModeDtoList.size() != 0) {
                            if (!TextUtils.isEmpty(careerDto.getCareerDirectionName())) {
                                ll_career_info.setVisibility(View.VISIBLE);
                                tv_career_info.setText(careerDto.getCareerDirectionName());
                                tv_career_info_work_year.setText(careerDto.getWorkYearsName() + " | 当前薪资：" + (Double.parseDouble(careerDto.getCurSalary()) / 1000) + "K");
                                tv_career_info_work_mode.setText(workModeDtoList.get(0).getWorkDayModeName() + " | 期望薪资：" +
                                        (Double.parseDouble(workModeDtoList.get(0).getLowestSalary()) / 1000) + "K"
                                        + "-" + (Double.parseDouble(workModeDtoList.get(0).getHighestSalary()) / 1000) + "K");
                                progress++;
                            }
                        }

                        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
                        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
                        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();
                        if (educationDtoList.size() != 0) {
                            progress++;
                        }
                        addEducationAdapter = new AddEducationAdapter(EnterDeveloperActivity.this, educationDtoList);
                        lv1.setAdapter(addEducationAdapter);

                        if (workExperienceDtoList.size() != 0) {
                            progress++;
                        }
                        addWorkAdapter = new AddWorkAdapter(EnterDeveloperActivity.this, workExperienceDtoList);
                        lv2.setAdapter(addWorkAdapter);
                        if (projectDtoList.size() >= 1) {
                            progress++;
                        }
                        addProjectAdapter = new AddProjectAdapter(EnterDeveloperActivity.this, projectDtoList);
                        lv3.setAdapter(addProjectAdapter);
                        if (projectDtoList.size() >= 2) {
                            progress++;
                        }
                        if (projectDtoList.size() >= 3) {
                            progress++;
                        }
                        if (projectDtoList.size() >= 4) {
                            progress++;
                        }
                        if (projectDtoList.size() >= 5) {
                            progress++;
                        }
                        if (projectDtoList.size() >= 6) {
                            progress++;
                        }
                        if (projectDtoList.size() >= 7) {
                            progress++;
                        }


                        EasyLog.print("=====progress==" + progress);
                        ll_progress.setVisibility(View.VISIBLE);
                        if (progress == 0) {
                            ll_progress.setVisibility(View.GONE);
                        } else if (progress == 1) {
                            tv_progress.setText("\"完成度超过2%的用户，仍需努力哦~\"");
                            progress_bar.setProgress(2);
                        } else if (progress == 2) {
                            tv_progress.setText("\"完成度超过5%的用户，加油~\"");
                            progress_bar.setProgress(5);
                        } else if (progress == 3) {
                            tv_progress.setText("\"完成度超过10%的用户，继续加油~\"");
                            progress_bar.setProgress(10);
                        } else if (progress == 4) {
                            tv_progress.setText("\"完成度超过30%的用户，继续完善让履历更风采~\"");
                            progress_bar.setProgress(30);
                        } else if (progress == 5) {
                            tv_progress.setText("\"恭喜你！完成度超过60%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(60);
                        } else if (progress == 6) {
                            tv_progress.setText("\"恭喜你！完成度超过65%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(65);
                        } else if (progress == 7) {
                            tv_progress.setText("\"恭喜你！完成度超过70%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(70);
                        } else if (progress == 8) {
                            tv_progress.setText("\"恭喜你！完成度超过75%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(75);
                        } else if (progress == 9) {
                            tv_progress.setText("\"恭喜你！完成度超过80%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(80);
                        } else if (progress == 10) {
                            tv_progress.setText("\"恭喜你！完成度超过85%的用户，全面的工作和项目经历可以进一步提升竞争力~\"");
                            progress_bar.setProgress(85);
                        } else {
                            tv_progress.setText("\"恭喜你！完成度超过90%以上的用户，未来可期～\"");
                            progress_bar.setProgress(90);
                        }

                        if (bean.getStatus() == 2) {
                            tv_progress.setText("\"审核中，专属顾问将在1-3个工作日内完成审核\"");
                            iv_progress.setImageResource(R.drawable.icon_warning);
                            progress_bar.setVisibility(View.GONE);
                        } else if (bean.getStatus() == 3) {
                            tv_welcome.setVisibility(View.GONE);
                            ll_progress.setVisibility(View.GONE);
                            mCommit.setVisibility(View.GONE);
                        }
                        sv.smoothScrollTo(0, 0);
                    }
                });
    }

    public void submitDeveloper() {
        EasyHttp.post(this)
                .api(new SubmitDeveloperApi())
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
//                        startActivity(SaveQRActivity.class);
//                        finish();
                        onResume();
                    }
                });
    }

    /**
     * 裁剪图片
     */
    private void cropImageFile(File sourceFile) {
        ImageCropActivity.start(this, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {

            @Override
            public void onSucceed(Uri fileUri, String fileName) {
                File outputFile;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    outputFile = new FileContentResolver(getActivity(), fileUri, fileName);
                } else {
                    try {
                        outputFile = new File(new URI(fileUri.toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        outputFile = new File(fileUri.toString());
                    }
                }
                updateCropImage(outputFile, true);
            }

            @Override
            public void onError(String details) {
                // 没有的话就不裁剪，直接上传原图片
                // 但是这种情况极其少见，可以忽略不计
                updateCropImage(sourceFile, false);
            }
        });
    }

    /**
     * 头像地址
     */
    private Uri mAvatarUrl;

    /**
     * 上传裁剪后的图片
     */
    private void updateCropImage(File file, boolean deleteFile) {
        ll_add_photo.setVisibility(View.GONE);
        fl_add_photo.setVisibility(View.VISIBLE);
        EasyHttp.post(this)
                .api(new UpdateAvatarApi()
                        .setFiles(file))
                .request(new HttpCallback<HttpData<String>>(this) {

                    @Override
                    public void onSucceed(HttpData<String> data) {
                        mAvatarUrl = Uri.parse(data.getData());
                        GlideApp.with(getActivity())
                                .load(mAvatarUrl)
                                .transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_8))))
                                .into(iv_photo_avatar);
                        if (deleteFile) {
                            file.delete();
                        }
                    }
                });
    }
}
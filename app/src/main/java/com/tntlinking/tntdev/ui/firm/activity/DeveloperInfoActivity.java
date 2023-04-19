package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengShare;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CollectDeveloperCancelApi;
import com.tntlinking.tntdev.http.api.CollectDeveloperApi;
import com.tntlinking.tntdev.http.api.CollectDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetFirmDevDetailApi;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.http.api.GetFirmPositionListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.OnItemClickListener;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.BottomListDialog;
import com.tntlinking.tntdev.ui.dialog.ShareAppDialog;
import com.tntlinking.tntdev.ui.dialog.ShareToWXDialog;
import com.tntlinking.tntdev.ui.firm.adapter.DevEducationAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.DevProjectAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.DevWorkAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.TagFirmAdapter;
import com.tntlinking.tntdev.widget.FlowTagLayout;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 开发者详情页面
 */
public final class DeveloperInfoActivity extends AppActivity {

    private ListView lv1, lv2, lv3;
    private ScrollView sv;
    private ImageView iv_avatar;
    private ImageView iv_collect;
    private TextView tv_dev_name;
    private TextView tv_dev_info;
    private TextView tv_salary;
    private TextView tv_all_day;
    private FlowTagLayout tag_flow_layout;
    private LinearLayout ll_bottom;
    private LinearLayout ll_to_collect;
    private LinearLayout ll_to_sign;
    private AppCompatButton btn_to_interview;

    private DeveloperInfoBean bean;
    private List<GetFirmPositionApi.Bean.ListBean> mList = new ArrayList<>();

    private DevEducationAdapter addEducationAdapter;
    private DevWorkAdapter addWorkAdapter;
    private DevProjectAdapter addProjectAdapter;
    private boolean isCollect = false;//是否收藏
    private int mDeveloperId;

    @Override
    protected int getLayoutId() {
        return R.layout.developer_info_activity;
    }


    @Override
    protected void initView() {
        lv1 = findViewById(R.id.lv_1);
        lv1 = findViewById(R.id.lv_1);
        lv2 = findViewById(R.id.lv_2);
        lv3 = findViewById(R.id.lv_3);
        sv = findViewById(R.id.sv);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_dev_name = findViewById(R.id.tv_dev_name);
        tv_dev_info = findViewById(R.id.tv_dev_info);
        tv_salary = findViewById(R.id.tv_salary);
        tv_all_day = findViewById(R.id.tv_all_day);
        tag_flow_layout = findViewById(R.id.tag_flow_layout);
        ll_to_collect = findViewById(R.id.ll_to_collect);
        iv_collect = findViewById(R.id.iv_collect);
        ll_to_sign = findViewById(R.id.ll_to_sign);
        btn_to_interview = findViewById(R.id.btn_to_interview);
        ll_bottom = findViewById(R.id.ll_bottom);

        setOnClickListener(ll_to_collect, ll_to_sign, btn_to_interview);


    }


    @Override
    protected void initData() {
        mDeveloperId = getInt("developerId");
        getFirmDevDetail(mDeveloperId);
        getCollectStatus(mDeveloperId);
        if (!TextUtils.isEmpty(getString("from"))) {
            ll_bottom.setVisibility(View.GONE);
        } else {
            getAppList(1);
            ll_bottom.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRightClick(View view) {
        String shareUrl = "http://talent.staging.tntlinking.com/#/developer-details?developerId=" + bean.getId();
        UMWeb content = new UMWeb(shareUrl);
        content.setTitle(bean.getRealName());
        content.setDescription(bean.getCareerDto().getCareerDirectionName());
        content.setThumb(new UMImage(this, R.mipmap.app_logo));

        new ShareToWXDialog.Builder(this)
                .setShareLink(content)
                .setListener(new UmengShare.OnShareListener() {

                    @Override
                    public void onSucceed(Platform platform) {
                        toast("分享成功");
                    }

                    @Override
                    public void onError(Platform platform, Throwable t) {
                        toast(t.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform) {
                        toast("分享取消");
                    }
                })
                .show();

    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_to_collect:
                if (isCollect) {
                    cancelCollectDeveloper(mDeveloperId);
                } else {
                    collectDeveloper(mDeveloperId);
                }
                break;
            case R.id.ll_to_sign:
                new BottomListDialog.Builder(this).setData(mList).setListener(new BottomListDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog) {
//                        startActivity(SendPositionActivity.class);
                        Intent intent = new Intent();
                        intent.setClass(DeveloperInfoActivity.this, SendPositionActivity.class);
                        intent.putExtra("developerId", mDeveloperId);
                        startActivity(intent);
                    }
                }).setOnItemListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        GetFirmPositionApi.Bean.ListBean listBean = mList.get(position);
                        Intent intent = new Intent();
                        intent.setClass(DeveloperInfoActivity.this, ContractDetailActivity.class);
                        intent.putExtra("positionId", listBean.getId());

                        intent.putExtra("positionName", bean.getCareerDto().getCareerDirectionName());
                        intent.putExtra("expectSalary", bean.getWorkModeDtoList().get(0).getExpectSalary());
                        intent.putExtra("developerId", bean.getId());
                        intent.putExtra("realName", bean.getRealName());
                        intent.putExtra("avatarUrl", bean.getAvatarUrl());
                        startActivity(intent);
                    }
                }).show();
                break;
            case R.id.btn_to_interview:
                new BaseDialog.Builder<>(this)
                        .setContentView(R.layout.to_add_service_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setOnClickListener(R.id.iv_close, (dialog, views) -> {
                            dialog.dismiss();
                        }).show();
                break;
        }

    }

    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    /**
     * 获取开发者详情
     */
    public void getFirmDevDetail(int developerId) {
        EasyHttp.get(this)
                .api(new GetFirmDevDetailApi().setDeveloperId(developerId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {
                        setDeveloperInfo(data.getData());

                    }
                });
    }


    /**
     * 端职位列表(分页)
     * 底部签约列表list
     */
    private void getAppList(int pageNum) {
        EasyHttp.get(this)
                .api(new GetFirmPositionListApi())
                .request(new HttpCallback<HttpData<List<GetFirmPositionApi.Bean.ListBean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetFirmPositionApi.Bean.ListBean>> data) {
                        if (data.getData() != null && data.getData().size() >= 0) {
                            mList.clear();
                            mList.addAll(data.getData());

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    /**
     * 判断收藏状态 true  false
     *
     * @param developerId
     */
    public void getCollectStatus(int developerId) {
        EasyHttp.get(this)
                .api(new CollectDeveloperStatusApi().setDeveloperId(developerId))
                .request(new HttpCallback<HttpData<Boolean>>(this) {

                    @Override
                    public void onSucceed(HttpData<Boolean> data) {
                        isCollect = data.getData();
                        if (isCollect) {
                            iv_collect.setImageResource(R.drawable.icon_collected);
                        } else {
                            iv_collect.setImageResource(R.drawable.icon_collect);
                        }

                    }
                });
    }


    /**
     * 收藏开发者
     *
     * @param developerId
     */
    public void collectDeveloper(int developerId) {
        EasyHttp.post(this)
                .api(new CollectDeveloperApi().setDeveloperId(developerId))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        isCollect = true;
                        iv_collect.setImageResource(R.drawable.icon_collected);
                        toast("收藏成功");
                    }
                });
    }

    /**
     * 取消收藏开发者
     *
     * @param developerId
     */
    public void cancelCollectDeveloper(int developerId) {
        EasyHttp.post(this)
                .api(new CollectDeveloperCancelApi().setDeveloperId(developerId))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        isCollect = false;
                        iv_collect.setImageResource(R.drawable.icon_collect);
                        toast("取消收藏");
                    }
                });
    }

    /**
     * 填充开发者信息
     */
    @SuppressLint("SetTextI18n")
    public void setDeveloperInfo(DeveloperInfoBean data) {
        bean = data;
        GlideUtils.loadRoundCorners(this, data.getAvatarUrl(), iv_avatar, (int) getResources().getDimension(R.dimen.dp_8));
        tv_dev_name.setText(data.getRealName());

        DeveloperInfoBean.DeveloperCareer careerDto = bean.getCareerDto();
        List<DeveloperInfoBean.WorkMode> workModeDtoList = bean.getWorkModeDtoList();
        if (workModeDtoList.size() != 0) {
            double expectSalary = Double.parseDouble(workModeDtoList.get(0).getExpectSalary()) / 1000;
            tv_salary.setText((Utils.formatMoney(expectSalary) + "k/月"));
//            tv_salary.setText(workModeDtoList.get(0).getExpectSalary());
            if (workModeDtoList.get(0).getWorkDayMode() == 1) { // 1 全日 2 半日
                tv_all_day.setText("全日");
            } else {
                tv_all_day.setText("半日");
            }

        }

        tv_dev_info.setText(careerDto.getCareerDirectionName() + "·工作经验" + careerDto.getWorkYearsName());

        TagFirmAdapter adapter = new TagFirmAdapter(getContext(), 2);
        tag_flow_layout.setAdapter(adapter);
        List<DeveloperInfoBean.DeveloperSkillDto> developerSkillDtoList = bean.getDeveloperSkillDtoList();
        List<String> skill = new ArrayList<>();
        if (developerSkillDtoList != null && developerSkillDtoList.size() != 0) {
            for (int i = 0; i < developerSkillDtoList.size(); i++) {
                skill.add(developerSkillDtoList.get(i).getSkillName());
            }
            adapter.onlyAddAll(skill);
        }

        List<DeveloperInfoBean.DeveloperEducation> educationDtoList = bean.getEducationDtoList();
        List<DeveloperInfoBean.DeveloperWork> workExperienceDtoList = bean.getWorkExperienceDtoList();
        List<DeveloperInfoBean.DeveloperProject> projectDtoList = bean.getProjectDtoList();

        addEducationAdapter = new DevEducationAdapter(DeveloperInfoActivity.this, educationDtoList);
        lv1.setAdapter(addEducationAdapter);


        addWorkAdapter = new DevWorkAdapter(DeveloperInfoActivity.this, workExperienceDtoList);
        lv2.setAdapter(addWorkAdapter);


        addProjectAdapter = new DevProjectAdapter(DeveloperInfoActivity.this, projectDtoList);
        lv3.setAdapter(addProjectAdapter);

        sv.smoothScrollTo(0, 0);
    }

}
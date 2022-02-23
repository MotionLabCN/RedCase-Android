package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.GetCodeApi;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.HistoryProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.HomeTaskAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.ui.bean.BannerBean;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 工作主页面
 */
public final class HomeStatusActivity extends AppActivity {
    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_status;
//    private TextView tv_do_task;
//    private TextView tv_task_name;
//    private TextView tv_task_description;
//    private TextView tv_rewardNum;
//    private View view_dot;
//    private TextView tv_remain_day;
    private ImageView iv_interview;
    private LinearLayout ll_cooperation;
    private LinearLayout ll_service;
    private LinearLayout ll_question;
    private LinearLayout ll_contact;
    private LinearLayout ll_cooperation_1;
    private LinearLayout ll_service_1;
    private LinearLayout ll_question_1;
    private LinearLayout ll_contact_1;
    private ViewPager viewPager;
    private LinearLayout ll_title;
    private LinearLayout ll_empty;
    private LinearLayout ll_status;// 平台介绍页面
    private LinearLayout ll_work;// 工作服务列表页面
    private MyListView lv_task;
    private MyListView lv_1;
    private MyListView lv_2;

    private int appSize = 0; //工作请求列表size
    private int interSize = 0; //面试请求列表size
    private int historySize = 0;//历史记录列表size
    private HomeTaskAdapter mTaskAdapter;
    private ServiceProjectAdapter mServiceAdapter;
    private HistoryProjectAdapter mHistoryAdapter;
    String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME, "朋友");

    @Override
    protected int getLayoutId() {
        return R.layout.home_status_activity;
    }

    @Override
    protected void initView() {
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_status = findViewById(R.id.tv_status);
//        tv_do_task = findViewById(R.id.tv_do_task);
//        tv_task_name = findViewById(R.id.tv_task_name);
//        tv_task_description = findViewById(R.id.tv_task_description);
//        view_dot = findViewById(R.id.view_dot);
//        tv_remain_day = findViewById(R.id.tv_remain_day);
//        tv_rewardNum = findViewById(R.id.tv_rewardNum);
        iv_interview = findViewById(R.id.iv_interview);
        ll_cooperation = findViewById(R.id.ll_cooperation);
        ll_service = findViewById(R.id.ll_service);
        ll_question = findViewById(R.id.ll_question);
        ll_contact = findViewById(R.id.ll_contact);
        ll_cooperation_1 = findViewById(R.id.ll_cooperation_1);
        ll_service_1 = findViewById(R.id.ll_service_1);
        ll_question_1 = findViewById(R.id.ll_question_1);
        ll_contact_1 = findViewById(R.id.ll_contact_1);
        viewPager = findViewById(R.id.viewpager);
        ll_title = findViewById(R.id.ll_title);
        lv_task = findViewById(R.id.lv_task);
        lv_1 = findViewById(R.id.lv_1);
        lv_2 = findViewById(R.id.lv_2);
        ll_empty = findViewById(R.id.ll_empty);
        ll_status = findViewById(R.id.ll_status);
        ll_work = findViewById(R.id.ll_work);

        tv_avatar.setText(Utils.formatName(name));
        tv_name.setText("你好," + name);

        ImmersionBar.setTitleBar(this, ll_title);
        setOnClickListener(iv_interview, tv_avatar, ll_cooperation, ll_service, ll_question, ll_contact,
                ll_cooperation_1, ll_service_1, ll_question_1, ll_contact_1);

        GlideApp.with(this)
                .load(R.drawable.icon_gif_interview)
                .into(iv_interview);

        BannerBean banner1 = new BannerBean();
        banner1.setImgRes(R.drawable.bg_img_1);
        banner1.setStar("4.6");
        banner1.setPosition("小影/哈尔滨市/后端开发");
        banner1.setDescription("享受远程办公工作模式，提高工作效率");
        BannerBean banner2 = new BannerBean();
        banner2.setImgRes(R.drawable.bg_img_2);
        banner2.setStar("4.8");
        banner2.setPosition("婷婷/随州市/前端开发");
        banner2.setDescription("陪伴孩子，抵抗变迁，远程让我自在安心");
        BannerBean banner3 = new BannerBean();
        banner3.setImgRes(R.drawable.bg_img_3);
        banner3.setStar("4.5");
        banner3.setPosition("阿炳/运城市/后端开发");
        banner3.setDescription("为我远程链接客户，帮我重新平衡了工作与生活");

        List<BannerBean> list = new ArrayList<>();
        list.add(banner1);
        list.add(banner2);
        list.add(banner3);


        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.dp_16)); //显示viewpager间距
        viewPager.setOffscreenPageLimit(3);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, list);
        viewPager.setAdapter(adapter);
        mTaskAdapter = new HomeTaskAdapter(this, mTaskList);
        lv_task.setAdapter(mTaskAdapter);
        mServiceAdapter = new ServiceProjectAdapter(this, mServiceList);
        mHistoryAdapter = new HistoryProjectAdapter(this, mHistoryList);
        lv_1.setAdapter(mServiceAdapter);
        lv_2.setAdapter(mHistoryAdapter);
        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppListApi.Bean item = (AppListApi.Bean) mServiceAdapter.getItem(position);
                if (!TextUtils.isEmpty(item.getServiceName())) {

                    Intent intent = new Intent(HomeStatusActivity.this, WriteDailyActivity.class);
                    intent.putExtra("orderId", item.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeStatusActivity.this, InterviewDetailActivity.class);
                    intent.putExtra("interviewId", item.getId());
                    startActivity(intent);
                }

            }
        });

        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetNewbieApi.Bean item = mTaskAdapter.getItem(position);
                if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //做任务
                    startActivity(EnterDeveloperActivity.class);
                } else if (item.getTaskStatus() == 2) {
                    if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// 已完成
                        startActivity(SaveQRActivity.class);
                    }

                }
            }
        });

    }


    @Override
    protected void initData() {
//        getStatus();
        getNewbie();
        int status = getInt(AppConfig.DEVELOP_STATUS, 0);
        long createTime = getLong(AppConfig.CREATE_TIME, 0);

        if (status == 3) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("已认证");
//            tv_name.setText("你好," + name);

//            tv_do_task.setText("领红包");
//            view_dot.setVisibility(View.GONE);
//            tv_remain_day.setVisibility(View.GONE);

            getAppList();
        } else {
            ll_status.setVisibility(View.VISIBLE); //1、2、4 状态下显示平台介绍和新手任务
            ll_work.setVisibility(View.GONE);

            tv_status.setVisibility(View.GONE);
//            tv_name.setText("你好,新朋友");

        }


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_avatar:
                startActivity(PersonDataActivity.class);
                break;
            case R.id.iv_interview:
                startActivity(InterviewActivity.class);
                break;
            case R.id.ll_cooperation:
            case R.id.ll_cooperation_1:
//                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/recruit_guide.pdf","合作模式");
                startActivity(PDFViewActivity.class);
                break;
            case R.id.ll_service:
            case R.id.ll_service_1:
//                String service_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md";
                String service_guide = AppConfig.SERVICE_GUIDE_URL;
                intent.setClass(this, MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "服务手册");
                startActivity(intent);
                break;
            case R.id.ll_question:
            case R.id.ll_question_1:
//                String faq_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/faq_guide.md";
                String faq_guide = AppConfig.FAQ_GUIDE_URL;
                intent.setClass(this, MDViewActivity.class);
                intent.putExtra("md_url", faq_guide);
                intent.putExtra("title", "常见问题");
                startActivity(intent);
                break;
            case R.id.ll_contact:
            case R.id.ll_contact_1:
                intent.setClass(this, SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
                break;
//            case R.id.tv_do_task:
//                int status = getInt(AppConfig.DEVELOP_STATUS, 0);
//                if (status == 3) {
//                    startActivity(SaveQRActivity.class);
//                } else {
//                    startActivity(EnterDeveloperActivity.class);
//                }
//                break;

        }

    }


    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private List<AppListApi.Bean> mHistoryList = new ArrayList<>();

    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            mServiceList.addAll(data.getData());
                        }
                        getInterviewAppList();
                        appSize = data.getData().size();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getHistoryList();
                        appSize = 0;
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getInterviewAppList() {
        EasyHttp.get(this)
                .api(new AppListInterviewApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        interSize = data.getData().size();
                        if (data.getData().size() > 0) {
                            mServiceList.addAll(data.getData());
                        }
                        getHistoryList();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getHistoryList();
                        interSize = 0;
                        if (appSize + interSize == 0) {
                            ll_empty.setVisibility(View.VISIBLE);
                        } else {
                            ll_empty.setVisibility(View.GONE);
                        }
                    }
                });


    }

    private void getHistoryList() {
        EasyHttp.get(this)
                .api(new HistoryListApi().setOrderData("2018-10-10"))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {

                            //无服务项目（包含面试邀约）且无历史服务项目  显示平台介绍和新手任务
                            if (appSize + interSize == 0) {
                                ll_status.setVisibility(View.VISIBLE);
                                ll_work.setVisibility(View.GONE);

                                ll_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_status.setVisibility(View.GONE);
                                ll_work.setVisibility(View.VISIBLE);
                                mServiceAdapter.setData(mServiceList);

                                ll_empty.setVisibility(View.GONE);
                            }
                            mHistoryList.addAll(data.getData());
                            mHistoryAdapter.setData(mHistoryList);

                        } else {
                            //无服务项目（包含面试邀约）但有历史服务项目  显示暂无工作和历史项目
                            if (appSize + interSize == 0) {
                                ll_status.setVisibility(View.VISIBLE);
                                ll_work.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        ll_status.setVisibility(View.VISIBLE);
                        ll_work.setVisibility(View.GONE);
                    }
                });
    }

    public void getNewbie() {
        EasyHttp.get(this)
                .api(new GetNewbieApi())
                .request(new HttpCallback<HttpData<List<GetNewbieApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetNewbieApi.Bean>> data) {
                        if (data.getData() != null || data.getData().size() != 0) {
                            mTaskList.clear();
                            mTaskList.addAll(data.getData());
                            mTaskAdapter.setData(mTaskList);
                        }

//                        tv_task_name.setText(data.getData().getTaskName());
//                        tv_task_description.setText(data.getData().getTaskDescription());
//                        tv_rewardNum.setText(Utils.StripZeros(data.getData().getRewardNum()) + "元");
//
//                        if (taskStatus == 0 || taskStatus == 1) { //做任务
//                            tv_do_task.setText("做任务");
//                            view_dot.setVisibility(View.VISIBLE);
//                            tv_remain_day.setVisibility(View.VISIBLE);
//                            tv_remain_day.setText("距失效剩余" + remainingDays + "天");
//                        } else if (taskStatus == 2) {
//                            if (rewardStatus == 0 || taskStatus == 1) {// 已完成
//                                tv_do_task.setText("领红包");
//
//                            } else { //已领取
//                                tv_do_task.setText("已领取");
//                                tv_do_task.setTextColor(getResources().getColor(R.color.color_hint_color));
//                                tv_do_task.setBackground(getResources().getDrawable(R.drawable.btn_gray_radius_20));
//                                tv_do_task.setClickable(false);
//
//                            }
//                            view_dot.setVisibility(View.GONE);
//                            tv_remain_day.setVisibility(View.GONE);
//                        } else {// 已失效
//
//                            tv_do_task.setText("已失效");
//                            tv_do_task.setTextColor(getResources().getColor(R.color.color_hint_color));
//                            tv_do_task.setBackground(getResources().getDrawable(R.drawable.btn_gray_radius_20));
//                            tv_do_task.setClickable(false);
//
//                            view_dot.setVisibility(View.GONE);
//                            tv_remain_day.setVisibility(View.GONE);
//                        }
                    }
                });
    }

    public void getStatus() {
        EasyHttp.get(HomeStatusActivity.this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(HomeStatusActivity.this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        // 1->待认证  2->待审核   3->审核成功 4->审核失败
                        SPUtils.getInstance().put(AppConfig.DEVELOP_STATUS, data.getData().getStatus());
                        SPUtils.getInstance().put(AppConfig.DEVELOP_NAME, data.getData().getRealName());
                        String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME, "朋友");
                        tv_avatar.setText(name);
                        String createDate = data.getData().getCreateDate();
                        long createTime = 0;
                        if (createDate.contains("T")) {
                            String replace = createDate.replace("T", " ");
                            createTime = TimeUtils.getTimeSpanByNow(replace, TimeConstants.DAY);
                        } else if (createDate.contains(" ")) {
                            createTime = TimeUtils.getTimeSpanByNow(createDate, TimeConstants.DAY);
                        }
                        int status = Integer.parseInt(data.getData().getStatus());

                    }
                });
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<BannerBean> mList;

        public MyViewPagerAdapter(Context context, List<BannerBean> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_viewpager, null);

            BannerBean bean = mList.get(position);
            ImageView ivViewPager = view.findViewById(R.id.iv_viewpager);
            TextView tv_star = view.findViewById(R.id.tv_star);
            TextView tv_position = view.findViewById(R.id.tv_position);
            TextView tv_description = view.findViewById(R.id.tv_description);

            ivViewPager.setImageResource(bean.getImgRes());
            tv_star.setText(bean.getStar());
            tv_position.setText(bean.getPosition());
            tv_description.setText(bean.getDescription());
            container.addView(view);  //这一步很关键 把当前的imageview添加到适配器上
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public float getPageWidth(int position) {
            return (float) 0.9;
        }
    }


}

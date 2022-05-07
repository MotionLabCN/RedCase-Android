package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.GetAppUpdateApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.InterviewActivity;
import com.tntlinking.tntdev.ui.activity.InterviewDetailActivity;
import com.tntlinking.tntdev.ui.activity.MDViewActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.PDFViewActivity;
import com.tntlinking.tntdev.ui.activity.PersonDataActivity;
import com.tntlinking.tntdev.ui.activity.SaveQRActivity;
import com.tntlinking.tntdev.ui.activity.SignContactActivity;
import com.tntlinking.tntdev.ui.activity.WriteDailyActivity;
import com.tntlinking.tntdev.ui.adapter.HistoryProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.HomeTaskAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import com.tntlinking.tntdev.ui.bean.BannerBean;
import com.tntlinking.tntdev.ui.dialog.AppUpdateDialog;
import com.tntlinking.tntdev.widget.MyListView;
import com.tntlinking.tntdev.widget.XCollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 *
 *    desc   : 首页 Fragment
 */
public final class HomeFragment1 extends TitleBarFragment<MainActivity>{

    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_status;
    private ImageView iv_interview;
    private LinearLayout ll_cooperation;
    private LinearLayout ll_service;
    private LinearLayout ll_question;
    private LinearLayout ll_contact;
    private ViewPager viewPager;
    private LinearLayout ll_title;

    private LinearLayout ll_status;// 平台介绍页面
    //    private LinearLayout ll_work;// 工作服务列表页面
    private LinearLayout ll_task_empty;//
    private MyListView lv_task;

    private LinearLayout ll_accept_order;
    private TextView tv_accept_status;

    private int appSize = 0; //工作请求列表size
    private int interSize = 0; //面试请求列表size
    private int historySize = 0;//历史记录列表size
    private HomeTaskAdapter mTaskAdapter;
    private ServiceProjectAdapter mServiceAdapter;
    private HistoryProjectAdapter mHistoryAdapter;
    String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME, "朋友");
    private int mStatus = 1;// 接单状态 1 默认可接单

    public static HomeFragment1 newInstance() {
        return new HomeFragment1();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment1;
    }


    @Override
    protected void initView() {
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_status = findViewById(R.id.tv_status);

        iv_interview = findViewById(R.id.iv_interview);
        ll_cooperation = findViewById(R.id.ll_cooperation);
        ll_service = findViewById(R.id.ll_service);
        ll_question = findViewById(R.id.ll_question);
        ll_contact = findViewById(R.id.ll_contact);

        viewPager = findViewById(R.id.viewpager);
        ll_title = findViewById(R.id.ll_title);
        lv_task = findViewById(R.id.lv_task);
        ll_status = findViewById(R.id.ll_status);
//        ll_work = findViewById(R.id.ll_work);
        ll_task_empty = findViewById(R.id.ll_task_empty);
        ll_accept_order = findViewById(R.id.ll_accept_order);
        tv_accept_status = findViewById(R.id.tv_accept_status);

        tv_avatar.setText(Utils.formatName(name));
        tv_name.setText("你好," + name);

        ImmersionBar.setTitleBar(this, ll_title);
        setOnClickListener(iv_interview, tv_avatar, ll_cooperation, ll_service, ll_question, ll_contact, ll_accept_order);

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
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        mTaskAdapter = new HomeTaskAdapter(getActivity(), mTaskList);
        lv_task.setAdapter(mTaskAdapter);


    }


    @Override
    protected void initData() {
//        getStatus();
        getNewbie();
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");

        if (status.equals("3")) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("已认证");

//            getAppList();
        } else {
            ll_status.setVisibility(View.VISIBLE); //1、2、4 状态下显示平台介绍和新手任务
//            ll_work.setVisibility(View.GONE);

            tv_status.setVisibility(View.GONE);

        }

//        getAppUpdate();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
//            case R.id.tv_avatar:
//                startActivity(PersonDataActivity.class);
//                break;
            case R.id.iv_interview:
                startActivity(InterviewActivity.class);
                break;
            case R.id.ll_cooperation:
//                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/recruit_guide.pdf","合作模式");
                String PDFUrl = AppConfig.RECRUIT_GUIDE_URL;
                intent.setClass(getActivity(), PDFViewActivity.class);
                intent.putExtra("pdf_url", PDFUrl);
                intent.putExtra("title", "合作模式");
                startActivity(intent);
                break;
            case R.id.ll_service:
//                String service_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md";
                String service_guide = AppConfig.SERVICE_GUIDE_URL;
                intent.setClass(getActivity(), MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "服务手册");
                startActivity(intent);
                break;
            case R.id.ll_question:
//                String faq_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/faq_guide.md";
                String faq_guide = AppConfig.FAQ_GUIDE_URL;
                intent.setClass(getActivity(), MDViewActivity.class);
                intent.putExtra("md_url", faq_guide);
                intent.putExtra("title", "常见问题");
                startActivity(intent);
                break;
            case R.id.ll_contact:
                intent.setClass(getActivity(), SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
                break;

            case R.id.ll_accept_order:
                checkStatus(getActivity(), mStatus);
                break;

        }

    }


    /**
     * 切换 是否可接单状态
     *
     * @param activity
     * @param status
     */
    public void checkStatus(FragmentActivity activity, int status) {
        String title = (status == 1) ? "确定切换为不接单吗？" : "确定切换为可接单吗？";
        String content = (status == 1) ? "切换为不接单状态后将不在显示职位推荐" : "切换为可接单状态后将展示职位推荐";

        new BaseDialog.Builder<>(activity)
                .setContentView(R.layout.check_order_status_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setText(R.id.tv_title, title)
                .setText(R.id.tv_content, content)
                .setText(R.id.btn_dialog_custom_cancel, "取消")
                .setText(R.id.btn_dialog_custom_ok, "确定")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                    mStatus = (status == 1) ? 2 : 1;
                    String str = (mStatus == 1) ? "可接单" : "不接单";
                    tv_accept_status.setText(str);
                    dialog.dismiss();
                }).show();
    }


    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private List<AppListApi.Bean> mHistoryList = new ArrayList<>();

    /**
     * 获取在服务企业list
     */
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

    /**
     * 获取面试邀约list
     */
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
//                            ll_empty.setVisibility(View.VISIBLE);
                        } else {
//                            ll_empty.setVisibility(View.GONE);
                        }
                    }
                });


    }


    /**
     * 获取历史服务list
     */
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
//                                ll_work.setVisibility(View.GONE);

//                                ll_empty.setVisibility(View.VISIBLE);
                            } else {
                                ll_status.setVisibility(View.GONE);
//                                ll_work.setVisibility(View.VISIBLE);
                                mServiceAdapter.setData(mServiceList);

//                                ll_empty.setVisibility(View.GONE);
                            }
                            mHistoryList.addAll(data.getData());
                            mHistoryAdapter.setData(mHistoryList);

                        } else {
                            //无服务项目（包含面试邀约）但有历史服务项目  显示暂无工作和历史项目
                            if (appSize + interSize == 0) {
                                ll_status.setVisibility(View.VISIBLE);
//                                ll_work.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        ll_status.setVisibility(View.VISIBLE);
//                        ll_work.setVisibility(View.GONE);
                    }
                });
    }

    /**
     * 获取任务状态
     */
    public void getNewbie() {
        EasyHttp.get(this)
                .api(new GetNewbieApi())
                .request(new HttpCallback<HttpData<List<GetNewbieApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetNewbieApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            ll_task_empty.setVisibility(View.GONE);
                            mTaskList.clear();
                            mTaskList.addAll(data.getData());
                            mTaskAdapter.setData(mTaskList);
                        } else {
                            ll_task_empty.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }


    /**
     * 检查更新
     */
    public void getAppUpdate() {
        EasyHttp.get(this)
                .api(new GetAppUpdateApi()
                        .setOsType(2)//1 ios   2 android
                        .setCurrVersion(AppConfig.getVersionName()))
                .request(new HttpCallback<HttpData<GetAppUpdateApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetAppUpdateApi.Bean> data) {
                        if (data.getData() != null) {
                            GetAppUpdateApi.Bean bean = data.getData();
                            if (AppConfig.getVersionName().equals(bean.getVersion())) {

                            } else {

                                String description = bean.getDescription();
                                if (description.contains("\\n")) {
                                    description = description.replace("\\n", "\n");
                                }
                                boolean isForce = bean.getForceUpdate() == 1;
                                // 升级对话框
                                new AppUpdateDialog.Builder(getActivity())
                                        // 版本名
                                        .setVersionName("最新版本：" + bean.getVersion())
                                        // 是否强制更新
                                        .setForceUpdate(isForce)
                                        // 更新日志
                                        .setUpdateLog(description)
                                        // 下载 URL
                                        .setDownloadUrl(bean.getDownloadUrl())
                                        // 文件 MD5
//                                            .setFileMd5("df2f045dfa854d8461d9cefe08b813a8")
                                        .show();
                            }
                        }
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
            return (float) 0.85;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;

import com.hjq.http.listener.HttpCallback;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetAppUpdateApi;
import com.tntlinking.tntdev.http.api.GetDeveloperJkStatusApi;
import com.tntlinking.tntdev.http.api.GetDeveloperRecommendsApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.api.UpdateServiceStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationActivity;
import com.tntlinking.tntdev.ui.activity.EvaluationNeedsTokNowActivity;
import com.tntlinking.tntdev.ui.activity.InterviewActivity;
import com.tntlinking.tntdev.ui.activity.JkBrowserActivity;
import com.tntlinking.tntdev.ui.activity.MDViewActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.PDFViewActivity;
import com.tntlinking.tntdev.ui.activity.SaveQRActivity;
import com.tntlinking.tntdev.ui.activity.SignContactActivity;
import com.tntlinking.tntdev.ui.adapter.HistoryProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.HomeTaskAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.ui.bean.BannerBean;
import com.tntlinking.tntdev.ui.dialog.AppUpdateDialog;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * desc   : ?????? Fragment
 */
public final class HomeFragment1 extends TitleBarFragment<MainActivity> implements HomeChangeListener {

    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_status;
    private TextView tv_order_switching;
    private LinearLayout ll_cooperation;
    private LinearLayout ll_service;
    private LinearLayout ll_question;
    private LinearLayout ll_contact;
    private ViewPager viewPager;
    private ViewPager2 viewPager2;
    private LinearLayout ll_title;
    private LinearLayout ll_empty;
    private LinearLayout ll_status;// ??????????????????
    //    private LinearLayout ll_work;// ????????????????????????
    private LinearLayout ll_tab;// ????????????????????????
    private LinearLayout ll_task;// ????????????????????????

    private LinearLayout ll_task_empty;//
    private MyListView lv_task;
    private MyListView lv_1;
    private MyListView lv_2;
    private int appSize = 0; //??????????????????size
    private int interSize = 0; //??????????????????size
    private int historySize = 0;//??????????????????size
    private HomeTaskAdapter mTaskAdapter;
    private ServiceProjectAdapter mServiceAdapter;
    private HistoryProjectAdapter mHistoryAdapter;
    String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME, "??????");
    private String[] titles = {"????????????", "????????????"};
    private List<Fragment> fragmentList = new ArrayList<>();

    private int mStatus = SPUtils.getInstance().getInt(AppConfig.SERVICE_STATUS, 1); // ???????????? 1 ??????????????? 2 ????????????


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

        tv_order_switching = findViewById(R.id.tv_order_switching);
        ll_cooperation = findViewById(R.id.ll_cooperation);
        ll_service = findViewById(R.id.ll_service);
        ll_question = findViewById(R.id.ll_question);
        ll_contact = findViewById(R.id.ll_contact);
        viewPager = findViewById(R.id.viewpager);
        ll_title = findViewById(R.id.ll_title);
        lv_task = findViewById(R.id.lv_task);

        ll_empty = findViewById(R.id.ll_empty);
        ll_status = findViewById(R.id.ll_status);
//        ll_work = findViewById(R.id.ll_work);
        ll_task_empty = findViewById(R.id.ll_task_empty);
        ll_tab = findViewById(R.id.ll_tab);
        ll_task = findViewById(R.id.ll_task);

        tv_avatar.setText(Utils.formatName(name));
        tv_name.setText("??????," + name);
        ImmersionBar.setTitleBar(this, ll_title);
        setOnClickListener(tv_order_switching, tv_avatar, ll_cooperation, ll_service, ll_question, ll_contact);


        BannerBean banner1 = new BannerBean();
        banner1.setImgRes(R.drawable.bg_img_1);
        banner1.setStar("4.6");
        banner1.setPosition("??????/????????????/????????????");
        banner1.setDescription("???????????????????????????????????????????????????");
        BannerBean banner2 = new BannerBean();
        banner2.setImgRes(R.drawable.bg_img_2);
        banner2.setStar("4.8");
        banner2.setPosition("??????/?????????/????????????");
        banner2.setDescription("??????????????????????????????????????????????????????");
        BannerBean banner3 = new BannerBean();
        banner3.setImgRes(R.drawable.bg_img_3);
        banner3.setStar("4.5");
        banner3.setPosition("??????/?????????/????????????");
        banner3.setDescription("???????????????????????????????????????????????????????????????");

        List<BannerBean> list = new ArrayList<>();
        list.add(banner1);
        list.add(banner2);
        list.add(banner3);


        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.dp_16)); //??????viewpager??????
        viewPager.setOffscreenPageLimit(3);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getActivity(), list);
        viewPager.setAdapter(adapter);
        mTaskAdapter = new HomeTaskAdapter(getActivity(), mTaskList);
        lv_task.setAdapter(mTaskAdapter);
//        mServiceAdapter = new ServiceProjectAdapter(getActivity(), mServiceList);
//        mHistoryAdapter = new HistoryProjectAdapter(getActivity(), mHistoryList);
//        lv_1.setAdapter(mServiceAdapter);
//        lv_2.setAdapter(mHistoryAdapter);


        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetNewbieApi.Bean item = mTaskAdapter.getItem(position);
//                if (item.getTaskId() == 2) { //????????????
//                    if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //?????????
//                        startActivity(EnterDeveloperActivity.class);
//                    } else if (item.getTaskStatus() == 2) {
//                        if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// ?????????
//                            startActivity(SaveQRActivity.class);
//                        }
//                    }
//                } else if (item.getTaskId() == 3) {//??????????????????
//
//                    String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
//                    if (status.equals("3")) {
//                        if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //?????????
//                            startActivity(SignContactActivity.class);
//                        } else if (item.getTaskStatus() == 2) {
//                            if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// ?????????
//                                startActivity(SaveQRActivity.class);
//                            }
//                        }
//                    } else {
//                        new BaseDialog.Builder<>(getActivity())
//                                .setContentView(R.layout.write_daily_delete_dialog)
//                                .setAnimStyle(BaseDialog.ANIM_SCALE)
//                                .setText(R.id.tv_title, "??????????????????????????????????????????")
//                                .setText(R.id.btn_dialog_custom_cancel, "??????")
//                                .setText(R.id.btn_dialog_custom_ok, "?????????")
//                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
//                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
//
//                                    startActivity(EnterDeveloperActivity.class);
//                                    dialog.dismiss();
//                                })
//                                .show();
//                    }
//
//                }

                if (item.getTaskId() == 2) { //????????????
                    if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //?????????
                        startActivity(EnterDeveloperActivity.class);
                    } else if (item.getTaskStatus() == 2) {
                        if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// ?????????
                            startActivity(SaveQRActivity.class);
                        }
                    }
                } else if (item.getTaskId() == 4) {//????????????
                    getDeveloperJkStatus();
                } else {//??????????????????
                    if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //?????????
                        startActivity(SignContactActivity.class);
                    } else if (item.getTaskStatus() == 2) {
                        if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// ?????????
                            startActivity(SaveQRActivity.class);
                        }
                    }
                }
            }
        });
        //?????????
        PositionRecommendationFragment first = new PositionRecommendationFragment();
        ActiveTaskFragment second = new ActiveTaskFragment();
        first.setListener(this);
        second.setListener(this);
        fragmentList.add(first);
        fragmentList.add(second);
        TabLayout tabs = findViewById(R.id.tab_position);
        viewPager2 = findViewById(R.id.vp_position);
        tabs.setSelectedTabIndicatorHeight(0);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setCustomView(R.layout.layout_tab);
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
                textView.setText(tab.getText());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                View view = fragmentList.get(position).getView();
                updatePagerHeight(view, viewPager2);
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabs, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        });
        //??????????????????
        tabLayoutMediator.attach();
    }

    private void updatePagerHeight(View view, ViewPager2 pager) {
        view.post(() -> {
            int wMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
            int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(wMeasureSpec, hMeasureSpec);
            if (pager.getLayoutParams().height != view.getMeasuredHeight()) {
                ViewGroup.LayoutParams layoutParams = pager.getLayoutParams();
                layoutParams.height = view.getMeasuredHeight();
                pager.setLayoutParams(layoutParams);
            }
        });
    }

    private void updatePagerHeightForChild(int height) {
        viewPager2.post(new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getMeasuredHeight() < height) {
                    ViewGroup.LayoutParams layoutParams = viewPager2.getLayoutParams();
                    layoutParams.height = height;
                    viewPager2.setLayoutParams(layoutParams);
                }
            }
        });
    }


    @Override
    protected void initData() {
        getNewbie();
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");

        if (status.equals("3")) {
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("?????????");

//            getAppList();
        } else {
            ll_status.setVisibility(View.VISIBLE); //1???2???4 ??????????????????????????????????????????

            tv_status.setVisibility(View.GONE);

        }
        //??????????????????
        if (mStatus == 1) {
            tv_order_switching.setText("?????????");
            tv_order_switching.setTextColor(getResources().getColor(R.color.main_color));
            tv_order_switching.setBackground(getResources().getDrawable(R.drawable.bg_blue_stroke));
            Drawable drawable = getResources().getDrawable(R.drawable.icon_refresh);
            tv_order_switching.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            ll_tab.setVisibility(View.VISIBLE);
            ll_task.setVisibility(View.GONE);
        } else {
            tv_order_switching.setText("?????????");
            tv_order_switching.setTextColor(getResources().getColor(R.color.color_444E64));
            tv_order_switching.setBackground(getResources().getDrawable(R.drawable.bg_dark_grey_stroke));
            Drawable drawable = getResources().getDrawable(R.drawable.icon_change_over);
            tv_order_switching.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            ll_tab.setVisibility(View.GONE);
            ll_task.setVisibility(View.VISIBLE);
        }
        getAppUpdate();
    }

    private void getDeveloper_Recommends() {
        EasyHttp.get(this)
                .api(new GetDeveloperRecommendsApi())
                .request(new HttpCallback<HttpData<List<GetDeveloperRecommendsApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetDeveloperRecommendsApi.Bean>> data) {
                        int mStatus = SPUtils.getInstance().getInt(AppConfig.SERVICE_STATUS, 1);
                        if (mStatus == 1) {
                            if (data.getData() != null && data.getData().size() != 0) {
                                ll_tab.setVisibility(View.VISIBLE);
                                ll_task.setVisibility(View.GONE);
                            } else {
                                ll_tab.setVisibility(View.GONE);
                                ll_task.setVisibility(View.VISIBLE);
                            }
                        } else {
                            ll_tab.setVisibility(View.GONE);
                            ll_task.setVisibility(View.VISIBLE);
                        }


                    }
                });
    }

    private void getDeveloperJkStatus() {
        EasyHttp.get(this)
                .api(new GetDeveloperJkStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperJkStatusApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<GetDeveloperJkStatusApi.Bean> data) {
                        //1->?????????  2->?????????   3->???????????? 4->????????????
                        String mStatus = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
                        if (mStatus.equals("1")) {
                            startActivity(EvaluationActivity.class);
                        } else if (data.getData() != null && data.getData().getUserPlanStatus() == 0) {
                            startActivity(EvaluationNeedsTokNowActivity.class);
                        } else {
                            JkBrowserActivity.start(getActivity(), data.getData().getPlanUrl());

                        }

                    }
                });
    }


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
//                BrowserActivity.start(getActivity(), "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/recruit_guide.pdf","????????????");
                String PDFUrl = AppConfig.RECRUIT_GUIDE_URL;
                intent.setClass(getActivity(), PDFViewActivity.class);
                intent.putExtra("pdf_url", PDFUrl);
                intent.putExtra("title", "????????????");
                startActivity(intent);
                break;
            case R.id.ll_service:
//                String service_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/service_guide.md";
                String service_guide = AppConfig.SERVICE_GUIDE_URL;
                intent.setClass(getActivity(), MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "????????????");
                startActivity(intent);
                break;
            case R.id.ll_question:
//                String faq_guide = "https://stage-ttchain.tntlinking.com/api/minio/manpower-pages/faq_guide.md";
                String faq_guide = AppConfig.FAQ_GUIDE_URL;
                intent.setClass(getActivity(), MDViewActivity.class);
                intent.putExtra("md_url", faq_guide);
                intent.putExtra("title", "????????????");
                startActivity(intent);
                break;
            case R.id.ll_contact:
                intent.setClass(getActivity(), SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
                break;
            case R.id.tv_order_switching:

                checkStatus(getActivity(), mStatus);
                break;
        }

    }

    /**
     * ?????? ?????????????????????
     *
     * @param activity
     * @param status
     */
    public void checkStatus(FragmentActivity activity, int status) {
        String title = (status == 1) ? "????????????????????????" : "????????????????????????";
        String content = (status == 1) ? "???????????????????????????,???????????????????????????" : "???????????????????????????,?????????????????????";

        new BaseDialog.Builder<>(activity)
                .setContentView(R.layout.check_order_status_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setText(R.id.tv_title, title)
                .setText(R.id.tv_content, content)
                .setText(R.id.btn_dialog_custom_cancel, "??????")
                .setText(R.id.btn_dialog_custom_ok, "??????")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                    mStatus = (status == 1) ? 2 : 1;
                    updateServiceStatus(mStatus);
                    dialog.dismiss();
                }).show();
    }


    /**
     * ?????????????????????
     */
    public void updateServiceStatus(int status) {
        EasyHttp.post(this)
                .api(new UpdateServiceStatusApi()
                        .setType(status))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        SPUtils.getInstance().put(AppConfig.SERVICE_STATUS, status);
                        if (mStatus == 1) {
                            tv_order_switching.setText("?????????");
                            tv_order_switching.setTextColor(getResources().getColor(R.color.main_color));
                            tv_order_switching.setBackground(getResources().getDrawable(R.drawable.bg_blue_stroke));
                            Drawable drawable = getResources().getDrawable(R.drawable.icon_refresh);
                            tv_order_switching.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                            ll_tab.setVisibility(View.VISIBLE);
                            ll_task.setVisibility(View.GONE);

                        } else {
                            tv_order_switching.setText("?????????");
                            tv_order_switching.setTextColor(getResources().getColor(R.color.color_444E64));
                            tv_order_switching.setBackground(getResources().getDrawable(R.drawable.bg_dark_grey_stroke));
                            Drawable drawable = getResources().getDrawable(R.drawable.icon_change_over);
                            tv_order_switching.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                            ll_tab.setVisibility(View.GONE);
                            ll_task.setVisibility(View.VISIBLE);
                        }

//                        getDeveloper_Recommends();
                    }
                });
    }


    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private List<AppListApi.Bean> mHistoryList = new ArrayList<>();


    /**
     * ??????????????????
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
     * ????????????
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
                                // ???????????????
                                new AppUpdateDialog.Builder(getActivity())
                                        // ?????????
                                        .setVersionName("???????????????" + bean.getVersion())
                                        // ??????????????????
                                        .setForceUpdate(isForce)
                                        // ????????????
                                        .setUpdateLog(description)
                                        // ?????? URL
                                        .setDownloadUrl(bean.getDownloadUrl())
                                        // ?????? MD5
//                                            .setFileMd5("df2f045dfa854d8461d9cefe08b813a8")
                                        .show();
                            }
                        }
                    }
                });

    }

    @Override
    public void onDataChanged(int height) {
        updatePagerHeightForChild(height);
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
            container.addView(view);  //?????????????????? ????????????imageview?????????????????????
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
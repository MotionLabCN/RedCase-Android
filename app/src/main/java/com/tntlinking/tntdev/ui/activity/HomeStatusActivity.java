package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.AppListInterviewApi;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.api.HistoryListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.adapter.AppListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 工作主页面
 */
public final class HomeStatusActivity extends AppActivity {
    private TextView tv_avatar;
    private TextView tv_name;
    private ImageView iv_interview;
    private LinearLayout ll_cooperation;
    private LinearLayout ll_service;
    private LinearLayout ll_question;
    private LinearLayout ll_contact;
    private ViewPager viewPager;
    private LinearLayout ll_title;

    @Override
    protected int getLayoutId() {
        return R.layout.home_status_activity;
    }

    @Override
    protected void initView() {
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        iv_interview = findViewById(R.id.iv_interview);
        ll_cooperation = findViewById(R.id.ll_cooperation);
        ll_service = findViewById(R.id.ll_service);
        ll_question = findViewById(R.id.ll_question);
        ll_contact = findViewById(R.id.ll_contact);
        viewPager = findViewById(R.id.viewpager);
        ll_title = findViewById(R.id.ll_title);
        String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME);
        tv_avatar.setText(name);
        ImmersionBar.setTitleBar(this, ll_title);
        setOnClickListener(iv_interview, tv_avatar, ll_cooperation, ll_service, ll_question, ll_contact);

        View v1 = new View(this);
        View v2 = new View(this);
        View v3 = new View(this);
        View v4 = new View(this);
        View v5 = new View(this);

        v1.setBackgroundResource(R.drawable.bg_view_1);
        v2.setBackgroundResource(R.drawable.bg_view_2);
        v3.setBackgroundResource(R.drawable.bg_view_3);
        v4.setBackgroundResource(R.drawable.bg_view_1);
        v5.setBackgroundResource(R.drawable.bg_view_2);

        List<View> views = new ArrayList<View>();
        views.add(v1);
        views.add(v2);
        views.add(v3);
        views.add(v4);

        viewPager.setAdapter(new AZPagerAdapter(views));
        viewPager.setPageTransformer(true, new ScrollOffsetTransformer());
        viewPager.setOffscreenPageLimit(2);
    }

    public class ScrollOffsetTransformer implements ViewPager.PageTransformer {
        /**
         * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。
         * 当一个页面（page)填充整个屏幕时，positoin值为0；
         * 当一个页面（page)刚刚离开屏幕右(左）侧时，position值为1（-1）；
         * 当两个页面分别滚动到一半时，其中一个页面是-0.5，另一个页面是0.5。
         * 基于屏幕上页面的位置，通过诸如setAlpha()、setTranslationX()或setScaleY()方法来设置页面的属性，创建自定义的滑动动画。
         */
        @Override
        public void transformPage(View page, float position) {
            if (position > 0) {
                //右侧的缓存页往左偏移100
                page.setTranslationX(-100 * position);
            }
        }
    }

    @Override
    protected void initData() {
//        getHistoryList();

        int status = getInt(AppConfig.DEVELOP_STATUS, 0);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_avatar:
                break;
            case R.id.iv_interview:
                startActivity(InterviewActivity.class);
                break;
            case R.id.ll_cooperation:
                break;
            case R.id.ll_service:
                break;
            case R.id.ll_question:
                break;
            case R.id.ll_contact:
                break;

        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    private List<AppListApi.Bean> mList = new ArrayList<>();

    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi())
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(1);
                                mList.add(bean);
                            }
//                            getHistoryList();
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);

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
                        if (data.getData().size() > 0) {
                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(1);
                                mList.add(bean);
                            }

                        }


                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getHistoryList();

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
                            AppListApi.Bean appBean = new AppListApi.Bean();
                            appBean.setType(3);
                            mList.add(appBean);

                            for (AppListApi.Bean bean : data.getData()) {
                                bean.setType(2);
                                mList.add(bean);
                            }
                        } else { //历史记录空白页面

                        }


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
                        tv_avatar.setText(data.getData().getRealName());
                        if (data.getData().getStatus().equals("1")) {
                            startActivity(LoginActivityView.class);
                            finish();
                        } else if (data.getData().getStatus().equals("3")) {

                        } else if (data.getData().getStatus().equals("2")) {
//                            startActivity(CheckDeveloperActivity.class);
                        } else {
                            startActivity(CheckDeveloperFailActivity.class);
                            finish();
                        }

                    }
                });
    }

    public class AZPagerAdapter extends PagerAdapter {

        protected List<View> views;

        public AZPagerAdapter(List<View> viewList) {
            views = viewList;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    }

}

package com.tntlinking.tntdev.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * desc   : 首页 Fragment
 */
public final class TreatyFragment1 extends TitleBarFragment<MainActivity> implements ViewPager.OnPageChangeListener, TabAdapter.OnTabListener, HomeChangeListener {

    private ViewPager mViewPager;
    private RecyclerView mTabView;
    private TabAdapter mTabAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    private LinearLayout ll_empty;
    private LinearLayout ll_tab;
    private TextView tv_refresh;

    public static TreatyFragment1 newInstance() {
        return new TreatyFragment1();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_fragment2;
    }


    @Override
    protected void initView() {

        ll_empty = findViewById(R.id.ll_empty);
        ll_tab = findViewById(R.id.ll_tab);
        tv_refresh = findViewById(R.id.tv_refresh);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(TreatyService1Fragment.newInstance());
        mPagerAdapter.addFragment(TreatyServiced2Fragment.newInstance());
//        TreatyService1Fragment.newInstance().setListener(this);
//        TreatyServiced2Fragment.newInstance().setListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(getAttachActivity(), TabAdapter.TAB_MODE_SERVICE, true);
        mTabView.setAdapter(mTabAdapter);

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EnterDeveloperActivity.class);
            }
        });
    }


    @Override
    protected void initData() {
        mTabAdapter.addItem("服务中");
        mTabAdapter.addItem("待服务");
        mTabAdapter.setOnTabListener(this);
        getAppList2(2);


    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    /**
     * {@link TabAdapter.OnTabListener}
     */

    @Override
    public boolean onTabSelected(RecyclerView recyclerView, int position) {
        mViewPager.setCurrentItem(position);
        return true;
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mTabAdapter == null) {
            return;
        }
        mTabAdapter.setSelectedPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }

    @Override
    public void onDataChanged(int height) {

    }

    private int serviceSize = 0;
    private int noServiceSize = 0;

    /**
     * @param status 2 待服务 3 服务中
     */
    private void getAppList2(int status) {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(status))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() == 0) {
                            getAppList3(3);
                        }

                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        getAppList3(3);
                    }
                });
    }

    private void getAppList3(int status) {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(status))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() == 0) {
                            ll_tab.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                        } else {
                            ll_tab.setVisibility(View.VISIBLE);
                            ll_empty.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        ll_tab.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                });
    }
}
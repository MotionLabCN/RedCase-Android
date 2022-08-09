package com.tntlinking.tntdev.ui.firm.fragment;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetCareerDirectionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.activity.PositionSearchListActivity;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * desc   : 首页 Fragment
 */
public final class FirmHomeFragment extends TitleBarFragment<FirmMainActivity> implements ViewPager.OnPageChangeListener, TabAdapter.OnTabListener {

    private ViewPager mViewPager;
    private RecyclerView mTabView;
    private TabAdapter mTabAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    private LinearLayout ll_empty;
    private LinearLayout ll_tab;
    private TextView tv_refresh;
    private TextView tv_search;
    private LinearLayout ll_title;

    public static FirmHomeFragment newInstance() {
        return new FirmHomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_home_fragment;
    }


    @Override
    protected void initView() {

        ll_empty = findViewById(R.id.ll_empty);
        ll_tab = findViewById(R.id.ll_tab);
        tv_search = findViewById(R.id.tv_search);
        tv_refresh = findViewById(R.id.tv_refresh);
        ll_title = findViewById(R.id.ll_title);

        mTabView = findViewById(R.id.rv_home_tab);
        ImmersionBar.setTitleBar(this, ll_title);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
//        mPagerAdapter.addFragment(PositionFragment.newInstance("1"));
//        mPagerAdapter.addFragment(PositionFragment.newInstance("2"));
//        mPagerAdapter.addFragment(PositionFragment.newInstance("3"));
//        mPagerAdapter.addFragment(PositionFragment.newInstance("4"));
//        mPagerAdapter.addFragment(PositionFragment.newInstance("5"));
//
//        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(getAttachActivity(), TabAdapter.TAB_MODE_SERVICE, false);
        mTabView.setAdapter(mTabAdapter);
        GetCareerDirection();

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PositionSearchListActivity.class);
            }
        });
    }


    @Override
    protected void initData() {
//        mTabAdapter.addItem("为你推荐");
//        mTabAdapter.addItem("前端开发");
//        mTabAdapter.addItem("后端开发");
//        mTabAdapter.addItem("移动端开发");
//        mTabAdapter.addItem("测试");
        mTabAdapter.setOnTabListener(this);
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

    /**
     * 获取职业方向
     */
    public void GetCareerDirection() {
        EasyHttp.get(this)
                .api(new GetCareerDirectionApi())
                .request(new HttpCallback<HttpData<List<GetCareerDirectionApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetCareerDirectionApi.Bean>> data) {

                        List<GetCareerDirectionApi.Bean> bean = data.getData();
                        for (GetCareerDirectionApi.Bean s : bean) {
                            String name = s.getName();
                            mPagerAdapter.addFragment(PositionFragment.newInstance(s.getId()));
                            mTabAdapter.addItem(name);
                        }
                        mViewPager.setAdapter(mPagerAdapter);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }

}
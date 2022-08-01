package com.tntlinking.tntdev.ui.firm.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import com.tntlinking.tntdev.ui.firm.fragment.RecommendPositionFragment;
import com.tntlinking.tntdev.ui.firm.fragment.TreatyOrderFragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * 账单明细列表页面
 */
public final class RecommendPositionActivity extends AppActivity implements ViewPager.OnPageChangeListener, TabAdapter.OnTabListener {

    private ViewPager mViewPager;
    private RecyclerView mTabView;
    private TabAdapter mTabAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    private LinearLayout ll_empty;
    private LinearLayout ll_tab;
    private TextView tv_refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.recommend_position_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        ll_tab = findViewById(R.id.ll_tab);
        tv_refresh = findViewById(R.id.tv_refresh);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance("1"));
        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance("2"));


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(this, TabAdapter.TAB_MODE_SERVICE, false);
        mTabView.setAdapter(mTabAdapter);


    }

    @Override
    protected void initData() {
        mTabAdapter.addItem("为您推荐");
        mTabAdapter.addItem("自荐");
        mTabAdapter.setOnTabListener(this);
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
        EasyLog.print("====state==" + state);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }

}
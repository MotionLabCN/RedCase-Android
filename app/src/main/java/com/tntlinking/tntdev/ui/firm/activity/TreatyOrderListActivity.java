package com.tntlinking.tntdev.ui.firm.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import com.tntlinking.tntdev.ui.firm.fragment.TreatyOrderFragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * 账单明细列表页面
 */
public final class TreatyOrderListActivity extends AppActivity implements ViewPager.OnPageChangeListener, TabAdapter.OnTabListener {

    private ViewPager mViewPager;
    private RecyclerView mTabView;
    private TabAdapter mTabAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    private LinearLayout ll_empty;
    private LinearLayout ll_tab;
    private TextView tv_search;

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_order_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        ll_tab = findViewById(R.id.ll_tab);
        tv_search = findViewById(R.id.tv_search);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        //0 全部 1待冻结 2服务中 3已完成 4待结算
        mPagerAdapter.addFragment(TreatyOrderFragment.newInstance("0"));
        mPagerAdapter.addFragment(TreatyOrderFragment.newInstance("1"));
        mPagerAdapter.addFragment(TreatyOrderFragment.newInstance("2"));
        mPagerAdapter.addFragment(TreatyOrderFragment.newInstance("3"));
        mPagerAdapter.addFragment(TreatyOrderFragment.newInstance("4"));

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(this, TabAdapter.TAB_MODE_SERVICE, true);
        mTabView.setAdapter(mTabAdapter);
        EasyConfig.getInstance().addHeader("loginRole", "Recruiter");
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TreatyOrderSearchActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        mTabAdapter.addItem("全部");
        mTabAdapter.addItem("待冻结");
        mTabAdapter.addItem("服务中");
        mTabAdapter.addItem("已完成");
        mTabAdapter.addItem("待结算");
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

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mViewPager.removeOnPageChangeListener(this);
        mTabAdapter.setOnTabListener(null);
    }

}
package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
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
    private TextView tv_position;
    private TextView tv_company_name;
    private TextView tv_position_desc;

    @Override
    protected int getLayoutId() {
        return R.layout.recommend_position_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        ll_tab = findViewById(R.id.ll_tab);
        tv_refresh = findViewById(R.id.tv_refresh);
        tv_position = findViewById(R.id.tv_position);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_position_desc = findViewById(R.id.tv_position_desc);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
//        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(1));
//        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(2));


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(this, TabAdapter.TAB_MODE_SERVICE, true);
        mTabView.setAdapter(mTabAdapter);


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mTabAdapter.addItem("为您推荐");
        mTabAdapter.addItem("自荐");
        mTabAdapter.setOnTabListener(this);

        GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");
        if (bean != null) {
            tv_position.setText(bean.getCareerDirection());
            tv_company_name.setText(bean.getCompanyName());
            tv_position_desc.setText(bean.getTrainingMode() + "·" + bean.getEducation() + "·" + bean.getWorkYears() + "·" + bean.getIndustryName());
        }

        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(1, bean.getId()));
        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(2, bean.getId()));
        mViewPager.setAdapter(mPagerAdapter);

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
package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.http.api.GetPositionOriginalApi;
import com.tntlinking.tntdev.http.api.PutPositionStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.adapter.TabAdapter;
import com.tntlinking.tntdev.ui.firm.fragment.RecommendPositionFragment;
import com.tntlinking.tntdev.ui.firm.fragment.TreatyOrderFragment;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * 推荐列表页面
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
    private TextView tv_salary;
    private TextView tv_position_desc;
    private LinearLayout ll_recommend;
    private AppCompatButton btn_commit;

    private int mPositionId;

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
        tv_salary = findViewById(R.id.tv_salary);
        tv_position_desc = findViewById(R.id.tv_position_desc);
        ll_recommend = findViewById(R.id.ll_recommend);

        btn_commit = findViewById(R.id.btn_commit);

        mTabView = findViewById(R.id.rv_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);
        mPagerAdapter = new FragmentPagerAdapter<>(this);
//        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(1));
//        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(2));


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabAdapter = new TabAdapter(this, TabAdapter.TAB_MODE_SERVICE, true);
        mTabView.setAdapter(mTabAdapter);

        ll_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");
                Intent intent = new Intent(RecommendPositionActivity.this, SendPositionActivity.class);
                intent.putExtra("position_bean", bean);
                intent.putExtra("position_bean_ids", beanIds);
                startActivity(intent);
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BaseDialog.Builder<>(RecommendPositionActivity.this)
                        .setContentView(R.layout.write_daily_delete_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title, "确定要关闭职位？")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                            closePosition(mPositionId, 0);
                        })
                        .show();


            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {

        GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");
        int cunt = bean.getCountRecommends();
        int self = bean.getCountSelfRecommends();
        mTabAdapter.addItem("为您推荐·" + cunt);
        mTabAdapter.addItem("自荐·" + self);
        mTabAdapter.setOnTabListener(this);

        if (bean != null) {
            tv_position.setText(bean.getCareerDirection());
            tv_position_desc.setText(bean.getTrainingMode() + "·" + bean.getEducation() + "·工作经验"
                    + bean.getWorkYears() + "·" + bean.getIndustryName());
//            double startPay = bean.getStartPay() / 1000;
//            double endPay = bean.getEndPay() / 1000;
//            tv_salary.setText((Utils.formatMoney(startPay) + "k") + "-" + (Utils.formatMoney(endPay) + "k"));

            String startPay = Utils.StripZeros(bean.getStartPay() + "");
            String endPay = Utils.StripZeros(bean.getEndPay() + "");
            tv_salary.setText(startPay + "k-" + endPay+ "k");

            mPositionId = bean.getId();
            getPositionOriginal(bean.getId());
        }


        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(1, bean.getId()));
        mPagerAdapter.addFragment(RecommendPositionFragment.newInstance(2, bean.getId()));
        mViewPager.setAdapter(mPagerAdapter);

    }

    GetPositionOriginalApi.Bean beanIds;

    //获取职位相关id
    private void getPositionOriginal(int positionId) {
        EasyHttp.get(this)
                .api(new GetPositionOriginalApi().setPositionId(positionId))
                .request(new HttpCallback<HttpData<GetPositionOriginalApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetPositionOriginalApi.Bean> data) {
                        beanIds = data.getData();

                    }

                });
    }


    //关闭职位   //0关1开
    private void closePosition(int positionId, int status) {
        EasyHttp.put(this)
                .api(new PutPositionStatusApi().setPositionId(positionId).setStatus(status))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {

                        finish();
                    }

                });
    }


    private void updatePagerHeight(View view, ViewPager pager) {
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
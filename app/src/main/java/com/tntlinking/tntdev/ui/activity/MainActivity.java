package com.tntlinking.tntdev.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.http.api.GetAppUpdateApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.DoubleClickHelper;
import com.tntlinking.tntdev.ui.adapter.NavigationAdapter;
import com.tntlinking.tntdev.ui.dialog.AppUpdateDialog;
import com.tntlinking.tntdev.ui.fragment.HomeFragment;
import com.tntlinking.tntdev.ui.fragment.HomeFragment1;
import com.tntlinking.tntdev.ui.fragment.MineFragment1;
import com.tntlinking.tntdev.ui.fragment.TreatyFragment1;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * desc   : 首页界面
 */
public final class MainActivity extends AppActivity
        implements NavigationAdapter.OnNavigationListener {

    private static final String INTENT_KEY_IN_FRAGMENT_INDEX = "fragmentIndex";
    private static final String INTENT_KEY_IN_FRAGMENT_CLASS = "fragmentClass";

    private ViewPager mViewPager;
    private RecyclerView mNavigationView;

    private NavigationAdapter mNavigationAdapter;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;

    public static void start(Context context) {
        start(context, HomeFragment.class);
    }

    public static void start(Context context, Class<? extends AppFragment<?>> fragmentClass) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(INTENT_KEY_IN_FRAGMENT_CLASS, fragmentClass);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_home_pager);
        mNavigationView = findViewById(R.id.rv_home_navigation);

        mNavigationAdapter = new NavigationAdapter(this);
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_index),
                ContextCompat.getDrawable(this, R.drawable.tab_home_selector)));
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_treaty),
                ContextCompat.getDrawable(this, R.drawable.tab_treaty_selector)));
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_me),
                ContextCompat.getDrawable(this, R.drawable.tab_mine_selector)));
        mNavigationAdapter.setOnNavigationListener(this);
        mNavigationView.setAdapter(mNavigationAdapter);

    }

    @Override
    protected void initData() {
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(HomeFragment1.newInstance());
//        mPagerAdapter.addFragment(TreatyFragment.newInstance());
        mPagerAdapter.addFragment(TreatyFragment1.newInstance());
        mPagerAdapter.addFragment(MineFragment1.newInstance());
        mViewPager.setAdapter(mPagerAdapter);

        onNewIntent(getIntent());

//        getAppUpdate();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        switchFragment(mPagerAdapter.getFragmentIndex(getSerializable(INTENT_KEY_IN_FRAGMENT_CLASS)));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存当前 Fragment 索引位置
        outState.putInt(INTENT_KEY_IN_FRAGMENT_INDEX, mViewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢复当前 Fragment 索引位置
        switchFragment(savedInstanceState.getInt(INTENT_KEY_IN_FRAGMENT_INDEX));
    }

    private void switchFragment(int fragmentIndex) {
        if (fragmentIndex == -1) {
            return;
        }

        switch (fragmentIndex) {
            case 0:
            case 1:
            case 2:
                mViewPager.setCurrentItem(fragmentIndex);
                mNavigationAdapter.setSelectedPosition(fragmentIndex);
                break;
            default:
                break;
        }
    }

    /**
     * {@link NavigationAdapter.OnNavigationListener}
     */

    @Override
    public boolean onNavigationItemSelected(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
                mViewPager.setCurrentItem(position);
                return true;
            default:
                return false;
        }
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            toast(R.string.home_exit_hint);
            return;
        }

        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false);
        postDelayed(() -> {
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities();
            // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
            // System.exit(0);
        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mNavigationView.setAdapter(null);
        mNavigationAdapter.setOnNavigationListener(null);
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
                                new AppUpdateDialog.Builder(MainActivity.this)
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
}
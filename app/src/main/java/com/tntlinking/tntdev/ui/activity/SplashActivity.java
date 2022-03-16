package com.tntlinking.tntdev.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyLog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SlantedTextView;
import com.tntlinking.tntdev.other.TimeUtil;
import com.umeng.commonsdk.debug.E;

import java.util.logging.Handler;

/**
 * desc   : 闪屏界面
 */
public final class SplashActivity extends AppActivity {

    private LottieAnimationView mLottieView;
    private SlantedTextView mDebugView;
    private ImageView iv_logo;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        mLottieView = findViewById(R.id.lav_splash_lottie);
        mDebugView = findViewById(R.id.iv_splash_debug);
        iv_logo = findViewById(R.id.iv_logo);
        // 设置动画监听
        mLottieView.addAnimatorListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mLottieView.removeAnimatorListener(this);
//                HomeActivity.start(getContext());


//                finish();
            }
        });

        ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
        animation.setDuration(2000);
        //设置持续时间
        animation.setFillAfter(true);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        animation.setRepeatCount(0);
        //设置循环次数，0为1次
        iv_logo.startAnimation(animation);
    }

    @Override
    protected void initData() {
        mDebugView.setText(AppConfig.getBuildType().toUpperCase());
        if (AppConfig.isDebug()) {
            mDebugView.setVisibility(View.VISIBLE);
        } else {
            mDebugView.setVisibility(View.INVISIBLE);
        }


        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(AppConfig.ACCESS_TOKEN))) {
                    if (SPUtils.getInstance().getBoolean(AppConfig.HAS_LOGIN, false)) {
                        if (SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1").equals("1")) {
                            startActivity(HomeStatusActivity.class);
                        } else if (SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1").equals("3")) {
                            startActivity(HomeStatusActivity.class);
                        } else if (SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1").equals("2")) {
                            startActivity(HomeStatusActivity.class);
                        } else {
                            startActivity(CheckDeveloperFailActivity.class);
                        }
                    } else {
                        startActivity(LoginActivity1.class);
                    }
                    finish();
                } else {
//                    startActivity(HomeStatusActivity.class);
                    EasyHttp.get(SplashActivity.this)
                            .api(new GetDeveloperStatusApi())
                            .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(SplashActivity.this) {

                                @Override
                                public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                                    // 1->待认证  2->待审核   3->审核成功 4->审核失败
                                    SPUtils.getInstance().put(AppConfig.DEVELOP_STATUS, data.getData().getStatus());
                                    SPUtils.getInstance().put(AppConfig.DEVELOP_NAME, data.getData().getRealName());
                                    SPUtils.getInstance().put(AppConfig.DEVELOPER_ID, data.getData().getId());

                                    String createDate = data.getData().getCreateDate();
                                    int status = Integer.parseInt(data.getData().getStatus());
                                    Intent intent = new Intent(SplashActivity.this, HomeStatusActivity.class);
                                    intent.putExtra(AppConfig.DEVELOP_STATUS, status);
                                    if (createDate.contains("T")) {
                                        String replace = createDate.replace("T", " ");
                                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(replace, TimeConstants.DAY);
                                        intent.putExtra(AppConfig.CREATE_TIME, Math.abs(timeSpanByNow));
                                    } else if (createDate.contains(" ")) {
                                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(createDate, TimeConstants.DAY);
                                        intent.putExtra(AppConfig.CREATE_TIME, Math.abs(timeSpanByNow));
                                    }
                                    startActivity(intent);
                                    finish();


                                }
                            });
                }

            }
        }, 2000);

    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void initActivity() {
        // 问题及方案：https://www.cnblogs.com/net168/p/5722752.html
        // 如果当前 Activity 不是任务栈中的第一个 Activity
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            // 如果当前 Activity 是通过桌面图标启动进入的
            if (intent != null && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN.equals(intent.getAction())) {
                // 对当前 Activity 执行销毁操作，避免重复实例化入口
                finish();
                return;
            }
        }
        super.initActivity();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        // 因为修复了一个启动页被重复启动的问题，所以有可能 Activity 还没有初始化完成就已经销毁了
        // 所以如果需要在此处释放对象资源需要先对这个对象进行判空，否则可能会导致空指针异常
        super.onDestroy();
    }

    private void toLogin() {


    }
}
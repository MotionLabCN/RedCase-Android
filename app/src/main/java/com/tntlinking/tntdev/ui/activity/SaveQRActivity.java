package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.glide.GlideApp;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户审核失败页面
 */
public final class SaveQRActivity extends AppActivity {
    private TitleBar title_bar;
    private LinearLayout ll_commit_tips;
    private TextView tv_tips_1;
    private TextView tv_tips_2;
    private LinearLayout ll_slide;
    private LinearLayout ll_slide_bottom;
    private ImageView iv_interview;
    private GestureDetector detector = null;// 声明一个手势监听器

    @Override
    protected int getLayoutId() {
        return R.layout.save_qr_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        ll_commit_tips = findViewById(R.id.ll_commit_tips);
        tv_tips_1 = findViewById(R.id.tv_tips_1);
        tv_tips_2 = findViewById(R.id.tv_tips_2);
        ll_slide = findViewById(R.id.ll_slide);
        ll_slide_bottom = findViewById(R.id.ll_slide_bottom);
        iv_interview = findViewById(R.id.iv_interview);


        GlideApp.with(this)
                .load(R.drawable.gif_slide)
                .into(iv_interview);

        ll_slide.setClickable(true);

        String contact = getString("contact");
        if (TextUtils.isEmpty(contact)) {
            ll_slide.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return detector.onTouchEvent(event);
                }
            });
        }

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("TAG", "onScroll");
                Log.e("TAG", "onScroll distanceX = " + distanceX);
                Log.e("TAG", "onScroll distanceY = " + distanceY);

                //distanceY > 0 表示上滑了
                if (distanceY > 0 && distanceY > 3) {
                    Log.e("TAG", "表示上滑了");
                    startActivity(InterviewActivity.class);
                    overridePendingTransition(R.anim.window_bottom_in, R.anim.window_bottom_out);

                }

                // distanceY > 0 表示下滑了
                if (distanceY < 0) {
                    Log.e("TAG", "表示下滑了");
                }

                // 表示左滑了
                if (distanceX > 0) {
                    Log.e("TAG", "表示左滑了");
                }

                // 表示右滑了
                if (distanceX < 0) {
                    Log.e("TAG", "表示右滑了");
                }

                return true;// 事件被消费了，不会继续传递
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                // 需要用户手指触摸屏幕后的一次稍微快速的滑动并且手指抬起后才会回调该方法
                // 如果速度较慢，那么该方法不会回调（可能也正好解释了 fling：猛动 这个词的意思吧）
                Log.e("TAG", "onFling");

                return true;// 事件被消费了，不会继续传递
            }
        });

    }

    @Override
    protected void initData() {
        String contact = getString("contact");
        if (TextUtils.isEmpty(contact)) {
            ll_slide_bottom.setVisibility(View.VISIBLE);
            ll_commit_tips.setVisibility(View.VISIBLE);
        } else {
            title_bar.setTitle("联系顾问");
            ll_commit_tips.setVisibility(View.INVISIBLE);
            ll_slide_bottom.setVisibility(View.GONE);
            tv_tips_2.setVisibility(View.GONE);
            tv_tips_1.setText("添加顾问");
        }

    }


    @SingleClick
    @Override
    public void onClick(View view) {


    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

}
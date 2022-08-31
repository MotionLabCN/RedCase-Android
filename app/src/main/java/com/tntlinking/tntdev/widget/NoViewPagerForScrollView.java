package com.tntlinking.tntdev.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 禁用水平滑动的ViewPager（一般用于 APP 首页的 ViewPager + Fragment）
 */
public final class NoViewPagerForScrollView extends ViewPager {

    public NoViewPagerForScrollView(Context context) {
        super(context);
    }

    public NoViewPagerForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 不拦截这个事件
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 不处理这个事件
        return false;
    }

    @Override
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        // 不响应按键事件
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        // 只有相邻页才会有动画
        super.setCurrentItem(item, Math.abs(getCurrentItem() - item) == 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
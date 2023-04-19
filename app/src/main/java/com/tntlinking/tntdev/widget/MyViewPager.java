package com.tntlinking.tntdev.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //保存的子View的高度，如果能知道每个子View的高度更好，从外面传进来就行了
    protected SparseIntArray sourceHeights = new SparseIntArray();
    //默认的高度
    private float defaultHeight;
    int count = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //设置ViewPager初始化最大测量次数
        //目前项目中的子View是两个高度不定的Fragment
        //多次测试发现ViewPager初始化进来onMeasure会调用4次才测量完当前页高度
        //为了之后平滑过渡，首次得到当前页高度后,滑动时不再让ViewPager测量设置当前页高度
        //否则不会有平滑滑动效果
        int maxCount = 5;
        if (count < 5) {
            count++;
            //获取当前页
            int index = getCurrentItem();
            int height = 0;
            View childAt = getChildAt(index);
            if (childAt != null) {
                //计算当前页的高度
                childAt.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = childAt.getMeasuredHeight();
            }
//            System.out.println("=====height======" + height + "==count==" + count);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            int height1 = 0;
            View childAt1 = getChildAt(i);
            if (childAt1 != null) {
                childAt1.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height1 = childAt1.getMeasuredHeight();
                //保存所有子View的高度，以便滑动时计算
                sourceHeights.put(i, height1);
//                System.out.println("=====height1======" + height1);
            }

        }


    }

    public static int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        //计算ViewPager即将变化到的高度
        int height = (int) ((sourceHeights.get(position) == 0 ?
                defaultHeight :
                sourceHeights.get(position))
                * (1 - offset)
                + (sourceHeights.get(position + 1) == 0 ? defaultHeight :
                sourceHeights.get(position + 1)) * offset);
        //为ViewPager设置高度
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
//        System.out.println("====onPageScrolled====" + sourceHeights.get(position) + "=========" + sourceHeights.get(position + 1));
    }


}


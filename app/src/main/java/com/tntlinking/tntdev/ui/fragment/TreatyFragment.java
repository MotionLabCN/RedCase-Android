package com.tntlinking.tntdev.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.SwitchButton;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.glide.GlideApp;
import com.tntlinking.tntdev.ui.activity.HomeActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;

/**
 * desc   : 服务 Fragment
 */
public final class TreatyFragment extends TitleBarFragment<MainActivity> {


    public static TreatyFragment newInstance() {
        return new TreatyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

}
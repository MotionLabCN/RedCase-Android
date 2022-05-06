package com.tntlinking.tntdev.ui.fragment;

import androidx.fragment.app.Fragment;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.ui.activity.HomeActivity;

public class OneFragment extends TitleBarFragment<HomeActivity> {
    @Override
    protected int getLayoutId() {
        return R.layout.two_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

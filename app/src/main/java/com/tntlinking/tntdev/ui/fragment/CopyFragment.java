package com.tntlinking.tntdev.ui.fragment;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppFragment;
import com.tntlinking.tntdev.ui.activity.CopyActivity;

/**
 *
 *    desc   : 可进行拷贝的副本
 */
public final class CopyFragment extends AppFragment<CopyActivity> {

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.copy_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
package com.tntlinking.tntdev.ui.popup;

import android.content.Context;

import com.hjq.base.BasePopupWindow;
import com.tntlinking.tntdev.R;

/**
 *
 *    desc   : 可进行拷贝的副本
 */
public final class CopyPopup {

    public static final class Builder
            extends BasePopupWindow.Builder<Builder> {

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.copy_popup);
        }
    }
}
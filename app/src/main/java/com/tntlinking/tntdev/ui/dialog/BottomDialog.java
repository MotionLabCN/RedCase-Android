package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;

import androidx.appcompat.widget.AppCompatButton;


public final class BottomDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {
        private final ImageView iv_logo;
        private final ImageView iv_close;
        private final TextView tv_name;
        private final TextView tv_company_name;
        private final AppCompatButton btn_commit;

        private OnListener mListener;
        private boolean mAutoDismiss = true;


        public Builder(Context context) {
            super(context);

            setContentView(R.layout.bottom_dialog);

            iv_logo = findViewById(R.id.iv_logo);
            iv_close = findViewById(R.id.iv_close);
            tv_name = findViewById(R.id.tv_name);
            tv_company_name = findViewById(R.id.tv_company_name);
            btn_commit = findViewById(R.id.btn_commit);

            setOnClickListener(iv_close, btn_commit);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }


        @SingleClick
        @Override
        public void onClick(View view) {
            if (view == btn_commit) {
                if (mAutoDismiss) {
                    dismiss();
                }

                if (mListener == null) {
                    return;
                }
                mListener.onSelected(getDialog());
            } else if (view == iv_close) {
                if (mAutoDismiss) {
                    dismiss();
                }

            }
        }


    }

    public interface OnListener {

        void onSelected(BaseDialog dialog);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
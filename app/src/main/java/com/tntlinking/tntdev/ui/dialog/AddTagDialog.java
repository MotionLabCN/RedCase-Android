package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;


public final class AddTagDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {

        private final AppCompatImageView ivClose;
        private final AppCompatButton btnNext;

        private OnListener mListener;
        private boolean mAutoDismiss = true;
        private final EditText et_add_tag;


        public Builder(Context context) {
            super(context);

            setContentView(R.layout.add_tag_dialog);

            ivClose = findViewById(R.id.iv_close);
            btnNext = findViewById(R.id.btn_next);
            et_add_tag = findViewById(R.id.et_add_tag);


            setOnClickListener(ivClose, btnNext);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setContent(String content) {
            et_add_tag.setText(content);
            return this;
        }

        @SingleClick
        @Override
        public void onClick(View view) {
            if (view == btnNext) {
                if (mAutoDismiss) {
                    dismiss();
                }

                if (mListener == null) {
                    return;
                }
                mListener.onSelected(getDialog(), et_add_tag.getText().toString());
            } else if (view == ivClose) {
                if (mAutoDismiss) {
                    dismiss();
                }

                if (mListener == null) {
                    return;
                }
                mListener.onCancel(getDialog());
            }
        }


    }

    public interface OnListener {

        void onSelected(BaseDialog dialog, String str);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
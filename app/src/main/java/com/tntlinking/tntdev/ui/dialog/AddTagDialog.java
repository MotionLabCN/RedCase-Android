package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyLog;
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
        private final TextView tv_lengths;


        public Builder(Context context) {
            super(context);

            setContentView(R.layout.add_tag_dialog);
            ivClose = findViewById(R.id.iv_close);
            btnNext = findViewById(R.id.btn_next);
            et_add_tag = findViewById(R.id.et_add_tag);
            tv_lengths = findViewById(R.id.tv_lengths);

            et_add_tag.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int length = s.toString().length();
                    tv_lengths.setText(length + "/256");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
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
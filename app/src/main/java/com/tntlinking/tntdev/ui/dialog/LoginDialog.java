package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.manager.PickerLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


public final class LoginDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {
        private final TextView tv_phone;
        private final TextView tv_phone_service;
        private final AppCompatButton btn_to_login;
        private final AppCompatButton btn_to_other;
        private OnListener mListener;
        private boolean mAutoDismiss = true;


        public Builder(Context context) {
            super(context);

            setContentView(R.layout.login_dialog);

            tv_phone = findViewById(R.id.tv_phone);
            tv_phone_service = findViewById(R.id.tv_phone_service);
            btn_to_login = findViewById(R.id.btn_to_login);
            btn_to_other = findViewById(R.id.btn_to_other);


            setOnClickListener(btn_to_login, btn_to_other);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setPhone(String title) {
            tv_phone.setText(title);
            return this;
        }

        public Builder setPhoneService(String title) {
            tv_phone_service.setText(title);
            return this;
        }


        @SingleClick
        @Override
        public void onClick(View view) {
            if (view == btn_to_login) {
                if (mAutoDismiss) {
                    dismiss();
                }

                if (mListener == null) {
                    return;
                }
                mListener.onSelected(getDialog());
            } else if (view == btn_to_other) {
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

        void onSelected(BaseDialog dialog);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
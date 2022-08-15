package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.other.OnItemClickListener;
import com.tntlinking.tntdev.ui.firm.adapter.BottomPositionAdapter;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


public final class BottomListDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {
        private final ListView list_view;
        private final ImageView iv_close;
        private final TextView tv_name;
        private final TextView tv_company_name;
        private final AppCompatButton btn_commit;
        private BottomPositionAdapter mAdapter;
        private OnListener mListener;
        private boolean mAutoDismiss = true;
        private OnItemClickListener onItemClickListener;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.bottom_list_dialog);
            setHeight(getResources().getDisplayMetrics().heightPixels * 2 / 3);

            list_view = findViewById(R.id.list_view);
            iv_close = findViewById(R.id.iv_close);
            tv_name = findViewById(R.id.tv_name);
            tv_company_name = findViewById(R.id.tv_company_name);
            btn_commit = findViewById(R.id.btn_commit);
            mAdapter = new BottomPositionAdapter(context);
            list_view.setAdapter(mAdapter);
            setOnClickListener(iv_close, btn_commit);
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                    dismiss();
                }
            });
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setOnItemListener(OnItemClickListener listener) {
            onItemClickListener = listener;
            return this;
        }

        public BottomListDialog.Builder setData(List<GetFirmPositionApi.Bean.ListBean> str) {
            mAdapter.setData(str);
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
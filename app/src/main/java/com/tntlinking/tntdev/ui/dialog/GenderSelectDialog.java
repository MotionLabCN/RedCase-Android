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


public final class GenderSelectDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {
        private final AppCompatTextView tvTitle;
        private final AppCompatImageView ivClose;
        private final AppCompatButton btnNext;
//        private final TextView tvMan;
//        private final TextView tvWoman;
        private OnListener mListener;
        private boolean mAutoDismiss = true;
        private final RecyclerView mHourView;
        private final PickerAdapter mHourAdapter;

        private final PickerLayoutManager mHourManager;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.gender_select_dialog);

            tvTitle = findViewById(R.id.tv_title);
            ivClose = findViewById(R.id.iv_close);
            btnNext = findViewById(R.id.btn_next);
            mHourView = findViewById(R.id.rv_time_hour);
//            tvMan = findViewById(R.id.tv_man);
//            tvWoman = findViewById(R.id.tv_woman);

            mHourAdapter = new PickerAdapter(context);
            ArrayList<String> hourData = new ArrayList<>();
            hourData.add("男");
            hourData.add("女");
            mHourAdapter.setData(hourData);


            mHourManager = new PickerLayoutManager.Builder(context)
                    .build();


            mHourView.setLayoutManager(mHourManager);
            mHourView.setAdapter(mHourAdapter);

            mHourView.scrollToPosition(0);
            setOnClickListener(ivClose, btnNext);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setTitle(String title) {
            tvTitle.setText(title);
            return this;
        }

        public Builder setList(int... ids) {
            List<String> data = new ArrayList<>(ids.length);
            for (int id : ids) {
                data.add(getString(id));
            }
            return setList(data);
        }

        public Builder setList(String... data) {
            return setList(Arrays.asList(data));
        }

        @SuppressWarnings("all")
        public Builder setList(List data) {
            mHourAdapter.setData(data);
//            mHourView.addOnLayoutChangeListener(this);
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
                mListener.onSelected(getDialog(), mHourManager.getPickedPosition());
            }  else if (view == ivClose) {
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
    private static final class PickerAdapter extends AppAdapter<String> {

        private PickerAdapter(Context context) {
            super(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder();
        }

        private final class ViewHolder extends AppAdapter<?>.ViewHolder {

            private final TextView mPickerView;

            ViewHolder() {
                super(R.layout.picker_item);
                mPickerView = findViewById(R.id.tv_picker_name);
            }

            @Override
            public void onBindView(int position) {
                mPickerView.setText(getItem(position));
            }
        }
    }
    public interface OnListener {

        void onSelected(BaseDialog dialog, int type);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
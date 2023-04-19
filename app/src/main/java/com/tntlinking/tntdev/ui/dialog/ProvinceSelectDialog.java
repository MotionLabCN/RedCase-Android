package com.tntlinking.tntdev.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.manager.PickerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * desc   : 支付密码对话框
 */
public final class ProvinceSelectDialog {

    public static final class Builder extends BaseDialog.Builder<Builder>
            implements Runnable, PickerLayoutManager.OnPickerListener {
        private final AppCompatTextView tvTitle;
        private final RecyclerView mYearView;
        private final RecyclerView mMonthView;
        private final RecyclerView mDayView;

        private final PickerLayoutManager mYearManager;
        private final PickerLayoutManager mMonthManager;
        private final PickerLayoutManager mDayManager;

        private final PickerAdapter1 mYearAdapter1;
        private final PickerAdapter2 mMonthAdapter2;
        private final PickerAdapter3 mDayAdapter3;

        private final AppCompatImageView ivClose;
        private final AppCompatButton btnNext;
        private OnListener mListener;
        private boolean mAutoDismiss = true;
        List<GetProvinceApi.ProvinceBean> list1 = new ArrayList<>();


        public Builder(Context context) {
            super(context);

            setContentView(R.layout.date_select_dialog);

            tvTitle = findViewById(R.id.tv_title);
            ivClose = findViewById(R.id.iv_close);
            btnNext = findViewById(R.id.btn_next);

            mYearView = findViewById(R.id.rv_date_year);
            mMonthView = findViewById(R.id.rv_date_month);
            mDayView = findViewById(R.id.rv_date_day);

            mYearAdapter1 = new PickerAdapter1(context);
            mMonthAdapter2 = new PickerAdapter2(context);
            mDayAdapter3 = new PickerAdapter3(context);


            setOnClickListener(ivClose, btnNext);

            String provinces = SPUtils.getInstance().getString("province", "");

            List<GetProvinceApi.ProvinceBean> provinceList = GsonUtils.fromJson(provinces, new TypeToken<List<GetProvinceApi.ProvinceBean>>() {
            }.getType());

            list1 = new ArrayList<>();
            list1.addAll(provinceList);
            if (!TextUtils.isEmpty(provinces)) {
                mYearAdapter1.setData(list1);
                mMonthAdapter2.setData(list1.get(0).getChildren());
                mDayAdapter3.setData(list1.get(0).getChildren().get(0).getChildren());
            }
            mYearManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();

            mMonthManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();
            mDayManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();

            mYearView.setLayoutManager(mYearManager);
            mMonthView.setLayoutManager(mMonthManager);
            mDayView.setLayoutManager(mDayManager);

            mYearView.setAdapter(mYearAdapter1);
            mMonthView.setAdapter(mMonthAdapter2);
            mDayView.setAdapter(mDayAdapter3);


            mYearManager.setOnPickerListener(this);
            mMonthManager.setOnPickerListener(this);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }
        public Builder setTitle(String title) {
            tvTitle.setText(title);
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
                mListener.onSelected(getDialog(),
                        mYearAdapter1.getData().get(mYearManager.getPickedPosition()),
                        mMonthAdapter2.getData().get(mMonthManager.getPickedPosition()),
                        mDayAdapter3.getData().get(mDayManager.getPickedPosition()));
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

        /**
         * {@link PickerLayoutManager.OnPickerListener}
         *
         * @param recyclerView RecyclerView 对象
         * @param position     当前滚动的位置
         */
        private int provinceIndex = 0;
        private int areaIndex = 0;

        @Override
        public void onPicked(RecyclerView recyclerView, int position) {
            refreshMonthMaximumDay();
            if (recyclerView == mYearView) {
                provinceIndex = position;
                mMonthAdapter2.setData(list1.get(provinceIndex).getChildren());
                mDayAdapter3.setData(list1.get(provinceIndex).getChildren().get(0).getChildren());
            } else if (recyclerView == mMonthView) {
                areaIndex = position;
                mDayAdapter3.setData(list1.get(provinceIndex).getChildren().get(areaIndex).getChildren());
            } else if (recyclerView == mDayView) {

            }

        }

        @Override
        public void run() {
            // 获取这个月最多有多少天
//            Calendar calendar = Calendar.getInstance(Locale.CHINA);
//            calendar.set(mStartYear + mYearManager.getPickedPosition(), mMonthManager.getPickedPosition(), 1);
//
//            int day = calendar.getActualMaximum(Calendar.DATE);
//            if (mDayAdapter.getCount() != day) {
//                ArrayList<String> dayData = new ArrayList<>(day);
//                for (int i = 1; i <= day; i++) {
//                    dayData.add(i + " " + getString(R.string.common_day));
//                }
//                mDayAdapter.setData(dayData);
//            }
        }

        /**
         * 刷新每个月天最大天数
         */
        private void refreshMonthMaximumDay() {
            mYearView.removeCallbacks(this);
            mYearView.post(this);
        }

        private static final class PickerAdapter1 extends AppAdapter<GetProvinceApi.ProvinceBean> {

            private PickerAdapter1(Context context) {
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
                    mPickerView.setText(getItem(position).getRegionName());
                }
            }
        }

        private static final class PickerAdapter2 extends AppAdapter<GetProvinceApi.AreaBean> {

            private PickerAdapter2(Context context) {
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
                    mPickerView.setText(getItem(position).getRegionName());
                }
            }
        }

        private static final class PickerAdapter3 extends AppAdapter<GetProvinceApi.CityBean> {

            private PickerAdapter3(Context context) {
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
                    mPickerView.setText(getItem(position).getRegionName());
                }
            }
        }
    }

    public interface OnListener {

        /**
         * @param dialog
         * @param province
         * @param area
         * @param city
         */
        void onSelected(BaseDialog dialog, GetProvinceApi.ProvinceBean province, GetProvinceApi.AreaBean area, GetProvinceApi.CityBean city);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
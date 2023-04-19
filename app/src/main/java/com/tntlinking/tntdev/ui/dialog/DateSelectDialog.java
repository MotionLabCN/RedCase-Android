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
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * desc   : 支付密码对话框
 */
public final class DateSelectDialog {

    public static final class Builder extends BaseDialog.Builder<Builder>
            implements Runnable, PickerLayoutManager.OnPickerListener {

        private final int mStartYear;
        private TextView tv_title;

        private final RecyclerView mYearView;
        private final RecyclerView mMonthView;
        private final RecyclerView mDayView;

        private final PickerLayoutManager mYearManager;
        private final PickerLayoutManager mMonthManager;
        private final PickerLayoutManager mDayManager;

        private final PickerAdapter mYearAdapter;
        private final PickerAdapter mMonthAdapter;
        private final PickerAdapter mDayAdapter;

        private final AppCompatImageView ivClose;
        private final AppCompatButton btnNext;
        private OnListener mListener;
        private boolean mAutoDismiss = true;

        public Builder(Context context) {
            this(context, Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR) - 65);
        }

        public Builder(Context context, int startYear) {
            this(context, startYear, Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR) + 35);
        }

        public Builder(Context context, int startYear, int endYear) {
            super(context);
            mStartYear = startYear;

            setContentView(R.layout.date_select_dialog);


            tv_title = findViewById(R.id.tv_title);
            ivClose = findViewById(R.id.iv_close);
            btnNext = findViewById(R.id.btn_next);

            mYearView = findViewById(R.id.rv_date_year);
            mMonthView = findViewById(R.id.rv_date_month);
            mDayView = findViewById(R.id.rv_date_day);

            mYearAdapter = new PickerAdapter(context);
            mMonthAdapter = new PickerAdapter(context);
            mDayAdapter = new PickerAdapter(context);


            setOnClickListener(ivClose, btnNext);
            // 生产年份
            ArrayList<String> yearData = new ArrayList<>(10);
            for (int i = mStartYear; i <= endYear; i++) {
                yearData.add(i + " " + getString(R.string.common_year));
            }

            // 生产月份
            ArrayList<String> monthData = new ArrayList<>(12);
            for (int i = 1; i <= 12; i++) {
                monthData.add(i + " " + getString(R.string.common_month));
            }

            Calendar calendar = Calendar.getInstance(Locale.CHINA);

            int day = calendar.getActualMaximum(Calendar.DATE);
            // 生产天数
            ArrayList<String> dayData = new ArrayList<>(day);
            for (int i = 1; i <= day; i++) {
                dayData.add(i + " " + getString(R.string.common_day));
            }

            mYearAdapter.setData(yearData);
            mMonthAdapter.setData(monthData);
            mDayAdapter.setData(dayData);

            mYearManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();

            mMonthManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();
            mDayManager = new PickerLayoutManager.Builder(context).setMaxItem(3)
                    .build();

            mYearView.setLayoutManager(mYearManager);
            mMonthView.setLayoutManager(mMonthManager);
            mDayView.setLayoutManager(mDayManager);

            mYearView.setAdapter(mYearAdapter);
            mMonthView.setAdapter(mMonthAdapter);
            mDayView.setAdapter(mDayAdapter);

            setYear(calendar.get(Calendar.YEAR));
            setMonth(calendar.get(Calendar.MONTH) + 1);
            setDay(calendar.get(Calendar.DAY_OF_MONTH));

            mYearManager.setOnPickerListener(this);
            mMonthManager.setOnPickerListener(this);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 不选择天数
         */
        public Builder setIgnoreDay() {
            mDayView.setVisibility(View.GONE);
            return this;
        }

        public Builder setDate(String date) {
            if (date.matches("\\d{8}")) {
                // 20190519
                setYear(date.substring(0, 4));
                setMonth(date.substring(4, 6));
                setDay(date.substring(6, 8));
            } else if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // 2019-05-19
                setYear(date.substring(0, 4));
                setMonth(date.substring(5, 7));
                setDay(date.substring(8, 10));
            }
            return this;
        }

        public Builder setYear(String year) {
            return setYear(Integer.parseInt(year));
        }

        public Builder setYear(int year) {
            int index = year - mStartYear;
            if (index < 0) {
                index = 0;
            } else if (index > mYearAdapter.getCount() - 1) {
                index = mYearAdapter.getCount() - 1;
            }
            mYearView.scrollToPosition(index);
            refreshMonthMaximumDay();
            return this;
        }

        public Builder setMonth(String month) {
            return setMonth(Integer.parseInt(month));
        }

        public Builder setMonth(int month) {
            int index = month - 1;
            if (index < 0) {
                index = 0;
            } else if (index > mMonthAdapter.getCount() - 1) {
                index = mMonthAdapter.getCount() - 1;
            }
            mMonthView.scrollToPosition(index);
            refreshMonthMaximumDay();
            return this;
        }

        public Builder setDay(String day) {
            return setDay(Integer.parseInt(day));
        }

        public Builder setDay(int day) {
            int index = day - 1;
            if (index < 0) {
                index = 0;
            } else if (index > mDayAdapter.getCount() - 1) {
                index = mDayAdapter.getCount() - 1;
            }
            mDayView.scrollToPosition(index);
            refreshMonthMaximumDay();
            return this;
        }

        public Builder setTitle(String str) {
            tv_title.setText(str);
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
                        mStartYear + mYearManager.getPickedPosition(),
                        mMonthManager.getPickedPosition() + 1,
                        mDayManager.getPickedPosition() + 1);
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
        @Override
        public void onPicked(RecyclerView recyclerView, int position) {
            refreshMonthMaximumDay();
        }

        @Override
        public void run() {
            // 获取这个月最多有多少天
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.set(mStartYear + mYearManager.getPickedPosition(), mMonthManager.getPickedPosition(), 1);

            int day = calendar.getActualMaximum(Calendar.DATE);
            if (mDayAdapter.getCount() != day) {
                ArrayList<String> dayData = new ArrayList<>(day);
                for (int i = 1; i <= day; i++) {
                    dayData.add(i + " " + getString(R.string.common_day));
                }
                mDayAdapter.setData(dayData);
            }
        }

        /**
         * 刷新每个月天最大天数
         */
        private void refreshMonthMaximumDay() {
            mYearView.removeCallbacks(this);
            mYearView.post(this);
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
    }

    public interface OnListener {

        /**
         * 选择完日期后回调
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void onSelected(BaseDialog dialog, int year, int month, int day);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }
    }
}
package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetDailyListApi;

import androidx.annotation.NonNull;


public final class WriteDailyAdapter extends AppAdapter<GetDailyListApi.ListBean> {

    public static final int TAB_FINISHED = 1; // 已完成
    public static final int ADD_DOING = 2;// 进心中
    public static final int ADD_TOMORROW = 3;// 明日计划
    public static final int ADD_HELP = 4;// 需要的帮助
    public static final int ADD_NORMAL = 5;// 通用


    private Context mContext;

    public WriteDailyAdapter(Context context) {

        super(context);
        this.mContext = context;
    }


    @Override
    public int getItemViewType(int position) {

        return getData().get(position).getType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TAB_FINISHED:
                return new Tab1ViewHolder();
            case ADD_DOING:
                return new Tab2ViewHolder();
            case ADD_TOMORROW:
                return new Tab3ViewHolder();
            case ADD_HELP:
                return new Tab4ViewHolder();
            case ADD_NORMAL:
                return new AddEducationViewHolder();
            default:
                throw new IllegalArgumentException("are you ok?");
        }
    }


    private final class Tab1ViewHolder extends ViewHolder {
        private final TextView tv_tab_left;
        private final ImageView iv_tab_icon;

        private Tab1ViewHolder() {
            super(R.layout.item_tab_write_daily);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            iv_tab_icon = findViewById(R.id.iv_tab_icon);


        }

        @Override
        public void onBindView(int position) {
//            mTitleView.setText(getItem(position).getStr());
            tv_tab_left.setText("已完成");
            iv_tab_icon.setImageResource(R.drawable.icon_done);
        }
    }

    private final class Tab2ViewHolder extends ViewHolder {
        private final TextView tv_tab_left;
        private final ImageView iv_tab_icon;


        private Tab2ViewHolder() {
            super(R.layout.item_tab_write_daily);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            iv_tab_icon = findViewById(R.id.iv_tab_icon);

        }

        @Override
        public void onBindView(int position) {
            tv_tab_left.setText("进行中");
            iv_tab_icon.setImageResource(R.drawable.icon_running);

        }

    }

    private final class Tab3ViewHolder extends ViewHolder {
        private final TextView tv_tab_left;
        private final ImageView iv_tab_icon;


        private Tab3ViewHolder() {
            super(R.layout.item_tab_write_daily);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            iv_tab_icon = findViewById(R.id.iv_tab_icon);

        }

        @Override
        public void onBindView(int position) {
            tv_tab_left.setText("明日计划");
            iv_tab_icon.setImageResource(R.drawable.icon_running);

        }

    }

    private final class Tab4ViewHolder extends ViewHolder {
        private final TextView tv_tab_left;
        private final ImageView iv_tab_icon;


        private Tab4ViewHolder() {
            super(R.layout.item_tab_write_daily);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            iv_tab_icon = findViewById(R.id.iv_tab_icon);

        }

        @Override
        public void onBindView(int position) {
//            mTitleView.setText(getItem(position).getStr());
            tv_tab_left.setText("需要的帮助");
            iv_tab_icon.setImageResource(R.drawable.iocn_help);

        }

    }


    private final class AddEducationViewHolder extends ViewHolder {

        private final TextView tv_tab_left;


        private AddEducationViewHolder() {
            super(R.layout.item_write_daily);
            tv_tab_left = findViewById(R.id.tv_tab_left);



        }

        @Override
        public void onBindView(int position) {
            GetDailyListApi.ListBean item = getItem(position);
            tv_tab_left.setText(item.getItem());

        }
    }

}
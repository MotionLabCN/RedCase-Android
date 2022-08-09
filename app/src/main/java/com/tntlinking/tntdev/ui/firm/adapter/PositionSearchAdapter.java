package com.tntlinking.tntdev.ui.firm.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetFirmDevApi;
import com.tntlinking.tntdev.other.GlideUtils;

import androidx.annotation.NonNull;


public final class PositionSearchAdapter extends AppAdapter<GetFirmDevApi.Bean.ListBean> {

    public PositionSearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final ImageView iv_avatar;
        private final TextView tv_name;
        private final TextView tv_position;
        private final TextView tv_all_day;
        private final TextView tv_salary;


        private ViewHolder() {
            super(R.layout.item_firm_home);
            iv_avatar = findViewById(R.id.iv_avatar);
            tv_name = findViewById(R.id.tv_name);
            tv_position = findViewById(R.id.tv_position);
            tv_all_day = findViewById(R.id.tv_all_day);
            tv_salary = findViewById(R.id.tv_salary);
        }

        @Override
        public void onBindView(int position) {
            GetFirmDevApi.Bean.ListBean item = getItem(position);


            GlideUtils.loadRoundCorners(getContext(), item.getAvatarUrl(), iv_avatar,
                    (int) getContext().getResources().getDimension(R.dimen.dp_8));
            tv_name.setText(item.getRealName());
            tv_all_day.setText(item.getWorkDayModeName());
            tv_salary.setText(item.getExpectSalary() + "");
            tv_position.setText(item.getCareerDirectionName() + "·工作经验" + item.getWorkYearsName());
        }
    }
}
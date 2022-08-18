package com.tntlinking.tntdev.ui.firm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetFirmDevApi;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.Arrays;
import java.util.List;

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
        private final FlowTagLayout tag_flow_layout;

        private ViewHolder() {
            super(R.layout.item_firm_home);
            iv_avatar = findViewById(R.id.iv_avatar);
            tv_name = findViewById(R.id.tv_name);
            tv_position = findViewById(R.id.tv_position);
            tv_all_day = findViewById(R.id.tv_all_day);
            tv_salary = findViewById(R.id.tv_salary);
            tag_flow_layout = findViewById(R.id.tag_flow_layout);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindView(int position) {
            GetFirmDevApi.Bean.ListBean item = getItem(position);


            GlideUtils.loadRoundCorners(getContext(), item.getAvatarUrl(), iv_avatar,
                    (int) getContext().getResources().getDimension(R.dimen.dp_8));
            tv_name.setText(item.getRealName());
            tv_all_day.setText(item.getWorkDayModeName());
            tv_salary.setText((Utils.formatMoney(item.getExpectSalary() / 1000) + "k/月"));
            tv_position.setText(item.getCareerDirectionName() + "·工作经验" + item.getWorkYearsName());

            TagFirmAdapter adapter = new TagFirmAdapter(getContext(), 2);
            tag_flow_layout.setAdapter(adapter);
            if (item.getSkillName().contains(",")) {
                String[] split = item.getSkillName().split(",");
                List<String> strings = Arrays.asList(split);
                if (strings.size() > 4) {
                    adapter.onlyAddAll(strings.subList(0, 4));
                } else {
                    adapter.onlyAddAll(strings);
                }

            } else {
                adapter.onlyAddAll(Arrays.asList(item.getSkillName()));
            }
        }
    }
}
package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.List;

import androidx.annotation.NonNull;


public final class SkillTagAdapter extends AppAdapter<GetTagListApi.Bean> {


    /** 当前选中条目位置 */
    private int mSelectedPosition = 0;

    private Context mContext;
    public SkillTagAdapter(Context context) {
        super(context);
        this.mContext =context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SkillViewHolder();
    }





    private final class SkillViewHolder extends ViewHolder {

        private final TextView tag_title;
        private final FlowTagLayout tags_flow_layout;

        private SkillViewHolder() {
            super(R.layout.item_skill_tag);
            tag_title = findViewById(R.id.tag_title);
            tags_flow_layout = findViewById(R.id.tags_flow_layout);
            tags_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        }

        @Override
        public void onBindView(int position) {

            tag_title.setText(getItem(position).getSkillName());
//            tv_tab_right.setText("添加教育经历");
            List<GetTagListApi.Bean.ChildrenBean> children = getItem(position).getChildren();
            TagAdapter<String> tagAdapter = new TagAdapter(mContext);

            tags_flow_layout.setAdapter(tagAdapter);
            tagAdapter.onlyAddAll(children);
            tags_flow_layout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

                }
            });
        }
    }



}
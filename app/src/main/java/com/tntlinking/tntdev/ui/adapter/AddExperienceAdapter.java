package com.tntlinking.tntdev.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppAdapter;
import com.tntlinking.tntdev.ui.bean.ExperienceBean;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import androidx.annotation.NonNull;


public final class AddExperienceAdapter extends AppAdapter<ExperienceBean> {

    public static final int ADD_EDUCATION_TAB = 1;
    public static final int ADD_PROJECT_TAB = 2;
    public static final int ADD_EDUCATION = 3;
    public static final int ADD_PROJECT = 4;


//    public static final int ADD_EDUCATION_TAB = 1;
//    public static final int ADD_EDUCATION = 2;
//    public static final int ADD_PROJECT_TAB = 3;
//    public static final int ADD_PROJECT = 4;
    /**
     * 当前选中条目位置
     */
    private int mSelectedPosition = 0;

    private Context mContext;

    public AddExperienceAdapter(Context context) {
//        this(context, TAB_MODE_DESIGN, true);
        super(context);
        this.mContext = context;
    }

//    public AddExpericenAdapter(Context context, int tabMode, boolean fixed) {
//        super(context);
//        mTabMode = tabMode;
//        mFixed = fixed;
//        setOnItemClickListener(this);
//        registerAdapterDataObserver(new TabAdapterDataObserver());
//    }

    @Override
    public int getItemViewType(int position) {

        return getData().get(position).getType();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ADD_EDUCATION_TAB:
                return new Tab1ViewHolder();
            case ADD_PROJECT_TAB:
                return new Tab2ViewHolder();
            case ADD_EDUCATION:
                return new AddEducationViewHolder();
            case ADD_PROJECT:
                return new AddProjectViewHolder();
            default:
                throw new IllegalArgumentException("are you ok?");
        }
    }


    private final class Tab1ViewHolder extends ViewHolder {

        private final TextView tv_tab_left;
        private final TextView tv_tab_right;

        private Tab1ViewHolder() {
            super(R.layout.item_tab_add_education);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            tv_tab_right = findViewById(R.id.tv_tab_right);

        }

        @Override
        public void onBindView(int position) {
//            mTitleView.setText(getItem(position).getStr());
            tv_tab_left.setText("教育经历");
            tv_tab_right.setText("添加教育经历");
        }
    }

    private final class Tab2ViewHolder extends ViewHolder {


        private final TextView tv_tab_left;
        private final TextView tv_tab_right;

        private Tab2ViewHolder() {
            super(R.layout.item_tab_add_education);
            tv_tab_left = findViewById(R.id.tv_tab_left);
            tv_tab_right = findViewById(R.id.tv_tab_right);


        }

        @Override
        public void onBindView(int position) {
//            mTitleView.setText(getItem(position).getStr());
            tv_tab_left.setText("项目经历");
            tv_tab_right.setText("添加项目");

        }

    }


    private final class AddEducationViewHolder extends ViewHolder {

        private final TextView tv_school_name;
        private final TextView tv_school_time;
        private final TextView tv_school_info;

        private AddEducationViewHolder() {
            super(R.layout.item_tab_education);
            tv_school_name = findViewById(R.id.tv_school_name);
            tv_school_time = findViewById(R.id.tv_school_time);
            tv_school_info = findViewById(R.id.tv_school_info);


        }

        @Override
        public void onBindView(int position) {
            ExperienceBean item = getItem(position);
            tv_school_name.setText(item.getCollegeName());
            String time = item.getInSchoolStartTime() + " - " + item.getInSchoolEndTime();
            tv_school_time.setText(time);
            String info = item.getEducation() + " | " + item.getmTrainingMode() + " | " + item.getMajor();
            tv_school_info.setText(info);

        }
    }

    private final class AddProjectViewHolder extends ViewHolder {

        private final TextView tv_project_name;
        private final TextView tv_project_time;
        private final TextView tv_character;
        private final TextView tv_company_name;
        private final TextView tv_description;
        private final FlowTagLayout tag_flow_layout;

        private AddProjectViewHolder() {
            super(R.layout.item_tab_project);
            tv_project_name = findViewById(R.id.tv_project_name);
            tv_project_time = findViewById(R.id.tv_project_time);
            tv_character = findViewById(R.id.tv_character);
            tv_company_name = findViewById(R.id.tv_company_name);
            tv_description = findViewById(R.id.tv_description);
            tag_flow_layout = findViewById(R.id.tag_flow_layout);


        }

        @Override
        public void onBindView(int position) {
            ExperienceBean item = getItem(position);

            tv_project_name.setText(item.getProjectName());
            String time = item.getProjectStartDate() + " - " + item.getProjectEndDate();
            String character = item.getPosition() + " | " + item.getmWorkMode();
            String company = item.getCompanyName() + " | " + item.getIndustry();
            tv_project_time.setText(time);
            tv_character.setText(character);
            tv_company_name.setText(company);
            tv_description.setText(item.getDescription());

            TagAdapter adapter = new TagAdapter(mContext);
            tag_flow_layout.setAdapter(adapter);
            adapter.onlyAddAll(item.getSkillList());

        }
    }

}
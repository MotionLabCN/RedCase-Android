package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AddTagApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.adapter.SkillTagAdapter;
import com.tntlinking.tntdev.ui.adapter.TagAdapter;
import com.tntlinking.tntdev.ui.adapter.TagSelectedAdapter;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 添加技能标签页面
 */
public final class AddProjectTagActivityNew extends AppActivity {

    private TextView tv_skill_tag_1;
    private TextView tv_skill_tag_2;
    private TextView tv_skill_tag_3;
    private TextView tv_skill_tag_4;
    private TextView tv_skill_tag_5;
    private FlowTagLayout fl_skill_tag_select;
    private FlowTagLayout fl_skill_tag_1;
    private FlowTagLayout fl_skill_tag_2;
    private FlowTagLayout fl_skill_tag_3;
    private FlowTagLayout fl_skill_tag_4;
    private FlowTagLayout fl_skill_tag_5;
    private AppCompatButton btn_next;
    private TagSelectedAdapter<String> mTagAdapterSelect;
    private TagAdapter<String> mTagAdapter1;
    private TagAdapter<String> mTagAdapter2;
    private TagAdapter<String> mTagAdapter3;
    private TagAdapter<String> mTagAdapter4;
    private TagAdapter<String> mTagAdapter5;

    private LinearLayout ll_skill_tag_1;
    private LinearLayout ll_skill_tag_2;
    private LinearLayout ll_skill_tag_3;
    private LinearLayout ll_skill_tag_4;
    private LinearLayout ll_skill_tag_5;
    private LinearLayout ll_search;

    private SkillTagAdapter mAdapter;
    private RecyclerView mTagRV;
    private List<GetTagListApi.Bean.ChildrenBean> mSelectList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.add_project_tag_activity;
    }

    @Override
    protected void initView() {
        ll_search = findViewById(R.id.ll_search);
        tv_skill_tag_1 = findViewById(R.id.tv_skill_tag_1);
        tv_skill_tag_2 = findViewById(R.id.tv_skill_tag_2);
        tv_skill_tag_3 = findViewById(R.id.tv_skill_tag_3);
        tv_skill_tag_4 = findViewById(R.id.tv_skill_tag_4);
        tv_skill_tag_5 = findViewById(R.id.tv_skill_tag_5);

        fl_skill_tag_select = findViewById(R.id.fl_skill_tag_select);
        fl_skill_tag_1 = findViewById(R.id.fl_skill_tag_1);
        fl_skill_tag_2 = findViewById(R.id.fl_skill_tag_2);
        fl_skill_tag_3 = findViewById(R.id.fl_skill_tag_3);
        fl_skill_tag_4 = findViewById(R.id.fl_skill_tag_4);
        fl_skill_tag_5 = findViewById(R.id.fl_skill_tag_5);

        ll_skill_tag_1 = findViewById(R.id.ll_skill_tag_1);
        ll_skill_tag_2 = findViewById(R.id.ll_skill_tag_2);
        ll_skill_tag_3 = findViewById(R.id.ll_skill_tag_3);
        ll_skill_tag_4 = findViewById(R.id.ll_skill_tag_4);
        ll_skill_tag_5 = findViewById(R.id.ll_skill_tag_5);
        btn_next = findViewById(R.id.btn_next);

        setOnClickListener(btn_next, ll_search);
//        mTagRV = findViewById(R.id.rv_skill_tag);
//        mAdapter = new SkillTagAdapter(this);
//        mTagRV.setAdapter(mAdapter);

        mTagAdapterSelect = new TagSelectedAdapter<>(this);
        fl_skill_tag_select.setAdapter(mTagAdapterSelect);
        fl_skill_tag_select.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {

                GetTagListApi.Bean.ChildrenBean childrenBean = mSelectList.get(position);
                if (childrenBean.getType() == 1) {
                    for (int i = 0; i < mTagAdapter1.getData().size(); i++) {
                        if (mTagAdapter1.getData().get(i).getSkillName().equals(mSelectList.get(position).getSkillName())) {
                            fl_skill_tag_1.getChildAt(i).setSelected(false);
                            continue;
                        }
                    }
                } else if (childrenBean.getType() == 2) {
                    for (int i = 0; i < mTagAdapter2.getData().size(); i++) {
                        if (mTagAdapter2.getData().get(i).getSkillName().equals(mSelectList.get(position).getSkillName())) {
                            fl_skill_tag_2.getChildAt(i).setSelected(false);
                            continue;
                        }
                    }
                } else if (childrenBean.getType() == 3) {
                    for (int i = 0; i < mTagAdapter3.getData().size(); i++) {
                        if (mTagAdapter3.getData().get(i).getSkillName().equals(mSelectList.get(position).getSkillName())) {
                            fl_skill_tag_3.getChildAt(i).setSelected(false);
                            continue;
                        }
                    }
                } else if (childrenBean.getType() == 4) {
                    for (int i = 0; i < mTagAdapter4.getData().size(); i++) {
                        if (mTagAdapter4.getData().get(i).getSkillName().equals(mSelectList.get(position).getSkillName())) {
                            fl_skill_tag_4.getChildAt(i).setSelected(false);
                            continue;
                        }
                    }
                } else if (childrenBean.getType() == 5) {
                    for (int i = 0; i < mTagAdapter5.getData().size(); i++) {
                        if (mTagAdapter5.getData().get(i).getSkillName().equals(mSelectList.get(position).getSkillName())) {
                            fl_skill_tag_5.getChildAt(i).setSelected(false);
                            continue;
                        }
                    }
                }
                mSelectList.remove(position);
                mTagAdapterSelect.onlyAddAll(mSelectList);

            }
        });


        mTagAdapter1 = new TagAdapter<>(this);
        fl_skill_tag_1.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        fl_skill_tag_1.setAdapter(mTagAdapter1);
        fl_skill_tag_1.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(position);
                childrenBean.setType(1);
                int pos = positionList(mSelectList, childrenBean);
                if (view.isSelected()) {
                    mSelectList.add(childrenBean);
                } else {
//                    mSelectList.remove(childrenBean);
                    mSelectList.remove(pos);
                }

                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        });


        mTagAdapter2 = new TagAdapter<>(this);
        fl_skill_tag_2.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        fl_skill_tag_2.setAdapter(mTagAdapter2);
        fl_skill_tag_2.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter2.getData().get(position);
                childrenBean.setType(2);
                int pos = positionList(mSelectList, childrenBean);
                if (view.isSelected()) {
                    mSelectList.add(childrenBean);
                } else {
//                    mSelectList.remove(childrenBean);
                    mSelectList.remove(pos);
                }

                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        });
//        fl_skill_tag_2.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//
//            }
//        });

        //移动研发标签
        mTagAdapter3 = new TagAdapter<>(this);
        fl_skill_tag_3.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        fl_skill_tag_3.setAdapter(mTagAdapter3);
        fl_skill_tag_3.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter3.getData().get(position);
                childrenBean.setType(3);
                int pos = positionList(mSelectList, childrenBean);
                if (view.isSelected()) {
                    mSelectList.add(childrenBean);
                } else {
//                    mSelectList.remove(childrenBean);
                    mSelectList.remove(pos);
                }

                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        });
//        fl_skill_tag_3.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//
//            }
//        });

        //移动研发标签
        mTagAdapter4 = new TagAdapter<>(this);
        fl_skill_tag_4.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        fl_skill_tag_4.setAdapter(mTagAdapter4);
        fl_skill_tag_4.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter4.getData().get(position);
                childrenBean.setType(4);
                int pos = positionList(mSelectList, childrenBean);
                if (view.isSelected()) {
                    mSelectList.add(childrenBean);
                } else {
//                    mSelectList.remove(childrenBean);
                    mSelectList.remove(pos);
                }

                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        });
//        fl_skill_tag_4.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
//            @Override
//            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//
//            }
//        });
        //移动研发标签
        mTagAdapter5 = new TagAdapter<>(this);
        fl_skill_tag_5.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        fl_skill_tag_5.setAdapter(mTagAdapter5);
        fl_skill_tag_5.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter5.getData().get(position);
                childrenBean.setType(5);
                int pos = positionList(mSelectList, childrenBean);
                if (view.isSelected()) {
                    mSelectList.add(childrenBean);
                } else {
//                    mSelectList.remove(childrenBean);
                    mSelectList.remove(pos);
                }

                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        });

        //清除所有已经被选择的选项
//        findViewById(R.id.bt_clear_all)
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        mColorFlowTagLayout.clearAllOption();
//                        mSizeFlowTagLayout.clearAllOption();
//                        mMobileFlowTagLayout.clearAllOption();
//                    }
//                });
    }


    @Override
    protected void initData() {
//        int careerDirectionId = SendDeveloperBean.getSingleton().getCareerDirectionId();
        int careerDirectionId = SPUtils.getInstance().getInt(AppConfig.CAREER_ID, 163);
        getTagList(careerDirectionId+"");

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
//                new AddTagDialog.Builder(this).setListener(new AddTagDialog.OnListener() {
//                    @Override
//                    public void onSelected(BaseDialog dialog, String type) {
//
//                        addTag(type);
//                    }
//                }).show();
                if (mSelectList.size() == 0) {
                    toast("你还没有选择标签");
                    return;
                }
                setResult(RESULT_OK, new Intent().putExtra("list", (Serializable) mSelectList));
                finish();
                break;
            case R.id.ll_search:

//                mallList.addAll(mTagAdapter1.getData());
//                mallList.addAll(mTagAdapter2.getData());
//                mallList.addAll(mTagAdapter3.getData());
//                mallList.addAll(mTagAdapter4.getData());
//                mallList.addAll(mTagAdapter5.getData());
                Intent intents = new Intent(AddProjectTagActivityNew.this, AddTagSearchActivity.class);
                intents.putExtra("searchList", (Serializable) mallList);
                getActivity().startActivityForResult(intents, 1001);
                break;


        }
    }

    private List<GetTagListApi.Bean.ChildrenBean> mList1 = new ArrayList<>();
    private List<GetTagListApi.Bean.ChildrenBean> mallList = new ArrayList<>();
    private int mListSize = 0;//返回标签list 个数

    private void getTagList(String careerId) {
        List<GetTagListApi.Bean.ChildrenBean> list = getSerializable("list");
        if (list != null) {
            mSelectList.addAll(list);
//            mTagAdapterSelect.onlyAddAll(mSelectList);
        }

        EasyLog.print("===mSelectList===" + GsonUtils.toJson(list));

        EasyHttp.get(this)
                .api(new GetTagListApi().setCareerDirectionId(careerId))
                .request(new HttpCallback<HttpData<List<GetTagListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetTagListApi.Bean>> data) {
                        List<GetTagListApi.Bean> dataBean = data.getData();
                        for (GetTagListApi.Bean i : dataBean) {
                            mallList.addAll(i.getChildren());
                        }
                        mListSize = dataBean.size();
                        if (dataBean.size() == 1) {
                            ll_skill_tag_1.setVisibility(View.VISIBLE);
                            ll_skill_tag_2.setVisibility(View.GONE);
                            ll_skill_tag_3.setVisibility(View.GONE);
                            ll_skill_tag_4.setVisibility(View.GONE);
                            ll_skill_tag_5.setVisibility(View.GONE);
                            tv_skill_tag_1.setText(dataBean.get(0).getSkillName());
                            mTagAdapter1.onlyAddAll(dataBean.get(0).getChildren());

                            if (mSelectList != null) {
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (mSelectList.get(i).getType() == 1) {
                                        for (int j = 0; j < dataBean.get(0).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(0).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_1.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(0).getChildren().get(j);
                                                childrenBean.setType(1);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    }else if (mSelectList.get(i).getType() == 0) {
                                        int position1 = positionList(dataBean.get(0).getChildren(), mSelectList.get(i));
                                        if (position1 != -1) {
                                            fl_skill_tag_1.getChildAt(position1).setSelected(true);
                                            mSelectList.get(i).setType(1);
                                            mList1.add(mSelectList.get(i));
                                        }

                                    }

                                }

                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (!isInList(mallList, mSelectList.get(i))) {
                                        mList1.add(mSelectList.get(i));
                                    }
                                }

                                mSelectList.clear();
                                mSelectList.addAll(mList1);
                                mTagAdapterSelect.onlyAddAll(mSelectList);
                            }
                        } else if (dataBean.size() == 2) {
                            ll_skill_tag_1.setVisibility(View.VISIBLE);
                            ll_skill_tag_2.setVisibility(View.VISIBLE);
                            ll_skill_tag_3.setVisibility(View.GONE);
                            ll_skill_tag_4.setVisibility(View.GONE);
                            ll_skill_tag_5.setVisibility(View.GONE);
                            tv_skill_tag_1.setText(dataBean.get(0).getSkillName());
                            tv_skill_tag_2.setText(dataBean.get(1).getSkillName());
                            mTagAdapter1.onlyAddAll(data.getData().get(0).getChildren());
                            mTagAdapter2.onlyAddAll(data.getData().get(1).getChildren());

                            if (mSelectList != null) {
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (mSelectList.get(i).getType() == 1) {
                                        for (int j = 0; j < dataBean.get(0).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(0).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_1.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(0).getChildren().get(j);
                                                childrenBean.setType(1);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 2) {
                                        for (int j = 0; j < dataBean.get(1).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(1).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_2.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(1).getChildren().get(j);
                                                childrenBean.setType(2);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    }else if (mSelectList.get(i).getType() == 0) {
                                        int position1 = positionList(dataBean.get(0).getChildren(), mSelectList.get(i));
                                        if (position1 != -1) {
                                            fl_skill_tag_1.getChildAt(position1).setSelected(true);
                                            mSelectList.get(i).setType(1);
                                            mList1.add(mSelectList.get(i));
                                        }

                                        int position2 = positionList(dataBean.get(1).getChildren(), mSelectList.get(i));
                                        if (position2 != -1) {
                                            fl_skill_tag_2.getChildAt(position2).setSelected(true);
                                            mSelectList.get(i).setType(2);
                                            mList1.add(mSelectList.get(i));
                                        }

                                    }

                                }

                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (!isInList(mallList, mSelectList.get(i))) {
                                        mList1.add(mSelectList.get(i));
                                    }
                                }

                                mSelectList.clear();
                                mSelectList.addAll(mList1);
                                mTagAdapterSelect.onlyAddAll(mSelectList);
                            }
                        } else if (dataBean.size() == 3) {
                            ll_skill_tag_1.setVisibility(View.VISIBLE);
                            ll_skill_tag_2.setVisibility(View.VISIBLE);
                            ll_skill_tag_3.setVisibility(View.VISIBLE);
                            ll_skill_tag_4.setVisibility(View.GONE);
                            ll_skill_tag_5.setVisibility(View.GONE);
                            tv_skill_tag_1.setText(dataBean.get(0).getSkillName());
                            tv_skill_tag_2.setText(dataBean.get(1).getSkillName());
                            tv_skill_tag_3.setText(dataBean.get(2).getSkillName());
                            mTagAdapter1.onlyAddAll(data.getData().get(0).getChildren());
                            mTagAdapter2.onlyAddAll(data.getData().get(1).getChildren());
                            mTagAdapter3.onlyAddAll(data.getData().get(2).getChildren());

                            if (mSelectList != null) {
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (mSelectList.get(i).getType() == 1) {
                                        for (int j = 0; j < dataBean.get(0).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(0).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_1.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(0).getChildren().get(j);
                                                childrenBean.setType(1);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 2) {
                                        for (int j = 0; j < dataBean.get(1).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(1).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_2.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(1).getChildren().get(j);
                                                childrenBean.setType(2);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 3) {
                                        for (int j = 0; j < dataBean.get(2).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(2).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_3.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(2).getChildren().get(j);
                                                childrenBean.setType(3);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    }else if (mSelectList.get(i).getType() == 0) {
                                        int position1 = positionList(dataBean.get(0).getChildren(), mSelectList.get(i));
                                        if (position1 != -1) {
                                            fl_skill_tag_1.getChildAt(position1).setSelected(true);
                                            mSelectList.get(i).setType(1);
                                            mList1.add(mSelectList.get(i));
                                        }

                                        int position2 = positionList(dataBean.get(1).getChildren(), mSelectList.get(i));
                                        if (position2 != -1) {
                                            fl_skill_tag_2.getChildAt(position2).setSelected(true);
                                            mSelectList.get(i).setType(2);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position3 = positionList(dataBean.get(2).getChildren(), mSelectList.get(i));
                                        if (position3 != -1) {
                                            fl_skill_tag_3.getChildAt(position3).setSelected(true);
                                            mSelectList.get(i).setType(3);
                                            mList1.add(mSelectList.get(i));
                                        }

                                    }

                                }
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (!isInList(mallList, mSelectList.get(i))) {
                                        mList1.add(mSelectList.get(i));
                                    }
                                }

                                mSelectList.clear();
                                mSelectList.addAll(mList1);
                                mTagAdapterSelect.onlyAddAll(mSelectList);
                            }

                        } else if (dataBean.size() == 4) {
                            ll_skill_tag_1.setVisibility(View.VISIBLE);
                            ll_skill_tag_2.setVisibility(View.VISIBLE);
                            ll_skill_tag_3.setVisibility(View.VISIBLE);
                            ll_skill_tag_4.setVisibility(View.VISIBLE);
                            ll_skill_tag_5.setVisibility(View.GONE);
                            tv_skill_tag_1.setText(dataBean.get(0).getSkillName());
                            tv_skill_tag_2.setText(dataBean.get(1).getSkillName());
                            tv_skill_tag_3.setText(dataBean.get(2).getSkillName());
                            tv_skill_tag_4.setText(dataBean.get(3).getSkillName());
                            mTagAdapter1.onlyAddAll(data.getData().get(0).getChildren());
                            mTagAdapter2.onlyAddAll(data.getData().get(1).getChildren());
                            mTagAdapter3.onlyAddAll(data.getData().get(2).getChildren());
                            mTagAdapter4.onlyAddAll(data.getData().get(3).getChildren());

                            if (mSelectList != null) {
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (mSelectList.get(i).getType() == 1) {
                                        for (int j = 0; j < dataBean.get(0).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(0).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_1.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(0).getChildren().get(j);
                                                childrenBean.setType(1);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 2) {
                                        for (int j = 0; j < dataBean.get(1).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(1).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_2.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(1).getChildren().get(j);
                                                childrenBean.setType(2);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 3) {
                                        for (int j = 0; j < dataBean.get(2).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(2).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_3.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(2).getChildren().get(j);
                                                childrenBean.setType(3);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 4) {
                                        for (int j = 0; j < dataBean.get(3).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(3).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_4.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(3).getChildren().get(j);
                                                childrenBean.setType(4);
                                                mList1.add(childrenBean);
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 0) {
                                        int position1 = positionList(dataBean.get(0).getChildren(), mSelectList.get(i));
                                        if (position1 != -1) {
                                            fl_skill_tag_1.getChildAt(position1).setSelected(true);
                                            mSelectList.get(i).setType(1);
                                            mList1.add(mSelectList.get(i));
                                        }

                                        int position2 = positionList(dataBean.get(1).getChildren(), mSelectList.get(i));
                                        if (position2 != -1) {
                                            fl_skill_tag_2.getChildAt(position2).setSelected(true);
                                            mSelectList.get(i).setType(2);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position3 = positionList(dataBean.get(2).getChildren(), mSelectList.get(i));
                                        if (position3 != -1) {
                                            fl_skill_tag_3.getChildAt(position3).setSelected(true);
                                            mSelectList.get(i).setType(3);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position4 = positionList(dataBean.get(3).getChildren(), mSelectList.get(i));
                                        if (position4 != -1) {
                                            fl_skill_tag_4.getChildAt(position4).setSelected(true);
                                            mSelectList.get(i).setType(4);
                                            mList1.add(mSelectList.get(i));
                                        }

                                    }

                                }


                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (!isInList(mallList, mSelectList.get(i))) {
                                        mList1.add(mSelectList.get(i));
                                    }
                                }
                                mSelectList.clear();
                                mSelectList.addAll(mList1);
                                mTagAdapterSelect.onlyAddAll(mSelectList);
                            }
                        } else if (dataBean.size() == 5) {
                            ll_skill_tag_1.setVisibility(View.VISIBLE);
                            ll_skill_tag_2.setVisibility(View.VISIBLE);
                            ll_skill_tag_3.setVisibility(View.VISIBLE);
                            ll_skill_tag_4.setVisibility(View.VISIBLE);
                            ll_skill_tag_5.setVisibility(View.VISIBLE);
                            tv_skill_tag_1.setText(dataBean.get(0).getSkillName());
                            tv_skill_tag_2.setText(dataBean.get(1).getSkillName());
                            tv_skill_tag_3.setText(dataBean.get(2).getSkillName());
                            tv_skill_tag_4.setText(dataBean.get(3).getSkillName());
                            tv_skill_tag_5.setText(dataBean.get(4).getSkillName());
                            mTagAdapter1.onlyAddAll(data.getData().get(0).getChildren());
                            mTagAdapter2.onlyAddAll(data.getData().get(1).getChildren());
                            mTagAdapter3.onlyAddAll(data.getData().get(2).getChildren());
                            mTagAdapter4.onlyAddAll(data.getData().get(3).getChildren());
                            mTagAdapter5.onlyAddAll(data.getData().get(4).getChildren());

                            if (mSelectList != null) {
                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (mSelectList.get(i).getType() == 1) {
                                        for (int j = 0; j < dataBean.get(0).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(0).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_1.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(0).getChildren().get(j);
                                                childrenBean.setType(1);
                                                mList1.add(childrenBean);
                                                EasyLog.print("===111======"+mSelectList.get(i).getSkillName());
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 2) {
                                        for (int j = 0; j < dataBean.get(1).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(1).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_2.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(1).getChildren().get(j);
                                                childrenBean.setType(2);
                                                mList1.add(childrenBean);
                                                EasyLog.print("===2222======"+mSelectList.get(i).getSkillName());
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 3) {
                                        for (int j = 0; j < dataBean.get(2).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(2).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_3.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(2).getChildren().get(j);
                                                childrenBean.setType(3);
                                                mList1.add(childrenBean);
                                                EasyLog.print("===333======"+mSelectList.get(i).getSkillName());
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 4) {
                                        for (int j = 0; j < dataBean.get(3).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(3).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_4.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(3).getChildren().get(j);
                                                childrenBean.setType(4);
                                                mList1.add(childrenBean);
                                                EasyLog.print("===4444======"+mSelectList.get(i).getSkillName());
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 5) {
                                        for (int j = 0; j < dataBean.get(4).getChildren().size(); j++) {
                                            if (mSelectList.get(i).getSkillName().equals(dataBean.get(4).getChildren().get(j).getSkillName())) {
                                                fl_skill_tag_5.getChildAt(j).setSelected(true);
                                                GetTagListApi.Bean.ChildrenBean childrenBean = dataBean.get(4).getChildren().get(j);
                                                childrenBean.setType(5);
                                                mList1.add(childrenBean);
                                                EasyLog.print("===5555======"+mSelectList.get(i).getSkillName());
                                                continue;
                                            }
                                        }
                                    } else if (mSelectList.get(i).getType() == 0) {
                                        int position1 = positionList(dataBean.get(0).getChildren(), mSelectList.get(i));
                                        if (position1 != -1) {
                                            fl_skill_tag_1.getChildAt(position1).setSelected(true);
                                            mSelectList.get(i).setType(1);
                                            mList1.add(mSelectList.get(i));
                                        }

                                        int position2 = positionList(dataBean.get(1).getChildren(), mSelectList.get(i));
                                        if (position2 != -1) {
                                            fl_skill_tag_2.getChildAt(position2).setSelected(true);
                                            mSelectList.get(i).setType(2);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position3 = positionList(dataBean.get(2).getChildren(), mSelectList.get(i));
                                        if (position3 != -1) {
                                            fl_skill_tag_3.getChildAt(position3).setSelected(true);
                                            mSelectList.get(i).setType(3);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position4 = positionList(dataBean.get(3).getChildren(), mSelectList.get(i));
                                        if (position4 != -1) {
                                            fl_skill_tag_4.getChildAt(position4).setSelected(true);
                                            mSelectList.get(i).setType(4);
                                            mList1.add(mSelectList.get(i));
                                        }
                                        int position5 = positionList(dataBean.get(4).getChildren(), mSelectList.get(i));
                                        if (position5 != -1) {
                                            fl_skill_tag_5.getChildAt(position4).setSelected(true);
                                            mSelectList.get(i).setType(5);
                                            mList1.add(mSelectList.get(i));
                                        }

                                    }

                                }


                                for (int i = 0; i < mSelectList.size(); i++) {
                                    if (!isInList(mallList, mSelectList.get(i))) {
                                        mList1.add(mSelectList.get(i));
                                    }
                                }
                                mSelectList.clear();
                                mSelectList.addAll(mList1);
                                mTagAdapterSelect.onlyAddAll(mSelectList);
                            }

                        }


//                        mAdapter.setData(data.getData());


                    }
                });

    }

    private void addTag(String tag) {
        EasyHttp.post(this)
                .api(new AddTagApi().setSkillName(tag))
                .request(new HttpCallback<HttpData<AddTagApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<AddTagApi.Bean> data) {


                    }
                });

    }

    public interface OnCallbackListener {
        void onSucceed(List<GetTagListApi.Bean.ChildrenBean> mSelectList);

        default void onCancel() {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                GetTagListApi.Bean.ChildrenBean bean = (GetTagListApi.Bean.ChildrenBean) data.getSerializableExtra("list");
                EasyLog.print("1====" + bean.getSkillName());
                EasyLog.print("2====" + bean.getParentId());
                EasyLog.print("3====" + bean.getId());

                for (int i = 0; i < mSelectList.size(); i++) {
                    if (mSelectList.get(i).getId() == bean.getId()) {
                        return;
                    }
                }

                if (mListSize == 1) {
                    if (bean.getParentId().equals(mTagAdapter1.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter1.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter1.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_1.getChildAt(j).isSelected()) {
                                    fl_skill_tag_1.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(j);
                                    childrenBean.setType(1);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    EasyLog.print("4====" + bean.getId());
                                    break;
                                }
                            }
                        }
                    }
                } else if (mListSize == 2) {
                    if (bean.getParentId().equals(mTagAdapter1.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter1.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter1.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_1.getChildAt(j).isSelected()) {
                                    fl_skill_tag_1.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(j);
                                    childrenBean.setType(1);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    EasyLog.print("4====" + bean.getId());
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter2.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter2.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter2.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_2.getChildAt(j).isSelected()) {
                                    fl_skill_tag_2.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter2.getData().get(j);
                                    childrenBean.setType(2);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }

                        }

                    }
                } else if (mListSize == 3) {
                    if (bean.getParentId().equals(mTagAdapter1.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter1.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter1.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_1.getChildAt(j).isSelected()) {
                                    fl_skill_tag_1.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(j);
                                    childrenBean.setType(1);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    EasyLog.print("4====" + bean.getId());
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter2.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter2.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter2.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_2.getChildAt(j).isSelected()) {
                                    fl_skill_tag_2.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter2.getData().get(j);
                                    childrenBean.setType(2);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }

                        }

                    } else if (bean.getParentId().equals(mTagAdapter3.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter3.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter3.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_3.getChildAt(j).isSelected()) {
                                    fl_skill_tag_3.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter3.getData().get(j);
                                    childrenBean.setType(3);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }
                            }
                        }
                    }
                } else if (mListSize == 4) {
                    if (bean.getParentId().equals(mTagAdapter1.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter1.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter1.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_1.getChildAt(j).isSelected()) {
                                    fl_skill_tag_1.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(j);
                                    childrenBean.setType(1);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    EasyLog.print("4====" + bean.getId());
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter2.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter2.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter2.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_2.getChildAt(j).isSelected()) {
                                    fl_skill_tag_2.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter2.getData().get(j);
                                    childrenBean.setType(2);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }

                        }

                    } else if (bean.getParentId().equals(mTagAdapter3.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter3.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter3.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_3.getChildAt(j).isSelected()) {
                                    fl_skill_tag_3.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter3.getData().get(j);
                                    childrenBean.setType(3);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter4.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter4.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter4.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_4.getChildAt(j).isSelected()) {
                                    fl_skill_tag_4.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter4.getData().get(j);
                                    childrenBean.setType(4);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }
                        }
                    }
                } else if (mListSize == 5) {
                    if (bean.getParentId().equals(mTagAdapter1.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter1.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter1.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_1.getChildAt(j).isSelected()) {
                                    fl_skill_tag_1.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter1.getData().get(j);
                                    childrenBean.setType(1);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    EasyLog.print("4====" + bean.getId());
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter2.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter2.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter2.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_2.getChildAt(j).isSelected()) {
                                    fl_skill_tag_2.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter2.getData().get(j);
                                    childrenBean.setType(2);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }

                        }

                    } else if (bean.getParentId().equals(mTagAdapter3.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter3.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter3.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_3.getChildAt(j).isSelected()) {
                                    fl_skill_tag_3.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter3.getData().get(j);
                                    childrenBean.setType(3);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }
                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter4.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter4.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter4.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_4.getChildAt(j).isSelected()) {
                                    fl_skill_tag_4.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter4.getData().get(j);
                                    childrenBean.setType(4);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }
                        }
                    } else if (bean.getParentId().equals(mTagAdapter5.getData().get(0).getParentId())) {
                        for (int j = 0; j < mTagAdapter5.getData().size(); j++) {
                            if (bean.getSkillName().equals(mTagAdapter5.getData().get(j).getSkillName())) {
                                if (!fl_skill_tag_5.getChildAt(j).isSelected()) {
                                    fl_skill_tag_5.getChildAt(j).setSelected(true);
                                    GetTagListApi.Bean.ChildrenBean childrenBean = mTagAdapter5.getData().get(j);
                                    childrenBean.setType(5);
//                            mList1.add(childrenBean);
                                    mSelectList.add(childrenBean);
                                    break;
                                }

                            }
                        }
                    }
                }

                boolean inList = isInList(mallList, bean);
                if (!inList) {
                    mSelectList.add(bean);
                }
//                mSelectList.addAll(mList1);
                mTagAdapterSelect.onlyAddAll(mSelectList);
            }
        }
    }

    //判断值是否在list中
    private boolean isInList(List<GetTagListApi.Bean.ChildrenBean> list, GetTagListApi.Bean.ChildrenBean bean) {
        for (int i = 0; i < list.size(); i++) {
            if (bean.getSkillName().equals(list.get(i).getSkillName())) {
                return true;
            }
        }
        return false;
    }

    //判断值是否在list中
    private int positionList(List<GetTagListApi.Bean.ChildrenBean> list, GetTagListApi.Bean.ChildrenBean bean) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (bean.getSkillName().equals(list.get(i).getSkillName())) {
                return i;
            }
        }
        return position;
    }
}
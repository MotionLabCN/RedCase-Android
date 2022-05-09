package com.tntlinking.tntdev.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetNewbieApi;

import com.tntlinking.tntdev.ui.activity.JobDetailsActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;

import com.tntlinking.tntdev.ui.adapter.PositionRecommendationAdapter;
import com.tntlinking.tntdev.ui.bean.PositionBean;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class PositionRecommendationFragment extends TitleBarFragment<MainActivity> {
    private MyListView lv_position;
    private PositionRecommendationAdapter mPositionRecommendationAdapter;
    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    public static PositionRecommendationFragment newInstance() {
        return new PositionRecommendationFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.position_recommendation_fragment;
    }

    @Override
    protected void initView() {
        lv_position = findViewById(R.id.lv_position);



    }

    @Override
    protected void initData() {
        PositionBean positionBean = new PositionBean();
        positionBean.setPosition_name("web后端开发工程师");
        positionBean.setRecommend("已推荐");
        positionBean.setSalary("10-11k·月");
        positionBean.setContent("1.负责产品的核心功能和模块的代码编写....");
        positionBean.setName("柏鑫");
        positionBean.setProfessional_title("柏鑫·技术总监");
        positionBean.setCompany("黄焖鸡科技有限公司");
        positionBean.setIcon_recommend(true);
        PositionBean positionBean2 = new PositionBean();
        positionBean2.setPosition_name("Android开发工程师");
        positionBean2.setRecommend("已推荐");
        positionBean2.setSalary("10-11k·月");
        positionBean2.setContent("1.负责产品的核心功能和模块的代码编写....");
        positionBean2.setName("测试");
        positionBean2.setProfessional_title("测试·技术总监");
        positionBean2.setCompany("黄焖鸡科技有限公司");
        positionBean2.setIcon_recommend(false);
        PositionBean positionBean3 = new PositionBean();
        positionBean3.setPosition_name("iOS开发工程师");
        positionBean3.setRecommend("已推荐");
        positionBean3.setSalary("10-11k·月");
        positionBean3.setContent("1.负责产品的核心功能和模块的代码编写....");
        positionBean3.setName("测试1");
        positionBean3.setProfessional_title("测试·技术总监");
        positionBean3.setCompany("黄焖鸡科技有限公司");
        positionBean3.setIcon_recommend(false);

        List<PositionBean> list = new ArrayList<>();
        list.add(positionBean);
        list.add(positionBean2);
        list.add(positionBean3);
        mPositionRecommendationAdapter = new PositionRecommendationAdapter(getActivity(), list);
        lv_position.setAdapter(mPositionRecommendationAdapter);

        lv_position.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PositionBean item = mPositionRecommendationAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);

                intent.putExtra("Position", item);
                startActivity(intent);

            }
        });
    }

}

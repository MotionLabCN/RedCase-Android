package com.tntlinking.tntdev.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetDeveloperRecommendsApi;
import com.tntlinking.tntdev.http.api.GetNewbieApi;

import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.EvaluationListActivity;
import com.tntlinking.tntdev.ui.activity.JobDetailsActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;

import com.tntlinking.tntdev.ui.activity.SaveQRActivity;
import com.tntlinking.tntdev.ui.activity.WriteDailyActivity;
import com.tntlinking.tntdev.ui.adapter.PositionRecommendationAdapter;
import com.tntlinking.tntdev.ui.bean.PositionBean;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class PositionRecommendationFragment extends TitleBarFragment<MainActivity> {
    private MyListView lv_position;

    private PositionRecommendationAdapter mPositionRecommendationAdapter;
    private List<GetDeveloperRecommendsApi.Bean> mList = new ArrayList<>();
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
    public void onResume() {
        super.onResume();
        getDeveloper_Recommends();
    }

    @Override
    protected void initData() {
        getDeveloper_Recommends();
        mPositionRecommendationAdapter = new PositionRecommendationAdapter(getActivity(), mList);
        lv_position.setAdapter(mPositionRecommendationAdapter);

        lv_position.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDeveloperRecommendsApi.Bean item = (GetDeveloperRecommendsApi.Bean) mPositionRecommendationAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
                intent.putExtra("positionId", item.getPositionId());
                startActivity(intent);




            }
        });
    }
    /**
     * 开发者_推荐
     */
    private void getDeveloper_Recommends() {
        EasyHttp.get(this)
                .api(new GetDeveloperRecommendsApi())
                .request(new HttpCallback<HttpData<List<GetDeveloperRecommendsApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetDeveloperRecommendsApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            mList.clear();
                            mList.addAll(data.getData());
                            mPositionRecommendationAdapter.setData(mList);
                        } else {
                        }

                    }
                });
    }
}

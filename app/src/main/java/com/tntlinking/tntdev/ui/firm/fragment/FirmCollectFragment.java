package com.tntlinking.tntdev.ui.firm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetFavoriteDeveloperApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.activity.DeveloperInfoActivity;
import com.tntlinking.tntdev.ui.firm.activity.FirmMainActivity;
import com.tntlinking.tntdev.ui.firm.adapter.CollectPositionAdapter;
import com.tntlinking.tntdev.widget.MyListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;


/**
 * desc   :企业收藏页面
 */
public final class FirmCollectFragment extends TitleBarFragment<FirmMainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;

    private SmartRefreshLayout mRefreshLayout;
    private List<GetFavoriteDeveloperApi.Bean.ListBean> mList = new ArrayList<>();
    private CollectPositionAdapter mAdapter;
    private int mPageNum = 1;


    public static FirmCollectFragment newInstance() {
        return new FirmCollectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.firm_collect_fragment;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);
        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter = new CollectPositionAdapter(getActivity());
        lv_1.setAdapter(mAdapter);


        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
                intent.putExtra("developerId", mList.get(position).getDeveloperId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void initData() {

        getFavoriteDeveloperList(mPageNum);
    }


    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getCount(); i < mAdapter.getCount() + 20; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    private void getFavoriteDeveloperList(int pageNum) {
        EasyHttp.get(this)
                .api(new GetFavoriteDeveloperApi().setPageNum(pageNum))
                .request(new HttpCallback<HttpData<GetFavoriteDeveloperApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetFavoriteDeveloperApi.Bean> data) {
                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                } else {
                                    mList.clear();
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishRefresh();
                            } else {
                                if (pageNum == data.getData().getPageNum()) { //当前pageNum 是否等于后台传过来的当前页pagenum 数
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPageNum = 1;
        getFavoriteDeveloperList(mPageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPageNum++;
        getFavoriteDeveloperList(mPageNum);
    }


}
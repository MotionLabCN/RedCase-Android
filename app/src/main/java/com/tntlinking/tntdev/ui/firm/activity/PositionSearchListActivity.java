package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmDevApi;
import com.tntlinking.tntdev.http.api.SearchDeveloperApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.AuditionHistoryAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.PositionSearchAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.TagFirmAdapter;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 账单明细列表页面
 */
public final class PositionSearchListActivity extends AppActivity implements OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {
    private LinearLayout ll_empty;
    private EditText et_search;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private LinearLayout ll_history;
    private FlowTagLayout fl_search;

    private PositionSearchAdapter mAdapter;
    private List<GetFirmDevApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;
    private String mSearch = "";
    private List<String> stringList = new ArrayList<>();
    private TagFirmAdapter tagAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.position_search_activity;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        et_search = findViewById(R.id.et_search);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);
        ll_history = findViewById(R.id.ll_history);
        fl_search = findViewById(R.id.fl_search);


        tagAdapter = new TagFirmAdapter(this, 2);
        fl_search.setAdapter(tagAdapter);
        tagAdapter.onlyAddAll(stringList);
        fl_search.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {

                List<String> collectList = stringList.stream().distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                mSearch = collectList.get(position);
                et_search.setText(mSearch);

//                mSearch = stringList.get(position);
//                et_search.setText(mSearch);
//                searchDeveloper(mSearch, 1);
            }
        });


        mAdapter = new PositionSearchAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 隐藏软键盘
                    hideKeyboard(getCurrentFocus());
                    mSearch = v.getText().toString();
                    if (!TextUtils.isEmpty(mSearch)) {
                        searchDeveloper(mSearch, 1);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    protected void initData() {
        String position_search = SPUtils.getInstance().getString("position_search");
        if (!TextUtils.isEmpty(position_search)) {
            ll_history.setVisibility(View.VISIBLE);
            stringList = GsonUtils.fromJson(position_search, new TypeToken<List<String>>() {
            }.getType());

            List<String> collectList = stringList.stream().distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            tagAdapter.onlyAddAll(collectList);
        } else {
            ll_history.setVisibility(View.GONE);
        }
//        getBillList(pageNum);
    }


    @SingleClick
    @Override
    public void onClick(View view) {

    }

    private void searchDeveloper(String str, int pageNum) {
        EasyHttp.get(this)
                .api(new SearchDeveloperApi().setSearch(str).setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<GetFirmDevApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmDevApi.Bean> data) {
                        mRefreshLayout.setEnableLoadMore(true);
                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    mList.clear();
                                    mAdapter.setData(mList);
                                    ll_empty.setVisibility(View.VISIBLE);
                                    mRefreshLayout.setEnableLoadMore(false);
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

                        ll_history.setVisibility(View.GONE);
                        stringList.add(str);
                        SPUtils.getInstance().put("position_search", GsonUtils.toJson(stringList));

                    }
                });
    }

    /**
     * 自定义去重
     *
     * @param list
     */
    @SuppressLint("NewApi")
    public static List<String> deleteSame(List<String> list) {
        // 新集合
        List<String> newList = new ArrayList<>(list.size());
        list.forEach(i -> {
            if (!newList.contains(i)) { // 如果新集合中不存在则插入
                newList.add(i);
            }
        });
//        System.out.println("去重集合:" + newList);
        return newList;

    }

    /**
     * {@link BaseAdapter.OnItemClickListener}
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Intent intent = new Intent(getActivity(), DeveloperInfoActivity.class);
        intent.putExtra("developerId", mList.get(position).getId());
        startActivity(intent);
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        searchDeveloper(mSearch, pageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        searchDeveloper(mSearch, pageNum);

    }
}
package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.SearchCompanyApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.CompanySearchAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 选择公司页面
 */
public final class SearchCompanySelectActivity extends AppActivity implements OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {
    private LinearLayout ll_empty;
    private EditText et_search;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private CompanySearchAdapter mAdapter;
    private List<SearchCompanyApi.Bean> mList = new ArrayList<>();
    private int pageNum = 1;
    private String mSearch = "";

    @Override
    protected int getLayoutId() {
        return R.layout.company_search_activity;
    }

    @Override
    protected void initView() {
        ll_empty = findViewById(R.id.ll_empty);
        et_search = findViewById(R.id.et_search);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new CompanySearchAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    //关闭软键盘
//                    YUtils.closeSoftKeyboard();
                    mSearch = v.getText().toString();
                    searchCompany(mSearch);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }

    private void searchCompany(String name) {
        EasyHttp.get(this)
                .api(new SearchCompanyApi().setName(name))
                .request(new HttpCallback<HttpData<List<SearchCompanyApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<SearchCompanyApi.Bean>> data) {
                        mRefreshLayout.setEnableLoadMore(true);

                        if (data.getData().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                    mRefreshLayout.setEnableLoadMore(false);
                                } else {
                                    mList.clear();
                                    mList.addAll(data.getData());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishRefresh();
                            }

                        }

                    }
                });
    }


    /**
     * {@link BaseAdapter.OnItemClickListener}
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Intent intent = new Intent();
        intent.putExtra("companyId", mAdapter.getItem(position).getId());
        intent.putExtra("companyName", mAdapter.getItem(position).getCompanyName());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        searchCompany(mSearch);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {


    }
}
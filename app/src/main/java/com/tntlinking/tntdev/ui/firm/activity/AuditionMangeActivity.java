package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.AuditionManageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 面试管理页面
 */
public final class AuditionMangeActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {


    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private AuditionManageAdapter mAdapter;
    private List<developerBillListApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.audition_manage_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new AuditionManageAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter.setOnChildClickListener(R.id.ll_interview_resume, this);
        mAdapter.setOnChildClickListener(R.id.ll_interview_link, this);
        mAdapter.setOnChildClickListener(R.id.btn_cancel, this);
        mAdapter.setOnChildClickListener(R.id.btn_contact, this);
        mAdapter.setOnChildClickListener(R.id.btn_enter, this);

        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {

        getBillList(pageNum);
    }


    @SingleClick
    @Override
    public void onClick(View view) {


    }

    @Override
    public void onRightClick(View view) {
        startActivity(AuditionHistoryListActivity.class);
    }

    private void getBillList(int pageNum) {
        EasyHttp.get(this)
                .api(new developerBillListApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<developerBillListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<developerBillListApi.Bean> data) {
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
                                if (pageNum == Integer.valueOf(data.getData().getPageNum())) { //当前pageNum 是否等于后台传过来的当前页pagenum 数
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }

                    }
                });
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

    /**
     * {@link BaseAdapter.OnItemClickListener}
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
//        Intent intent = new Intent(this, IncomeDetailActivity.class);
//        intent.putExtra("orderId", mAdapter.getItem(position).getId());
//        startActivity(intent);

        startActivity(FirmAuditionDetailActivity.class);
    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        if (childView.getId() == R.id.ll_interview_resume) {
            toast("查看简历");
        } else if (childView.getId() == R.id.ll_interview_link) {
            toast("会议连接");
        } else if (childView.getId() == R.id.btn_cancel) {
            new BaseDialog.Builder<>(this)
                    .setContentView(R.layout.check_order_status_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "取消面试")
                    .setText(R.id.tv_content, "是否取消面试")
                    .setText(R.id.btn_dialog_custom_cancel, "否")
                    .setText(R.id.btn_dialog_custom_ok, "是")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        dialog.dismiss();
                    }).show();
        } else if (childView.getId() == R.id.btn_contact) {
            toast("联系客服");
        } else if (childView.getId() == R.id.btn_enter) {
            toast("进入会议");
        }
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getBillList(pageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getBillList(pageNum);

    }
}
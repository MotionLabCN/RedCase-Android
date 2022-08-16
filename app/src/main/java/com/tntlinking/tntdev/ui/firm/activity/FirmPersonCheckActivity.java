package com.tntlinking.tntdev.ui.firm.activity;

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
import com.tntlinking.tntdev.http.api.AgreeJoinApi;
import com.tntlinking.tntdev.http.api.DisagreeJoinApi;
import com.tntlinking.tntdev.http.api.FirmMemberListApi;
import com.tntlinking.tntdev.http.api.FirmMemberToBeAuditedApi;
import com.tntlinking.tntdev.http.api.ModifyAdminApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.FirmManageAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.FirmPersonCheckAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 人员审核页面
 */
public final class FirmPersonCheckActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {

    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;

    private FirmPersonCheckAdapter mAdapter;
    private List<FirmMemberToBeAuditedApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_manage_check_activity;
    }

    @Override
    protected void initView() {

        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new FirmPersonCheckAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnChildClickListener(R.id.btn_disagree, this);
        mAdapter.setOnChildClickListener(R.id.btn_agree, this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void initData() {

        getMemberToBeAudited(pageNum);
    }


    /**
     * 获取公司下待审核员工列表
     *
     * @param pageNum
     */
    private void getMemberToBeAudited(int pageNum) {
        EasyHttp.get(this)
                .api(new FirmMemberToBeAuditedApi().setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<FirmMemberToBeAuditedApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<FirmMemberToBeAuditedApi.Bean> data) {
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

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        ll_empty.setVisibility(View.VISIBLE);
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


    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        FirmMemberToBeAuditedApi.Bean.ListBean item = mAdapter.getItem(position);
        if (childView.getId() == R.id.btn_disagree) {
            disagreeJoin(item.getId());
        } else if (childView.getId() == R.id.btn_agree) {
            agreeJoin(item.getId());
        }
    }


    /**
     * 拒绝成员申请
     *
     * @param memberId
     */
    private void disagreeJoin(int memberId) {
        EasyHttp.post(this)
                .api(new DisagreeJoinApi().setMemberId(memberId))
                .request(new HttpCallback<HttpData<FirmMemberListApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<FirmMemberListApi.Bean> data) {
                        toast("已拒绝");
                        getMemberToBeAudited(1);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);

                    }
                });
    }

    /**
     * 同意成员申请
     *
     * @param memberId
     */
    private void agreeJoin(int memberId) {
        EasyHttp.post(this)
                .api(new AgreeJoinApi().setMemberId(memberId))
                .request(new HttpCallback<HttpData<FirmMemberListApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<FirmMemberListApi.Bean> data) {
                        toast("已同意");
                        getMemberToBeAudited(1);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getMemberToBeAudited(pageNum);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getMemberToBeAudited(pageNum);

    }

}
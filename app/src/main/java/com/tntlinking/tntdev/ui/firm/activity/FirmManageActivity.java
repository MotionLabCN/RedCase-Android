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
import com.tntlinking.tntdev.http.api.FirmMemberListApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.firm.adapter.AuditionHistoryAdapter;
import com.tntlinking.tntdev.ui.firm.adapter.FirmManageAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 企业管理页面
 */
public final class FirmManageActivity extends AppActivity implements OnRefreshLoadMoreListener,
        BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {


    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private AppCompatButton btn_commit;

    private FirmManageAdapter mAdapter;
    private List<developerBillListApi.Bean.ListBean> mList = new ArrayList<>();
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_manage_activity;
    }

    @Override
    protected void initView() {


        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);
        btn_commit = findViewById(R.id.btn_commit);

        mAdapter = new FirmManageAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnChildClickListener(R.id.btn_hand_over, this);
        mAdapter.setOnChildClickListener(R.id.btn_remove, this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(InterviewPersonActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

        getFirmMemberList(pageNum);
    }


    @SingleClick
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        startActivity(FirmPersonCheckActivity.class);
    }

    private void getFirmMemberList(int pageNum) {
        EasyHttp.get(this)
                .api(new FirmMemberListApi().setCompanyId(00).setPageNum(pageNum).setPageSize(20))
                .request(new HttpCallback<HttpData<developerBillListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<developerBillListApi.Bean> data) {
                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
//                                    ll_empty.setVisibility(View.VISIBLE);
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


    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        if (childView.getId() == R.id.btn_hand_over) {
            new BaseDialog.Builder<>(this)
                    .setContentView(R.layout.check_order_status_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "移交管理员")
                    .setText(R.id.tv_content, "是否移交管理员给小王")
                    .setText(R.id.btn_dialog_custom_cancel, "否")
                    .setText(R.id.btn_dialog_custom_ok, "是")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        dialog.dismiss();
                    }).show();
        } else if (childView.getId() == R.id.btn_remove) {
            new BaseDialog.Builder<>(this)
                    .setContentView(R.layout.check_order_status_dialog)
                    .setAnimStyle(BaseDialog.ANIM_SCALE)
                    .setText(R.id.tv_title, "移除")
                    .setText(R.id.tv_content, "是否移除小王")
                    .setText(R.id.btn_dialog_custom_cancel, "否")
                    .setText(R.id.btn_dialog_custom_ok, "是")
                    .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                    .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                        dialog.dismiss();
                    }).show();
        }
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;


    }
}
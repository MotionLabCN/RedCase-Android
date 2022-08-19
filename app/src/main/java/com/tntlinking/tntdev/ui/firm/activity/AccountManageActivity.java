package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmFreezeRecordApi;
import com.tntlinking.tntdev.http.api.GetFirmInterviewListApi;
import com.tntlinking.tntdev.http.api.GetFirmWalletCurrentApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.firm.adapter.FreezeAdapter;
import com.tntlinking.tntdev.widget.MyListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;


/**
 * 账户管理
 */
public final class AccountManageActivity extends AppActivity implements OnRefreshLoadMoreListener {
    private ImageView iv_select;
    private MyListView lv_1;
    private LinearLayout ll_empty;
    private SmartRefreshLayout mRefreshLayout;
    private TextView tv_balance_money;
    private TextView tv_freeze_money;

    private List<GetFirmFreezeRecordApi.Bean.ListBean> mList = new ArrayList<>();
    private FreezeAdapter mAdapter;
    private int pageNum = 1;
    private String mInTime = "";//选择的时间

    @Override
    protected int getLayoutId() {
        return R.layout.account_manage_activity;
    }


    @Override
    protected void initView() {
        iv_select = findViewById(R.id.iv_select);
        tv_balance_money = findViewById(R.id.tv_balance_money);
        tv_freeze_money = findViewById(R.id.tv_freeze_money);
        lv_1 = findViewById(R.id.lv_1);
        ll_empty = findViewById(R.id.ll_empty);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        mAdapter = new FreezeAdapter(this);
        lv_1.setAdapter(mAdapter);

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateSelectDialog.Builder(AccountManageActivity.this).setTitle("筛选时间").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        mInTime = year + "-" + Utils.formatDate(month);
                        getFirmFreezeRecord(mInTime);
                    }

                }).show();
            }
        });
        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


    }


    @Override
    protected void initData() {
        getFirmWalletCurrent();
        getFirmFreezeRecord(mInTime);
    }


    @SingleClick
    @Override
    public void onClick(View view) {
    }

    /**
     * 获取账户余额
     */
    @SuppressLint("SetTextI18n")
    public void getFirmWalletCurrent() {
        EasyHttp.get(this)
                .api(new GetFirmWalletCurrentApi())
                .request(new HttpCallback<HttpData<GetFirmWalletCurrentApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmWalletCurrentApi.Bean> data) {
                        tv_balance_money.setText("¥" + Utils.formatMoney(data.getData().getBalance()));
                        tv_freeze_money.setText("¥" + Utils.formatMoney(data.getData().getFreezeMoney()));
                    }
                });
    }

    // 查询某个时间段的记录
    public void getFirmFreezeRecord(String date) {
        GetFirmFreezeRecordApi mApi1 = new GetFirmFreezeRecordApi().setPageNum(1);
        GetFirmFreezeRecordApi mApi2 = new GetFirmFreezeRecordApi().setDate(date).setPageNum(1);

        EasyHttp.get(this)
                .api(TextUtils.isEmpty(date) ? mApi1 : mApi2)
                .request(new HttpCallback<HttpData<GetFirmFreezeRecordApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmFreezeRecordApi.Bean> data) {

                        mRefreshLayout.setEnableLoadMore(true);
                        if (data.getData().getList().size() >= 0) {
                            ll_empty.setVisibility(View.GONE);
                            lv_1.setVisibility(View.VISIBLE);

                            if (pageNum == 1) {
                                if (data.getData().getList().size() == 0) {
                                    ll_empty.setVisibility(View.VISIBLE);
                                    lv_1.setVisibility(View.GONE);
                                    mRefreshLayout.setEnableLoadMore(false);
                                } else {
                                    mList.clear();
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishRefresh();
                            } else {
                                if (pageNum == data.getData().getPageNum()) { //当前pageNum 是否等于后台传过来的当前页pageNum 数
                                    mList.addAll(data.getData().getList());
                                    mAdapter.setData(mList);
                                }
                                mRefreshLayout.finishLoadMore();
                            }

                        }
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getFirmWalletCurrent();
        getFirmFreezeRecord(mInTime);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getFirmWalletCurrent();
        getFirmFreezeRecord(mInTime);
    }

}
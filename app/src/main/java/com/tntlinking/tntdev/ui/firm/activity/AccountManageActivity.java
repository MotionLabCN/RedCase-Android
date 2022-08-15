package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
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
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.GetFirmFreezeRecordApi;
import com.tntlinking.tntdev.http.api.GetFirmInterviewListApi;
import com.tntlinking.tntdev.http.api.GetFirmWalletCurrentApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
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

    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private FreezeAdapter mServiceAdapter;

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

        mServiceAdapter = new FreezeAdapter(getActivity(), mServiceList);
        lv_1.setAdapter(mServiceAdapter);

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateSelectDialog.Builder(AccountManageActivity.this).setTitle("筛选时间").setIgnoreDay().setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month);

                        toast("===year==" + year + "===month=" + month);

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
        getFirmFreezeRecord();
    }


    @SingleClick
    @Override
    public void onClick(View view) {
    }

    /**
     * 获取账户余额
     */
    public void getFirmWalletCurrent() {
        EasyHttp.get(this)
                .api(new GetFirmWalletCurrentApi())
                .request(new HttpCallback<HttpData<GetFirmWalletCurrentApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmWalletCurrentApi.Bean> data) {
                        tv_balance_money.setText(data.getData().getBalance());
                        tv_freeze_money.setText(data.getData().getFreezeMoney());
                    }
                });
    }

    public void getFirmFreezeRecord() {
        EasyHttp.get(this)
                .api(new GetFirmFreezeRecordApi().setDate("2022-8").setPageNum(1).setPageNum(20))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

}
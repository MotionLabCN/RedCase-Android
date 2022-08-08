package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.SPUtils;
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


    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private FreezeAdapter mServiceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.account_manage_activity;
    }


    @Override
    protected void initView() {
        iv_select = findViewById(R.id.iv_select);
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);


        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);

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
    protected void initData() {// 一个是从简历解析传过来的，一个是进入页面接口请求显示数据的
        getAppList();
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_add_photo:
                break;
            case R.id.ll_add_base_info:
                break;

        }

    }

    /**
     * 获取在服务企业list //2 待服务，3 服务中
     */
    private void getAppList() {
        EasyHttp.get(this)
                .api(new AppListApi().setOrderStatus(3))
                .request(new HttpCallback<HttpData<List<AppListApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<AppListApi.Bean>> data) {
                        if (data.getData().size() > 0) {
                            ll_empty.setVisibility(View.GONE);


                            mServiceList.addAll(data.getData());
                            mServiceAdapter.setData(mServiceList);


                        } else {
                            ll_empty.setVisibility(View.VISIBLE);

                        }
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mServiceList.clear();
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
        if (status.equals("3")) {
            getAppList();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

}
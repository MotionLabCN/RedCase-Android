package com.tntlinking.tntdev.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.AppListApi;
import com.tntlinking.tntdev.http.api.CreateDailyApi;
import com.tntlinking.tntdev.http.api.DeleteDailyApi;
import com.tntlinking.tntdev.http.api.GetDailyListApi;
import com.tntlinking.tntdev.http.api.UpdateDailyApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.ui.activity.HistoryDailyListActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.adapter.DailyWriteAdapter;
import com.tntlinking.tntdev.ui.adapter.ServiceProjectAdapter;
import com.tntlinking.tntdev.ui.dialog.AddTagDialog;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * desc   : 服务 Fragment
 */
public final class TreatyService1Fragment extends TitleBarFragment<MainActivity> implements OnRefreshLoadMoreListener {

    private MyListView lv_1;
    private LinearLayout ll_empty;
    private LinearLayout ll_daily;
    private SmartRefreshLayout mRefreshLayout;
    private TextView tv_time;
    private LinearLayout ll_history;
    private LinearLayout ll_done, ll_running, ll_future, ll_help;
    private MyListView my_lv_1, my_lv_2, my_lv_3, my_lv_4;

    private List<AppListApi.Bean> mServiceList = new ArrayList<>();
    private ServiceProjectAdapter mServiceAdapter;
    private DailyWriteAdapter mAdapter1, mAdapter2, mAdapter3, mAdapter4;

    private String orderId;

    private HomeChangeListener listener;

    public void setListener(HomeChangeListener listener) {
        this.listener = listener;
    }

    public static TreatyService1Fragment newInstance() {
        return new TreatyService1Fragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.treaty_fragment1;
    }

    @Override
    protected void initView() {
        lv_1 = findViewById(R.id.lv_1);

        ll_empty = findViewById(R.id.ll_empty);
        ll_daily = findViewById(R.id.ll_daily);
        ll_history = findViewById(R.id.ll_history);
        tv_time = findViewById(R.id.tv_time);
        ll_done = findViewById(R.id.ll_done);
        ll_running = findViewById(R.id.ll_running);
        ll_future = findViewById(R.id.ll_future);
        ll_help = findViewById(R.id.ll_help);
        my_lv_1 = findViewById(R.id.my_lv_1);
        my_lv_2 = findViewById(R.id.my_lv_2);
        my_lv_3 = findViewById(R.id.my_lv_3);
        my_lv_4 = findViewById(R.id.my_lv_4);

        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        setOnClickListener(ll_history, ll_done, ll_running, ll_future, ll_help);
        mServiceAdapter = new ServiceProjectAdapter(getActivity(), mServiceList);
        mAdapter1 = new DailyWriteAdapter(getActivity());
        mAdapter2 = new DailyWriteAdapter(getActivity());
        mAdapter3 = new DailyWriteAdapter(getActivity());
        mAdapter4 = new DailyWriteAdapter(getActivity());
        my_lv_1.setAdapter(mAdapter1);
        my_lv_2.setAdapter(mAdapter2);
        my_lv_3.setAdapter(mAdapter3);
        my_lv_4.setAdapter(mAdapter4);

        lv_1.setAdapter(mServiceAdapter);
        tv_time.setText("写日报 " + TimeUtil.getTimeString("yyyy-MM-dd"));

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AppListApi.Bean item = (AppListApi.Bean) mServiceAdapter.getItem(position);
//                if (!TextUtils.isEmpty(item.getServiceName()) && item.getServiceName().equals("服务中")) {
//
//                    Intent intent = new Intent(getActivity(), WriteDailyActivity.class);
//                    intent.putExtra("orderId", item.getId());
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getActivity(), AuditionDetailActivity.class);
//                    intent.putExtra("interviewId", item.getId());
//                    startActivity(intent);
//                }

            }
        });

        /** ----------------------------点击事件 更新---------------------------------*/
        my_lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDailyListApi.ListBean item = mAdapter1.getItem(position);
                new AddTagDialog.Builder(getActivity()).setContent(item.getItem()).setListener(new AddTagDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, String content) {
                        if (TextUtils.isEmpty(content)) {
                            toast("输入内容不能为空");
                            return;
                        }
                        updateDaily(content, orderId, item.getId(), item.getTypeId(), position, dialog);
                    }
                }).show();
            }
        });
        my_lv_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDailyListApi.ListBean item = mAdapter2.getItem(position);
                new AddTagDialog.Builder(getActivity()).setContent(item.getItem()).setListener(new AddTagDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, String content) {
                        if (TextUtils.isEmpty(content)) {
                            toast("输入内容不能为空");
                            return;
                        }
                        updateDaily(content, orderId, item.getId(), item.getTypeId(), position, dialog);
                    }
                }).show();
            }
        });
        my_lv_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDailyListApi.ListBean item = mAdapter3.getItem(position);
                new AddTagDialog.Builder(getActivity()).setContent(item.getItem()).setListener(new AddTagDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, String content) {
                        if (TextUtils.isEmpty(content)) {
                            toast("输入内容不能为空");
                            return;
                        }
                        updateDaily(content, orderId, item.getId(), item.getTypeId(), position, dialog);
                    }
                }).show();
            }
        });
        my_lv_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetDailyListApi.ListBean item = mAdapter4.getItem(position);
                new AddTagDialog.Builder(getActivity()).setContent(item.getItem()).setListener(new AddTagDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, String content) {
                        if (TextUtils.isEmpty(content)) {
                            toast("输入内容不能为空");
                            return;
                        }
                        updateDaily(content, orderId, item.getId(), item.getTypeId(), position, dialog);
                    }
                }).show();
            }
        });
        /** ----------------------------长按事件 删除---------------------------------*/
        my_lv_1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View views, int position, long id) {
                deleteDailyDialog(mAdapter1.getItem(position).getId());
                return false;
            }
        });
        my_lv_2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View views, int position, long id) {
                deleteDailyDialog(mAdapter2.getItem(position).getId());
                return false;
            }
        });
        my_lv_3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View views, int position, long id) {

                deleteDailyDialog(mAdapter3.getItem(position).getId());
                return false;
            }
        });
        my_lv_4.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View views, int position, long id) {

                deleteDailyDialog(mAdapter4.getItem(position).getId());
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");

        if (status.equals("3")) {
//            mRefreshLayout.autoRefresh();
            getAppList();
        } else {
            toast("您还没有认证");
            ll_empty.setVisibility(View.VISIBLE);
            ll_daily.setVisibility(View.GONE);
        }

    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_history:
                Intent intent = new Intent(getActivity(), HistoryDailyListActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                break;
            case R.id.ll_done:
                writeDaily(1);
                break;
            case R.id.ll_running:
                writeDaily(2);
                break;
            case R.id.ll_future:
                writeDaily(3);
                break;
            case R.id.ll_help:
                writeDaily(4);
                break;
        }
    }

    /**
     * 类型:1.已完成/2.进行中/3.明日计划/4.需要的帮助
     *
     * @param type
     */
    public void writeDaily(int type) {
        new AddTagDialog.Builder(getActivity()).setListener(new AddTagDialog.OnListener() {
            @Override
            public void onSelected(BaseDialog dialog, String content) {

                if (TextUtils.isEmpty(content)) {
                    toast("输入内容不能为空");
                    return;
                }
                createDaily(content, type);
            }
        }).show();
    }


    /**
     * 创建日报
     *
     * @param type 类型:1.已完成/2.进行中/3.明日计划/4.需要的帮助
     */
    public void createDaily(String content, int type) {
        EasyHttp.post(this)
                .api(new CreateDailyApi()
                        .setDateOf("")
                        .setCreateDate("")
                        .setItem(content)
                        .setOrderId(orderId)
                        .setTypeId(type))
                .request(new HttpCallback<HttpData<CreateDailyApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<CreateDailyApi.Bean> data) {

                        getDailyList(orderId);

                    }
                });
    }

    /**
     * 删除日报 弹框
     *
     * @param id
     */
    public void deleteDailyDialog(int id) {
        new BaseDialog.Builder<>(getActivity())
                .setContentView(R.layout.write_daily_delete_dialog)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                //.setText(id, "我是预设置的文本")
                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, view) -> {
                    deleteDaily(dialog, id);
                }).show();
    }

    /**
     * 删除日报
     */
    public void deleteDaily(BaseDialog dialog, int id) {
        EasyHttp.post(this)
                .api(new DeleteDailyApi().setOrderScheduleId(id))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {

                        dialog.dismiss();
                        getDailyList(orderId);
                    }
                });
    }


    /**
     * 修改日报
     */
    public void updateDaily(String content, String orderId, int id, int typeId, int position, BaseDialog dialog) {
        EasyHttp.post(this)
                .api(new UpdateDailyApi()
                        .setDateOf("")
                        .setCreateDate("")
                        .setOrderId(orderId)
                        .setId(id)
                        .setItem(content)
                        .setTypeId(typeId))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {

                        dialog.dismiss();
                        getDailyList(orderId);
                    }
                });
    }

//    @Override
//    public boolean isStatusBarEnabled() {
//        // 使用沉浸式状态栏
//        return !super.isStatusBarEnabled();
//    }


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
                            ll_daily.setVisibility(View.VISIBLE);

                            mServiceList.addAll(data.getData());
                            mServiceAdapter.setData(mServiceList);
                            orderId = mServiceList.get(0).getId();// 服务项目只会有一个

                            getDailyList(orderId);

                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
                            ll_daily.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    /**
     * 获取日报列表
     */
    public void getDailyList(String orderId) {
        EasyHttp.get(this)
                .api(new GetDailyListApi().setOrderId(orderId))
                .request(new HttpCallback<HttpData<GetDailyListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetDailyListApi.Bean> data) {
                        if (data.getData() != null) {
                            GetDailyListApi.Bean bean = data.getData();

                            mAdapter1.setData(bean.getDone());
                            mAdapter2.setData(bean.getRunning());
                            mAdapter3.setData(bean.getFuture());
                            mAdapter4.setData(bean.getHelp());
                        }
                        mRefreshLayout.finishRefresh();
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
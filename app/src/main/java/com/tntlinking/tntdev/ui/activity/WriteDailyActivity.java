package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.TimeUtils;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateDailyApi;
import com.tntlinking.tntdev.http.api.DeleteDailyApi;
import com.tntlinking.tntdev.http.api.GetDailyListApi;
import com.tntlinking.tntdev.http.api.UpdateDailyApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.WriteDailyAdapter;
import com.tntlinking.tntdev.ui.dialog.AddTagDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 写日报页面
 */
public final class WriteDailyActivity extends AppActivity {
    private AppCompatButton mCommit;
    private WriteDailyAdapter mAdapter;
    private RecyclerView mRecyclerview;
    private List<GetDailyListApi.ListBean> mList = new ArrayList<>();
    private String mToday = TimeUtils.millis2String(TimeUtils.getNowMills(), "yyyy-MM-dd");// 默认获取当天日程
    private String orderId = "";
    public static final int TAB_FINISHED = 1; // 已完成
    public static final int ADD_DOING = 2;// 进心中
    public static final int ADD_TOMORROW = 3;// 明日计划
    public static final int ADD_HELP = 4;// 需要的帮助
    public static final int ADD_NORMAL = 5;// 通用
    @Override
    protected int getLayoutId() {
        return R.layout.write_daily_activity;
    }

    @Override
    protected void initView() {

        mCommit = findViewById(R.id.btn_commit);
        mRecyclerview = findViewById(R.id.rv_home_tab);

        setOnClickListener(mCommit);

        mAdapter = new WriteDailyAdapter(this);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                GetDailyListApi.ListBean listBean = mList.get(position);
                if (listBean.getType() == TAB_FINISHED) {
                    new AddTagDialog.Builder(WriteDailyActivity.this).setListener(new AddTagDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, String content) {
//                            GetDailyListApi.ListBean bean = new GetDailyListApi.ListBean();
//                            bean.setType(5);
//                            bean.setItem(content);
//                            bean.setTypeId(1);
//
//                            int index = getPosition(1, mAdapter.getData());
//                            mAdapter.addItem(index + 1, bean);
                            if (TextUtils.isEmpty(content)){
                                toast("输入内容不能为空");
                                return;
                            }
                            createDaily(content, 1);
                        }
                    }).show();

                } else if (listBean.getType() == ADD_DOING) {

                    new AddTagDialog.Builder(WriteDailyActivity.this).setListener(new AddTagDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, String content) {
//                            GetDailyListApi.ListBean bean = new GetDailyListApi.ListBean();
//                            bean.setType(5);
//                            bean.setItem(content);
//                            bean.setTypeId(2);
//
//                            int index = getPosition(2, mAdapter.getData());
//                            mAdapter.addItem(index + 1, bean);
                            if (TextUtils.isEmpty(content)){
                                toast("输入内容不能为空");
                                return;
                            }
                            createDaily(content, 2);
                        }
                    }).show();


                } else if (listBean.getType() == ADD_TOMORROW) {
                    new AddTagDialog.Builder(WriteDailyActivity.this).setListener(new AddTagDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, String content) {
//                            GetDailyListApi.ListBean bean = new GetDailyListApi.ListBean();
//                            bean.setTypeId(3);
//                            bean.setItem(content);
//                            bean.setType(5);
//
//                            int index = getPosition(3, mAdapter.getData());
//                            mAdapter.addItem(index + 1, bean);
                            if (TextUtils.isEmpty(content)){
                                toast("输入内容不能为空");
                                return;
                            }
                            createDaily(content, 3);
                        }
                    }).show();

                } else if (listBean.getType() == ADD_HELP) {
                    new AddTagDialog.Builder(WriteDailyActivity.this).setListener(new AddTagDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, String content) {
//                            GetDailyListApi.ListBean bean = new GetDailyListApi.ListBean();
//                            bean.setTypeId(4);
//                            bean.setItem(content);
//                            bean.setType(5);
//
//                            int index = getPosition(4, mAdapter.getData());
//                            mAdapter.addItem(index + 1, bean);

                            if (TextUtils.isEmpty(content)){
                                toast("输入内容不能为空");
                                return;
                            }
                            createDaily(content, 4);
                        }
                    }).show();

                } else if (listBean.getType() == ADD_NORMAL) {
                    new AddTagDialog.Builder(WriteDailyActivity.this).setContent(mList.get(position).getItem()).setListener(new AddTagDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, String content) {
                            if (TextUtils.isEmpty(content)){
                                toast("输入内容不能为空");
                                return;
                            }
                            updateDaily(content, orderId, listBean.getId(), listBean.getTypeId(), position, dialog);
                        }
                    }).show();
                }
            }
        });

        /**
         * 列表长按事件，，，，
         */
        mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position) {
                new BaseDialog.Builder<>(WriteDailyActivity.this)
                        .setContentView(R.layout.write_daily_delete_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        //.setText(id, "我是预设置的文本")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, view) -> {

                            deleteDaily(position, dialog);


                        })
                        .show();
                return false;
            }
        });
        mRecyclerview.setAdapter(mAdapter);


    }


    @Override
    protected void initData() {
        orderId = getString("orderId");
        getDailyList(orderId);
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
                        mList.clear();
                        if (data.getData() != null) {
                            GetDailyListApi.Bean bean = data.getData();
                            mList.add(new GetDailyListApi.ListBean(1));
                            List<GetDailyListApi.ListBean> done = bean.getDone();
                            mList.addAll(done);
                            mList.add(new GetDailyListApi.ListBean(2));
                            List<GetDailyListApi.ListBean> running = bean.getRunning();
                            mList.addAll(running);
                            mList.add(new GetDailyListApi.ListBean(3));
                            List<GetDailyListApi.ListBean> future = bean.getFuture();
                            mList.addAll(future);
                            mList.add(new GetDailyListApi.ListBean(4));
                            List<GetDailyListApi.ListBean> help = bean.getHelp();
                            mList.addAll(help);
                        }
//                        mList.add(new GetDailyListApi.ListBean(1));
//                        mList.add(new GetDailyListApi.ListBean(2));
//                        mList.add(new GetDailyListApi.ListBean(3));
//                        mList.add(new GetDailyListApi.ListBean(4));
                        mAdapter.setData(mList);

                    }
                });
    }

    /**
     * 创建日报
     */
    public void createDaily(String content, int type) { //类型:1.已完成/2.进行中/3.明日计划/4.需要的帮助
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

                        GetDailyListApi.ListBean bean = new GetDailyListApi.ListBean();
                        bean.setType(5);
                        bean.setItem(content);
                        bean.setTypeId(type);
                        bean.setId(Integer.valueOf(data.getData().getOrderSchedule()));

                        int index = getPosition(type, mAdapter.getData());
                        mAdapter.addItem(index + 1, bean);

//                        getDailyList(orderId);
                    }
                });
    }


    /**
     * 删除日报
     */
    public void deleteDaily(int position, BaseDialog dialog) {
        GetDailyListApi.ListBean item = mAdapter.getItem(position);
        EasyHttp.post(this)
                .api(new DeleteDailyApi().setOrderScheduleId(item.getId()))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mAdapter.removeItem(position);
                        dialog.dismiss();

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

                        mAdapter.getData().get(position).setItem(content);
                        mAdapter.notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCommit) {
            finish();
        }
    }


    /**
     * @param type
     * @param list
     * @return
     */
    public int getPosition(int type, List<GetDailyListApi.ListBean> list) {
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == type || list.get(i).getTypeId() == type) {
                position = i;
            }
        }
        return position;
    }

}
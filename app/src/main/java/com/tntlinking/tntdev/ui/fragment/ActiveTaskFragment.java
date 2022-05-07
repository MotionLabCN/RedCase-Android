package com.tntlinking.tntdev.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.GetNewbieApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;
import com.tntlinking.tntdev.ui.activity.SaveQRActivity;
import com.tntlinking.tntdev.ui.activity.SignContactActivity;
import com.tntlinking.tntdev.ui.adapter.HomeTaskAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class ActiveTaskFragment extends TitleBarFragment<MainActivity> {
    private MyListView lv_task;
    private HomeTaskAdapter mTaskAdapter;
    private List<GetNewbieApi.Bean> mTaskList = new ArrayList<>();
    public static ActiveTaskFragment newInstance() {
        return new ActiveTaskFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.active_task_fragment;
    }

    @Override
    protected void initView() {
        lv_task = findViewById(R.id.lv_task);
        mTaskAdapter = new HomeTaskAdapter(getActivity(), mTaskList);
        lv_task.setAdapter(mTaskAdapter);

        lv_task.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetNewbieApi.Bean item = mTaskAdapter.getItem(position);
                if (item.getTaskId() == 2) { //入驻任务
                    if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //做任务
                        startActivity(EnterDeveloperActivity.class);
                    } else if (item.getTaskStatus() == 2) {
                        if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// 已完成
                            startActivity(SaveQRActivity.class);
                        }
                    }
                } else if (item.getTaskId() == 3) {//签订协议任务

                    String status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
                    if (status.equals("3")){
                        if (item.getTaskStatus() == 0 || item.getTaskStatus() == 1) { //做任务
                            startActivity(SignContactActivity.class);
                        } else if (item.getTaskStatus() == 2) {
                            if (item.getRewardStatus() == 0 || item.getRewardStatus() == 1) {// 已完成
                                startActivity(SaveQRActivity.class);
                            }
                        }
                    }else {
                        new BaseDialog.Builder<>(getActivity())
                                .setContentView(R.layout.write_daily_delete_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "请先完成“完善入驻信息”任务")
                                .setText(R.id.btn_dialog_custom_cancel, "取消")
                                .setText(R.id.btn_dialog_custom_ok, "做任务")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                                    startActivity(EnterDeveloperActivity.class);
                                    dialog.dismiss();
                                })
                                .show();
                    }

                }
            }
        });
    }

    @Override
    protected void initData() {
        getNewbie();
    }

    public void getNewbie() {
        EasyHttp.get(this)
                .api(new GetNewbieApi())
                .request(new HttpCallback<HttpData<List<GetNewbieApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetNewbieApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            mTaskList.clear();
                            mTaskList.addAll(data.getData());
                            mTaskAdapter.setData(mTaskList);
                        } else {
                        }

                    }
                });
    }
}

package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.SubmitDeveloperApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.activity.AddEducationActivityNew;
import com.tntlinking.tntdev.ui.activity.AddProjectActivityNew;
import com.tntlinking.tntdev.ui.activity.AddWorkActivity;
import com.tntlinking.tntdev.ui.activity.UploadResumeActivity;
import com.tntlinking.tntdev.ui.adapter.AddEducationAdapter;
import com.tntlinking.tntdev.ui.adapter.AddProjectAdapter;
import com.tntlinking.tntdev.ui.adapter.AddWorkAdapter;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约单页面
 */
public final class ContractDetailActivity extends AppActivity {
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_work_mode;
    private TextView tv_work_salary;
    private LinearLayout ll_work_time;
    private TextView tv_work_time;
    private TextView tv_work_money;
    private TextView tv_work_time_start;
    private TextView tv_work_time_end;
    private TextView tv_work_freeze_money;
    private AppCompatButton btn_create;

    @Override
    protected int getLayoutId() {
        return R.layout.contract_detail_activity;
    }


    @Override
    protected void initView() {

        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_work_mode = findViewById(R.id.tv_work_mode);
        tv_work_salary = findViewById(R.id.tv_work_salary);
        ll_work_time = findViewById(R.id.ll_work_time);
        tv_work_time = findViewById(R.id.tv_work_time);
        tv_work_money = findViewById(R.id.tv_work_money);
        tv_work_time_start = findViewById(R.id.tv_work_time_start);
        tv_work_time_end = findViewById(R.id.tv_work_time_end);
        tv_work_freeze_money = findViewById(R.id.tv_work_freeze_money);
        btn_create = findViewById(R.id.btn_create);

        setOnClickListener(ll_work_time, btn_create);


    }


    @Override
    protected void initData() {// 一个是从简历解析传过来的，一个是进入页面接口请求显示数据的

    }


    @SingleClick
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_work_time:
                new DateSelectDialog.Builder(this).setTitle("选择日期").setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
                        String mInTime = year + "-" + Utils.formatDate(month) + "-" + day;
                        toast(mInTime);
                    }

                }).show();
                break;
            case R.id.btn_create:
                startActivity(ContractPayActivity.class);
                break;
        }

    }


    @SuppressLint("SetTextI18n")
    public void getDeveloperDetail(int parentId) {
        EasyHttp.get(this)
                .api(new GetDeveloperDetailApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {


                    }
                });
    }


}
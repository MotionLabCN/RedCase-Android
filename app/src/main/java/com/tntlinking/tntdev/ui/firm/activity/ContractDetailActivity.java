package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.PostCalculateApi;
import com.tntlinking.tntdev.http.api.SendPositionApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.GlideUtils;
import com.tntlinking.tntdev.other.TimeUtil;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约单页面
 */
public final class ContractDetailActivity extends AppActivity {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_work_mode;
    private TextView tv_work_salary;
    private LinearLayout ll_work_time;
    private TextView tv_work_time;
    private TextView tv_work_money;
    private TextView tv_work_time_start;
    private TextView tv_work_time_start_money;
    private TextView tv_work_time_end;
    private TextView tv_work_time_end_money;
    private TextView tv_work_service_money;
    private TextView tv_work_freeze_money;
    private AppCompatButton btn_create;

    @Override
    protected int getLayoutId() {
        return R.layout.contract_detail_activity;
    }


    @Override
    protected void initView() {

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_work_mode = findViewById(R.id.tv_work_mode);
        tv_work_salary = findViewById(R.id.tv_work_salary);
        ll_work_time = findViewById(R.id.ll_work_time);
        tv_work_time = findViewById(R.id.tv_work_time);
        tv_work_money = findViewById(R.id.tv_work_money);
        tv_work_time_start = findViewById(R.id.tv_work_time_start);
        tv_work_time_start_money = findViewById(R.id.tv_work_time_start_money);
        tv_work_time_end = findViewById(R.id.tv_work_time_end);
        tv_work_time_end_money = findViewById(R.id.tv_work_time_end_money);
        tv_work_freeze_money = findViewById(R.id.tv_work_freeze_money);
        tv_work_service_money = findViewById(R.id.tv_work_service_money);
        btn_create = findViewById(R.id.btn_create);

        setOnClickListener(ll_work_time, btn_create);


    }

    int positionId;
    int developerId;

    @Override
    protected void initData() {
        positionId = getInt("orderId");
        developerId = getInt("developerId");
        String name = getString("name");
        String avatarUrl = getString("avatarUrl");

        GlideUtils.loadRoundCorners(this, avatarUrl, iv_avatar,
                (int) getResources().getDimension(R.dimen.dp_8));

        tv_name.setText(name);
        String nowTime = TimeUtil.getTimeString("yyyy-MM-dd");
        EasyConfig.getInstance().addHeader("loginRole", "Recruiter");
        postCalculate(nowTime);
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
                        postCalculate(mInTime);
                    }

                }).show();
                break;
            case R.id.btn_create:
                startActivity(ContractPayActivity.class);
                break;
        }

    }

    //订单前置计算
    public void postCalculate(String date) {
        EasyHttp.post(this)
                .api(new PostCalculateApi()
                        .setDeveloperId(developerId)
                        .setPositionId(positionId)
                        .setWorkStartDate(date))
                .request(new HttpCallback<HttpData<PostCalculateApi.Bean>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<PostCalculateApi.Bean> data) {
                        List<PostCalculateApi.Bean.PreListBean> preList = data.getData().getPreList();
                        if (preList.size() != 0) {
                            if (preList.size() == 1) {
                                PostCalculateApi.Bean.PreListBean bean = preList.get(0);
                                tv_work_time_start.setText(bean.getWorkStartDate() + "-" + bean.getWorkEndDate());
                                tv_work_time_start_money.setText(bean.getServiceMoney() + "");
                                tv_work_time_end.setVisibility(View.GONE);
                                tv_work_time_end_money.setVisibility(View.GONE);
                            } else if (preList.size() == 2) {
                                PostCalculateApi.Bean.PreListBean bean1 = preList.get(0);
                                tv_work_time_start.setText(bean1.getWorkStartDate() + "-" + bean1.getWorkEndDate());
                                tv_work_time_start_money.setText(bean1.getServiceMoney() + "");
                                PostCalculateApi.Bean.PreListBean bean2 = preList.get(1);
                                tv_work_time_end.setText(bean2.getWorkStartDate() + "-" + bean2.getWorkEndDate());
                                tv_work_time_end_money.setText(bean2.getServiceMoney() + "");

                            }
                            tv_work_money.setText(data.getData().getTotalAmount() + "");
                            tv_work_service_money.setText(data.getData().getServiceAmount() + "");
                            tv_work_freeze_money.setText(data.getData().getFreezeAmount() + "");
                        }
                    }
                });
    }

}
package com.tntlinking.tntdev.ui.firm.activity;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmCompanyInfoApi;
import com.tntlinking.tntdev.http.api.QuitCompanyApi;
import com.tntlinking.tntdev.http.api.ToSignApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.BrowserActivity;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 发布职位信息
 */
public final class MyCompanyActivity extends AppActivity {
    private TitleBar title_bar;
    private TextView tv_company_name;
    private TextView tv_company;
    private TextView tv_company_desc;
    private AppCompatButton btn_leave;
    private AppCompatButton btn_change;

    @Override
    protected int getLayoutId() {
        return R.layout.my_company_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_company = findViewById(R.id.tv_company);
        tv_company_desc = findViewById(R.id.tv_company_desc);
        btn_leave = findViewById(R.id.btn_leave);
        btn_change = findViewById(R.id.btn_change);


        btn_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BaseDialog.Builder<>(MyCompanyActivity.this)
                        .setContentView(R.layout.three_tips_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title, "离开公司可能造成的影响")
                        .setText(R.id.tv_tips_1, "·无法查看当前公司下的所有信息")
                        .setText(R.id.tv_tips_2, "·发布中的人力需求会被关闭")
                        .setText(R.id.tv_tips_3, "·失去所有企业账户相关权益")
                        .setText(R.id.btn_dialog_custom_cancel, "取消")
                        .setText(R.id.btn_dialog_custom_ok, "仍要离开")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {


                        }).show();
            }
        });


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(FirmCertificationActivity.class);
                finish();

            }
        });
    }


    @Override
    protected void initData() {
        getCompanyInfo();
    }

    /**
     * 获取公司信息
     */
    @SuppressLint("SetTextI18n")
    public void getCompanyInfo() {
        EasyHttp.get(this)
                .api(new GetFirmCompanyInfoApi())
                .request(new HttpCallback<HttpData<GetFirmCompanyInfoApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmCompanyInfoApi.Bean> data) {
                        GetFirmCompanyInfoApi.Bean bean = data.getData();
                        if (bean != null) {
                            tv_company.setText(bean.getShortName());
                            tv_company_name.setText(bean.getCompanyName());
                            tv_company_desc.setText(bean.getIndustry() + "·" + bean.getPersonSize());
                        }
                    }
                });
    }


    /**
     * 离开公司
     */
    @SuppressLint("SetTextI18n")
    public void quitCompany() {
        EasyHttp.post(this)
                .api(new QuitCompanyApi())
                .request(new HttpCallback<HttpData<GetFirmCompanyInfoApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetFirmCompanyInfoApi.Bean> data) {

                    }
                });
    }
}
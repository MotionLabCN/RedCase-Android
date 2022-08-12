package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.CreateOrderApi;
import com.tntlinking.tntdev.http.api.GetAdminPhoneApi;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.api.SendAdminSmsApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

/**
 *
 */
public final class ContractPayCodeActivity extends AppActivity {
    private TextView tv_iphone;
    private AppCompatEditText et_safe_code;
    private CountdownView cv_countdown;


    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_code_activity;
    }


    @Override
    protected void initView() {

        tv_iphone = findViewById(R.id.tv_iphone);
        et_safe_code = findViewById(R.id.et_safe_code);
        cv_countdown = findViewById(R.id.cv_countdown);

        cv_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAdminSms(getString("orderIds"));
            }
        });
    }


    @Override
    protected void initData() {

        getAdminPhone();
    }


    /**
     * 获取企业管理员手机号
     */
    public void getAdminPhone() {
        EasyHttp.get(this)
                .api(new GetAdminPhoneApi())
                .request(new HttpCallback<HttpData<String>>(this) {
                    @Override
                    public void onSucceed(HttpData<String> data) {

                        String iphone = Utils.changPhoneNumber(data.getData());
                        tv_iphone.setText(iphone);
                    }
                });
    }

    /**
     * 获取验证码
     */
    public void sendAdminSms(String orderId) {
        EasyHttp.get(this)
                .api(new SendAdminSmsApi().setOrderIds(orderId))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {


                    }
                });
    }

}
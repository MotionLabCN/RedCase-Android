package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetAdminPhoneApi;
import com.tntlinking.tntdev.http.api.OrderPayApi;
import com.tntlinking.tntdev.http.api.SendAdminSmsApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.widget.PasswordInputView;


/**
 *
 */
public final class ContractPayCodeActivity extends AppActivity {
    private TextView tv_iphone;
    private TextView tv_error;
    private PasswordInputView pwd_input;
    private CountdownView cv_countdown;


    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_code_activity;
    }


    @Override
    protected void initView() {

        tv_iphone = findViewById(R.id.tv_iphone);
        cv_countdown = findViewById(R.id.cv_countdown);
        tv_error = findViewById(R.id.tv_error);
        pwd_input = findViewById(R.id.pwd_input);
        cv_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAdminSms(getString("orderIds"));
            }
        });
        pwd_input.setInputListener(new PasswordInputView.InputListener() {
            @Override
            public void onInputCompleted(String text) {
                if (!TextUtils.isEmpty(text)) {
                    orderPay(getString("orderIds"), text);
                }
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
                        tv_error.setVisibility(View.INVISIBLE);
                        cv_countdown.start();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);

                    }
                });
    }

    /**
     * 订单支付
     */
    public void orderPay(String orderId, String smscode) {
        EasyHttp.post(this)
                .api(new OrderPayApi().setOrders(orderId).setSmsCode(smscode))
                .request(new HttpCallback<HttpData<String>>(this) {
                    @Override
                    public void onSucceed(HttpData<String> data) {
                        if (!TextUtils.isEmpty(data.getData())) {
                            Intent intent = new Intent(ContractPayCodeActivity.this, ContractResultStatusActivity.class);
                            intent.putExtra("freezeStatus", data.getData());
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        tv_error.setVisibility(View.VISIBLE);
                    }
                });
    }

}
package com.tntlinking.tntdev.ui.activity;


import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetSignParamApi;
import com.tntlinking.tntdev.http.api.ToSignApi;
import com.tntlinking.tntdev.http.model.HttpData;

import androidx.appcompat.widget.AppCompatButton;


/**
 * 去签约页面
 */
public final class SignInfoActivity extends AppActivity {
    private ClearEditText et_sign_info_name;
    private ClearEditText et_sign_info_phone;
    private ClearEditText et_sign_info_idcard;
    private ClearEditText et_sign_info_bank_number;
    private ClearEditText et_sign_info_bank_name;

    private AppCompatButton btn_next;

    private String mName = "";
    private String mPhone = "";
    private String mIdCard = "";
    private String mBankName = "";
    private String mBankNumber = "";

    @Override
    protected int getLayoutId() {
        return R.layout.sign_info_activity;
    }

    @Override
    protected void initView() {
        et_sign_info_name = findViewById(R.id.et_sign_info_name);
        et_sign_info_phone = findViewById(R.id.et_sign_info_phone);
        et_sign_info_idcard = findViewById(R.id.et_sign_info_idcard);
        et_sign_info_bank_number = findViewById(R.id.et_sign_info_bank_number);
        et_sign_info_bank_name = findViewById(R.id.et_sign_info_bank_name);
        btn_next = findViewById(R.id.btn_next);

        setOnClickListener(btn_next);
    }


    @Override
    protected void initData() {
        getSignParam();
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                mName = et_sign_info_name.getText().toString();
                mPhone = et_sign_info_phone.getText().toString();
                mIdCard = et_sign_info_idcard.getText().toString();
                mBankNumber = et_sign_info_bank_number.getText().toString();
                mBankName = et_sign_info_bank_name.getText().toString();

                if (TextUtils.isEmpty(mName)) {
                    toast("您的姓名输入有误");
                    return;
                }
                if (TextUtils.isEmpty(mPhone) && mPhone.length() < 11) {
                    toast("您的手机号码输入有误");
                    return;
                }
                if (TextUtils.isEmpty(mIdCard) && RegexUtils.isIDCard18(mIdCard)) {
                    toast("您的证件号码输入有误");
                    return;
                }
                if (TextUtils.isEmpty(mBankNumber) && mBankNumber.length() < 20) {
                    toast("您的银行卡号输入有误");
                    return;
                }
                if (TextUtils.isEmpty(mBankName)) {
                    toast("您的发卡行名输入有误");
                    return;
                }
                toSign();

                break;

        }
    }


    public void getSignParam() {
        EasyHttp.get(this)
                .api(new GetSignParamApi())
                .request(new HttpCallback<HttpData<GetSignParamApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetSignParamApi.Bean> data) {
                        if (data.getData() != null) {

                            et_sign_info_name.setText(data.getData().getRealName());
                            et_sign_info_phone.setText(data.getData().getMobile());
                            et_sign_info_name.setSelection(et_sign_info_name.getText().toString().length());
                            et_sign_info_phone.setSelection(et_sign_info_phone.getText().toString().length());
                        }

                    }
                });
    }

    public void toSign() {
        EasyHttp.post(this)
                .api(new ToSignApi()
                        .setRealName(mName)
                        .setMobile(mPhone)
                        .setIdNumber(mIdCard)
                        .setBankName(mBankName)
                        .setBankCardId(mBankNumber))
                .request(new HttpCallback<HttpData<ToSignApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<ToSignApi.Bean> data) {
                        if (data.getData() != null) {
                            if (!TextUtils.isEmpty(data.getData().getContractUrl())) {
                                BrowserActivity.start(getActivity(), data.getData().getContractUrl());
                            }
                        }
                    }
                });
    }

}
package com.tntlinking.tntdev.ui.activity;



import android.view.View;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.model.HttpData;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


/**
 * 用户信息填写页面1
 */
public final class SignInfoActivity extends AppActivity {
    private ClearEditText et_sign_info_name;
    private ClearEditText et_sign_info_phone;
    private ClearEditText et_sign_info_idcard;
    private ClearEditText et_sign_info_bank_number;
    private ClearEditText et_sign_info_bank_name;

    private AppCompatButton btn_next;


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

        setOnClickListener( btn_next);
    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:


                break;

        }
    }



    public void GetProvince() {
        EasyHttp.get(this)
                .api(new GetProvinceApi())
                .request(new HttpCallback<HttpData<List<GetProvinceApi.ProvinceBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetProvinceApi.ProvinceBean>> data) {
                        if (!data.getData().isEmpty()) {

                            SPUtils.getInstance().put("province", GsonUtils.toJson(data.getData()));
                        }
                    }
                });


    }

}
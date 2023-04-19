package com.tntlinking.tntdev.ui.firm.activity;


import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetCompanyInfoApi;
import com.tntlinking.tntdev.http.api.InviteMembersApi;
import com.tntlinking.tntdev.http.api.developerBillListApi;
import com.tntlinking.tntdev.http.model.HttpData;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 邀请成员
 */
public final class InterviewPersonActivity extends AppActivity {
    private ClearEditText et_name;
    private ClearEditText et_phone;
    private ClearEditText et_position;
    private ClearEditText et_email;
    private TextView tv_address;
    private AppCompatButton btn_commit;


    @Override
    protected int getLayoutId() {
        return R.layout.interview_person_activity;
    }

    @Override
    protected void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_position = findViewById(R.id.et_position);
        et_email = findViewById(R.id.et_email);
        tv_address = findViewById(R.id.tv_address);
        btn_commit = findViewById(R.id.btn_commit);

        setOnClickListener(btn_commit);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        GetCompanyInfoApi();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String mName = et_name.getText().toString();
                String mPhone = et_phone.getText().toString();
                String mPosition = et_position.getText().toString();
                String mEmail = et_email.getText().toString();
                if (TextUtils.isEmpty(mName)) {
                    toast("请输入正确名字");
                    return;
                }
                if (TextUtils.isEmpty(mPhone)) {
                    toast("请输入正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(mPosition)) {
                    toast("请输入职位");
                    return;
                }
                if (TextUtils.isEmpty(mEmail)) {
                    toast("请输入正确邮箱号");
                    return;
                }

                InviteMember(mEmail, mPhone, mPosition, mName);
                break;

        }

    }

    private void InviteMember(String email, String mobile, String position, String name) {
        EasyHttp.post(this)
                .api(new InviteMembersApi()
                        .setEmail(email)
                        .setMobile(mobile)
                        .setPosition(position)
                        .setRealName(name))
                .request(new HttpCallback<HttpData<developerBillListApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<developerBillListApi.Bean> data) {
                        toast("邀请已发送");
                        finish();
                    }

                });
    }

    /**
     * 获取企业地址
     */
    private void GetCompanyInfoApi() {
        EasyHttp.get(this)
                .api(new GetCompanyInfoApi())
                .request(new HttpCallback<HttpData<GetCompanyInfoApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetCompanyInfoApi.Bean> data) {
                        if (data.getData() != null) {
                            tv_address.setText(data.getData().getAddress());
                        }

                    }
                });

    }
}
package com.tntlinking.tntdev.ui.firm.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyLog;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.FirmCertificationApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.api.SendCompanyEmailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.AddEducationActivityNew;
import com.tntlinking.tntdev.ui.activity.EnterDeveloperActivity;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;
import com.tntlinking.tntdev.ui.dialog.ProvinceSelectDialog;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 企业认证页面
 */
public final class FirmCertificationActivity extends AppActivity {
    private ClearEditText et_name;
    private TextView tv_company_name;
    private TextView tv_new_company;
    private ClearEditText et_position;
    private ClearEditText et_mobile;
    private ClearEditText et_email;
    private CountdownView cv_countdown;
    private ClearEditText et_email_code;
    private SettingBar sb_area;
    private ClearEditText et_address;
    private AppCompatButton btn_commit;

    private String mRealName;
    private int mCompanyId;
    private String mPosition = "";
    private String mMobile;
    private String mEmail;
    private String mEmailCode;
    private int mProvinceId = 0;
    private int mCityId = 0;
    private int mAreasId = 0;
    private String mAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_certification_activity;
    }

    @Override
    protected void initView() {

        et_name = findViewById(R.id.et_name);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_new_company = findViewById(R.id.tv_new_company);
        et_position = findViewById(R.id.et_position);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        cv_countdown = findViewById(R.id.cv_countdown);
        et_email_code = findViewById(R.id.et_email_code);
        sb_area = findViewById(R.id.sb_area);
        et_address = findViewById(R.id.et_address);
        btn_commit = findViewById(R.id.btn_commit);


        setOnClickListener(tv_company_name, tv_new_company, cv_countdown, sb_area, btn_commit);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {
            GetProvince();
        }

    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company_name:
                Intent intent = new Intent(this, SearchCompanySelectActivity.class);
                getActivity().startActivityForResult(intent, 10000);
                break;
            case R.id.tv_new_company://新建公司
                startActivity(NewCompanyActivity.class);
                break;
            case R.id.cv_countdown:
                getEmailCode(et_email.getText().toString());
                break;
            case R.id.sb_area:
                if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {

                    return;
                }

                new ProvinceSelectDialog.Builder(this).setTitle("选择所在地").setListener(new ProvinceSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, GetProvinceApi.ProvinceBean province, GetProvinceApi.AreaBean city, GetProvinceApi.CityBean area) {
                        // 省 市 区
                        sb_area.setLeftText(province.getRegionName() + "-" + city.getRegionName() + "-" + area.getRegionName());

                        mProvinceId = province.getId();
                        mCityId = city.getId();
                        mAreasId = area.getId();
                    }
                }).show();
                break;

            case R.id.btn_commit:
                mRealName = et_name.getText().toString();
                mPosition = et_position.getText().toString();
                mEmail = et_email.getText().toString();
                mEmailCode = et_email_code.getText().toString();
                mAddress = et_address.getText().toString();
//                startActivity(MyCompanyActivity.class);
                if (TextUtils.isEmpty(mRealName)) {
                    toast("你还没有填写真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(mEmail)) {
                    toast("你还没有填写真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(mAddress)) {
                    toast("你还没有填写详情地址");
                    return;
                }

                firmCertification();
                break;

        }

    }

    /**
     *
     */
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

    public void getEmailCode(String mEmail) {
        EasyHttp.post(this)
                .api(new SendCompanyEmailApi()
                        .setCompanyId(mCompanyId)
                        .setEmail(mEmail))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {

                    }
                });
    }

    public void firmCertification() {
        EasyHttp.post(this)
                .api(new FirmCertificationApi()
                        .setRealName(mRealName)
                        .setCompanyId(mCompanyId)
                        .setPositionName(mPosition)
                        .setEmail(mEmail)
                        .setEmailCode(mEmailCode)
                        .setProvinceId(mProvinceId)
                        .setCityId(mCityId)
                        .setAreaId(mAreasId)
                        .setAddress(mAddress))
                .request(new HttpCallback<HttpData<DeveloperInfoBean.DeveloperWork>>(this) {

                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean.DeveloperWork> data) {

                        toast("申请成功");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int companyId = data.getIntExtra("companyId", 0);
            String companyName = data.getStringExtra("companyName");
            mCompanyId = companyId;
            tv_company_name.setText(companyName);
        }
    }

}
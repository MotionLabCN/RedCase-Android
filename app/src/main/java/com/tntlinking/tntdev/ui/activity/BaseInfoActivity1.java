package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.api.GetProvinceApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.tntlinking.tntdev.ui.dialog.DateSelectDialog;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;
import com.tntlinking.tntdev.ui.dialog.GenderSelectDialog;
import com.tntlinking.tntdev.ui.dialog.ProvinceSelectDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


/**
 * 用户信息填写页面1
 */
public final class BaseInfoActivity1 extends AppActivity {
    private ClearEditText mInfoName;
    private SettingBar mInfoGender;
    private SettingBar mInfoBirth;
    private SettingBar mInfoAddress;
    private SettingBar mInfoReason;
    private AppCompatButton mNext;

    private SendDeveloperBean postBean = SendDeveloperBean.getSingleton();

    @Override
    protected int getLayoutId() {
        return R.layout.baseinfo_activity_1;
    }

    @Override
    protected void initView() {
        mInfoName = findViewById(R.id.et_name);
        mInfoGender = findViewById(R.id.info_gender);
        mInfoBirth = findViewById(R.id.info_birth);
        mInfoAddress = findViewById(R.id.info_address);
        mInfoReason = findViewById(R.id.info_reason);
        mNext = findViewById(R.id.btn_baseinfo_next);

        setOnClickListener(mInfoGender, mInfoBirth, mInfoAddress, mInfoReason, mNext);
    }

    private List<GetDictionaryApi.DictionaryBean> mDictionaryList;

    @Override
    protected void initData() {
        mDictionaryList = getDictionaryList("8");//获取远程办公原因list

        if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {
            GetProvince();
        }
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_gender:

                new GenderSelectDialog.Builder(this)
                        .setTitle("选择性别")
                        .setList("男", "女").setListener(new GenderSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {
                        if (type == 0) {
                            mInfoGender.setLeftText("男");
                        } else {
                            mInfoGender.setLeftText("女");
                        }
                        postBean.setSex(type);
                        postBean.setmSex(mInfoGender.getLeftText().toString());
                    }
                }).show();


                break;
            case R.id.info_birth:
                new DateSelectDialog.Builder(this).setListener(new DateSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int year, int month, int day) {
//                        String birth = year + "." + month + "." + day;
//                        mInfoBirth.setLeftText(birth);
                        String mBirthday = year + "-" + Utils.formatDate(month) + "-" + Utils.formatDate(day);
                        mInfoBirth.setLeftText(mBirthday);
                        postBean.setBirthday(mBirthday);
                    }

                }).show();
                break;
            case R.id.info_address:
                if (TextUtils.isEmpty(SPUtils.getInstance().getString("province"))) {

                    return;
                }

                new ProvinceSelectDialog.Builder(this).setTitle("选择所在地").setListener(new ProvinceSelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, GetProvinceApi.ProvinceBean province,
                                           GetProvinceApi.AreaBean city, GetProvinceApi.CityBean area) {
                        // 省 市 区
                        postBean.setProvinceId(province.getId());
                        postBean.setCityId(city.getId());
                        postBean.setAreasId(area.getId());


                        postBean.setmProvince(province.getRegionName());
                        postBean.setmCity(city.getRegionName());
                        postBean.setmArea(area.getRegionName());


                        mInfoAddress.setLeftText(province.getRegionName() + "-" + city.getRegionName() + "-" + area.getRegionName());

                    }
                }).show();

                break;
            case R.id.info_reason:
                new DictionarySelectDialog.Builder(this)
                        .setTitle("选择原因")
                        .setList(mDictionaryList).setListener(new DictionarySelectDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int type) {
                        mInfoReason.setLeftText(mDictionaryList.get(type).getName());

                        postBean.setRemoteWorkReason(mDictionaryList.get(type).getId());
                        postBean.setmRemoteWorkReason(mDictionaryList.get(type).getName());
                    }
                }).show();

                break;

            case R.id.btn_baseinfo_next:
                String name = mInfoName.getText().toString();
                if (TextUtils.isEmpty(name) && name.length() < 2) {
                    toast("没有输入用户名或者输入长度不够");
                    return;
                }
                if (TextUtils.isEmpty(postBean.getmSex())) {
                    toast("没选择性别");
                    return;
                }
                if (TextUtils.isEmpty(postBean.getBirthday())) {
                    toast("没选择出生年龄");
                    return;
                }
                if (TextUtils.isEmpty(postBean.getmProvince())) {
                    toast("没选择地区");
                    return;
                }
                if (TextUtils.isEmpty(postBean.getmRemoteWorkReason())) {
                    toast("没选择办公原因");
                    return;
                }

                postBean.setRealName(mInfoName.getText().toString());
                Intent intent = new Intent(this, BaseInfoActivity2.class);
                intent.putExtra("postBean", postBean);

                startActivity(intent);

                break;
        }
    }

    /**
     * (1->行业 || 2->人员规模 || 3->常用协作工具 || 4->经验要求 || 5->学历要求 6->职业方向
     * || 7->培养方式 || 8->远程工作原因 || 9->职业状态 || 10->工作方式 || 11 ->面试方式)
     */
    public List<GetDictionaryApi.DictionaryBean> getDictionaryList(String parentId) {
        List<GetDictionaryApi.DictionaryBean> mList = new ArrayList();
        EasyHttp.get(this)
                .api(new GetDictionaryApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<List<GetDictionaryApi.DictionaryBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetDictionaryApi.DictionaryBean>> data) {
                        if (!data.getData().isEmpty()) {
                            mList.addAll(data.getData());

                        }
                    }
                });

        return mList;
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
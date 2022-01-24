package com.tntlinking.tntdev.ui.activity;


import android.view.View;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDictionaryApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.bean.SendDeveloperBean;
import com.tntlinking.tntdev.ui.dialog.DictionarySelectDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


/**
 * 用户信息填写页面1
 */
public final class InterviewActivity extends AppActivity {
    private ClearEditText mInfoName;
    private SettingBar mInfoGender;
    private SettingBar mInfoBirth;
    private SettingBar mInfoAddress;
    private SettingBar mInfoReason;
    private AppCompatButton btn_next;

    private SendDeveloperBean postBean = SendDeveloperBean.getSingleton();

    @Override
    protected int getLayoutId() {
        return R.layout.interview_activity;
    }

    @Override
    protected void initView() {
//        mInfoName = findViewById(R.id.et_name);
//        mInfoGender = findViewById(R.id.info_gender);
//        mInfoBirth = findViewById(R.id.info_birth);
        btn_next = findViewById(R.id.btn_next);


        setOnClickListener(btn_next);
    }


    @Override
    protected void initData() {
//        mDictionaryList = getDictionaryList("8");//获取远程办公原因list

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                startActivity(SaveQRActivity.class);
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


}
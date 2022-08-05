package com.tntlinking.tntdev.ui.firm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.activity.UploadResumeActivity;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 签约支付页面
 */
public final class ContractPayActivity extends AppActivity {
    private AppCompatButton mCommit;
    private ScrollView sv;



    @Override
    protected int getLayoutId() {
        return R.layout.contract_pay_activity;
    }


    @Override
    protected void initView() {

        sv = findViewById(R.id.sv);


        mCommit = findViewById(R.id.btn_commit);
        setOnClickListener(mCommit);


    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_commit:
                new BaseDialog.Builder<>(getActivity())
                        .setContentView(R.layout.check_order_status_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title, "订单支付")
                        .setText(R.id.tv_content, "您有订单还未支付，是否退出！")
                        .setText(R.id.btn_dialog_custom_cancel, "取消支付")
                        .setText(R.id.btn_dialog_custom_ok, "继续支付")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                            toast("继续支付");
                        }).show();
                break;
            case R.id.ll_import_resume:
                startActivity(UploadResumeActivity.class);
                finish();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int developId = SPUtils.getInstance().getInt(AppConfig.DEVELOPER_ID);
            getDeveloperDetail(developId);
        }
    }


    @SuppressLint("SetTextI18n")
    public void getDeveloperDetail(int parentId) {
        EasyHttp.get(this)
                .api(new GetDeveloperDetailApi().setParentId(parentId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {


                    }
                });
    }


}
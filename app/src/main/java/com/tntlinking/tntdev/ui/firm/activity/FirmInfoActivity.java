package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmPositionApi;
import com.tntlinking.tntdev.http.api.GetPositionOriginalApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.activity.SaveQRActivity;


import java.io.Serializable;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 企业信息页面
 */
public final class FirmInfoActivity extends AppActivity {

    private AppCompatButton btn_modify;
    private AppCompatButton btn_contact;
    private TextView tv_position;
    private TextView tv_company_name;
    private TextView tv_position_desc;
    private TextView tv_reason;

    @Override
    protected int getLayoutId() {
        return R.layout.firm_info_activity;
    }


    @Override
    protected void initView() {

        tv_position = findViewById(R.id.tv_position);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_position_desc = findViewById(R.id.tv_position_desc);
        tv_reason = findViewById(R.id.tv_reason);
        btn_modify = findViewById(R.id.btn_modify);
        btn_contact = findViewById(R.id.btn_contact);

        setOnClickListener(btn_modify, btn_contact);


    }


    @Override
    protected void initData() {
        GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");

        if (bean != null) {
            tv_position.setText(bean.getCareerDirection());
            tv_company_name.setText(bean.getCompanyName());
            tv_position_desc.setText(bean.getTrainingMode() + "·" + bean.getEducation() + "·" + bean.getWorkYears() + "·" + bean.getIndustryName());
            tv_reason.setText(bean.getAuditFailReason());

            getPositionOriginal(bean.getId());
        }


    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify:
                GetFirmPositionApi.Bean.ListBean bean = getSerializable("position_bean");
                Intent intent = new Intent(this, SendPositionActivity.class);
                intent.putExtra("position_bean", bean);
                intent.putExtra("position_bean_ids", beanIds);
                startActivity(intent);
                break;
            case R.id.btn_contact:
                new BaseDialog.Builder<>(this)
                        .setContentView(R.layout.bottom_dialog)
                        .setAnimStyle(BaseDialog.ANIM_BOTTOM)
                        //.setText(id, "我是预设置的文本")
                        .setOnClickListener(R.id.iv_close, (dialog, views) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_commit, (dialog, views) -> {
                            Intent intent1 = new Intent();
                            intent1.setClass(this, SaveQRActivity.class);
                            intent1.putExtra("contact", "contact");
                            startActivity(intent1);
                        }).show();
                break;
        }

    }
    GetPositionOriginalApi.Bean beanIds;
    //获取职位相关id
    private void getPositionOriginal(int positionId) {
        EasyHttp.get(this)
                .api(new GetPositionOriginalApi().setPositionId(positionId))
                .request(new HttpCallback<HttpData<GetPositionOriginalApi.Bean>>(this) {
                    @Override
                    public void onSucceed(HttpData<GetPositionOriginalApi.Bean> data) {
                         beanIds = data.getData();

                    }

                });
    }
}
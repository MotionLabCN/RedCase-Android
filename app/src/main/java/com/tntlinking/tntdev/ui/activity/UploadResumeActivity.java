package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.other.AppConfig;

import java.io.File;

public class UploadResumeActivity extends AppActivity {
    private LinearLayout ll_wechat_upload;
    private LinearLayout ll_mobile_upload;
    private LinearLayout ll_other_uploads;

    @Override
    protected int getLayoutId() {
        return R.layout.uploadresume_activity;
    }

    @Override
    protected void initView() {
        ll_wechat_upload = findViewById(R.id.ll_wechat_upload);
        ll_mobile_upload = findViewById(R.id.ll_mobile_upload);
        ll_other_uploads = findViewById(R.id.ll_other_uploads);
        setOnClickListener(ll_wechat_upload, ll_mobile_upload, ll_other_uploads);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat_upload:
                String appId = "wxd930ea5d5a258f4f"; // 填移动应用(App)的 AppId，非小程序的 AppID
                IWXAPI api = WXAPIFactory.createWXAPI(UploadResumeActivity.this, appId);

                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_d43f693ca31f"; // 填小程序原始id
                req.path = "path";                  ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                break;
            case R.id.ll_mobile_upload:
                break;
            case R.id.ll_other_uploads:
                break;
        }
    }
}

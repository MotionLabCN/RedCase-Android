package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;


public class UploadResumeActivity extends AppActivity implements IWXAPIEventHandler {

    @Override
    protected int getLayoutId() {
        return R.layout.uploadresume_activity;
    }

    @Override
    protected void initView() {
        LinearLayout ll_wechat_upload = findViewById(R.id.ll_wechat_upload);
        LinearLayout ll_mobile_upload = findViewById(R.id.ll_mobile_upload);
        LinearLayout ll_other_uploads = findViewById(R.id.ll_other_uploads);
        setOnClickListener(ll_wechat_upload, ll_mobile_upload, ll_other_uploads);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat_upload:
                IWXAPI wxApi = WXAPIFactory.createWXAPI(this, null);
                wxApi.registerApp("wx1e91399d09c1cd9a");
                boolean bIsWXAppInstalled;
                bIsWXAppInstalled = wxApi.isWXAppInstalled();
                if(!bIsWXAppInstalled){
                    Toast.makeText(this,"请先安装微信",Toast.LENGTH_SHORT).show();
                    return;
                }
                String appId = "wx1e91399d09c1cd9a"; // 填移动应用(App)的 AppId，非小程序的 AppID
                IWXAPI api = WXAPIFactory.createWXAPI(UploadResumeActivity.this, appId);
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_33ffbefbd529"; // 填小程序id
                //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                req.path = "pages/resumeUpload/index";
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                break;
            case R.id.ll_mobile_upload:
                break;
            case R.id.ll_other_uploads:
                break;
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {


    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
        }
    }
}

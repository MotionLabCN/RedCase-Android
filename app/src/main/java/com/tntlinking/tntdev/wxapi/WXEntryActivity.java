package com.tntlinking.tntdev.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.ui.activity.ResumeAnalysisActivity;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.xiaomi.mipush.sdk.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * desc   : 微信登录回调（请注意这个 Activity 放置的包名要和当前项目的包名保持一致，否则将不能正常回调）
 */
public final class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;

    private static class MyHandler extends Handler {

        public MyHandler(WXEntryActivity wxEntryActivity) {
            WeakReference<WXEntryActivity> wxEntryActivityWeakReference = new WeakReference<>(wxEntryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        MyHandler handler = new MyHandler(this);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.setting_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.setting_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.setting_cancel_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.setting_unsupported;
                break;
            default:
                break;
        }

        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProgramResp = (WXLaunchMiniProgram.Resp) resp;
            //**************解析小程序传递的json数据******************
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(launchMiniProgramResp.extMsg);
                String mFilename = jsonObject.getString("fileName");
                String mUrl = jsonObject.getString("url");
                Intent intent = new Intent(this, ResumeAnalysisActivity.class);
                intent.putExtra("url", mUrl);
                intent.putExtra("fileName", mFilename);
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

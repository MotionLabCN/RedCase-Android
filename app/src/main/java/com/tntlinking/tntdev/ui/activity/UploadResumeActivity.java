package com.tntlinking.tntdev.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.ParseResumeApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;

import java.io.File;
import java.util.List;


public final class UploadResumeActivity extends AppActivity implements IWXAPIEventHandler {
    private final int REQUEST_CODE_FROM_ACTIVITY = 1000;

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
                String appId = "wx1e91399d09c1cd9a"; // 填移动应用(App)的 AppId，非小程序的 AppID
                // IWXAPI 是第三方app和微信通信的openapi接口
                IWXAPI api = WXAPIFactory.createWXAPI(this, appId, false);
                api.registerApp("wx1e91399d09c1cd9a");
                boolean bIsWXAppInstalled;
                bIsWXAppInstalled = api.isWXAppInstalled();
                if (!bIsWXAppInstalled) {
                    Toast.makeText(this, "请先安装微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = "gh_33ffbefbd529"; // 填小程序id
                //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                String mobile = (SPUtils.getInstance().getString(AppConfig.DEVELOP_MOBILE));//开发者手机号码
                req.path = "pages/resumeUpload/index?phone=" + mobile;
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                break;
            case R.id.ll_mobile_upload:
//                resumeUpload();
                pickFile(null);
                break;
            case R.id.ll_other_uploads:
                startActivity(UploadOtherActivity.class);
                break;
        }
    }


    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
    }

    /**
     * 点击打开文件选择器
     */


    public void pickFile(View view) {
        new LFilePicker()
                .withActivity(UploadResumeActivity.this)
                .withRequestCode(REQUEST_CODE_FROM_ACTIVITY)
                .withFileFilter(new String[]{".txt", ".png", ".doc", ".pdf", ".JPG"})
                .withIsGreater(false)
                .withFileSize(7 * 1024 * 1024)// 选择文件不能大于7M
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FROM_ACTIVITY) {
                //如果是文件选择模式，需要获取选择的所有文件的路径集合
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                if (list.size() > 0) {
                    if (!TextUtils.isEmpty(list.get(0))) {
                        parseResume(new File(list.get(0)));
                    }
                }
            }
        }
    }


    private void parseResume(File file) {
        EasyHttp.post(this)
                .api(new ParseResumeApi().setFile(file))
                .request(new HttpCallback<HttpData<ParseResumeApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<ParseResumeApi.Bean> data) {
                        String url = data.getData().getUrl();
                        String fileName = data.getData().getFileName();
                        new BaseDialog.Builder<>(UploadResumeActivity.this)
                                .setContentView(R.layout.check_order_status_dialog)
                                .setAnimStyle(BaseDialog.ANIM_SCALE)
                                .setText(R.id.tv_title, "简历上传")
                                .setText(R.id.tv_content, "是否将简历上传到天天数链开发者")
                                .setText(R.id.btn_dialog_custom_cancel, "取消")
                                .setText(R.id.btn_dialog_custom_ok, "确认")
                                .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                                .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                                    Intent intent = new Intent(UploadResumeActivity.this, ResumeAnalysisActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("fileName", fileName);
                                    startActivity(intent);
                                    dialog.dismiss();
                                    finish();
                                }).show();
                    }
                });
    }
}

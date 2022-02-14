package com.tntlinking.tntdev.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.util.Util;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengShare;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.InvitationUrlApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.Utils;
import com.tntlinking.tntdev.ui.dialog.ShareAppDialog;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 用户审核失败页面
 */
public final class PosterActivity extends AppActivity {

    private AppCompatButton btn_next;
    private String shareUrl = "https://stage-ttchain.tntlinking.com";
    private ImageView iv_qr;

    @Override
    protected int getLayoutId() {
        return R.layout.poster_activity;
    }

    @Override
    protected void initView() {
        btn_next = findViewById(R.id.btn_next);
        iv_qr = findViewById(R.id.iv_qr);

        setOnClickListener(btn_next);
    }

    @Override
    protected void initData() {
        getInvitationUrl();

    }


    @SingleClick
    @Override
    public void onClick(View view) {

        if (!TextUtils.isEmpty(shareUrl)) {
            UMWeb content = new UMWeb(shareUrl);
            content.setTitle(getString(R.string.app_name));
            content.setThumb(new UMImage(this, R.mipmap.app_logo));
            content.setDescription(getString(R.string.app_name));
            // 分享对话框
            new ShareAppDialog.Builder(this)
                    .setShareLink(content)
                    .setListener(new UmengShare.OnShareListener() {

                        @Override
                        public void onSucceed(Platform platform) {
                            toast("分享成功");
                        }

                        @Override
                        public void onError(Platform platform, Throwable t) {
                            toast(t.getMessage());
                        }

                        @Override
                        public void onCancel(Platform platform) {
                            toast("分享取消");
                        }
                    })
                    .show();
        }


    }


    public void getInvitationUrl() {
        EasyHttp.post(this)
                .api(new InvitationUrlApi())
                .request(new HttpCallback<HttpData<String>>(this) {

                    @Override
                    public void onSucceed(HttpData<String> data) {
                        shareUrl = data.getData();

                        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
                        Bitmap qrCodeBitmap = Utils.createQRCodeBitmap(shareUrl, 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE, logoBitmap, 0.2F);
                        iv_qr.setImageBitmap(qrCodeBitmap);
                    }
                });


    }
}
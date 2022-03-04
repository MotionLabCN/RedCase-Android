package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.ImageSaveUtil;
import com.tntlinking.tntdev.other.PermissionCallback;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * 提交用户信息状态页面  是否审核通过
 */
public final class CheckDeveloperActivity extends AppActivity {


    private ImageView iv_avatar;
    private ImageView iv_qr;
    private TextView tv_enter;

    @Override
    protected int getLayoutId() {
        return R.layout.check_developer_activity;
    }

    @Override
    protected void initView() {
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_qr = findViewById(R.id.iv_qr);
        tv_enter = findViewById(R.id.tv_enter);


        setOnClickListener(iv_qr, tv_enter);
    }

    @Override
    protected void initData() {
//        Bitmap qrCodeBitmap = Utils.createQRCodeBitmap("content", 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE);
//
//        iv_qr.setImageBitmap(qrCodeBitmap);
    }

    @Override
    public void onRightClick(View view) {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == iv_qr) {
//            startActivity(PersonDataActivity.class);

            XXPermissions.with(this)
                    .permission(Permission.Group.STORAGE)
                    .request(new PermissionCallback() {
                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                ImageSaveUtil.toSaveQrImg(CheckDeveloperActivity.this, iv_qr, TimeUtils.getNowString(), uri ->
                                        toast("保存成功"));

                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            super.onDenied(permissions, never);

                        }
                    });
        } else if (view == tv_enter) {

            Intent intent = new Intent(this, HomeWorkActivity.class);
            intent.putExtra(AppConfig.DEVELOP_STATUS, 2); //是否认证  1->待认证  2->待审核   3->审核成功 4->审核失败
            startActivity(intent);
            finish();
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }
}
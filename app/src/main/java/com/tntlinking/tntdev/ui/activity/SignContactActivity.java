package com.tntlinking.tntdev.ui.activity;

import android.view.View;
import android.widget.Button;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.other.AppConfig;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public final class SignContactActivity extends AppActivity {


    private AppCompatButton btn_commit;
    private String sign_text = "在用户使用身份认证的功能或相关服务所需时，根据相关法律法规，本平台可能收集你的真实身份信息" +
            "（真实姓名、身份证号码、手机号、人脸信息）以完成验证，特别且必要情况下基于安全考虑，为了确保我们为用户本人提供服务，" +
            "本平台可能会根据用户提供的信息进行校验，将通过人脸识别的方式验证你的身份。身份证号以及人脸信息属于个人敏感信息，" +
            "用户可以拒绝提供，但用户将可能无法获得相关服务，但不影响其他功能与服务的正常使用。";

    @Override
    protected int getLayoutId() {
        return R.layout.sign_contract_activity;
    }

    @Override
    protected void initView() {
        btn_commit = findViewById(R.id.btn_commit);


        setOnClickListener(btn_commit);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightClick(View view) {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == btn_commit) {
            boolean mBoolean = SPUtils.getInstance().getBoolean(AppConfig.SERVICE_DIALOG, false);
            if (!mBoolean) {
                new BaseDialog.Builder<>(SignContactActivity.this)
                        .setContentView(R.layout.write_daily_delete_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title, sign_text)
                        .setText(R.id.btn_dialog_custom_cancel, "不同意")
                        .setText(R.id.btn_dialog_custom_ok, "同意")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {

                            startActivity(SignInfoActivity.class);
                            dialog.dismiss();
                            SPUtils.getInstance().put(AppConfig.SERVICE_DIALOG, true);
                        })
                        .show();
            } else {
                startActivity(SignInfoActivity.class);
            }

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
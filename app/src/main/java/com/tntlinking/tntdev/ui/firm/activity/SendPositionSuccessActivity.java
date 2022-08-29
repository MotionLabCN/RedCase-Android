package com.tntlinking.tntdev.ui.firm.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetFirmDevDetailApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.manager.ActivityManager;
import com.tntlinking.tntdev.ui.bean.DeveloperInfoBean;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 发布职位信息
 */
public final class SendPositionSuccessActivity extends AppActivity {
    private TitleBar title_bar;
    private AppCompatButton btn_commit;

    private int mPositionId;
    private String mPositionName;
    private String mExpectSalary;
    private int mDeveloperId;
    private String mRealName;
    private String mAvatarUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.send_position_success_activity;
    }

    @Override
    protected void initView() {

        title_bar = findViewById(R.id.title_bar);
        btn_commit = findViewById(R.id.btn_commit);


        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SendPositionSuccessActivity.this, ContractDetailActivity.class);
                intent.putExtra("positionId", mPositionId);

                intent.putExtra("positionName", mPositionName);
                intent.putExtra("expectSalary", mExpectSalary);
                intent.putExtra("developerId", mDeveloperId);
                intent.putExtra("realName", mRealName);
                intent.putExtra("avatarUrl", mAvatarUrl);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onLeftClick(View view) {
        ActivityManager.getInstance().finishAllActivities();
        startActivity(FirmMainActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        mDeveloperId = getInt("developerId");
        mPositionId = getInt("positionId");
        getFirmDevDetail(mDeveloperId);
    }


    /**
     * 获取开发者详情
     */
    public void getFirmDevDetail(int developerId) {
        EasyHttp.get(this)
                .api(new GetFirmDevDetailApi().setDeveloperId(developerId))
                .request(new HttpCallback<HttpData<DeveloperInfoBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<DeveloperInfoBean> data) {
                        DeveloperInfoBean bean = data.getData();
                        mPositionName = bean.getCareerDto().getCareerDirectionName();
                        mExpectSalary = bean.getWorkModeDtoList().get(0).getExpectSalary();
//                        int id = bean.getId();
                        mRealName = bean.getRealName();
                        mAvatarUrl = bean.getAvatarUrl();
                    }
                });
    }


}
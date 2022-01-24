package com.tntlinking.tntdev.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tntlinking.tntdev.BuildConfig;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.GetDeveloperStatusApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;

import androidx.annotation.NonNull;


public final class PersonDataActivity extends AppActivity {

    private SettingBar mPersonDataIncome;
    private SettingBar mPersonDataInterview;
    private SettingBar mPersonDataSetting;
    private SettingBar person_data_private;
    private SettingBar person_data_deal;
    private SettingBar person_data_dev;
    private SettingBar person_data_about;
    private TextView tv_avatar;
    private TextView tv_name;
    private TextView tv_position;
    private TextView tv_sign_num;
    private TextView tv_profit_total;
    //https://fuchsia-athlete-f65.notion.site/9f0df9c1265e4d00a99ac0591d390ac3
    //https://fuchsia-athlete-f65.notion.site/cfcae78c7aa643228502af8e9c6a0d17

    @Override
    protected int getLayoutId() {
        return R.layout.persondata_activity;
    }

    @Override
    protected void initView() {
        mPersonDataIncome = findViewById(R.id.person_data_income);
        mPersonDataInterview = findViewById(R.id.person_data_interview);
        mPersonDataSetting = findViewById(R.id.person_data_setting);
        person_data_private = findViewById(R.id.person_data_private);
        person_data_deal = findViewById(R.id.person_data_deal);
        person_data_dev = findViewById(R.id.person_data_dev);
        person_data_about = findViewById(R.id.person_data_about);
        tv_avatar = findViewById(R.id.tv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_position = findViewById(R.id.tv_position);
        tv_sign_num = findViewById(R.id.tv_sign_num);
        tv_profit_total = findViewById(R.id.tv_profit_total);

        setOnClickListener(mPersonDataIncome, mPersonDataSetting, mPersonDataInterview,
                person_data_private, person_data_deal,person_data_dev,person_data_about);

//        String name = SPUtils.getInstance().getString(AppConfig.DEVELOP_NAME);
//        if (!TextUtils.isEmpty(name)) {
//            tv_avatar.setText(name);
//        } else {
//            tv_avatar.setBackground(getResources().getDrawable(R.drawable.dot_oval_blue));
//        }
    }

    @Override
    protected void initData() {
        getData();

    }

    @SingleClick
    @Override
    public void onClick(View view) { //interview
        if (view == mPersonDataIncome) {
            startActivity(IncomeListActivity.class);
        } else if (view == mPersonDataInterview) {
            startActivity(InterviewSettingActivity.class);
        } else if (view == mPersonDataSetting) {
            startActivity(PersonSettingActivity.class);
        } else if (view == person_data_private) {
            BrowserActivity.start(getActivity(), BuildConfig.PRIVATE_URL);
        } else if (view == person_data_deal) {
            BrowserActivity.start(getActivity(), BuildConfig.AGREEMENT_URL);
        }else if (view == person_data_dev) {
            startActivity(EnterDeveloperActivity.class);
        }else if (view == person_data_about) {
//            startActivity(HomeStatusActivity.class);
            startActivity(AboutAppActivity.class);
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    public void getData() {
        EasyHttp.get(this)
                .api(new GetDeveloperStatusApi())
                .request(new HttpCallback<HttpData<GetDeveloperStatusApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<GetDeveloperStatusApi.Bean> data) {
                        tv_avatar.setText(data.getData().getRealName());
                        tv_name.setText(data.getData().getRealName());
                        tv_position.setText(data.getData().getCareerDirection());
                        tv_sign_num.setText(data.getData().getSignContractNum() + "次");
                        tv_profit_total.setText("¥" + data.getData().getProfitTotal());

                    }
                });
    }
}
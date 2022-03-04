package com.tntlinking.tntdev.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.InvitationListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.ui.adapter.InvitationAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;


/**
 * 邀请记录页面
 */
public final class InterviewActivity extends AppActivity {

    private AppCompatButton btn_next;
    private AppCompatButton btn_contact;
    private LinearLayout ll_list_empty;
    private TitleBar title_bar;
    private MyListView list_item;
    private InvitationAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.interview_activity;
    }

    @Override
    protected void initView() {
        title_bar = findViewById(R.id.title_bar);
        list_item = findViewById(R.id.list_item);
        ll_list_empty = findViewById(R.id.ll_list_empty);
        btn_next = findViewById(R.id.btn_next);
        btn_contact = findViewById(R.id.btn_contact);


        setOnClickListener(btn_next, btn_contact);
        title_bar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {
                Intent intent = new Intent();
                String service_guide = AppConfig.RECOMMEND_GUIDE_URL;
                intent.setClass(InterviewActivity.this, MDViewActivity.class);
                intent.putExtra("md_url", service_guide);
                intent.putExtra("title", "活动规则");
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initData() {
        getInvitationList();
    }


    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                startActivity(PosterActivity.class);
                break;
            case R.id.btn_contact:
                Intent intent = new Intent();
                intent.setClass(this, SaveQRActivity.class);
                intent.putExtra("contact", "contact");
                startActivity(intent);
                break;

        }
    }


    public void getInvitationList() {
        EasyHttp.post(this)
                .api(new InvitationListApi())
                .request(new HttpCallback<HttpData<List<InvitationListApi.Bean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<InvitationListApi.Bean>> data) {
                        if (data.getData().size() != 0) {
                            ll_list_empty.setVisibility(View.GONE);
                            mAdapter = new InvitationAdapter(InterviewActivity.this, data.getData());
                            list_item.setAdapter(mAdapter);
                        } else {
                            ll_list_empty.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }


}
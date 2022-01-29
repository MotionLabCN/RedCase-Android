package com.tntlinking.tntdev.ui.activity;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.InvitationListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.InvitationAdapter;
import com.tntlinking.tntdev.widget.MyListView;
import java.util.List;
import androidx.appcompat.widget.AppCompatButton;


/**
 * 邀请记录页面
 */
public final class InterviewActivity extends AppActivity {

    private AppCompatButton btn_next;
    private LinearLayout ll_list_empty;
    private MyListView list_item;
    private InvitationAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.interview_activity;
    }

    @Override
    protected void initView() {
        list_item = findViewById(R.id.list_item);
        ll_list_empty = findViewById(R.id.ll_list_empty);
        btn_next = findViewById(R.id.btn_next);


        setOnClickListener(btn_next);
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
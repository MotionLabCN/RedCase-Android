package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AddTagApi;
import com.tntlinking.tntdev.http.api.AddTagSearchApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.SkillTagAdapter;
import com.tntlinking.tntdev.ui.adapter.SkillTagSearchAdapter;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 用户信息填写页面2
 */
public final class AddTagSearchActivity extends AppActivity {

    private ClearEditText et_search;
    private SkillTagSearchAdapter mAdapter;
    private RecyclerView mTagRV;

    @Override
    protected int getLayoutId() {
        return R.layout.add_project_tag_search_activity;
    }

    @Override
    protected void initView() {
        et_search = findViewById(R.id.et_search);

        mTagRV = findViewById(R.id.rv_skill_tag);
        mAdapter = new SkillTagSearchAdapter(this);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

                setResult(RESULT_OK, new Intent().putExtra("list",  mAdapter.getItem(position)));
                finish();
            }
        });
        mTagRV.setAdapter(mAdapter);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    addTagSearch(s1);
                }
            }
        });

    }


    @Override
    protected void initData() {

    }


    @SingleClick
    @Override
    public void onClick(View view) {

    }

    private void addTagSearch(String tag) {
        EasyHttp.get(this)
                .api(new AddTagSearchApi().setSkillName(tag))
                .request(new HttpCallback<HttpData<List<GetTagListApi.Bean.ChildrenBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<GetTagListApi.Bean.ChildrenBean>> data) {

                        mAdapter.setData(data.getData());
                    }
                });

    }

}
package com.tntlinking.tntdev.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hjq.base.BaseAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.aop.SingleClick;
import com.tntlinking.tntdev.app.AppActivity;
import com.tntlinking.tntdev.http.api.AddTagSearchApi;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.ui.adapter.SkillTagSearchAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 用户信息填写页面2
 */
public final class AddTagSearchActivity extends AppActivity {

    private EditText et_search;
    private SkillTagSearchAdapter mAdapter;
    private RecyclerView mTagRV;
    private ImageView iv_clear;

    @Override
    protected int getLayoutId() {
        return R.layout.add_project_tag_search_activity;
    }

    @Override
    protected void initView() {
        et_search = findViewById(R.id.et_search);
        iv_clear = findViewById(R.id.iv_clear);

        mTagRV = findViewById(R.id.rv_skill_tag);
        mAdapter = new SkillTagSearchAdapter(this);

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

                setResult(RESULT_OK, new Intent().putExtra("list", mAdapter.getItem(position)));
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
                String search = s.toString();
                searchList.clear();
                if (!TextUtils.isEmpty(search)) {
                    iv_clear.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (mList.get(i).getSkillName().toUpperCase().contains(s1.toUpperCase())) {
//                            searchList.add(mList.get(i));
//                        }
//                    }
                    addTagSearch(search);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
                mAdapter.setData(searchList);
            }
        });

    }

    private List<GetTagListApi.Bean.ChildrenBean> mList;
    private List<GetTagListApi.Bean.ChildrenBean> searchList = new ArrayList<>();

    @Override
    protected void initData() {
        mList = getSerializable("searchList");
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
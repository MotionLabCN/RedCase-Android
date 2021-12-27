package com.tntlinking.tntdev.ui.adapter;

/**
 * Created by Dan on 2021/11/29.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.http.api.GetTagListApi;
import com.tntlinking.tntdev.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements FlowTagLayout.OnInitSelectedPosition {

    private final Context mContext;
    private List<GetTagListApi.Bean.ChildrenBean> mDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Nullable
    public List<GetTagListApi.Bean.ChildrenBean> getData() {
        return mDataList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        GetTagListApi.Bean.ChildrenBean t = mDataList.get(position);

//        if (t instanceof String) {
//            textView.setText((String) t);
//        }
        textView.setText(t.getSkillName());
        return view;
    }



    public void onlyAddAll(List<GetTagListApi.Bean.ChildrenBean> datas) {
        if (datas !=null){
            mDataList.clear();
            mDataList.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void clearAndAddAll(List<GetTagListApi.Bean.ChildrenBean> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    public void remove(int position){
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }
}
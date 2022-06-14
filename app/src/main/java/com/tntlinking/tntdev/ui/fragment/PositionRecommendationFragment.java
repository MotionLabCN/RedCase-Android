package com.tntlinking.tntdev.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tntlinking.tntdev.R;
import com.tntlinking.tntdev.app.TitleBarFragment;
import com.tntlinking.tntdev.http.api.DeleteDeveloperRecommendsApi;
import com.tntlinking.tntdev.http.api.GetDeveloperRecommendsApi;

import com.tntlinking.tntdev.http.model.HttpData;
import com.tntlinking.tntdev.other.AppConfig;
import com.tntlinking.tntdev.other.HomeChangeListener;
import com.tntlinking.tntdev.other.OnItemClickListener;
import com.tntlinking.tntdev.ui.activity.JobDetailsActivity;
import com.tntlinking.tntdev.ui.activity.MainActivity;

import com.tntlinking.tntdev.ui.adapter.PositionRecommendationAdapter;
import com.tntlinking.tntdev.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class PositionRecommendationFragment extends TitleBarFragment<MainActivity> {
    private MyListView lv_position;
    private LinearLayout ll_settled_material_empty;
    private TextView tv_name;
    private PositionRecommendationAdapter mPositionRecommendationAdapter;
    private final List<GetDeveloperRecommendsApi.Bean> mList = new ArrayList<>();
    private final String Status = SPUtils.getInstance().getString(AppConfig.DEVELOP_STATUS, "1");
    private HomeChangeListener listener;

    public void setListener(HomeChangeListener listener) {
        this.listener = listener;
    }

    public static PositionRecommendationFragment newInstance() {
        return new PositionRecommendationFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.position_recommendation_fragment;
    }

    @Override
    protected void initView() {
        lv_position = findViewById(R.id.lv_position);
        tv_name = findViewById(R.id.tv_name);
        ll_settled_material_empty = findViewById(R.id.ll_settled_material_empty);
    }
    @Override
    protected void initData() {
        //1->待认证  2->待审核   3->审核成功 4->审核失败
        switch (Status) {
            case "1":
                lv_position.setVisibility(View.GONE);
                ll_settled_material_empty.setVisibility(View.VISIBLE);
                tv_name.setText("完善入驻资料后,再来查看吧!");
                break;
            case "2":
                lv_position.setVisibility(View.GONE);
                ll_settled_material_empty.setVisibility(View.VISIBLE);
                tv_name.setText("入驻信息待审核,审核成功再来查看吧!");
                break;
            case "3":
                lv_position.setVisibility(View.VISIBLE);
                ll_settled_material_empty.setVisibility(View.GONE);
                getDeveloperRecommends();
                break;
        }
        mPositionRecommendationAdapter = new PositionRecommendationAdapter(getActivity(), mList);
        lv_position.setAdapter(mPositionRecommendationAdapter);
        lv_position.setOnItemClickListener((parent, view, position, id) -> {
            GetDeveloperRecommendsApi.Bean item = mPositionRecommendationAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
            intent.putExtra("positionId", item.getPositionId());
            intent.putExtra("selfRecommendStatus", item.getSelfRecommendStatus());
            startActivityForResult(intent, 1);
        });
        mPositionRecommendationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GetDeveloperRecommendsApi.Bean item = mPositionRecommendationAdapter.getItem(position);

                new BaseDialog.Builder<>(getActivity())
                        .setContentView(R.layout.check_order_status_dialog)
                        .setAnimStyle(BaseDialog.ANIM_SCALE)
                        .setText(R.id.tv_title,"关闭职位信息")
                        .setText(R.id.tv_content,"是否关闭当前职位信息")
                        .setText(R.id.btn_dialog_custom_cancel, "取消")
                        .setText(R.id.btn_dialog_custom_ok, "确认")
                        .setOnClickListener(R.id.btn_dialog_custom_cancel, (BaseDialog.OnClickListener<Button>) (dialog, button) -> dialog.dismiss())
                        .setOnClickListener(R.id.btn_dialog_custom_ok, (dialog, views) -> {
                            deleteDeveloperRecommends(Integer.valueOf(item.getPositionId()), item.getRecommendByOperate(), position, dialog);
                        }).show();
            }
        });
    }

    /**
     * 开发者_推荐
     */
    private void getDeveloperRecommends() {
        EasyHttp.get(this)
                .api(new GetDeveloperRecommendsApi())
                .request(new HttpCallback<HttpData<List<GetDeveloperRecommendsApi.Bean>>>(this) {
                    @Override
                    public void onSucceed(HttpData<List<GetDeveloperRecommendsApi.Bean>> data) {
                        if (data.getData() != null && data.getData().size() != 0) {
                            mList.clear();
                            mList.addAll(data.getData());
                            mPositionRecommendationAdapter.setData(mList);
                            lv_position.post(new Runnable() {
                                @Override
                                public void run() {
                                    int hMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                    lv_position.measure(0, hMeasureSpec);
                                    listener.onDataChanged(lv_position.getMeasuredHeight());
                                }
                            });
                        } else {
                            lv_position.setVisibility(View.GONE);
                            ll_settled_material_empty.setVisibility(View.VISIBLE);
                            tv_name.setText("当前暂无职位");
                        }

                    }
                });
    }


    /**
     * 删除某个推荐职位
     *
     * @param operate
     * @param position
     * @param dialog
     */
    private void deleteDeveloperRecommends(int positionId, boolean operate, int position, BaseDialog dialog) {
        EasyHttp.delete(this)
                .api(new DeleteDeveloperRecommendsApi()
                        .setPositionId(positionId)
                        .setRecommendByOperate(operate))
                .request(new HttpCallback<HttpData<Void>>(this) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        getDeveloperRecommends();//重新刷新界面获取列表数据

                        toast("职位信息关闭成功");
                        dialog.dismiss();

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            getDeveloperRecommends();
        }
    }
}

package com.tntlinking.tntdev.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;


public final class DeleteDeveloperRecommendsApi implements IRequestApi {


    @Override
    public String getApi() {
        return "developer_recommends/delete_position";
    }


    private int positionId;
    private boolean recommendByOperate;

    public DeleteDeveloperRecommendsApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public DeleteDeveloperRecommendsApi setRecommendByOperate(boolean recommendByOperate) {
        this.recommendByOperate = recommendByOperate;
        return this;
    }
}
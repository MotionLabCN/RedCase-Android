package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

public final  class SelfReCommendApi implements IRequestApi {



   /**
    * 加入测评计划请求接口
    */
   @Override
   public String getApi() {
      return "manpower-rest-api/developer_recommends/self_recommend/"+positionId;
   }


      private String positionId;

      public SelfReCommendApi setpositionId(String positionId) {
         this.positionId = positionId;
         return this;

   }

}

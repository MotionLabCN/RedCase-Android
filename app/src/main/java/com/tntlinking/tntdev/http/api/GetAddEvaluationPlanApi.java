package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;

public final  class GetAddEvaluationPlanApi implements IRequestApi , IRequestType {



   /**
    * 加入测评计划请求接口
    */
   @Override
   public String getApi() {
      return "jk/add_evaluation_plan";
   }


      private int jobId;
      public GetAddEvaluationPlanApi setjobId(int jobId) {
         this.jobId = jobId;
         return this;

   }


   /**
    * 参数提交类型
    */
   @Override
   public BodyType getType() {
      return BodyType.JSON;
   }
}

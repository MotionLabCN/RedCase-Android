package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

public final  class GetDeveloperJkStatusApi implements IRequestApi {
   /**
    * 获取开发者测评计划状态请求接口
    */
   @Override
   public String getApi() {
      return "jk/getDeveloperJkStatus";
   }

   public final static class Bean implements Serializable{
      private int developerId;

      public String getPlanUrl() {
         return planUrl;
      }

      public void setPlanUrl(String planUrl) {
         this.planUrl = planUrl;
      }

      private String planUrl;
      private int planId;
      private List<stackInfoListBean> stackInfoList;
      private int userPlanStatus;
      private String message;
      public static class stackInfoListBean implements Serializable {
         public int getAnswerDuration() {
            return answerDuration;
         }

         public void setAnswerDuration(int answerDuration) {
            this.answerDuration = answerDuration;
         }

         public String getAnswerEndAt() {
            return answerEndAt;
         }

         public void setAnswerEndAt(String answerEndAt) {
            this.answerEndAt = answerEndAt;
         }

         public int getCorrectQuestionCount() {
            return correctQuestionCount;
         }

         public void setCorrectQuestionCount(int correctQuestionCount) {
            this.correctQuestionCount = correctQuestionCount;
         }

         public String getEvaluationGrade() {
            return evaluationGrade;
         }

         public void setEvaluationGrade(String evaluationGrade) {
            this.evaluationGrade = evaluationGrade;
         }

         public int getEvaluationId() {
            return evaluationId;
         }

         public void setEvaluationId(int evaluationId) {
            this.evaluationId = evaluationId;
         }

         public int getPlanId() {
            return planId;
         }

         public void setPlanId(int planId) {
            this.planId = planId;
         }

         public int getScore() {
            return score;
         }

         public void setScore(int score) {
            this.score = score;
         }

         public String getSelfEvaluationGrade() {
            return selfEvaluationGrade;
         }

         public void setSelfEvaluationGrade(String selfEvaluationGrade) {
            this.selfEvaluationGrade = selfEvaluationGrade;
         }

         public String getStack() {
            return stack;
         }

         public void setStack(String stack) {
            this.stack = stack;
         }

         public int getTotalQuestionCount() {
            return totalQuestionCount;
         }

         public void setTotalQuestionCount(int totalQuestionCount) {
            this.totalQuestionCount = totalQuestionCount;
         }

         public int getUserId() {
            return userId;
         }

         public void setUserId(int userId) {
            this.userId = userId;
         }

         private int answerDuration;
         private String answerEndAt;

         private int correctQuestionCount;
         private String evaluationGrade;
         private int evaluationId;
         private int planId;
         private int score;

         @Override
         public String toString() {
            return "stackInfoListBean{" +
                    "answerDuration=" + answerDuration +
                    ", answerEndAt='" + answerEndAt + '\'' +
                    ", correctQuestionCount=" + correctQuestionCount +
                    ", evaluationGrade='" + evaluationGrade + '\'' +
                    ", evaluationId=" + evaluationId +
                    ", planId=" + planId +
                    ", score=" + score +
                    ", selfEvaluationGrade='" + selfEvaluationGrade + '\'' +
                    ", stack='" + stack + '\'' +
                    ", totalQuestionCount=" + totalQuestionCount +
                    ", userId=" + userId +
                    '}';
         }

         private String selfEvaluationGrade;
         private String stack;
         private int totalQuestionCount;
         private int userId;

      }
      public int getDeveloperId() {
         return developerId;
      }

      public void setDeveloperId(int developerId) {
         this.developerId = developerId;
      }

      public int getPlanId() {
         return planId;
      }

      public void setPlanId(int planId) {
         this.planId = planId;
      }

      public List<stackInfoListBean> getStackInfoList() {
         return stackInfoList;
      }

      public void setStackInfoList(List<stackInfoListBean> stackInfoList) {
         this.stackInfoList = stackInfoList;
      }

      public int getUserPlanStatus() {
         return userPlanStatus;
      }

      public void setUserPlanStatus(int userPlanStatus) {
         this.userPlanStatus = userPlanStatus;
      }

      public String getMessage() {
         return message;
      }

      public void setMessage(String message) {
         this.message = message;
      }




   }
}

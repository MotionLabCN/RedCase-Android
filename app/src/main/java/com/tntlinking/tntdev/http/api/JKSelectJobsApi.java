package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

public final class JKSelectJobsApi implements IRequestApi {
   /**
    * 选择极客岗位请求接口
    */
   @Override
   public String getApi() {
      return "manpower-rest-api/jk/select_jobs";
   }
   public final static class Bean {
      private String description;
      private int id;
      private String name;

      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      public int getId() {
         return id;
      }

      public void setId(int id) {
         this.id = id;
      }

      public String getName() {
         return name;
      }

      @Override
      public String toString() {
         return "Bean{" +
                 "description='" + description + '\'' +
                 ", id=" + id +
                 ", name='" + name + '\'' +
                 '}';
      }

      public void setName(String name) {
         this.name = name;
      }


   }
}

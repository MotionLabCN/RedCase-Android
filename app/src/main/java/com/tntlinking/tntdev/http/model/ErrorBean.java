package com.tntlinking.tntdev.http.model;

/**
 * Created by Dan on 2021/12/23.
 */

 public class ErrorBean {
     private int code;
     private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

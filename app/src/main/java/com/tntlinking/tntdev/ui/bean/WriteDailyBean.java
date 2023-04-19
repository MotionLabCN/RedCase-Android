package com.tntlinking.tntdev.ui.bean;

/**
 * Created by Dan on 2021/12/14.
 */

public class WriteDailyBean {
    private String content;
    private int type;
    private int tab;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WriteDailyBean() {
    }

    public WriteDailyBean(int type, String content) {
        this.content = content;
        this.type = type;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}

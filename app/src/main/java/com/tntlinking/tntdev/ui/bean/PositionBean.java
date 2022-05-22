package com.tntlinking.tntdev.ui.bean;

import java.io.Serializable;

public class PositionBean implements Serializable {
    private String position_name;
    private String recommend;
    private String salary;
    private String content;
    private String name;
    private String professional_title;
    private String company;
    private boolean icon_recommend;
    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessional_title() {
        return professional_title;
    }

    public void setProfessional_title(String professional_title) {
        this.professional_title = professional_title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isIcon_recommend() {
        return icon_recommend;
    }

    public void setIcon_recommend(boolean icon_recommend) {
        this.icon_recommend = icon_recommend;
    }



}

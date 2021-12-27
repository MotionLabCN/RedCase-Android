package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class AddTagApi implements IRequestApi {

    @Override
    public String getApi() {
        return "skill/new";
    }


    /** tag */
    private String skillName;

    public AddTagApi setSkillName(String skillName) {
        this.skillName = skillName;
        return this;
    }


    public final static class Bean {

        private String access_token;
        private String status;


    }
}
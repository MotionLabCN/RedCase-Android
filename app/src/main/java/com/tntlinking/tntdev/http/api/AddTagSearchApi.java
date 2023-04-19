package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;


public final class AddTagSearchApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/skill/list/developer/search";
    }


    /** tag */
    private String skillName;

    public AddTagSearchApi setSkillName(String skillName) {
        this.skillName = skillName;
        return this;
    }


    public final static class Bean {

        private String access_token;
        private String status;


    }
}
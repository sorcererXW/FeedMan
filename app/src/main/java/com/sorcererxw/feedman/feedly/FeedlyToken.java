package com.sorcererxw.feedman.feedly;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyToken {

    /**
     * id : c805fcbf-3acf-4302-a97e-d82f9d7c897f
     * state : ...
     * access_token : AQAAF4iTvPam_M4_dWheV_5NUL8E...
     * refresh_token : AQAA7rJ7InAiOjEsImEiOiJmZWVk...
     * token_type : Bearer
     * expires_in : 3920
     * plan : standard
     */

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

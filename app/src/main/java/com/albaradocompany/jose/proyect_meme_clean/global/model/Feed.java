package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public class Feed {
    String feedId;
    String userId;
    String xUserId;
    String xProfile;
    String xUsername;

    public Feed() {
        feedId = "";
        userId = "";
        xUserId = "";
        xProfile = "";
        xUsername = "";
    }

    ;

    public Feed(String feedId, String userId, String xUserId, String xProfile, String xUsername,
                Picture picture, List<Like> likeList, List<Comment> commentList) {
        this.feedId = feedId;
        this.userId = userId;
        this.xUserId = xUserId;
        this.xProfile = xProfile;
        this.xUsername = xUsername;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getxUserId() {
        return xUserId;
    }

    public void setxUserId(String xUserId) {
        this.xUserId = xUserId;
    }

    public String getxProfile() {
        return xProfile;
    }

    public void setxProfile(String xProfile) {
        this.xProfile = xProfile;
    }

    public String getxUsername() {
        return xUsername;
    }

    public void setxUsername(String xUsername) {
        this.xUsername = xUsername;
    }

}

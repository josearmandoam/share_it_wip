package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 20/05/2017.
 */

public class Feed {
    String feedId;
    String userId;
    String xUserId;
    String xProfile;
    String xUsername;
    String state;

    public Feed() {
        feedId = "";
        userId = "";
        xUserId = "";
        xProfile = "";
        xUsername = "";
        state = "";
    }

    public Feed(String feedId, String userId, String xUserId, String xProfile, String xUsername, String state) {
        this.feedId = feedId;
        this.userId = userId;
        this.xUserId = xUserId;
        this.xProfile = xProfile;
        this.xUsername = xUsername;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

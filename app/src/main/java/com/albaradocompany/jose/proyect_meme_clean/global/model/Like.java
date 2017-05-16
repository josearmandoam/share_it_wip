package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;

/**
 * Created by jose on 14/05/2017.
 */

public class Like implements Serializable {
    String imageId;
    String username;
    String profile;
    String userId;
    String likeId;

    public Like() {
        imageId = "";
        username = "";
        profile = "";
        userId = "";
        likeId = "";
    }

    public Like(String imageId, String username, String profile, String userId, String likeId) {
        this.imageId = imageId;
        this.username = username;
        this.profile = profile;
        this.userId = userId;
        this.likeId = likeId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }
}

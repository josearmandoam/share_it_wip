package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;

/**
 * Created by jose on 14/05/2017.
 */

public class Comment implements Serializable {
    String imageId;
    String commentId;
    String username;
    String profile;
    String comment;
    String userId;
    String date;
    String time;

    public Comment() {
        imageId = "";
        commentId = "";
        username = "";
        profile = "";
        date = "";
        time = "";
        comment = "";
        userId = "";
    }

    public Comment(String imageId, String commentId, String username, String profile, String comment,
                   String userId, String date, String time) {
        this.imageId = imageId;
        this.commentId = commentId;
        this.username = username;
        this.profile = profile;
        this.comment = comment;
        this.userId = userId;
        this.date = date;
        this.time = time;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

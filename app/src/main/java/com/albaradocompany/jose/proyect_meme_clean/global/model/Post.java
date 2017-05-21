package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public class Post {
    String feedId;
    String userId;
    String xUserId;
    String xProfile;
    String xUsername;
    Picture picture;
    List<Like> likeList;
    List<Comment> commentList;

    public Post() {
        feedId = "";
        userId = "";
        xUserId = "";
        xProfile = "";
        xUsername = "";
        likeList = new ArrayList<>();
        commentList = new ArrayList<>();
    }

    ;

    public Post(String feedId, String userId, String xUserId, String xProfile, String xUsername,
                Picture picture, List<Like> likeList, List<Comment> commentList) {
        this.feedId = feedId;
        this.userId = userId;
        this.xUserId = xUserId;
        this.xProfile = xProfile;
        this.xUsername = xUsername;
        this.picture = picture;
        this.likeList = likeList;
        this.commentList = commentList;
    }

    public String getPostId() {
        return feedId;
    }

    public void setPostId(String feedId) {
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

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }
}

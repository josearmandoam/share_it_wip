package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public class Picture implements Serializable {
    String userId;
    String imagePath;
    String description;
    String date;
    String imageId;
    String time;
    List<Like> likes;
    List<Comment> comments;

    public Picture(String userId, String imagePath, String description, String date, String imageId,
                   String time, List<Like> likes, List<Comment> comments) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
        this.imageId = imageId;
        this.time = time;
        this.likes = likes;
        this.comments = comments;
    }

    public Picture() {
        userId = "";
        imagePath = "";
        description = "";
        date = "";
        imageId = "";
        time = "";
        likes = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

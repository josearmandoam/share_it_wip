package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 05/05/2017.
 */

public class Picture {
    String userId;
    String imagePath;
    String description;
    String date;
    String imageId;
    String coments;
    String likes;

    public Picture(String userId, String imagePath, String description, String date, String imageId,
                   String coments, String likes) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
        this.imageId = imageId;
        this.coments = coments;
        this.likes = likes;
    }

    public Picture() {
        userId = "";
        imagePath = "";
        description = "";
        date = "";
        imageId = "";
        coments = "";
        likes = "";
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

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 05/05/2017.
 */

public class Picture {
    String userId;
    String imagePath;
    String description;
    String date;

    public Picture(String userId, String imagePath, String description, String date) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
    }

    public Picture() {
        userId = "";
        imagePath = "";
        description = "";
        date = "";
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
}

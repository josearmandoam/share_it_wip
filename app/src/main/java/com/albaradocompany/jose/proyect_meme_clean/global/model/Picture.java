package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;

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

    public Picture(String userId, String imagePath, String description, String date, String imageId,
                   String time) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
        this.imageId = imageId;
        this.time = time;
    }

    public Picture() {
        userId = "";
        imagePath = "";
        description = "";
        date = "";
        imageId = "";
        time = "";
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

}

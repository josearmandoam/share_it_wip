package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 31/05/2017.
 */

public class NotificationLine {
    String userId;
    String profile;
    String message;
    String time;
    String title;
    String state;
    String notificationId;
    String receptor;

    public NotificationLine(String userId, String profile, String message, String time, String title, String state, String notificationId, String receptor) {
        this.userId = userId;
        this.profile = profile;
        this.message = message;
        this.time = time;
        this.title = title;
        this.state = state;
        this.notificationId = notificationId;
        this.receptor = receptor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }
}

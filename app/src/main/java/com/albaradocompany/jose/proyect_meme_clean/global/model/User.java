package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;

/**
 * Created by jose on 16/05/2017.
 */

public class User implements Serializable {
    String userId;
    String username;
    String name;
    String lastname;
    String profile;
    String background;
    String description;
    String socialWhatsapp;
    String socialEmail;
    String socialInstagram;
    String socialWebsite;
    String socialFacebook;
    String socialTwitter;

    public User() {
        userId = "";
        name = "";
        lastname = "";
        profile = "";
        background = "";
        description = "";
        socialEmail = "";
        socialFacebook = "";
        socialInstagram = "";
        socialTwitter = "";
        socialWebsite = "";
        socialWhatsapp = "";
        username = "";
    }

    public User(String userId, String username, String name, String lastname, String profile,
                String background, String description, String socialWhatsapp, String socialEmail,
                String socialInstagram, String socialWebsite, String socialFacebook, String socialTwitter) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.profile = profile;
        this.background = background;
        this.description = description;
        this.socialWhatsapp = socialWhatsapp;
        this.socialEmail = socialEmail;
        this.socialInstagram = socialInstagram;
        this.socialWebsite = socialWebsite;
        this.socialFacebook = socialFacebook;
        this.socialTwitter = socialTwitter;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSocialWhatsapp() {
        return socialWhatsapp;
    }

    public void setSocialWhatsapp(String socialWhatsapp) {
        this.socialWhatsapp = socialWhatsapp;
    }

    public String getSocialEmail() {
        return socialEmail;
    }

    public void setSocialEmail(String socialEmail) {
        this.socialEmail = socialEmail;
    }

    public String getSocialInstagram() {
        return socialInstagram;
    }

    public void setSocialInstagram(String socialInstagram) {
        this.socialInstagram = socialInstagram;
    }

    public String getSocialWebsite() {
        return socialWebsite;
    }

    public void setSocialWebsite(String socialWebsite) {
        this.socialWebsite = socialWebsite;
    }

    public String getSocialFacebook() {
        return socialFacebook;
    }

    public void setSocialFacebook(String socialFacebook) {
        this.socialFacebook = socialFacebook;
    }

    public String getSocialTwitter() {
        return socialTwitter;
    }

    public void setSocialTwitter(String socialTwitter) {
        this.socialTwitter = socialTwitter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

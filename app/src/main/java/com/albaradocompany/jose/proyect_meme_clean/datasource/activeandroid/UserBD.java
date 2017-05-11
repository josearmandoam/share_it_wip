package com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jose on 05/05/2017.
 */
@Table(name = "User")
public class UserBD extends Model {
    @Column(name = "userId")
    public String userId;
    @Column(name = "user_name")
    public String user_name;
    @Column(name = "user_username")
    public String user_username;
    @Column(name = "user_profile")
    public String user_profile;
    @Column(name = "user_background")
    public String user_background;
    @Column(name = "user_description")
    public String user_description;
    @Column(name = "user_email")
    public String user_email;
    @Column(name = "user_date")
    public String user_date;
    @Column(name = "user_lastname")
    public String user_lastname;
    @Column(name = "social_whatsapp")
    public String social_whatsapp;
    @Column(name = "social_email")
    public String social_email;
    @Column(name = "social_website")
    public String social_website;
    @Column(name = "social_facebook")
    public String social_facebook;
    @Column(name = "social_instagram")
    public String social_instagram;
    @Column(name = "social_twitter")
    public String social_twitter;

    public UserBD() {
        super();
    }

    public UserBD(String userId, String user_name, String user_username, String user_profile,
                  String user_background, String user_description, String user_email, String user_date,
                  String user_lastname, String social_whatsapp, String social_email, String social_website,
                  String social_facebook, String social_instagram, String social_twitter) {
        this.userId = userId;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_profile = user_profile;
        this.user_background = user_background;
        this.user_description = user_description;
        this.user_email = user_email;
        this.user_date = user_date;
        this.user_lastname = user_lastname;
        this.social_whatsapp = social_whatsapp;
        this.social_email = social_email;
        this.social_website = social_website;
        this.social_facebook = social_facebook;
        this.social_instagram = social_instagram;
        this.social_twitter = social_twitter;
    }
}

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

    public UserBD() {
        super();
    }

    public UserBD(String userId, String user_name, String user_username, String user_profile,
                  String user_background, String user_description, String user_email, String user_date,
                  String user_lastname) {
        this.userId = userId;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_profile = user_profile;
        this.user_background = user_background;
        this.user_description = user_description;
        this.user_email = user_email;
        this.user_date = user_date;
        this.user_lastname = user_lastname;
    }
}

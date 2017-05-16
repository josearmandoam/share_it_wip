package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

/**
 * Created by jose on 16/05/2017.
 */

public class UserApiEntry {
    String idUser = "";
    String username = "";
    String nombre = "";
    String apellidos = "";
    String imagePath = "";
    String backgrundPath = "";
    String description = "";
    String socialWhatsapp = "";
    String socialEmail = "";
    String socialInstagram = "";
    String socialWebsite = "";
    String socialFacebook = "";
    String socialTwitter = "";

    public User parseUser() {
        User obj = new User();
        obj.setUserId(idUser);
        obj.setName(nombre);
        obj.setLastname(apellidos);
        obj.setProfile(imagePath);
        obj.setBackground(backgrundPath);
        obj.setDescription(description);
        obj.setSocialWhatsapp(socialWhatsapp);
        obj.setSocialEmail(socialEmail);
        obj.setSocialInstagram(socialInstagram);
        obj.setSocialWebsite(socialWebsite);
        obj.setSocialFacebook(socialFacebook);
        obj.setSocialTwitter(socialTwitter);
        obj.setUsername(username);
        return obj;
    }

}

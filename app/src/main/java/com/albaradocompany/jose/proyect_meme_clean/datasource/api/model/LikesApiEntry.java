package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesApiEntry {
    String imageId = "";
    String username = "";
    String profile = "";
    String userId = "";

    public Like parseLike() {
        Like obj = new Like();
        obj.setProfile(profile);
        obj.setUsername(username);
        obj.setImageId(imageId);
        obj.setUserId(userId);
        return obj;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 16/05/2017.
 */

public class UserApiResponse {
    List<UserApiEntry> user;

    public List<User> parseUsers(){
        List<User> list = new ArrayList<User>();
        if(user == null)
            return list;

        for(UserApiEntry e : user) {
            list.add(e.parseUser());
        }

        return list;
    }
}

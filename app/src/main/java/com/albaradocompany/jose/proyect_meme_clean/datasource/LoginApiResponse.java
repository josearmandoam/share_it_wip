package com.albaradocompany.jose.proyect_meme_clean.datasource;

import com.albaradocompany.jose.proyect_meme_clean.datasource.model.LoginApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 16/04/2017.
 */

public class LoginApiResponse {
    List<LoginApiEntry> login;

    public List<Login> parseLogins(){
        List<Login> list = new ArrayList<Login>();
        if(login == null)
            return list;

        for(LoginApiEntry e : login) {
            list.add(e.parseLogin());
        }

        return list;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesApiResponse {
    List<LikesApiEntry> likes;

    public List<Like> parseLikes() {
        List<Like> list = new ArrayList<Like>();
        if (likes == null)
            return list;

        for (LikesApiEntry e : likes) {
            list.add(e.parseLike());
        }

        return list;
    }
}

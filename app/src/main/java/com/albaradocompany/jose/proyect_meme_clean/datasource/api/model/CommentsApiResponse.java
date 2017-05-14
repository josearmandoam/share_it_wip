package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public class CommentsApiResponse {
    List<CommentsApiEntry> comments;

    public List<Comment> parseComments() {
        List<Comment> list = new ArrayList<Comment>();
        if (comments == null)
            return list;

        for (CommentsApiEntry e : comments) {
            list.add(e.parseComment());
        }

        return list;
    }
}

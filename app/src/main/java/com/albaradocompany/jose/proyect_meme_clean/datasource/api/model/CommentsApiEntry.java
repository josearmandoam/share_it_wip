package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;

/**
 * Created by jose on 14/05/2017.
 */

public class CommentsApiEntry {
    String imageId = "";
    String username = "";
    String date = "";
    String time = "";
    String commentId = "";
    String comment = "";
    String profile = "";

    public Comment parseComment() {
        Comment obj = new Comment();
        obj.setImageId(imageId);
        obj.setUsername(username);
        obj.setDate(date);
        obj.setTime(time);
        obj.setCommentId(commentId);
        obj.setComment(comment);
        obj.setProfile(profile);
        return obj;
    }
}

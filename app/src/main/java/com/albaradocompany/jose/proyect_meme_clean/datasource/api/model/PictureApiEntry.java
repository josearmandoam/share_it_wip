package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public class PictureApiEntry {
    String userId = "";
    String imagePath = "";
    String description = "";
    String date = "";
    String imageId = "";
    String time = "";
    List<LikesApiEntry> likes = new ArrayList<>();
    List<CommentsApiEntry> comments = new ArrayList<>();

    public Picture parsePicture() {
        Picture obj = new Picture();
        obj.setUserId(userId);
        obj.setImagePath(imagePath);
        obj.setDescription(description);
        obj.setDate(date);
        obj.setImageId(imageId);
        obj.setTime(time);
        obj.setLikes(parseLikes());
        obj.setComments(parseComments());
        return obj;
    }

    public List<Like> parseLikes() {
        List<Like> list = new ArrayList<Like>();
        if (likes == null)
            return list;

        for (LikesApiEntry e : likes) {
            list.add(e.parseLike());
        }

        return list;
    }

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

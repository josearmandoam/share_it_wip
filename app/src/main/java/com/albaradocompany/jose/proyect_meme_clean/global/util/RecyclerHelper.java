package com.albaradocompany.jose.proyect_meme_clean.global.util;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;

import java.util.List;

/**
 * Created by jose on 15/05/2017.
 */

public class RecyclerHelper {

    public static boolean hasNewComments(List<Comment> oldList, List<Comment> newList) {
        boolean newComments = false;
        if (newList.size() != oldList.size()) {
            newComments = true;
        }
        if (newList.size() == oldList.size()) {
            for (int i = 0; i < newList.size(); i++) {
                if (!newList.get(i).getCommentId().equals(oldList.get(i).getCommentId())) {
                    newComments = true;
                }
            }
        }
        return newComments;
    }
    public static boolean hasNewLikes(List<Like> oldList, List<Like> newList) {
        boolean newLikes = false;
        if (newList.size() != oldList.size()) {
            newLikes = true;
        }
        if (newList.size() == oldList.size()) {
            for (int i = 0; i < newList.size(); i++) {
                if (!newList.get(i).getLikeId().equals(oldList.get(i).getLikeId())) {
                    newLikes = true;
                }
            }
        }
        return newLikes;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.global.util;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;

import java.util.ArrayList;
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

    public static boolean hasNewPosts(List<Post> newPosts, List<Post> oldPosts) {
        if (newPosts.size() == oldPosts.size()) {
            return false;
        } else {
            for (int i = 0; i < newPosts.size(); i++) {
                for (int j = 0; j < oldPosts.size(); j++) {
                    if (newPosts.get(i).getPicture().getImageId().equals(oldPosts.get(j).getPicture().getImageId()))
                        return false;
                }
            }
            return true;
        }
    }

    public static List<Post> getNewPosts(List<Post> newPosts, List<Post> oldPosts) {
        boolean exist = false;
        List<Post> list = new ArrayList<Post>();
        for (int i = 0; i < newPosts.size(); i++) {
            for (int j = 0; j < oldPosts.size(); j++) {
                if (newPosts.get(i).getPicture().getImageId().equals(oldPosts.get(j).getPicture().getImageId()))
                    exist = true;
            }
            if (!exist)
                list.add(newPosts.get(i));
        }
        return list;
    }

    public static List<Post> updateLikesAndComments(List<Post> listpost, List<Post> posts) {
        for (int i = 0; i < listpost.size(); i++) {
            for (int j = 0; j < posts.size(); j++) {
                if (listpost.get(i).getPicture().getImageId().equals(posts.get(j).getPicture().getImageId())) {
                    posts.get(j).setCommentList(listpost.get(i).getCommentList());
                    posts.get(j).setLikeList(listpost.get(i).getLikeList());
                }
            }
        }
        return posts;
    }

    public static int hasPostNewComments(List<Comment> posts, List<Comment> listpost) {
        for (int i = 0; i < posts.size(); i++) {
            if (!posts.get(i).getCommentId().equals(listpost.get(i).getCommentId()))
                return i;
        }
        return -1;
    }

    public static int hasPostNewLikes(List<Like> likeList, List<Like> likeList1) {
        for (int i = 0; i < likeList.size(); i++) {
            if (!likeList.get(i).getLikeId().equals(likeList1.get(i).getLikeId()))
                return i;
        }
        return -1;
    }
}

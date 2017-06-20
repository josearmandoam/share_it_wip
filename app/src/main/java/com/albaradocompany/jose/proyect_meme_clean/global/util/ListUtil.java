package com.albaradocompany.jose.proyect_meme_clean.global.util;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jose on 24/05/2017.
 */

public class ListUtil {

    public static List<Post> orderPosts(List<Post> list) {
            Collections.sort(list, new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    if (Date.valueOf(post.getPicture().getDate()).before(Date.valueOf(t1.getPicture().getDate())))
                        return 0;
                    else
                        return -1;
                }
            });
            return list;
    }
    public static List<Comment> orderComments(List<Comment> list) {
        Collections.sort(list, new Comparator<Comment>() {
            @Override
            public int compare(Comment t, Comment t1) {
                if (Integer.parseInt(DateUtil.timeAgo(t.getDate(),t.getTime())) >= Integer.parseInt(DateUtil.timeAgo(t1.getDate(),t1.getTime())))
                    return 0;
                else
                    return -1;
            }
        });
        return list;
    }
}

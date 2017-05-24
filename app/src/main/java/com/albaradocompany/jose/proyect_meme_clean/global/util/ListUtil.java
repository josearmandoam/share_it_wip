package com.albaradocompany.jose.proyect_meme_clean.global.util;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jose on 24/05/2017.
 */

public class ListUtil {

    public static List<Post> orderList(List<Post> list) {
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
}

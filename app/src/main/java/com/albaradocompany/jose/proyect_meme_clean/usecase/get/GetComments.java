package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public interface GetComments {
    void getComments(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onCommentesReceived(List<Comment> comments);
    }
}

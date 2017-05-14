package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;

import java.util.List;

/**
 * Created by jose on 25/04/2017.
 */

public interface GetQuestions {
    void getQuestions(Listener listener);

    interface Listener {
        void onNoInternetAvailable();

        void onError(Exception e);

        void onQuestionsReceived(List<Question> questions);
    }
}

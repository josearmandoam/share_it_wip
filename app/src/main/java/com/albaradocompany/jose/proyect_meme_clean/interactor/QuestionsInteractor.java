package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetQuestions;

import java.util.List;

/**
 * Created by jose on 25/04/2017.
 */

public class QuestionsInteractor implements Interactor, GetQuestions, GetQuestions.Listener {
    GetQuestions getQuestions;
    MainThread mainThread;
    Executor executor;
    GetQuestions.Listener listener = new NullListener();

    public QuestionsInteractor(GetQuestions getQuestions, MainThread mainThread, Executor executor) {
        this.getQuestions = getQuestions;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        getQuestions.getQuestions(listener);
    }

    @Override
    public void getQuestions(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onQuestionsReceived(List<Question> questions) {
        listener.onQuestionsReceived(questions);
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onQuestionsReceived(List<Question> questions) {

        }
    }
}

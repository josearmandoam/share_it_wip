package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.os.Handler;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.global.model.RegistrationResponse;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetQuestions;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetRegistrationResponse;

import java.util.List;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupThreePresenter extends AbsSignupThree {
    Context context;
    GetQuestions getQuestions;

    public SignupThreePresenter(Context context, GetQuestions getQuestions) {
        this.context = context;
        this.getQuestions = getQuestions;
    }

    @Override
    public void initialize() {
        view.loadUserImage();
        getQuestions.getQuestions(new GetQuestions.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onQuestionsReceived(List<Question> questions) {
                view.showQuestions(questions);
            }
        });
    }

    @Override
    public void resume() {
        view.loadUserImage();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onBackPressed() {
        view.hideSignupThree();
    }

    @Override
    public void onMenuPressed() {
        view.showMenu();
    }

    @Override
    public void onConfirmClicked(GetRegistrationResponse getRegistrationResponse, Login login) {
        view.showLoading();
        getRegistrationResponse.getRegistrationResponse(new GetRegistrationResponse.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onRegistrationSuccess(RegistrationResponse response) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();
                    }
                },1000);
                view.showSuccess();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigator.navigateToLogin();
                      // Actions to do after 10 seconds
                    }
                }, 2000);
            }

            @Override
            public void onRegistrationFailed() {
                view.hideLoading();
                view.showErrorRegistration();
            }
        });
    }

    @Override
    public void onImageClicked() {
        view.showImage();
    }

    @Override
    public void onRefreshQuestionClicked() {
        view.refreshQuestions();
    }

}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.os.Handler;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdatePasswordInteractor;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserByEmail;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UpdatePassword;

import java.util.List;

/**
 * Created by jose on 27/04/2017.
 */

public class PasswordPresenter extends AbsPassword {
    Login user;

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onEmailSubmitClicked(GetUserByEmail getUserByEmail) {
        view.showEmailLoading();
        getUserByEmail.getUserByEmail(new GetUserByEmail.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideEmailLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideEmailLoading();
                view.showError(e);
            }

            @Override
            public void onUserReceived(List<Login> user) {
                view.showEmailSucces(user.get(0));
                view.hideEmailLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigator.navigateToQuestions();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onQuestionsSubmitClicked(Login login, String res1, String res2) {
        if (login.getPreguntaSeguridad().toUpperCase().equals(res1.toUpperCase())
                || login.getPreguntaSeguridad().toUpperCase().equals(res2.toUpperCase())) {
            view.showQuestionsSuccess();
        } else {
            view.shoQuestionsFailure();
        }
    }

    @Override
    public void onUpdateSubmitClicked(UpdatePasswordInteractor updatePasswordInteractor) {
        view.showUpdateLoading();
        updatePasswordInteractor.updatePassword(new UpdatePassword.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideUpdateLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideUpdateLoading();
                view.showError(e);
            }

            @Override
            public void onUpdateSuccess() {
                view.hideUpdateLoading();
                view.showUpdateSuccess();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigator.navigateToSignin();
                    }
                }, 2000);
            }

            @Override
            public void onUpdateFailure() {
                view.hideUpdateLoading();
                view.showUpdateFailure();
            }
        });
    }
}

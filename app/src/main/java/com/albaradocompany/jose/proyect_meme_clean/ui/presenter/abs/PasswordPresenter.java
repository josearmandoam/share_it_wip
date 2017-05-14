package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.os.Handler;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdatePasswordInteractor;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByEmail;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdatePassword;

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
            public void onUserReceived(Login user) {
                view.showEmailSucces(user);
                view.hideEmailLoading();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigator.navigateToQuestions();
                    }
                }, 2000);
            }

            @Override
            public void onFailure() {
                view.hideEmailLoading();
                view.showEmailFailure();
            }
        });
    }

    @Override
    public void onQuestionsSubmitClicked(Login login, String res1, String res2) {
        if (login.getRespuestaSeguridad().toUpperCase().equals(res1.toUpperCase())
                || login.getRespuestaSeguridad().toUpperCase().equals(res2.toUpperCase())
                || login.getRespuestaSeguridad2().toUpperCase().equals(res1.toUpperCase())
                || login.getRespuestaSeguridad2().toUpperCase().equals(res2.toUpperCase())) {
            view.showQuestionsSuccess();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigator.navigateToUpdatePassword();
                }
            }, 2000);
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

    @Override
    public void onBackPressed() {
        navigator.navigateToBack();
    }

    @Override
    public void onCleanClicked() {
        view.cleanFields();
    }
}

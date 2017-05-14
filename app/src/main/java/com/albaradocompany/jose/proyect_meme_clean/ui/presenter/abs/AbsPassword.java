package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdatePasswordInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByEmail;

/**
 * Created by jose on 27/04/2017.
 */

public abstract class AbsPassword extends Presenter<AbsPassword.View, AbsPassword.Navigator> {
    public abstract void onEmailSubmitClicked(GetUserByEmail getUserByEmail);

    public abstract void onQuestionsSubmitClicked(Login user, String res1, String res2);

    public abstract void onUpdateSubmitClicked(UpdatePasswordInteractor updatePasswordInteractor);

    public abstract void onBackPressed();

    public abstract void onCleanClicked();

    public interface View {
        void showNoInternetAvailable();

        void showError(Exception e);

        void showEmailLoading();

        void hideEmailLoading();

        void showQuestionsLoading();

        void hideQuestionsLoading();

        void showUpdateLoading();

        void hideUpdateLoading();

        void showEmailSucces(Login login);

        void showQuestionsSuccess();

        void shoQuestionsFailure();

        void showUpdateSuccess();

        void showUpdateFailure();

        void showEmailFailure();

        void cleanFields();
    }

    public interface Navigator {
        void navigateToQuestions();

        void navigateToUpdatePassword();

        void navigateToSignin();

        void navigateToBack();

    }
}

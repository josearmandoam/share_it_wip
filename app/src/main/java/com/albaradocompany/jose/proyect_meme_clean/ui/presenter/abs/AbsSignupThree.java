package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import android.graphics.Bitmap;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetRegistrationResponse;

import java.util.List;

/**
 * Created by jose on 19/04/2017.
 */

public abstract class AbsSignupThree extends Presenter<AbsSignupThree.View, AbsSignupThree.Navigator> {

    public abstract void onBackPressed();

    public abstract void onConfirmClicked(GetRegistrationResponse getRegistrationResponse, Login user, Bitmap bitmap);

    public abstract void onImageClicked();

    public abstract void onRefreshQuestionClicked();

    public abstract void onCleanClicked();

    public interface View {

        void checkInfoSaved();

        void showLoading();

        void hideLoading();

        void loadUserImage();

        void showImage();

        void showNoInternetAvailable();

        void showError(Exception e);

        void showSuccess();

        void showErrorRegistration();

        void showQuestions(List<Question> questions);

        void refreshQuestions();

        void cleanFields();
    }

    public interface Navigator {

        void navigateToLogin();
    }
}

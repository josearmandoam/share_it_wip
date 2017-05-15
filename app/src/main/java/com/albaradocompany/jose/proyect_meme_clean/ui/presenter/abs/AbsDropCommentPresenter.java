package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateCommentInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 15/05/2017.
 */

public abstract class AbsDropCommentPresenter extends Presenter<AbsDropCommentPresenter.View, AbsDropCommentPresenter.Navigator> {
    public abstract void onDropClicked(UpdateCommentInteractor delCommentInteractor, Comment comment);

    public interface View {
        void hideLoading();

        void showNoInternetAvailable();

        void showError(Exception e);

        void hideDialog();

        void showFailure();

        void showLoading();
    }

    public interface Navigator {
    }
}

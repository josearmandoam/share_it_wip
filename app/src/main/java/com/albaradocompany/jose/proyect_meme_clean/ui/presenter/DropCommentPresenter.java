package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateCommentInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsDropCommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateComment;

/**
 * Created by jose on 15/05/2017.
 */

public class DropCommentPresenter extends AbsDropCommentPresenter {
    Context context;

    public DropCommentPresenter(Context context) {
        this.context = context;
    }

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
    public void onDropClicked(UpdateCommentInteractor delCommentInteractor, Comment comment) {
        view.showLoading();
        delCommentInteractor.updateComments(new UpdateComment.Listener() {
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
            public void onSuccess() {
                view.hideLoading();
                view.hideDialog();

            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.showFailure();
            }
        });
    }
}

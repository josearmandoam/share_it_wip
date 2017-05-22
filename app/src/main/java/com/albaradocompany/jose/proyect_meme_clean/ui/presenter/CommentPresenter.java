package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsCommentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateComment;

import java.util.List;

/**
 * Created by jose on 15/05/2017.
 */

public class CommentPresenter extends AbsCommentPresenter {
    Context context;

    public CommentPresenter(Context context) {
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
    public void onSendCommentClicked(UpdateComment updateComment, String imageId) {
        view.showLoading();
        updateComment.updateComments(new UpdateComment.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.onNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.shoError(e);
            }

            @Override
            public void onSuccess() {
                view.hideLoading();
                view.updateComments();
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.showFailure();
            }
        });
    }

    @Override
    public void onCommentLongClick(Comment comment) {
        view.showDialogDropComment(comment);
    }

    @Override
    public void getPictureComments(GetComments getComments, String imageId) {
        getComments.getComments(new GetComments.Listener() {
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
            public void onCommentesReceived(List<Comment> comments) {
                view.showComments(comments);
                view.hideLoading();
            }
        });
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void onPictureClicked(Comment comment) {
        navigator.navigateToUserDetail(comment);
    }

    @Override
    public void onUsernameClicked(Comment comment) {
        navigator.navigateToUserDetail(comment);
    }
}

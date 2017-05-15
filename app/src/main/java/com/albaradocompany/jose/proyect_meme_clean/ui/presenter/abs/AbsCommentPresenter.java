package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateComment;

import java.util.List;

/**
 * Created by jose on 15/05/2017.
 */

public abstract class AbsCommentPresenter extends Presenter<AbsCommentPresenter.View, AbsCommentPresenter.Navigator> {
    public abstract void onSendCommentClicked(UpdateComment updateComment, String imageId);

    public abstract void onCommentLongClick(Comment comment);

    public abstract void getPictureComments(GetComments getComments, String imageId);

    public abstract void onBackClicked();

    public interface View {
        void showLoading();

        void hideLoading();

        void onNoInternetAvailable();

        void shoError(Exception e);

        void showFailure();

        void updateComments();

        void showDialogDropComment(Comment comment);

        void showNoInternetAvailable();

        void showError(Exception e);

        void showComments(List<Comment> comments);
    }

    public interface Navigator {
        void navigateToBack();
    }
}

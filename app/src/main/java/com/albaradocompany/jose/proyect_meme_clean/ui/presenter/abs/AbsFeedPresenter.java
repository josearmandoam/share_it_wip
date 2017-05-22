package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public abstract class AbsFeedPresenter extends Presenter<AbsFeedPresenter.View, AbsFeedPresenter.Navigator> {
    public abstract void getFeed(FeedInteractor feedInteracto);

    public abstract void onCommentsClicked(List<Comment> comments, String imageId, Post post, int adapterPosition);

    public abstract void onLikesClicked(List<Like> likes);

    public abstract void onSaveClicked(Picture picture, String userId);

    public abstract void onUnSaveClicked(Picture picture, String userId);

    public abstract void onSaveLikeClicked(String imageId);

    public abstract void onUnSaveLikeClicked(String userId, String imageId);

    public abstract void onSearchClickedFAB();

    public abstract void onSavePictureClickedFAB();

    public abstract void onProfileClickedFAB();

    public interface View {
        void showNoInternetAvailable();

        void showError(Exception e);

        void showPosts(List<Post> listpost);

        void showLoading();

        void hideLoading();

        void showLikesDialog(List<Like> likes);

        void showSaveSuccess();

        void showSaveFailure();

        void showUnSaveSuccess();

        void showUnSaveFailure();

        void showLikeSuccess();

        void showLikedFailure();

        void showUnLikeSuccess();

        void showUnLikeFailure();

        void showFloatingButton();

        void updatePosts(List<Post> posts);
    }

    public interface Navigator {
        void navigateToComments(List<Comment> comments, String imageId, Post post, int adapterPosition);

        void navigateToSearch();

        void navigateToSavePictures();

        void navigateToProfile();
    }
}

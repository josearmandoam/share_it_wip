package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public abstract class AbsPicturePresenter extends Presenter<AbsPicturePresenter.View, AbsPicturePresenter.Navigator> {
    public abstract void getPictureLikes(GetLikes getLikes, String imageId);

    public abstract void getPictureComments(GetComments commentsInteractor, String imageId);

    public abstract void initializeData(Login user, Picture pic, GetLikes likesInteractor, GetComments commentsInteractor);

    public abstract void onCommentsClicked();

    public abstract void onLikesClicked();

    public abstract void onUserLikePhoto(UpdateLike updateLike, List<Like> likeList);

    public abstract void onUserUnLikePhoto(UpdateLike updateLike, List<Like> likeList);

    public abstract void onBackClicked();

    public interface View {
        void showLoading();

        void showNoInternetAvailable();

        void showError(Exception e);

        void showLikes(List<Like> likes);

        void showComments(List<Comment> comments);

        void hideLoading();

        void showPicture(String imagePath);

        void showUserProfile(String imagePath);

        void showDescription(String description);

        void showUsername(String s);

        void showTime(String time);

        void showSaved(String imagePath);

        void showLikesDialog();

        void showUnLikePicture();

        void showLikePicture();
    }

    public interface Navigator {
        void navigateToComments();

        void navigateToBack();
    }
}

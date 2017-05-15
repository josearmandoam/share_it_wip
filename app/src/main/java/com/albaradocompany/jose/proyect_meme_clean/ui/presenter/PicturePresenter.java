package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsPicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;

import java.util.List;

/**
 * Created by jose on 14/05/2017.
 */

public class PicturePresenter extends AbsPicturePresenter {
    Context context;
    Login user;
    Picture picture;
    GetLikes getLikes;
    GetComments getComments;

    public PicturePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        view.showPicture(picture.getImagePath());
        view.showDescription(picture.getDescription());
        view.showUserProfile(user.getImagePath());
        view.showUsername(user.getNombre() + " " + user.getApellidos());
        view.showTime(picture.getTime());
        view.showSaved(picture.getImageId());
    }

    @Override
    public void resume() {
        getPictureLikes(getLikes, picture.getImageId());
        getPictureComments(getComments, picture.getImageId());
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void getPictureLikes(GetLikes getLikes, String imageId) {
        view.showLoading();
        getLikes.getLikes(new GetLikes.Listener() {
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
            public void onLikesReceived(List<Like> likes) {
                view.showLikes(likes);
            }
        });
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
                initialize();
            }
        });
    }

    @Override
    public void initializeData(Login user, Picture pic, GetLikes likesInteractor, GetComments commentsInteractor) {
        this.user = user;
        this.picture = pic;
        this.getLikes = likesInteractor;
        this.getComments = commentsInteractor;
    }

    @Override
    public void onCommentsClicked() {
        navigator.navigateToComments();
    }

    @Override
    public void onLikesClicked() {
        view.showLikesDialog();
    }

    @Override
    public void onUserLikePhoto(UpdateLike updateLike, List<Like> likeList) {
        view.showLikes(updateListAfterInsert(likeList));
        view.showLikePicture();
        updateLike.updateLike(new UpdateLike.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    private List<Like> updateListAfterInsert(List<Like> likeList) {
        likeList.add(new Like(picture.getImageId(), user.getNombre() + " " + user.getApellidos(),
                user.getImagePath(), user.getIdUser()));
        return likeList;
    }

    @Override
    public void onUserUnLikePhoto(UpdateLike updateLike, List<Like> likeList) {
        view.showLikes(updateListAfterDelete(likeList));
        view.showUnLikePicture();
        updateLike.updateLike(new UpdateLike.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    private List<Like> updateListAfterDelete(List<Like> likeList) {
        for (int i = 0; i < likeList.size(); i++) {
            if (likeList.get(i).getUserId().equals(user.getIdUser()))
                likeList.remove(i);
        }
        return likeList;
    }
}

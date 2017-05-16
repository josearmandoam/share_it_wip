package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateSavedPictureInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.PictureActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsPicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateSavedPicture;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 14/05/2017.
 */

public class PicturePresenter extends AbsPicturePresenter {
    Context context;
    User user;
    Picture picture;
    GetLikes getLikes;
    GetComments getComments;

    @Inject
    GetUserBDImp getUserBDImp;

    public PicturePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);

        view.showLoading();
        view.showPicture(picture.getImagePath());
        view.showDescription(picture.getDescription());
        view.showUserProfile(user.getProfile());
        view.showUsername(user.getName() + " " + user.getLastname());
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
        getLikes.getLikes(new GetLikes.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
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
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onCommentesReceived(List<Comment> comments) {
                view.hideLoading();
                view.showComments(comments);
            }
        });
    }

    @Override
    public void initializeData(User user, Picture pic, GetLikes likesInteractor, GetComments commentsInteractor) {
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
        likeList.add(new Like(picture.getImageId(), user.getName() + " " + user.getLastname(),
                user.getProfile(), user.getUserId(), "like" + DateUtil.getCurrentDateTime()));
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

    @Override
    public void onDeleteSavedPicture(UpdateSavedPictureInteractor updateDELSaveInteractor) {
        view.showPictureNotSaved();
        updateDELSaveInteractor.updateSavedPicture(new UpdateSavedPicture.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {
                getUserBDImp.deleteUserSavedPicture(picture.getImageId());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onInsertSavePicture(UpdateSavedPictureInteractor updateINSSaveInteractor) {
        view.showPictureSaved();
        updateINSSaveInteractor.updateSavedPicture(new UpdateSavedPicture.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {
                getUserBDImp.insertUserSavedPicture(picture);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private List<Like> updateListAfterDelete(List<Like> likeList) {
        for (int i = 0; i < likeList.size(); i++) {
            if (likeList.get(i).getUserId().equals(user.getUserId()))
                likeList.remove(i);
        }
        return likeList;
    }

    protected UIComponent getComponent() {
        return ((PictureActivity) context).component();
    }
}

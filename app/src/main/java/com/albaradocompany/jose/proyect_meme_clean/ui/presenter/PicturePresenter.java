package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.GetUserApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.PictureActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsPicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetComments;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLikes;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUser;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateLike;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateSavedPicture;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 14/05/2017.
 */

public class PicturePresenter extends AbsPicturePresenter {
    Context context;
    Picture picture;
    GetLikes getLikes;
    GetComments getComments;
    UserBD userBD;
    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;

    public PicturePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
        userBD = getUserBDImp.getUsers().get(0);
        view.showPicture(picture.getImagePath());
        view.showDescription(picture.getDescription());
        view.showTime(picture.getDate(), picture.getTime());
        view.showSaved(picture.getImageId());
        view.showLikes(picture.getLikes());
        view.showComments(picture.getComments());
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
                view.showComments(comments);
            }
        });
    }

    @Override
    public void initializeData(String userId, Picture pic, GetLikes likesInteractor, GetComments commentsInteractor) {
        UserInteractor interactor = getUserInteractor(userId);
        interactor.getUser(new GetUser.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onUserReceived(User user) {
                view.showUserProfile(user.getProfile());
                view.showUsername(user.getName() + " " + user.getLastname());
                view.hideLoading();
            }
        });
        this.picture = pic;
        this.getLikes = likesInteractor;
        this.getComments = commentsInteractor;
    }

    private UserInteractor getUserInteractor(String userId) {
        return new UserInteractor(new GetUserApiImp(userId), new MainThreadImp(), new ThreadExecutor());
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
        likeList.add(new Like(picture.getImageId(), userBD.user_name + " " + userBD.user_lastname,
                userBD.user_profile, userBD.userId, "like" + DateUtil.getCurrentDateTime()));
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
    public void onDeleteSavedPicture() {
        getUserBDImp.deleteUserSavedPicture(picture.getImageId());
        view.showPictureNotSaved();
    }

    @Override
    public void onInsertSavePicture() {
        getUserBDImp.insertUserSavedPicture(picture);
        view.showPictureSaved();
    }

    private List<Like> updateListAfterDelete(List<Like> likeList) {
        for (int i = 0; i < likeList.size(); i++) {
            if (likeList.get(i).getUserId().equals(userBD.userId))
                likeList.remove(i);
        }
        return likeList;
    }

    protected UIComponent getComponent() {
        return ((PictureActivity) context).component();
    }
}

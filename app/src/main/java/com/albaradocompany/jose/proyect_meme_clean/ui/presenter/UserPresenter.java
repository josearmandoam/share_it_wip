package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.FeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateFeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateFeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUser;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateFeed;

import java.util.List;

/**
 * Created by jose on 16/05/2017.
 */

public class UserPresenter extends AbsUserPresenter {
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    Context context;
    String userId;
    GetUser getUser;
    String mUserId;
    Feed feedRegistred;

    public UserPresenter(Context context, String userId, GetUser getUser, String mUserId) {
        this.context = context;
        this.userId = userId;
        this.getUser = getUser;
        this.mUserId = mUserId;
        feedRegistred = new Feed();
    }

    @Override
    public void initialize() {
        view.showLoading();
        getFeedInteractor().getFeed(new GetFeed.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onFeedReceived(List<Feed> feeds) {
                for (Feed feed : feeds) {
                    if (feed.getxUserId().equals(userId)) {
                        view.showNewFloatingButton();
                        feedRegistred = feed;
                    }
                }
            }
        });
        getUser.getUser(new GetUser.Listener() {
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
            public void onUserReceived(User user) {
                view.showBackground(user.getBackground());
                view.showProfile(user.getProfile());
                view.showName(user.getName() + " " + user.getLastname());
                view.showUsername("@" + user.getUsername());
                view.showDescription(user.getDescription());
                view.showWhatsapp(user.getSocialWhatsapp());
                view.showInstagram(user.getSocialInstagram());
                view.showFacebook(user.getSocialFacebook());
                view.showWebsite(user.getSocialWebsite());
                view.showTwitter(user.getSocialTwitter());
                view.showEmail(user.getSocialEmail());
                view.hideLoading();
                view.showPhotosLoading();
                view.showUser(user);
                getUserPhotos();
            }
        });
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

    private void getUserPhotos() {
        PicturesByIdInteractor interactor = new PicturesByIdInteractor(new PicturesByIdApiImp(userId), new MainThreadImp(), new ThreadExecutor());
        interactor.getPictures(new GetPicturesById.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hidePhotosLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hidePhotosLoading();
                view.showError(e);
            }

            @Override
            public void onPicturesReceived(List<Picture> pictures) {
                view.hidePhotosLoading();
                view.showPictures(pictures);
            }
        });
    }

    @Override
    public void onPictureClicked(Picture picture) {
        navigator.navigateToPicture(picture);
    }

    @Override
    public void onFacebookClicked() {
        view.showFacebookDialog();
    }

    @Override
    public void onWhastappClicked() {
        view.showWhastappDialog();
    }

    @Override
    public void onInstagramClicked() {
        view.showInstagramDialog();
    }

    @Override
    public void onWebsiteClicked() {
        view.showWebsiteDialog();
    }

    @Override
    public void onTwitterClicked() {
        view.showTwitterDialog();
    }

    @Override
    public void onEmailClicked() {
        view.showEmailDialog();
    }

    @Override
    public void onFacebookDialogAccepted() {
        navigator.openFacebookPage();
    }

    @Override
    public void onWhatsappDialogAccepted() {
        navigator.openWhatsapp();
    }

    @Override
    public void onInstagramDialogAccepted() {
        navigator.openInstagramPage();
    }

    @Override
    public void onWebsiteDialogAccepted() {
        navigator.openWebsitePage();
    }

    @Override
    public void onTwitterDialogAccepted() {
        navigator.openTwitterPage();
    }

    @Override
    public void onEmailDialogAccepted() {
        navigator.openEmailSelector();
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void updateFollow(String userId, String xUserId, String xUserNameComplete, String profile, final String action) {
        view.hideFloatinButton();
        if (action.equals(INSERT)) {
            UpdateFeedInteractor interactor = getUpdateFeedInteractorINS(userId, xUserId, xUserNameComplete, profile, action);
            interactor.updateFedd(new UpdateFeed.Listener() {
                @Override
                public void onNoInternetAvailable() {
                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e);
                }

                @Override
                public void onSuccess() {
                    view.showNewFloatingButton();
                }

                @Override
                public void onFailure() {
                    view.showError(new Exception("An error ocurred"));
                }
            });
        } else {
            UpdateFeedInteractor interactor = getUpdateFeedInteractorDEL();
            interactor.updateFedd(new UpdateFeed.Listener() {
                @Override
                public void onNoInternetAvailable() {
                    view.showNoInternetAvailable();
                }

                @Override
                public void onError(Exception e) {
                    view.showError(e);
                }

                @Override
                public void onSuccess() {
                    view.showInitialFloatingButton();
                }

                @Override
                public void onFailure() {
                    view.showError(new Exception("An error ocurred"));
                }
            });
        }
    }

    private UpdateFeedInteractor getUpdateFeedInteractorDEL() {
        return new UpdateFeedInteractor(new UpdateFeedApiImp(null, null, null, feedRegistred.getFeedId(), null, DELETE), new MainThreadImp(), new ThreadExecutor());
    }

    private UpdateFeedInteractor getUpdateFeedInteractorINS(String userId, String xUserId, String xUserNameComplete, String profile, String action) {
        feedRegistred.setFeedId(getFeedId());
        return new UpdateFeedInteractor(new UpdateFeedApiImp(userId, xUserId, profile, feedRegistred.getFeedId(), xUserNameComplete, action), new MainThreadImp(), new ThreadExecutor());
    }

    @Override
    public void onChatClicked(String userId, String xUserId, String xCompleteNAME, String userCompleteName) {
        navigator.openNotificationsPage(userId, xUserId, xCompleteNAME, userCompleteName);
    }

    public String getFeedId() {
        return "feed" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime();
    }

    public FeedInteractor getFeedInteractor() {
        return new FeedInteractor(new FeedApiImp(mUserId), new MainThreadImp(), new ThreadExecutor());
    }
}

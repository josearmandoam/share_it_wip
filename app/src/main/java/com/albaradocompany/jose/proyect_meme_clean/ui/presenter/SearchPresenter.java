package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.FeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateFeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UserByUsernameApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateFeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UserByUsernameInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserByUsername;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateFeed;

import java.util.List;

/**
 * Created by jose on 25/05/2017.
 */

public class SearchPresenter extends com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSearchPresenter {
    private static final String INSERT = "insert";
    private static final String DELETE = "delete";
    Context context;

    public SearchPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        view.configureToolbar();
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
    public void onSubmitClicked(String username, final String userId) {
        view.showLoading();
        UserByUsernameInteractor interactor = getUserByUsernameInteractor(username);
        interactor.getUser(new GetUserByUsername.Listener() {
            @Override
            public void onSuccess(List<User> users) {
                getFeed(userId, users);
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }
        });
    }

    @Override
    public void onFollowClicked(String userId, String xUserId, String xUsername, String xProfile) {
        UpdateFeedInteractor interactor = getINSUpdateFeedInteractor(userId, xUserId, xUsername, xProfile);
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
                view.showFollowSuccess();
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private UpdateFeedInteractor getINSUpdateFeedInteractor(String userId, String xUserId, String xUsername, String xProfile) {
        return new UpdateFeedInteractor(new UpdateFeedApiImp(userId, xUserId, xProfile,
                "feed" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime(), xUsername, INSERT), new MainThreadImp(), new ThreadExecutor());
    }

    @Override
    public void onUnFollowClicked(String feedId) {
        UpdateFeedInteractor interactor = getDELUpdateInteractor(feedId);
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
                view.showUnFollowSuccess();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private UpdateFeedInteractor getDELUpdateInteractor(String feedId) {
        return new UpdateFeedInteractor(new UpdateFeedApiImp(null, null, null,
                feedId, null, DELETE), new MainThreadImp(), new ThreadExecutor());
    }

    @Override
    public void onUserClicked(String xUserId, String userId) {
        if (xUserId.equals(userId))
            navigator.navigateToProfile();
        else
            navigator.navigateToUserDetail(xUserId);
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void updateFeeds(final String userId) {
        FeedInteractor interactor = getFeedInteractor(userId);
        interactor.getFeed(new GetFeed.Listener() {
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
            public void onFeedReceived(List<Feed> feeds) {
                view.hideLoading();
                view.updateRecyclerWithNewFeeds(userId, feeds);
            }
        });
    }

    private void getFeed(final String userId, final List<User> users) {
        FeedInteractor interactor = getFeedInteractor(userId);
        interactor.getFeed(new GetFeed.Listener() {
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
            public void onFeedReceived(List<Feed> feeds) {
                view.hideLoading();
                view.updateRecycler(userId, users, feeds);
            }
        });
    }

    private FeedInteractor getFeedInteractor(String userId) {
        return new FeedInteractor(new FeedApiImp(userId), new MainThreadImp(), new ThreadExecutor());
    }

    public UserByUsernameInteractor getUserByUsernameInteractor(String username) {
        return new UserByUsernameInteractor(new UserByUsernameApiImp(username), new MainThreadImp(), new ThreadExecutor());
    }
}

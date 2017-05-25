package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 25/05/2017.
 */

public abstract class AbsSearchPresenter extends Presenter<AbsSearchPresenter.View, AbsSearchPresenter.Navigator> {
    public abstract void onSubmitClicked(String username, String userId);

    public abstract void onFollowClicked(String userId, String xUserId, String xUsername, String xProfile);

    public abstract void onUnFollowClicked(String feedId);

    public abstract void onUserClicked(String xUserId, String userId);

    public abstract void onBackClicked();

    public abstract void updateFeeds(String userId);

    public interface View {
        void configureToolbar();

        void showLoading();

        void hideLoading();

        void showError(Exception e);

        void showNoInternetAvailable();

        void updateRecycler(String userId, List<User> users, List<Feed> feeds);

        void showFollowSuccess();

        void showUnFollowSuccess();

        void updateRecyclerWithNewFeeds(String userId, List<Feed> feeds);
    }

    public interface Navigator {
        void navigateToProfile();

        void navigateToUserDetail(String xUserId);

        void navigateToBack();
    }
}

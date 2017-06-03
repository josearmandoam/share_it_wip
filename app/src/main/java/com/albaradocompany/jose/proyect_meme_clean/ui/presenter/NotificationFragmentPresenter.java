package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.FeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.interactor.FeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsNotificationFragmentPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetFeed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 31/05/2017.
 */

public class NotificationFragmentPresenter extends AbsNotificationFragmentPresenter {
    Context context;
    @Inject
    GetUserBDImp db;

    public NotificationFragmentPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        getComponent().inject(this);
        view.showNotifications(db.parseNotificationLines(db.getNotificationLinesGRUOUPBY()), (db.getNotificationLines()));
        view.showFloatingButton();
    }

    @Override
    public void resume() {
        view.showNotifications(db.parseNotificationLines(db.getNotificationLinesGRUOUPBY()), (db.getNotificationLines()));
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    protected UIComponent getComponent() {
        return ((MainActivity) context).component();
    }

    @Override
    public void onNotificationLineClicked(String name, List<NotificationLine> list, String userId) {
        view.openNotifications(name, list, userId);
        view.hideFloatingButton();
    }

    @Override
    public void onFloatingClicked() {
        view.hideFloatingButton();
    }

    @Override
    public void onFloatingButtonHidden(final String mUserId) {
        FeedInteractor interactor = getFeedInteractor(mUserId);
        interactor.getFeed(new GetFeed.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onFeedReceived(List<Feed> feeds) {
                List<Feed> list = new ArrayList<Feed>();
                for (Feed feed : feeds) {
                    if (!feed.getxUserId().equals(mUserId))
                        list.add(feed);
                }
                view.showButtonSheet(list);
            }
        });
    }

    private FeedInteractor getFeedInteractor(String mUserId) {
        return new FeedInteractor(new FeedApiImp(mUserId), new MainThreadImp(), new ThreadExecutor());
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsAvatarsPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetAvatars;

import java.util.List;

/**
 * Created by jose on 03/05/2017.
 */

public class AvatarsPresenter extends AbsAvatarsPresenter {

    GetAvatars getAvatars;

    public AvatarsPresenter(GetAvatars getAvatars) {
        this.getAvatars = getAvatars;
    }

    @Override
    public void initialize() {
        view.showLoading();
        getAvatars.getAvatars(new GetAvatars.Listener() {
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
            public void onAvatarsReceived(List<Avatar> avatarList) {
                view.hideLoading();
                view.showAvatarList(avatarList);
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

    @Override
    public void onAvatarClicked(Avatar avatar) {
        view.showAvatarClicked(avatar);
    }
}

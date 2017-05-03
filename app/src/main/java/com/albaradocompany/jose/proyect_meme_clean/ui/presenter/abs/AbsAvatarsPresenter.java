package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

import java.util.List;

/**
 * Created by jose on 03/05/2017.
 */

public abstract class AbsAvatarsPresenter extends Presenter<AbsAvatarsPresenter.View, AbsAvatarsPresenter.Navigator> {

    public abstract void onAvatarClicked(Avatar avatar);

    public interface View {

        void showLoading();

        void hideLoading();

        void showAvatarList(List<Avatar> avatarList);

        void showNoInternetAvailable();

        void showError(Exception e);

        void showAvatarClicked(Avatar avatar);
    }

    public interface Navigator {
    }
}

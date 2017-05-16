package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 15/05/2017.
 */

public abstract class AbsLikesPresenter extends Presenter<AbsLikesPresenter.View, AbsLikesPresenter.Navigator> {
    public abstract void onBackClicked();

    public abstract void onPictureClicked(Like like);

    public abstract void onUsernameClicked(Like like);

    public interface View {
        void hideDialog();
    }

    public interface Navigator {
        void navigateToUserDetail(Like like);
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 15/05/2017.
 */

public abstract class AbsLikesPresenter extends Presenter<AbsLikesPresenter.View, AbsLikesPresenter.Navigator> {
    public abstract void onBackClicked();

    public interface View {
        void hideDialog();
    }

    public interface Navigator {
    }
}

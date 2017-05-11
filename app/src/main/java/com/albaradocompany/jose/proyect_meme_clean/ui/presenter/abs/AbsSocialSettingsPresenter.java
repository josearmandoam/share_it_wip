package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 11/05/2017.
 */

public abstract class AbsSocialSettingsPresenter extends Presenter<AbsSocialSettingsPresenter.View, AbsSocialSettingsPresenter.Navigator> {
    public abstract void onConfirmClicked();

    public abstract void updateSwitchs();

    public interface View {
        void dimissDialog();

        void updateSwitchs();
    }

    public interface Navigator {
    }
}

package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 18/04/2017.
 */

public abstract class AbsSplash extends Presenter<AbsSplash.View, AbsSplash.Navigator> {

    public abstract void userLogged(boolean userLogged);

    public interface View {

        void sleep();

    }

    public interface Navigator {
        void navigateToLoginActivity();

        void navigateToMainPage();
    }
}

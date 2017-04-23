package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 23/04/2017.
 */

public abstract class AbsConfirmAvatar extends Presenter<AbsConfirmAvatar.View, AbsConfirmAvatar.Navigator> {
    public abstract void onAcceptClicked();

    public interface View {

    }

    public interface Navigator {
        void closeConfirmAvatar();
    }
}

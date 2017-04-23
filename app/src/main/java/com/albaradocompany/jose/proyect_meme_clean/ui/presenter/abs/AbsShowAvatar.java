package com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs;

import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.Presenter;

/**
 * Created by jose on 23/04/2017.
 */

public abstract class AbsShowAvatar extends Presenter<AbsShowAvatar.View, AbsShowAvatar.Navigator> {
    public abstract void onEditClicked();

    public abstract void onDeleteClicked();

    public interface View {
        void deleteImage();

        void loadImage();
    }

    public interface Navigator {
        void navigateToEdit();
    }
}

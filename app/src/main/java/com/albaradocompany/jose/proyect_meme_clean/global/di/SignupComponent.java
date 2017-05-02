package com.albaradocompany.jose.proyect_meme_clean.global.di;

import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.LoginActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SplashActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;

import dagger.Component;

/**
 * Created by jose on 25/04/2017.
 */
@Component(dependencies = RootComponent.class, modules = {SignupModule.class, MainModule.class})
public interface SignupComponent {
    void inject(SignupThreeActivity signupThreeActivity);

    void inject(SignupTwoActivity signupTwoActivity);

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);

    void inject(SignupOneActivity signupOneActivity);

    void inject(SignupThreePresenter signupThreePresenter);

    void inject(ShowAvatarDialog showAvatarDialog);

    void inject(ConfirmAvatarDialog confirmAvatarDialog);

    void inject(AddPhotoActivty addPhotoActivty);
}

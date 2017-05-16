package com.albaradocompany.jose.proyect_meme_clean.global.di;

import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.CommentsActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.LoginActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.PictureActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.ProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SavedPicturesActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SplashActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.DropCommentDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ShowAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.SocialSettingsDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.AvatarsFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.fragments.CamGallFragment;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.EditProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.LoginPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.PicturePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SavedPicturesPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SignupThreePresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.SplashPresenter;

import dagger.Component;

/**
 * Created by jose on 25/04/2017.
 */
@Component(dependencies = RootComponent.class, modules = {UIModule.class, MainModule.class})
public interface UIComponent {
    void inject(SignupThreeActivity signupThreeActivity);

    void inject(SignupTwoActivity signupTwoActivity);

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);

    void inject(SignupOneActivity signupOneActivity);

    void inject(SignupThreePresenter signupThreePresenter);

    void inject(ShowAvatarDialog showAvatarDialog);

    void inject(ConfirmAvatarDialog confirmAvatarDialog);

    void inject(AddPhotoActivty addPhotoActivty);

    void inject(CamGallFragment camGallFragment);

    void inject(AvatarsFragment avatarsFragment);

    void inject(SplashPresenter splashPresenter);

    void inject(ProfileActivity profileActivity);

    void inject(LoginPresenter loginPresenter);

    void inject(EditProfileActivity editProfileActivity);

    void inject(EditProfilePresenter editProfilePresenter);

    void inject(ProfilePresenter profilePresenter);

    void inject(SocialSettingsDialog socialSettingsDialog);

    void inject(PictureActivity pictureActivity);

    void inject(CommentsActivity commentsActivity);

    void inject(PicturePresenter picturePresenter);

    void inject(SavedPicturesActivity savedPicturesActivity);

    void inject(SavedPicturesPresenter savedPicturesPresenter);
}

package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 28/04/2017.
 */

public interface UserShared {
    boolean isLogged();

    void cleanUserLogged();

    void saveUserLogged();

    Login getUser();

    void saveUser(Login user);

    String getBackground();

    String getProfile();

    void saveProfile(String profile);

    void saveBackground(String background);

    boolean isSelectedBackground();

    void deleteBackground();

    void saveUserID(String id);

    String getUserID();

    boolean isSelectedProfile();

    void deleteProfile();

    boolean isProfileFTPSelected();

    boolean isBackgroundFTPSelected();

    void saveProfileFTPSelected();

    void saveBackgroundFTPSelected();

    boolean isProfileChanged();

    void saveProfileChanges(String cond);

    void saveNewBackground(String dir);

    void saveNewPicture(String dir);

    String getNewProfile();

    String getNewBackground();

    void saveBackgroundChanges(String cond);

    boolean isBackgroundChanged();

    void deleteUserData();
}

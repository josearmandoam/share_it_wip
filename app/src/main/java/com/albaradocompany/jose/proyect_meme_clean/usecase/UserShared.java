package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 28/04/2017.
 */

public interface UserShared {
    boolean isActivityOnBackground();

    void saveActivityStatus(String status);

    boolean isLogged();

    void cleanUserLogged();

    void saveUserLogged();

    Login getUser();

    String getBackground();

    String getProfile();

    void saveProfile(String profile);

    void saveBackground(String background);

    void deleteBackground();

    void saveUserID(String id);

    String getUserID();

    void deleteProfile();

    boolean isProfileFTPSelected();

    boolean isBackgroundFTPSelected();

    void saveProfileFTPSelected(String state);

    void saveBackgroundFTPSelected(String state);

    boolean isProfileChanged();

    void saveProfileChanges(String cond);

    String getUserToken();

    void saveBackgroundChanges(String cond);

    boolean isBackgroundChanged();

    void deleteUserData();

    void saveProfileAvatar(Avatar avatar);

    void saveBackgroundAvatar(Avatar avatar);

    String getProfileAvatar();

    String getBackgroundAvatar();

    void saveFacebookPrivacity(String status);

    void saveTwitterPrivacity(String status);

    void saveInstagramPrivacity(String status);

    void saveWhatsappPrivacity(String status);

    void saveWebsitePrivacity(String status);

    void saveEmailPrivacity(String status);

    boolean getFacebookPrivacity();

    boolean getTwitterPrivacity();

    boolean getInstagramPrivacity();

    boolean getWhatsappPrivacity();

    boolean getWebsitePrivacity();

    boolean getEmailPrivacity();

    void updateSocialMedia(String socialTwitter, String socialFacebook, String socialWhatsapp,
                           String socialEmail, String socialInstagram, String socialWebsite);

    void saveUserToken(String recent_token);
}

package com.albaradocompany.jose.proyect_meme_clean.usecase;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;

/**
 * Created by jose on 28/04/2017.
 */

public interface SignupShared {
    void removeSignInformation();

    boolean isPhotoTaken();

    boolean isDateBirthDayTaken();

    void saveSignoneInfo(String name, String lastname, String email);

    void saveDateBirthday(int i, int i2, int i3);

    boolean isAvatarTaken();

    String getUserAvatar();

    String getUserPhoto();

    void savePhotoTaken(String path);

    String savePictureOnMemory(Bitmap b, String name);

    String getUserPasswordSaved();

    String getUserPassword2Saved();

    String getUserUsernamedSaved();

    void saveSigntwoData(String username, String password);

    void showUserPhoto(ImageView imageView, String path, UserBD userBD);

    void showUserBackground(ImageView imageView, String path, UserBD userBD);

    void saveSignThreeData(String question, String ans1, String asn2);

    void deleteSignoneData();

    void deleteSigntwoData();

    void deleteSignThreeData();

    String getUserAnswer1Saved();

    String getUserAnswer2Saved();

    void saveUserAvatar(Avatar avatar);

    void deleteImageProfile();

    void photoStateTaken(String state);

    String getUserName();

    String getUserLastName();

    void saveUsernameSucReg(String userUsernamedSaved);

    String getSavedUsernameSusReg();

    void removeUsernameSucReg();
}

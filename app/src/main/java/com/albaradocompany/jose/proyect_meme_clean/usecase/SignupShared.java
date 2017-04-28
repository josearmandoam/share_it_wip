package com.albaradocompany.jose.proyect_meme_clean.usecase;

import android.graphics.Bitmap;
import android.widget.ImageView;

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

    String guardarImagenOnInternal(Bitmap b, String name);

    String getUserPasswordSaved();

    String getUserPassword2Saved();

    String getUserUsernamedSaved();

    void saveSigntwoData(String username, String password);

    void showUserPhoto(ImageView imageView, String path);

    void saveSignThreeData(String question, String ans1, String asn2);

    void deleteSignoneData();

    void deleteSigntwoData();

    void deleteSignThreeData();

    String getUserAnswer1Saved();

    String getUserAnswer2Saved();
}

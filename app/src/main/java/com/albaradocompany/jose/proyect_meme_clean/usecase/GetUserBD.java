package com.albaradocompany.jose.proyect_meme_clean.usecase;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public interface GetUserBD {
    void insertUserDB(Login user);

    void deleteUserBD(String id);

    UserBD getUserBD(String id);

    void insertUserPicture(Picture picture);

    void insertUserSavedPicture(Picture picture);

    List<PicturesBD> getUserPictures(String userId);

    List<SavedPicturesBD> getUserSavedPictures(String userId);
}

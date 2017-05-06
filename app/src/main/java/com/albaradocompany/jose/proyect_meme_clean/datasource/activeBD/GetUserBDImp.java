package com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetUserBD;

import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public class GetUserBDImp implements GetUserBD {
    Context context;

    public GetUserBDImp(Context context) {
        this.context = context;
    }

    @Override
    public void insertUserDB(Login user) {
        UserBD userBD = new UserBD();
        userBD.user_name = user.getNombre();
        userBD.user_username = user.getUsername();
        userBD.user_profile = user.getImagePath();
        userBD.user_background = user.getBackgrundPath();
        userBD.user_description = user.getDescription();
        userBD.user_email = user.getEmail();
        userBD.user_date = user.getFechaNacimiento();
        userBD.user_lastname = user.getApellidos();
        userBD.userId = user.getIdUser();
        long n = userBD.save();
    }

    @Override
    public void deleteUserBD(String id) {
        new Delete().from(UserBD.class).where("userId = ?", id).execute();
    }

    @Override
    public UserBD getUserBD(String id) {
        return new Select().from(UserBD.class).where("userId = ?", id).executeSingle();
    }

    @Override
    public void insertUserPicture(Picture picture) {
        PicturesBD picturesBD = new PicturesBD();
        picturesBD.userId = picture.getUserId();
        picturesBD.imagePath = picture.getImagePath();
        picturesBD.description = picture.getDescription();
        picturesBD.date = picture.getDate();
        picturesBD.save();
    }

    @Override
    public void insertUserSavedPicture(Picture picture) {
        SavedPicturesBD picturesBD = new SavedPicturesBD();
        picturesBD.userId = picture.getUserId();
        picturesBD.imagePath = picture.getImagePath();
        picturesBD.description = picture.getDescription();
        picturesBD.date = picture.getDate();
        picturesBD.save();
    }

    @Override
    public List<PicturesBD> getUserPictures(String userId) {
        return new Select().from(PicturesBD.class).where("userId = ?", userId).execute();
    }

    @Override
    public List<SavedPicturesBD> getUserSavedPictures(String userId) {
        return new Select().from(SavedPicturesBD.class).where("userId = ?", userId).execute();
    }
}

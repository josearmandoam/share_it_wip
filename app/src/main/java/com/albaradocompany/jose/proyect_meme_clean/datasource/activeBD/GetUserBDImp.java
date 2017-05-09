package com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
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
        picturesBD.imageId = picture.getImageId();
        picturesBD.coments = picture.getComents();
        picturesBD.likes = picture.getLikes();
        picturesBD.save();
    }

    @Override
    public void insertUserSavedPicture(Picture picture) {
        SavedPicturesBD picturesBD = new SavedPicturesBD();
        picturesBD.userId = picture.getUserId();
        picturesBD.imagePath = picture.getImagePath();
        picturesBD.description = picture.getDescription();
        picturesBD.date = picture.getDate();
        picturesBD.imageId = picture.getImageId();
        picturesBD.coments = picture.getComents();
        picturesBD.likes = picture.getLikes();
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

    @Override
    public void deleteUserPictures(String id) {
        new Delete().from(PicturesBD.class).where("userId = ?", id).execute();
    }

    @Override
    public void deleteUserSavedPictures(String id) {
        new Delete().from(SavedPicturesBD.class).where("userId = ?", id).execute();
    }

    @Override
    public void updateUserBD(Login user) {
        new Update(UserBD.class).set("user_profile = ?", user.getImagePath()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_description = ?", user.getDescription()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_username = ?", user.getUsername()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_background = ?", user.getBackgrundPath()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_email = ?", user.getEmail()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_lastname = ?", user.getApellidos()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("user_name = ?", user.getNombre()).where("userId = ?", user.getIdUser()).execute();
//        new Update(UserBD.class).set("user_profile = ?, user_description = ?, user_username = ?," +
//                        " user_background = ?, user_email = ?, user_lastname = ?, user_name = ?", user.getImagePath(),
//                user.getDescription(), user.getUsername(), user.getBackgrundPath(), user.getEmail(), user.getApellidos(),
//                user.getNombre()).where("userId = ?", user.getIdUser()).execute();
    }

    @Override
    public void removeUserDBData() {
        new Delete().from(UserBD.class).execute();
    }

    @Override
    public List<UserBD> getUsers() {
        return new Select().from(UserBD.class).execute();
    }
}
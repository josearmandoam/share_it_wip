package com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD;

import android.content.Context;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.NotificationLineBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetUserBD;

import java.util.ArrayList;
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
        userBD.social_email = user.getSocialEmail();
        userBD.social_facebook = user.getSocialFacebook();
        userBD.social_instagram = user.getSocialInstagram();
        userBD.social_twitter = user.getSocialTwitter();
        userBD.social_whatsapp = user.getSocialWhatsapp();
        userBD.social_website = user.getSocialWebsite();
        userBD.save();
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
        picturesBD.time = picture.getTime();
        picturesBD.save();
    }

    @Override
    public void insertUserSavedPicture(Picture picture) {
        new Delete().from(SavedPicturesBD.class).where("imageId = ?", picture.getImageId()).execute();
        SavedPicturesBD picturesBD = new SavedPicturesBD();
        picturesBD.userId = picture.getUserId();
        picturesBD.imagePath = picture.getImagePath();
        picturesBD.description = picture.getDescription();
        picturesBD.date = picture.getDate();
        picturesBD.imageId = picture.getImageId();
        picturesBD.time = picture.getTime();
        picturesBD.save();
    }

    @Override
    public List<PicturesBD> getUserPictures(String userId) {
        return new Select().from(PicturesBD.class).where("userId = ?", userId).execute();
    }

    @Override
    public List<SavedPicturesBD> getUserSavedPictures(String userId) {
        return new Select().from(SavedPicturesBD.class).execute();
    }

    @Override
    public void deleteUserPictures(String id) {
        new Delete().from(PicturesBD.class).where("userId = ?", id).execute();
    }

    @Override
    public void deleteUserPicture(String imageId) {
        new Delete().from(PicturesBD.class).where("imageId = ?", imageId).execute();
    }

    @Override
    public void deleteUserSavedPictures(String id) {
        new Delete().from(SavedPicturesBD.class).where("userId = ?", id).execute();
    }

    @Override
    public void deleteUserSavedPicture(String imageId) {
        new Delete().from(SavedPicturesBD.class).where("imageId = ?", imageId).execute();
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
        new Update(UserBD.class).set("social_email = ?", user.getSocialEmail()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("social_whatsapp = ?", user.getSocialWhatsapp()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("social_website = ?", user.getSocialWebsite()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("social_instagram = ?", user.getSocialInstagram()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("social_facebook = ?", user.getSocialFacebook()).where("userId = ?", user.getIdUser()).execute();
        new Update(UserBD.class).set("social_twitter = ?", user.getSocialTwitter()).where("userId = ?", user.getIdUser()).execute();
    }

    @Override
    public void removeUserDBData() {
        new Delete().from(UserBD.class).execute();
    }

    @Override
    public List<UserBD> getUsers() {
        return new Select().from(UserBD.class).execute();
    }

    @Override
    public User parseUserBD(UserBD userBD) {
        User c = new User();
        c.setUserId(userBD.userId);
        c.setProfile(userBD.user_profile);
        c.setName(userBD.user_name);
        c.setLastname(userBD.user_lastname);
        return c;
    }

    @Override
    public Picture parsePictureBD(PicturesBD picture) {
        Picture pic = new Picture();
        pic.setUserId(picture.userId);
        pic.setDate(picture.date);
        pic.setTime(picture.time);
        pic.setImageId(picture.imageId);
        pic.setImagePath(picture.imagePath);
        pic.setDescription(picture.description);
        pic.setUserId(picture.userId);
        return pic;
    }

    @Override
    public List<Picture> parseSavedPicturesBDList(List<SavedPicturesBD> savedPicturesBDs) {
        List<Picture> list = new ArrayList<Picture>();
        for (SavedPicturesBD picture : savedPicturesBDs) {
            Picture c = new Picture();
            c.setImageId(picture.imageId);
            c.setUserId(picture.userId);
            c.setDescription(picture.description);
            c.setImagePath(picture.imagePath);
            c.setDate(picture.date);
            c.setTime(picture.time);
            list.add(c);
        }
        return list;
    }

    @Override
    public List<Picture> parsePicturesBDList(List<PicturesBD> PicturesBDs) {
        List<Picture> list = new ArrayList<Picture>();
        for (PicturesBD picture : PicturesBDs) {
            Picture c = new Picture();
            c.setImageId(picture.imageId);
            c.setUserId(picture.userId);
            c.setDescription(picture.description);
            c.setImagePath(picture.imagePath);
            c.setDate(picture.date);
            c.setTime(picture.time);
            list.add(c);
        }
        return list;
    }

    @Override
    public boolean isPhotoSaved(String imageId) {
        List a = new Select().from(SavedPicturesBD.class).where("imageId = ?", imageId).execute();
        if (a.size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean isNotificationLineOpen(String userId) {
        List a = new Select().from(NotificationLineBD.class).where("userId = ?", userId).execute();
        if (a.size() > 0)
            return true;
        else
            return false;
    }

    @Override
    public List<NotificationLine> getNotificationsById(String userId) {
        List<NotificationLineBD> notificationLineBDs = new Select().from(NotificationLineBD.class).where("userId = ?", userId).execute();
        return parseNotificationLines(notificationLineBDs);
    }

    @Override
    public List<NotificationLine> getNotificationLines() {
        List<NotificationLineBD> notificationLineBDs = new Select().from(NotificationLineBD.class).execute();
        return parseNotificationLines(notificationLineBDs);
    }

    @Override
    public List<NotificationLine> parseNotificationLines(List<NotificationLineBD> notificationLineBD) {
        List<NotificationLine> list = new ArrayList<NotificationLine>();
        for (NotificationLineBD notification : notificationLineBD) {
            list.add(new NotificationLine(notification.userId, notification.imagePath, notification.message, notification.time, notification.title, notification.state, notification.lineId, notification.receptor));
        }
        return list;
    }

    @Override
    public List<NotificationLineBD> getNotificationLinesGRUOUPBY() {
        List<NotificationLineBD> lines = new Select().from(NotificationLineBD.class).groupBy("userId").execute();
        List<NotificationLineBD> list = new ArrayList<NotificationLineBD>();
        for (NotificationLineBD line : lines) {
            if (!line.userId.equals(getUsers().get(0).userId))
                list.add(line);
        }
        return list;
    }

    @Override
    public void insertNotificationLine(String lineId, String userId, String profile, String message, String title, String time, String state, String receptor) {
        new NotificationLineBD(lineId, userId, profile, message, title, time, state, receptor).save();
    }
}

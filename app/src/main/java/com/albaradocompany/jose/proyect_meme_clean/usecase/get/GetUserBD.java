package com.albaradocompany.jose.proyect_meme_clean.usecase.get;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.NotificationLineBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.SavedPicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.NotificationLine;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;

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

    void deleteUserPictures(String id);

    void deleteUserPicture(String imageId);

    void deleteUserSavedPictures(String id);

    void deleteUserSavedPicture(String imageId);

    void updateUserBD(Login user);

    void removeUserDBData();

    List<UserBD> getUsers();

    User parseUserBD(UserBD userBD);

    Picture parsePictureBD(PicturesBD picturesBD);

    List<Picture> parseSavedPicturesBDList(List<SavedPicturesBD> savedPicturesBDs);

    List<Picture> parsePicturesBDList(List<PicturesBD> PicturesBDs);

    boolean isPhotoSaved(String imageId);

    boolean isNotificationLineOpen(String userId);

    List<NotificationLine> getNotificationsById(String userId);

    List<NotificationLine> getAllNotifications(String UserId);

    List<NotificationLine> parseNotificationLines(List<NotificationLineBD> notificationLineBD);

    List<NotificationLineBD> getNotificationLinesGRUOUPBY();

    void insertNotificationLine(String lineId, String userId, String profile, String message, String title, String time, String state, String sender);

    void updateNotificationsState(String userId);

    List<NotificationLine> getAllNotifications();
}

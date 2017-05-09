package com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SignupShared;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UserShared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by jose on 28/04/2017.
 */

public class UserSharedImp implements UserShared, SignupShared {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public UserSharedImp(Context context) {
        this.context = context;
    }

    @Override
    public void removeSignInformation() {
        deleteSignoneData();
        deleteSigntwoData();
        deleteSignThreeData();

        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().apply();
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    @Override
    public boolean isPhotoTaken() {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            SharedPreferences avatarShared = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            if (avatarShared.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
                editor = sharedPreferences.edit();
                editor.putString(BuildConfig.USER_AVATAR, avatarShared.getString(BuildConfig.AVATAR_IMAGE_PATH, ""));
                editor.apply();
                SignupOneActivity.avatar = true;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDateBirthDayTaken() {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_DATE, "false").equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveSignoneInfo(String name, String lastname, String email) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_NAME, name);
        editor.putString(BuildConfig.USER_EMAIL, email);
        editor.putString(BuildConfig.USER_LAST_NAME, lastname);
        if (SignupOneActivity.avatar) {
            SharedPreferences avatarShared = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            editor.putString(BuildConfig.USER_AVATAR, avatarShared.getString(BuildConfig.AVATAR_IMAGE_PATH, ""));
        }
        editor.putString(BuildConfig.USER_ID, createUserID());
        editor.putString(BuildConfig.USER_DATE_BIRTHDAY, sharedPreferences.getString(BuildConfig.USER_DATE_BIRTHDAY, ""));
        editor.apply();
    }

    private String createUserID() {
        Calendar calendar = Calendar.getInstance();
        String userID = "user" + calendar.get(calendar.DAY_OF_MONTH) + calendar.get(calendar.MONTH)
                + calendar.get(calendar.YEAR) + calendar.get(calendar.HOUR_OF_DAY) + calendar.get(calendar.SECOND) + calendar.get(Calendar.MILLISECOND);
        return userID;
    }

    @Override
    public void saveDateBirthday(int i, int i1, int i2) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_DATE, "true");
        editor.putString(BuildConfig.USER_DATE_BIRTHDAY, formatDate(i2, (i1 + 1), i));
        editor.apply();
    }

    private String formatDate(int i2, int i1, int i) {
        String year = "" + i;
        String month;
        String day;
        if (i1 < 10) {
            month = "0" + i1;
        } else {
            month = "" + i1;
        }
        if (i2 < 10) {
            day = "0" + i2;
        } else {
            day = "" + i2;
        }
        return day + "/" + month + "/" + year;
    }

    @Override
    public boolean isAvatarTaken() {
        SharedPreferences avatarShared = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (avatarShared.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
            editor = sharedPreferences.edit();
            editor.putString(BuildConfig.USER_AVATAR, avatarShared.getString(BuildConfig.AVATAR_IMAGE_PATH, ""));
            editor.apply();
            SignupOneActivity.avatar = true;
            return true;
        }
        return false;
    }

    @Override
    public String getUserAvatar() {
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_PATH, "");
    }

    @Override
    public String getUserPhoto() {
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_PHOTO, "");
    }

    public String getUserPhotoPath() {
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_PHOTO_PATH, "");
    }

    public void saveUserpPath(String path) {
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_PHOTO_PATH, path);
        editor.apply();
    }

    @Override
    public void savePhotoTaken(String path) {
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_PHOTO, path);
        editor.apply();
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_AVATAR, "false");
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, "true");
        editor.apply();
    }

    @Override
    public String savePictureOnMemory(Bitmap b, String name) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = new File(context.getFilesDir() + "/user_imagenes");
        directory.mkdirs();
        File mypath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
    public String getPicturesDir(){
        String dir = context.getFilesDir() + "/user_pictures";
        return dir;
    }
    @Override
    public String getUserPasswordSaved() {
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_PASSWORD, "");
    }

    @Override
    public String getUserPassword2Saved() {
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_PASSWORD, "");
    }

    @Override
    public String getUserUsernamedSaved() {
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_USERNAME, "");
    }

    @Override
    public void saveSigntwoData(String username, String password) {
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_USERNAME, username);
        editor.putString(BuildConfig.USER_PASSWORD, password);
        editor.apply();
    }

    @Override
    public void showUserPhoto(ImageView imageView, String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            imageView.setImageResource(R.drawable.user_default_image);
        }
    }

    @Override
    public void saveSignThreeData(String question, String answer1, String answer2) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(BuildConfig.USER_PHOTO, BuildConfig.BASE_URL_DEFAULT + getUser().getIdUser() + "_" + "profile");
        }
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_QUESTION, question);
        editor.putString(BuildConfig.USER_ANSWER1, answer1);
        if (answer2.isEmpty()) {
            editor.putString(BuildConfig.USER_ANSWER2, "");
        } else {
            editor.putString(BuildConfig.USER_ANSWER2, answer2);
        }
        editor.apply();
    }

    @Override
    public void deleteSignoneData() {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(),
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void deleteSigntwoData() {
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    @Override
    public void deleteSignThreeData() {
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    @Override
    public String getUserAnswer1Saved() {
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_ANSWER1, "");
    }

    @Override
    public String getUserAnswer2Saved() {
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_ANSWER2, "");
    }

    @Override
    public void saveUserAvatar(Avatar avatar) {
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.putString(BuildConfig.IS_SELECTED_AVATAR, "true");
        editor.putString(BuildConfig.AVATAR_IMAGE_PATH, avatar.getImagePath());
        editor.putString(BuildConfig.AVATAR_IMAGE_ID, String.valueOf(avatar.getId()));
        editor.putString(BuildConfig.AVATAR_IMAGE_DESCRIPTION, avatar.getDescription());
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, "true");
        editor.putString(BuildConfig.USER_AVATAR, avatar.getImagePath());
        editor.apply();
    }

    @Override
    public void deleteImageProfile() {
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void photoStateTaken(String state) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, state);
        editor.apply();
    }

    @Override
    public boolean isLogged() {
        SharedPreferences sharedPref = context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        String highScore = sharedPref.getString(BuildConfig.IS_LOGIN, "false");
        if (highScore.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cleanUserLogged() {
        sharedPreferences = context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_LOGIN, "false");
        editor.apply();
    }

    @Override
    public void saveUserLogged() {
        sharedPreferences = context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_LOGIN, "true");
        editor.apply();
    }

    @Override
    public Login getUser() {
        Login c = new Login();
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        c.setNombre(sharedPreferences.getString(BuildConfig.USER_NAME, ""));
        c.setApellidos(sharedPreferences.getString(BuildConfig.USER_LAST_NAME, ""));
        c.setEmail(sharedPreferences.getString(BuildConfig.USER_EMAIL, ""));
        c.setDescription(sharedPreferences.getString(BuildConfig.USER_DESCRIPTION, ""));
        c.setBackgrundPath(sharedPreferences.getString(BuildConfig.USER_BACKGROUND, ""));
        c.setFechaNacimiento(sharedPreferences.getString(BuildConfig.USER_DATE_BIRTHDAY, ""));
        c.setIdUser(sharedPreferences.getString(BuildConfig.USER_ID, ""));
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            if (sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
                sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
                c.setAvatarPath(sharedPreferences.getString(BuildConfig.USER_AVATAR, ""));
            } else {
                sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
//                c.setBlob(sharedPreferences.getString(BuildConfig.USER_PHOTO,""));
                c.setAvatarPath(sharedPreferences.getString(BuildConfig.USER_PHOTO, ""));

                //photo from camera or gallery
            }
        }
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        c.setUsername(sharedPreferences.getString(BuildConfig.USER_USERNAME, ""));
        c.setPassword(sharedPreferences.getString(BuildConfig.USER_PASSWORD, ""));
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        c.setPreguntaSeguridad(sharedPreferences.getString(BuildConfig.USER_QUESTION, ""));
        c.setRespuestaSeguridad(sharedPreferences.getString(BuildConfig.USER_ANSWER1, ""));
        c.setRespuestaSeguridad2(sharedPreferences.getString(BuildConfig.USER_ANSWER2, ""));
        return c;
    }

    @Override
    public void saveUser(Login user) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_NAME, user.getNombre());
        editor.putString(BuildConfig.USER_LAST_NAME, user.getApellidos());
        editor.putString(BuildConfig.USER_EMAIL, user.getEmail());
        editor.putString(BuildConfig.USER_AVATAR, user.getImagePath());
        editor.putString(BuildConfig.USER_BACKGROUND, user.getBackgrundPath());
        editor.putString(BuildConfig.USER_ID, user.getIdUser());
        editor.putString(BuildConfig.USER_DATE_BIRTHDAY, user.getFechaNacimiento());
        editor.putString(BuildConfig.USER_DESCRIPTION, user.getDescription());
        editor.putString(BuildConfig.IS_SELECTED_BACKGROUND, "true");
        editor.apply();

        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_USERNAME, user.getUsername());
        editor.putString(BuildConfig.USER_PASSWORD, user.getPassword());
        editor.apply();

        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_QUESTION, user.getPreguntaSeguridad());
        editor.putString(BuildConfig.USER_ANSWER1, user.getRespuestaSeguridad());
        editor.putString(BuildConfig.USER_ANSWER2, user.getRespuestaSeguridad2());
        editor.apply();

        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_AVATAR, "true");
        editor.apply();

        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.AVATAR_IMAGE_PATH, user.getImagePath());
        editor.apply();
    }

    @Override
    public String getBackground() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_BACKGROUND, "");
    }

    @Override
    public String getProfile() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_PHOTO, "");
    }

    @Override
    public void saveProfile(String profile) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_PHOTO, profile);
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, "true");
        editor.putString(BuildConfig.IS_PROFILE_FTP, "false");
        editor.apply();
    }

    @Override
    public boolean isSelectedProfile() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveBackground(String background) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_BACKGROUND, background);
        editor.putString(BuildConfig.IS_SELECTED_BACKGROUND, "true");
        editor.putString(BuildConfig.IS_BACKGROUND_FTP, "false");
        editor.apply();
    }

    @Override
    public boolean isSelectedBackground() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_BACKGROUND, "false").equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteBackground() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_BACKGROUND, "false");
        editor.apply();
        saveBackgroundChanges("false");
    }

    @Override
    public void deleteProfile() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, "false");
        editor.apply();
        saveProfileChanges("false");
    }

    @Override
    public boolean isProfileFTPSelected() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.IS_PROFILE_FTP, "false").equals("true");
    }

    @Override
    public boolean isBackgroundFTPSelected() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.IS_BACKGROUND_FTP, "false").equals("true");
    }

    @Override
    public void saveProfileFTPSelected() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_PROFILE_FTP, "true");
        editor.apply();
    }

    @Override
    public void saveBackgroundFTPSelected() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_BACKGROUND_FTP, "true");
        editor.apply();
    }

    @Override
    public boolean isProfileChanged() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.PROFILE_CHANGES, "false").equals("true");
    }

    @Override
    public void saveProfileChanges(String cond) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.PROFILE_CHANGES, cond);
        editor.apply();
    }

    @Override
    public boolean isBackgroundChanged() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.BACKGROUND_CHANGES, "false").equals("true");
    }

    @Override
    public void deleteUserData() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void saveBackgroundChanges(String cond) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.BACKGROUND_CHANGES, cond);
        editor.apply();
    }

    @Override
    public void saveNewBackground(String dir) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.NEW_BACKGROUND, dir);
        editor.apply();
    }

    @Override
    public void saveNewProfile(String dir) {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.NEW_PROFILE, dir);
        editor.apply();
    }

    @Override
    public String getNewProfile() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.NEW_PROFILE, "");
    }

    @Override
    public String getNewBackground() {
        sharedPreferences = context.getSharedPreferences(EditProfileActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.NEW_BACKGROUND, "");
    }

    @Override
    public void saveUserID(String id) {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.USER_LOGGED_ID, id);
        editor.apply();
    }

    @Override
    public String getUserID() {
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(BuildConfig.USER_LOGGED_ID, "");
    }
}

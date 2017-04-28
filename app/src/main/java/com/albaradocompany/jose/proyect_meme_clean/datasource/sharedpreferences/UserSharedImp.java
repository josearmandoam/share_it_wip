package com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.usecase.SignupShared;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UserShared;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
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
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupTwoActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
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
    public String guardarImagenOnInternal(Bitmap b, String name) {
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

    @Override
    public String getUserPasswordSaved() {
        return sharedPreferences.getString(BuildConfig.USER_PASSWORD, "");
    }

    @Override
    public String getUserPassword2Saved() {
        return sharedPreferences.getString(BuildConfig.USER_PASSWORD, "");
    }

    @Override
    public String getUserUsernamedSaved() {
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
            //e.printStackTrace();
        }
    }

    @Override
    public void saveSignThreeData(String question, String answer1, String answer2) {
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
        editor=sharedPreferences.edit();
        editor.clear().apply();
    }

    @Override
    public void deleteSignThreeData() {
        sharedPreferences = context.getSharedPreferences(SignupThreeActivity.class.getName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear().apply();
    }

    @Override
    public String getUserAnswer1Saved() {
        return sharedPreferences.getString(BuildConfig.USER_ANSWER1, "");
    }

    @Override
    public String getUserAnswer2Saved() {
        return sharedPreferences.getString(BuildConfig.USER_ANSWER2, "");
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

    }

    @Override
    public Login getUser(ImageView imageView) {
        Login c = new Login();
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        c.setNombre(sharedPreferences.getString(BuildConfig.USER_NAME, ""));
        c.setApellidos(sharedPreferences.getString(BuildConfig.USER_LAST_NAME, ""));
        c.setEmail(sharedPreferences.getString(BuildConfig.USER_EMAIL, ""));
        c.setFechaNacimiento(sharedPreferences.getString(BuildConfig.USER_DATE_BIRTHDAY, ""));
        c.setIdUser(sharedPreferences.getString(BuildConfig.USER_ID, ""));
        if (sharedPreferences.getString(BuildConfig.IS_SELECTED_PHOTO, "false").equals("true")) {
            sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
            if (sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false").equals("true")) {
                sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
                c.setImagePath(sharedPreferences.getString(BuildConfig.USER_AVATAR, ""));
            } else {
                c.setBlob(compressImage(imageView));
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
    public void saveUserLogged() {
        sharedPreferences=context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_LOGIN, "true");
        editor.apply();
    }
    private byte[] compressImage(ImageView image) {
        Bitmap bm = ((BitmapDrawable) image.getDrawable()).getBitmap();
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 400) / width;
        float scaleHeight = ((float) 450) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap bitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return b;
    }
}

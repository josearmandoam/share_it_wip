package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ShowAvatarPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsShowAvatar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 23/04/2017.
 */

public class ShowAvatarDialog extends AlertDialog implements AbsShowAvatar.View, AbsShowAvatar.Navigator {

    @BindView(R.id.show_avatar_edit)
    ImageButton edit;
    @BindView(R.id.show_avatar_delete)
    ImageButton delete;
    @BindView(R.id.show_avatar_image)
    ImageView image;
    @BindDrawable(R.drawable.user_default_image)
    Drawable defaultImageUser;

    @OnClick(R.id.show_avatar_edit)
    public void onEditClicked(View view) {
        presenter.onEditClicked();
    }

    @OnClick(R.id.show_avatar_delete)
    public void onDeleteClicked(View view) {
        presenter.onDeleteClicked();
    }

    AlertDialog dialog;
    Context context;
    AbsShowAvatar presenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int numberActivity;

    public ShowAvatarDialog(Context context) {
        super(context);

    }

    private int getWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (int) (size.x / 1.5);
    }

    private void initializePresenter() {
        presenter = new ShowAvatarPresenter(context);
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    private void initialize() {
        ButterKnife.bind(this, dialog);
    }

    public ShowAvatarDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ShowAvatarDialog(Context context, int numberActivity) {
        super(context);
        this.context = context;
        this.numberActivity = numberActivity;
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_show_avatar)
                .create();
        dialog.show();
        initialize();
        dialog.getWindow().setLayout(getWidth(), getWidth());
        initializePresenter();
    }

    @Override
    public void deleteImage() {
        deleteUserImage();
    }

    @Override
    public void loadImage() {
        checkImage();
    }

    private void checkImage() {
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        String isAvatarSelected = sharedPreferences.getString(BuildConfig.IS_SELECTED_AVATAR, "false");
        if (isAvatarSelected.equals("true")) {
            Picasso.with(context)
                    .load(sharedPreferences.getString(BuildConfig.AVATAR_IMAGE_PATH, ""))
                    .error(defaultImageUser)
                    .into(image);
        } else {
            sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
            cargarImagenPerfil(sharedPreferences.getString(BuildConfig.USER_PHOTO, ""));
        }
    }

    private void cargarImagenPerfil(String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            image.setImageResource(R.drawable.user_default_image);
            //e.printStackTrace();
        }

    }

    private void deleteUserImage() {
        sharedPreferences = context.getSharedPreferences(ConfirmAvatarDialog.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        sharedPreferences = context.getSharedPreferences(AddPhotoActivty.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        dialog.dismiss();
        switch (numberActivity) {
            case 1:
                ((SignupOneActivity) context).onResume();
                break;
            case 2:
                ((SignupTwoActivity) context).onResume();
                break;
            case 3:
                ((SignupThreeActivity) context).onResume();
                break;
            default:
                break;
        }
    }

    @Override
    public void navigateToEdit() {
        openAddPhoto(context);
        dialog.dismiss();
    }

    public static void openAddPhoto(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        ctx.startActivity(intent);
    }
}

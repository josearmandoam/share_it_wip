package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ConfirmAvatarPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsConfirmAvatar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 23/04/2017.
 */

public class ConfirmAvatarDialog extends AlertDialog implements AbsConfirmAvatar.View, AbsConfirmAvatar.Navigator {
    @BindView(R.id.confirm_avatar_accept)
    ImageButton accept;
    @BindView(R.id.confirm_avatar_image)
    ImageView avatarImage;

    @OnClick(R.id.confirm_avatar_accept)
    public void onAcceptClicked(View view) {
        presenter.onAcceptClicked();
    }

    private AlertDialog dialog;
    private Context context;
    private Avatar avatar;
    private AbsConfirmAvatar presenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public ConfirmAvatarDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ConfirmAvatarDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public ConfirmAvatarDialog(Context context, Avatar avatar) {
        super(context);
        this.context = context;
        this.avatar = avatar;
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_confirm_avatar)
                .create();
        dialog.show();
        dialog.getWindow().setLayout((int) (getWidth() / 1.5), (int) (getWidth() / 1.5));
        initialize();
        loadAvatarImage();
    }

    private int getWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void initialize() {
        ButterKnife.bind(this, dialog);
        presenter = new ConfirmAvatarPresenter(context);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    public void loadAvatarImage() {
        Picasso.with(context)
                .load(avatar.getImagePath())
                .into(avatarImage);
    }

    @Override
    public void closeConfirmAvatar() {
        saveImageData();
    }

    private void saveImageData() {
        sharedPreferences = context.getSharedPreferences(this.getClass().getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(BuildConfig.IS_SELECTED_AVATAR, "true");
        editor.putString(BuildConfig.AVATAR_IMAGE_PATH, avatar.getImagePath());
        editor.putString(BuildConfig.AVATAR_IMAGE_ID, String.valueOf(avatar.getId()));
        editor.putString(BuildConfig.AVATAR_IMAGE_DESCRIPTION, avatar.getDescription());
        editor.apply();
        sharedPreferences = context.getSharedPreferences(SignupOneActivity.class.getName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(BuildConfig.IS_SELECTED_PHOTO, "true");
        editor.apply();
        ((AddPhotoActivty) context).finish();
    }
}

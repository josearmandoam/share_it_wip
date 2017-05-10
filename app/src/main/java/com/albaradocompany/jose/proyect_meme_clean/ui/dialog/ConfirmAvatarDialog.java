package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ConfirmAvatarPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsConfirmAvatar;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

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

    int action;
    private AlertDialog dialog;
    private Activity activity;
    private Avatar avatar;
    private AbsConfirmAvatar presenter;
    @Inject
    UserSharedImp userSharedImp;

    public ConfirmAvatarDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ConfirmAvatarDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public ConfirmAvatarDialog(Activity activity, Avatar avatar, int action) {
        super(activity);
        this.activity = activity;
        this.avatar = avatar;
        this.action = action;
        dialog = new AlertDialog.Builder(activity)
                .setView(R.layout.dialog_confirm_avatar)
                .create();
        dialog.show();
        dialog.getWindow().setLayout((int) (getWidth() / 1.5), (int) (getWidth() / 1.5));
        initialize();
        loadAvatarImage();
        getComponent().inject(this);

    }

    private int getWidth() {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void initialize() {
        ButterKnife.bind(this, dialog);
        presenter = new ConfirmAvatarPresenter(activity);
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    public void loadAvatarImage() {
        Picasso.with(activity)
                .load(avatar.getImagePath())
                .into(avatarImage);
    }

    @Override
    public void closeConfirmAvatar() {
        dialog.dismiss();
        switch (action){
            case 0:
                userSharedImp.saveUserAvatar(avatar);
                activity.finish();
                break;
            case 1:
                userSharedImp.saveProfileAvatar(avatar);
                userSharedImp.saveProfileFTPSelected("true");
                userSharedImp.saveProfileChanges("true");
                activity.finish();
                break;
            case 2:
                userSharedImp.saveBackgroundAvatar(avatar);
                userSharedImp.saveBackgroundFTPSelected("true");
                userSharedImp.saveBackgroundChanges("true");
                activity.finish();
                break;
        }

    }

    protected UIComponent getComponent() {

        return ((AddPhotoActivty) activity).component();
    }
}

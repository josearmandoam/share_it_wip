package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
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

public class ConfirmAvatarDialog extends DialogFragment implements AbsConfirmAvatar.View, AbsConfirmAvatar.Navigator {
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
    private Avatar avatar;
    private AbsConfirmAvatar presenter;
    @Inject
    UserSharedImp userSharedImp;

    public ConfirmAvatarDialog(Avatar avatar, int action) {
        this.avatar = avatar;
        this.action = action;
    }

    @Override
    public void onResume() {
        dialog.getWindow().setLayout(getWidth(), getWidth());
        super.onResume();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_confirm_avatar, null);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        ButterKnife.bind(this, view);
        initialize();
        loadAvatarImage();
        getComponent().inject(this);
        return dialog;
    }

    private int getWidth() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void initialize() {
        presenter = new ConfirmAvatarPresenter(getActivity());
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    public void loadAvatarImage() {
        Picasso.with(getActivity())
                .load(avatar.getImagePath())
                .into(avatarImage);
    }

    @Override
    public void closeConfirmAvatar() {
        dialog.dismiss();
        switch (action) {
            case 0:
                userSharedImp.saveUserAvatar(avatar);
                getActivity().finish();
                break;
            case 1:
                userSharedImp.saveProfileAvatar(avatar);
                userSharedImp.saveProfileFTPSelected("true");
                userSharedImp.saveProfileChanges("true");
                getActivity().finish();
                break;
            case 2:
                userSharedImp.saveBackgroundAvatar(avatar);
                userSharedImp.saveBackgroundFTPSelected("true");
                userSharedImp.saveBackgroundChanges("true");
                getActivity().finish();
                break;
        }

    }

    protected UIComponent getComponent() {

        return ((AddPhotoActivty) getActivity()).component();
    }
}

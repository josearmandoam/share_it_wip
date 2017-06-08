package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.AddPhotoActivty;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupOneActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupTwoActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.ShowAvatarPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsShowAvatar;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 23/04/2017.
 */

public class ShowAvatarDialog extends DialogFragment implements AbsShowAvatar.View, AbsShowAvatar.Navigator {

    private static final int ACTION_SIGNUP = 0;
    private static final int ACTION_EDIT_PROFILE = 1;
    private static final int ACTION_EDIT_BACKGROUND = 2;
    private static final int ACTIVITY_SIGNONE = 1;
    private static final int ACTIVIVY_SIGNTWO = 2;
    private static final int ACTIVITY_SIGNTHREE = 3;
    private static final int ACTIVITY_EDITPROFILE = 4;
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

    private int action;
    AlertDialog dialog;
    AbsShowAvatar presenter;
    int numberActivity;
    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBDImp;


    private int getWidth() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (int) (size.x / 1.5);
    }

    private void initializePresenter() {
        presenter = new ShowAvatarPresenter(getActivity());
        presenter.setView(this);
        presenter.setNavigator(this);
        presenter.initialize();
    }

    public ShowAvatarDialog(int numberActivity, int action) {
        this.numberActivity = numberActivity;
        this.action = action;

    }

    public ShowAvatarDialog(int numberActivity) {
        this.numberActivity = numberActivity;
        action = 0;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        dialog.getWindow().setLayout(getWidth(), getWidth());
        super.onResume();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show_avatar, null);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
//        dialog.getWindow().setLayout(getWidth(), getWidth());
        getComponent().inject(this);
        ButterKnife.bind(this, view);
        initializePresenter();
        return dialog;
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
        switch (action) {
            case ACTION_SIGNUP:
                checkProfileSignup();
                break;
            case ACTION_EDIT_PROFILE:
                checkProfileEditProfile();
                break;
            case ACTION_EDIT_BACKGROUND:
                checkBackgroundEditBackground();
                break;
        }

    }

    private void checkBackgroundEditBackground() {
        if (EditProfileActivity.backgroundBitmapReceived != null) {
            image.setImageBitmap(EditProfileActivity.backgroundBitmapReceived);
        } else {
            if (EditProfileActivity.backgroundUriReceived != null) {
                image.setImageURI(EditProfileActivity.backgroundUriReceived);
            } else {
                if (userSharedImp.isBackgroundFTPSelected()) {
                    Picasso.with(getActivity()).load(userSharedImp.getBackgroundAvatar()).into(image);
                } else {
                    userSharedImp.showUserPhoto(image, userSharedImp.getPicturesDir() + "/"
                                    + getUserBDImp.getUserBD(userSharedImp.getUserID()).userId + "_background",
                            getUserBDImp.getUserBD(userSharedImp.getUserID()));
                }
            }
        }
    }

    private void checkProfileEditProfile() {
        if (EditProfileActivity.profileBitmapReceived != null) {
            image.setImageBitmap(EditProfileActivity.profileBitmapReceived);
        } else {
            if (EditProfileActivity.profileUriReceived != null) {
                image.setImageURI(EditProfileActivity.profileUriReceived);
            } else {
                if (userSharedImp.isProfileFTPSelected()) {
                    Picasso.with(getActivity()).load(userSharedImp.getProfileAvatar()).into(image);
                } else {
                    userSharedImp.showUserPhoto(image, userSharedImp.getPicturesDir() + "/"
                                    + getUserBDImp.getUserBD(userSharedImp.getUserID()).userId + "_profile",
                            getUserBDImp.getUserBD(userSharedImp.getUserID()));
                }
            }
        }
    }

    private void checkProfileSignup() {
        if (userSharedImp.isAvatarTaken()) {
            checkImageFromFTP();
        } else {
            checkImageFromActivity();
        }
    }

    private void checkImageFromFTP() {
        Picasso.with(getActivity())
                .load(userSharedImp.getUserAvatar())
                .error(defaultImageUser)
                .into(image);
    }

    private void checkImageFromActivity() {
        switch (numberActivity) {
            case ACTIVITY_SIGNONE:
                updateProfileSignone();
                break;
            case ACTIVIVY_SIGNTWO:
                updateProfileSigntwo();
                break;
            case ACTIVITY_SIGNTHREE:
                updateProfileSignthree();
                break;
        }
    }

    private void updateProfileSignthree() {
        if (SignupThreeActivity.bitmapReceived != null) {
            image.setImageBitmap(SignupThreeActivity.bitmapReceived);
        } else {
            if (SignupThreeActivity.uriReceived != null) {
                image.setImageURI(SignupThreeActivity.uriReceived);
            } else {
                image.setImageDrawable(defaultImageUser);
            }
        }
    }

    private void updateProfileSigntwo() {
        if (SignupTwoActivity.bitmapReceived != null) {
            image.setImageBitmap(SignupTwoActivity.bitmapReceived);
        } else {
            if (SignupTwoActivity.uriReceived != null) {
                image.setImageURI(SignupTwoActivity.uriReceived);
            } else {
                image.setImageDrawable(defaultImageUser);
            }
        }
    }

    private void updateProfileSignone() {
        if (SignupOneActivity.bitmapReceived != null) {
            image.setImageBitmap(SignupOneActivity.bitmapReceived);
        } else {
            if (SignupOneActivity.uriReceived != null) {
                image.setImageURI(SignupOneActivity.uriReceived);
            } else {
                image.setImageDrawable(defaultImageUser);
            }
        }
    }

    private void deleteUserImage() {
        switch (action) {
            case ACTION_SIGNUP:
                deleteProfileSignup();
                break;
            case ACTION_EDIT_PROFILE:
                deleteProfileEditProfile();
                break;
            case ACTION_EDIT_BACKGROUND:
                deleteBackgroundEditBackground();
                break;
        }

    }

    private void deleteBackgroundEditBackground() {
        userSharedImp.deleteBackground();
        userSharedImp.saveBackgroundChanges("false");
        EditProfileActivity.backgroundUriReceived = null;
        EditProfileActivity.backgroundBitmapReceived = null;
        userSharedImp.saveBackgroundFTPSelected("false");
        dialog.dismiss();
        ((EditProfileActivity) getActivity()).onResume();
    }

    private void deleteProfileEditProfile() {
        userSharedImp.deleteProfile();
        userSharedImp.saveProfileChanges("false");
        dialog.dismiss();
        EditProfileActivity.profileUriReceived = null;
        EditProfileActivity.profileBitmapReceived = null;
        userSharedImp.saveProfileFTPSelected("false");
        ((EditProfileActivity) getActivity()).onResume();
    }

    private void deleteProfileSignup() {
        userSharedImp.deleteImageProfile();
        userSharedImp.photoStateTaken("false");
        invalidatePhotos();
        dialog.dismiss();
        switch (numberActivity) {
            case 1:
                ((SignupOneActivity) getActivity()).onResume();
                break;
            case 2:
                ((SignupTwoActivity) getActivity()).onResume();
                break;
            case 3:
                ((SignupThreeActivity) getActivity()).onResume();
                break;
            default:
                break;
        }
    }

    private void invalidatePhotos() {
        SignupOneActivity.bitmapReceived = null;
        SignupTwoActivity.bitmapReceived = null;
        SignupThreeActivity.bitmapReceived = null;

        SignupOneActivity.uriReceived = null;
        SignupTwoActivity.uriReceived = null;
        SignupThreeActivity.uriReceived = null;
    }

    @Override
    public void navigateToEdit() {
        openAddPhoto(getActivity());
        dialog.dismiss();
    }

    public void openAddPhoto(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        intent.putExtra("action", this.action);
        ctx.startActivity(intent);
    }

    protected UIComponent getComponent() {
        switch (numberActivity) {
            case ACTIVITY_SIGNONE:
                return ((SignupOneActivity) getActivity()).component();
            case ACTIVIVY_SIGNTWO:
                return ((SignupTwoActivity) getActivity()).component();
            case ACTIVITY_SIGNTHREE:
                return ((SignupThreeActivity) getActivity()).component();
            case ACTIVITY_EDITPROFILE:
                return ((EditProfileActivity) getActivity()).component();
            default:
                return null;
        }
    }

}

package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

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

    private int action;
    AlertDialog dialog;
    Context context;
    AbsShowAvatar presenter;
    int numberActivity;
    @Inject
    UserSharedImp userSharedImp;
    @Inject
    GetUserBDImp getUserBDImp;

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

    public ShowAvatarDialog(Context context, int numberActivity, int action) {
        super(context);
        this.context = context;
        this.numberActivity = numberActivity;
        this.action = action;
        getComponent().inject(this);
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_show_avatar)
                .create();
        dialog.show();
        initialize();
        dialog.getWindow().setLayout(getWidth(), getWidth());
        initializePresenter();
    }

    public ShowAvatarDialog(Context context, int numberActivity) {
        super(context);
        this.context = context;
        this.numberActivity = numberActivity;
        action = 0;
        getComponent().inject(this);
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
        switch (action) {
            case 0:
                if (userSharedImp.isAvatarTaken()) {
                    Picasso.with(context)
                            .load(userSharedImp.getUserAvatar())
                            .error(defaultImageUser)
                            .into(image);
                } else {
                    loadImage(userSharedImp.getUserPhoto());
                }
                break;
            case 1: /* action profile */
                if (userSharedImp.isSelectedProfile()) {
                    if (userSharedImp.isProfileFTPSelected()){
                        Picasso.with(context)
                                .load(userSharedImp.getProfile())
                                .into(image);
                    }else {
                        loadImage(userSharedImp.getProfile());
                    }
                } else {
                    Picasso.with(context)
                            .load(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_profile)
                            .into(image);
                }
                break;
            case 2: /* action background */
                if (userSharedImp.isSelectedBackground()) {
                    if (userSharedImp.isBackgroundFTPSelected()){
                        Picasso.with(context)
                                .load(userSharedImp.getBackground())
                                .into(image);
                    }else {
                        loadImage(userSharedImp.getBackground());
                    }
                } else {
                    Picasso.with(context)
                            .load(getUserBDImp.getUserBD(userSharedImp.getUserID()).user_background)
                            .into(image);
                }
                break;
        }

    }

    private void loadImage(String path) {
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
        switch (action) {
            case 0:
                userSharedImp.deleteImageProfile();
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
                break;
            case 1: /* action profile */
                userSharedImp.deleteProfile();
                userSharedImp.saveProfileChanges("false");
                dialog.dismiss();
                ((EditProfileActivity) context).onResume();
                break;
            case 2: /*action background*/
                userSharedImp.deleteBackground();
                userSharedImp.saveBackgroundChanges("false");
                dialog.dismiss();
                ((EditProfileActivity) context).onResume();
                break;
        }

    }

    @Override
    public void navigateToEdit() {
        openAddPhoto(context);
        dialog.dismiss();
    }

    public void openAddPhoto(Context ctx) {
        Intent intent = new Intent(ctx, AddPhotoActivty.class);
        intent.putExtra("action", this.action);
        ctx.startActivity(intent);
    }

    protected UIComponent getComponent() {
        switch (numberActivity) {
            case 1:
                return ((SignupOneActivity) context).component();
            case 2:
                return ((SignupTwoActivity) context).component();
            case 3:
                return ((SignupThreeActivity) context).component();
            case 4:
                return ((EditProfileActivity) context).component();
            default:
                return null;
        }
    }

}

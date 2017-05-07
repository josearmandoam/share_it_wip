package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateUserInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.EditProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsEditProfilePresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateUser;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.net.InetAddress;

import javax.inject.Inject;

/**
 * Created by jose on 06/05/2017.
 */

public class EditProfilePresenter extends AbsEditProfilePresenter {
    Context context;
    private UIComponent component;
    @Inject
    UserSharedImp userSharedImp;

    public EditProfilePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        component().inject(this);
        view.showDescription();
        view.showBackgroundPicture();
        view.showEmail();
        view.showLastName();
        view.showProfilePicture();
        view.showName();
        view.showUserName();
    }

    @Override
    public void resume() {
        view.checkProfile();
        view.checkBaground();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onCancelClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void onAcceptClicked(UpdateUserInteractor interactor) {
        view.showLoading();
        interactor.updateUser(new UpdateUser.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
            }

            @Override
            public void onUpdateSuccess() {
                view.hideLoading();
                view.showSuccess();
                if (userSharedImp.isBackgroundChanged()){
                    uploadBackground();
                }
                if (userSharedImp.isProfileChanged()){
                    uploadProfile();
                }
                navigator.navigateToBack();
            }

            @Override
            public void onUpdateFailure() {
                view.hideLoading();
                view.showFailure();
            }
        });
    }
    private void uploadBackground(){
        new Thread(new Runnable() {
            public void run() {
                FTPClient ftpClient = null;
                try {
                    ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName(BuildConfig.ADDRESS));
                    if (ftpClient.login(BuildConfig.USERNAME, BuildConfig.PASSWORD)) {
                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        FileInputStream in = new FileInputStream(userSharedImp.getBackground());
                        String backgroundName = userSharedImp.getUserID() + "_background";
                        boolean result = ftpClient.storeFile(backgroundName, in);
                        in.close();
                        if (result)
                            Log.v("upload result", "succeeded");
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (Exception e) {
                    Log.v("count", "error");
                    e.printStackTrace();
                }
                ((EditProfileActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        view.hideLoading();
                        view.showSuccess();
                        userSharedImp.saveBackgroundChanges("false");
                        navigator.navigateToBack();

                    }
                });
            }
        }).start();
    }
    private void uploadProfile(){
        new Thread(new Runnable() {
            public void run() {
                FTPClient ftpClient = null;
                try {
                    ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName(BuildConfig.ADDRESS));
                    if (ftpClient.login(BuildConfig.USERNAME, BuildConfig.PASSWORD)) {
                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        String photoname = userSharedImp.getUserID() + "_profile";
                        FileInputStream in = new FileInputStream(userSharedImp.getProfile());
                        boolean result = ftpClient.storeFile(photoname, in);
                        in.close();
                        if (result)
                            Log.v("upload result", "succeeded");
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (Exception e) {
                    Log.v("count", "error");
                    e.printStackTrace();
                }
                ((EditProfileActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        view.hideLoading();
                        view.showSuccess();
                        userSharedImp.saveProfileChanges("false");
                        navigator.navigateToBack();
                    }
                });
            }
        }).start();
    }
    @Override
    public void onProfileClicked() {
        view.showProfileDialog();
    }

    @Override
    public void onBackgroundClicked() {
        view.showBackgroundDialog();
    }

    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) context.getApplicationContext()).getComponent())
                    .uIModule(new UIModule(context.getApplicationContext()))
                    .mainModule(((App) context.getApplicationContext()).getMainModule())
                    .build();
        }
        return component;
    }
}

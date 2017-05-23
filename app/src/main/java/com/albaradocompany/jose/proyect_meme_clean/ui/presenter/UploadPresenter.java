package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UploadPictureApiImp;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UploadPictureInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUploadPresenter;
import com.albaradocompany.jose.proyect_meme_clean.usecase.UploadPicture;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by jose on 23/05/2017.
 */

public class UploadPresenter extends AbsUploadPresenter {

    Context context;
    private String imageId;

    public UploadPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void initialize() {
        imageId = "image" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onUriReceivedFromGallery(Uri uri) {
        view.showPicture(uri);
    }

    @Override
    public void onBitmapReceivedFromCamera(Bitmap bm) {
        view.showPicture(bm);
    }

    @Override
    public void onBackClicked() {
        navigator.navigateToBack();
    }

    @Override
    public void onCheckClicked(Bitmap bitmap, String userID, String description) {
        savePictureOnMemory(bitmap, imageId);
        view.showLoading();
        UploadPictureInteractor interactor = getUploadPictureInteractor(userID, description);
        interactor.uploadPicture(new UploadPicture.Listener() {
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
            public void onSuccess() {
                view.hideLoading();
                view.showSuccess();
            }

            @Override
            public void onFailure() {
                view.hideLoading();
                view.showFailure();
            }
        });
    }

    private UploadPictureInteractor getUploadPictureInteractor(String userID, String description) {
        return new UploadPictureInteractor(new UploadPictureApiImp(userID, BuildConfig.BASE_URL_DEFAULT + imageId, description,
                imageId, DateUtil.getCurrentTimeFormated(), DateUtil.getCurrentDateFormated()), new MainThreadImp(), new ThreadExecutor());
    }

    private void uploadPictureFTP(final String absolutePath) {
//        new Thread(new Runnable() {
//            public void run() {
                FTPClient ftpClient = null;
                try {
                    ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName(BuildConfig.ADDRESS));
                    if (ftpClient.login(BuildConfig.USERNAME, BuildConfig.PASSWORD)) {
                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        String photoname = imageId;
                        FileInputStream in = new FileInputStream(absolutePath);
                        boolean result = ftpClient.storeFile(photoname, in);
                        in.close();
                        if (result) {
                            Log.v("upload result", "succeeded");
                        }
                        ftpClient.logout();
                        ftpClient.disconnect();
                        ((MainActivity)context).updateFeedFragment();
                    }
                } catch (Exception e) {
                    Log.v("count", "error");
                    e.printStackTrace();
                }
//            }
//        }).start();
    }

    public void savePictureOnMemory(final Bitmap b, final String name) {
        new Thread(new Runnable() {
            public void run() {
                ContextWrapper cw = new ContextWrapper(context);
                File directory = new File(context.getFilesDir() + "/user_pictures");
                directory.mkdirs();
                File mypath = new File(directory, name);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    uploadPictureFTP(mypath.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

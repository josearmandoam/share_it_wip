package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdateFeedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.UpdatePermissionsApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdateFeedInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.UpdatePermissionsInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetQuestions;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetRegistrationResponse;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdateFeed;
import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdatePermissions;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupThreePresenter extends AbsSignupThree {
    private static final String INSERT = "insert";
    Context context;
    GetQuestions getQuestions;
    public String data;

    @Inject
    UserSharedImp userSharedImp;

    public SignupThreePresenter(Context context, GetQuestions getQuestions) {
        this.context = context;
        this.getQuestions = getQuestions;
        getComponent().inject(this);
    }

    @Override
    public void initialize() {
        view.loadUserImage();
        getQuestions.getQuestions(new GetQuestions.Listener() {
            @Override
            public void onNoInternetAvailable() {
                view.showNoInternetAvailable();
            }

            @Override
            public void onError(Exception e) {
                view.showError(e);
            }

            @Override
            public void onQuestionsReceived(List<Question> questions) {
                view.showQuestions(questions);
            }
        });
    }

    @Override
    public void resume() {
        view.loadUserImage();
        view.checkInfoSaved();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onBackPressed() {
        view.checkInfoSaved();
    }

    @Override
    public void onConfirmClicked(GetRegistrationResponse getRegistrationResponse, final Login login, final Bitmap bitmap) {
        view.showLoading();
        getRegistrationResponse.getRegistrationResponse(new GetRegistrationResponse.Listener() {
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
            public void onRegistrationSuccess(GenericResponse response) {
                configureFeedHimSelf(login);
                saveImageOnMemory(context, bitmap, userSharedImp.getUser().getIdUser() + "_profile");
            }

            @Override
            public void onRegistrationFailed() {
                view.hideLoading();
                view.showErrorRegistration();
            }
        });
    }

    private void savePhotoFTP(final String name, final String absolutePath) {
        new Thread(new Runnable() {
            public void run() {
                FTPClient ftpClient = null;
                try {
                    ftpClient = new FTPClient();
                    ftpClient.connect(InetAddress.getByName(BuildConfig.ADDRESS));
                    if (ftpClient.login(BuildConfig.USERNAME, BuildConfig.PASSWORD)) {
                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        FileInputStream in = new FileInputStream(absolutePath);
                        boolean result = ftpClient.storeFile(name, in);
                        in.close();
                        if (result)
                            Log.v("upload result", "succeeded");
                        ftpClient.logout();
                        ftpClient.disconnect();
                        updatePermissions();
                    }
                } catch (Exception e) {
                    Log.v("count", "error");
                    e.printStackTrace();
                }
                ((SignupThreeActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                            /**/
                    }
                });
            }
        }).start();
    }

    private void configureFeedHimSelf(Login login) {
        UpdateFeedInteractor interactor = getUpdateFeedInteractor(login);
        interactor.updateFedd(new UpdateFeed.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onImageClicked() {
        view.showImage();
    }

    @Override
    public void onRefreshQuestionClicked() {
        view.refreshQuestions();
    }

    @Override
    public void onCleanClicked() {
        view.cleanFields();
    }

    protected UIComponent getComponent() {
        return ((SignupThreeActivity) context).component();
    }

    public void saveImageOnMemory(final Context context, final Bitmap b, final String name) {
        view.showLoading();
        new Thread(new Runnable() {
            public void run() {
                ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
                File directory = new File(context.getFilesDir() + "/user_pictures");
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
                userSharedImp.saveProfile(mypath.getAbsolutePath());
                savePhotoFTP(name, mypath.getAbsolutePath());
                Looper.prepare();
                notificateFinish();
                ((SignupThreeActivity) context).runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });
            }
        }).start();

    }

    private void updatePermissions() {
        UpdatePermissionsInteractor interactor = getUpdatePermissionsInteractor();
        interactor.updatePermissions(new UpdatePermissions.Listener() {
            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    private void notificateFinish() {
        new Thread(new Runnable() {
            public void run() {
                ((SignupThreeActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        ((SignupThreeActivity) context).hideLoading();
                        ((SignupThreeActivity) context).showSuccess();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((SignupThreeActivity) context).navigateToLogin();
                            }
                        }, 1500);
                    }
                });
            }
        }).start();
    }

    public UpdatePermissionsInteractor getUpdatePermissionsInteractor() {
        return new UpdatePermissionsInteractor(new UpdatePermissionsApiImp(), new MainThreadImp(), new ThreadExecutor());
    }

    public UpdateFeedInteractor getUpdateFeedInteractor(Login login) {
        return new UpdateFeedInteractor(new UpdateFeedApiImp(login.getIdUser(), login.getIdUser(),
                BuildConfig.BASE_URL_DEFAULT + login.getIdUser() + "_profile", "feed" + DateUtil.getCurrentDate() + DateUtil.getCurrentTime(),
                login.getNombre() + " " + login.getApellidos(), INSERT), new MainThreadImp(), new ThreadExecutor());
    }
}

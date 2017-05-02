package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.SignupComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.SignupThreeActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsSignupThree;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetQuestions;
import com.albaradocompany.jose.proyect_meme_clean.usecase.GetRegistrationResponse;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 19/04/2017.
 */

public class SignupThreePresenter extends AbsSignupThree {
    Context context;
    GetQuestions getQuestions;
    private SignupComponent component;
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
    public void onConfirmClicked(GetRegistrationResponse getRegistrationResponse, Login login) {
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
                if(userSharedImp.isPhotoTaken() && !userSharedImp.isAvatarTaken()){
                        new Thread(new Runnable() {
                            public void run() {
                                FTPClient ftpClient = null;
                                try {
                                    ftpClient = new FTPClient();
                                    ftpClient.connect(InetAddress.getByName("iesayala.ddns.net"));
                                    if (ftpClient.login("joseA", "joseA")) {
                                        ftpClient.enterLocalPassiveMode(); // important!
                                        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                                        String photoname=userSharedImp.getUser().getIdUser()+"_profile";
                                        FileInputStream in = new FileInputStream(userSharedImp.getUserPhotoPath());
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
                                ((SignupThreeActivity)context).runOnUiThread(new Runnable() {
                                    public void run() {
                                        view.hideLoading();
                                        view.showSuccess();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                navigator.navigateToLogin();
                                                // Actions to do after 10 seconds
                                            }
                                        }, 1500);
                                    }
                                });
                            }
                        }).start();
                }else {
                    view.hideLoading();
                    view.showSuccess();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            navigator.navigateToLogin();
                            // Actions to do after 10 seconds
                        }
                    }, 1500);
                }

            }

            @Override
            public void onRegistrationFailed() {
                view.hideLoading();
                view.showErrorRegistration();
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

    protected SignupComponent getComponent() {
            return ((SignupThreeActivity) context).component();
    }
}

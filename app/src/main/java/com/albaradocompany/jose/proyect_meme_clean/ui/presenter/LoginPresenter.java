package com.albaradocompany.jose.proyect_meme_clean.ui.presenter;

import android.content.Context;

import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesByIdApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.PicturesSavedApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.api.RegisterTokenApiImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.interactor.LoginInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.PicturesByIdInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.RegisterTokenInteractor;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.MainThreadImp;
import com.albaradocompany.jose.proyect_meme_clean.interactor.imp.ThreadExecutor;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.LoginActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsUserLogin;
import com.albaradocompany.jose.proyect_meme_clean.usecase.RegisterToken;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetLogin;
import com.albaradocompany.jose.proyect_meme_clean.usecase.get.GetPicturesById;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jose on 16/04/2017.
 */

public class LoginPresenter extends AbsUserLogin {
    String password;
    String username;
    Context context;

    GetLogin getLogin;
    GetPicturesById getPicturesById;
    boolean userFinded;

    Login user;
    @Inject
    GetUserBDImp bd;
    @Inject
    UserSharedImp userSharedImp;

    public LoginPresenter(Context context) {
        this.context = context;
        getComponent().inject(this);
    }

    @Override
    public void initialize() {
        view.hideButtonSignin();
        view.showLoading();
        getLogin.getLogin(new GetLogin.Listener() {

            @Override
            public void onLoginReceived(List<Login> login) {
                if (login.size() > 0) {
                    userFinded = false;
                    for (int i = 0; i < login.size(); i++) {
                        if (login.get(i).getPassword().equals(password)) {
                            user = login.get(i); // user without photos/photos saved
                            bd.removeUserDBData();
                            userSharedImp.saveUserID(login.get(i).getIdUser());
                            bd.insertUserDB(user);
                            bd.removeAllNotifications();
                            userSharedImp.saveUserLogged();
                            checkForUserPictures();
                            saveUserToken(login.get(i).getIdUser());
                            userFinded = true;
                        }
                        if (userFinded)
                            i = login.size() - 1;
                    }
                    if (!userFinded) {
                        view.hideLoading();
                        view.showErrorLoginPassword();
                        view.showButtonSignin();
                    }

                } else {
                    view.hideLoading();
                    view.showErrorUserNotFound();
                    view.showButtonSignin();
                }
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e);
                view.showButtonSignin();
            }

            @Override
            public void onNoInternetAvailable() {
                view.hideLoading();
                view.showNoInternetAvailable();
                view.showButtonSignin();
            }

            @Override
            public void onUserNotFound() {
                view.hideLoading();
                view.showErrorUserNotFound();
                view.showButtonSignin();
            }
        });
    }

    private void saveUserToken(String idUser) {
        RegisterTokenInteractor interactor = getRegisterTokenInteractor(idUser);
        interactor.registerToken(new RegisterToken.Listener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNoInternetAvailable() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private RegisterTokenInteractor getRegisterTokenInteractor(String idUser) {
        return new RegisterTokenInteractor(new RegisterTokenApiImp(idUser, userSharedImp.getUserToken()), new MainThreadImp(), new ThreadExecutor());
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
    public void onSigninClicked(LoginInteractor getLogin, String username, String password) {
        this.getLogin = getLogin;
        this.username = username;
        this.password = password;
    }

    @Override
    public void onSignupClicked() {
        navigator.navigateToSignupPage();
    }

    @Override
    public void onPasswordClicked() {
        navigator.navigateToPassword();
    }

    @Override
    public void checkForUserPictures() {
        getPicturesById = new PicturesByIdInteractor(new PicturesByIdApiImp(user.getIdUser()), new MainThreadImp(), new ThreadExecutor());
        getPicturesById.getPictures(new GetPicturesById.Listener() {
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
            public void onPicturesReceived(List<Picture> pictures) {
                for (Picture picture : pictures) {
                    bd.insertUserPicture(picture);
                }
                checkForUserSavedPictures();
            }
        });
    }

    private void insertNewPicturesBD(List<Picture> pictures) {
        List<PicturesBD> pictureFromBD = bd.getUserPictures(userSharedImp.getUserID());
        List<Picture> newPhotos = new ArrayList<Picture>();
        boolean exist = false;
        for (int i = 0; i < pictures.size(); i++) {
            for (int j = 0; j < pictureFromBD.size(); j++) {
                if (pictures.get(i).getImagePath().equals(pictureFromBD.get(j).imagePath)) {
                    exist = true;
                }
            }
            if (!exist) {
                newPhotos.add(pictures.get(i));
            }
            exist = false;
        }
        for (Picture picture : newPhotos) {
            bd.insertUserPicture(picture);
        }
    }

    @Override
    public void checkForUserSavedPictures() {
        getPicturesById = new PicturesByIdInteractor(new PicturesSavedApiImp(user.getIdUser()), new MainThreadImp(), new ThreadExecutor());
        getPicturesById.getPictures(new GetPicturesById.Listener() {
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
            public void onPicturesReceived(List<Picture> pictures) {
                for (Picture picture : pictures) {
                    bd.insertUserSavedPicture(picture);
                }
                userSharedImp.saveUserID(user.getIdUser());
                view.hideLoading();
                view.showButtonSignin();
                navigator.navigateToHomePage();
            }
        });
    }

    @Override
    public void onDataReceived(String username) {
        view.showDataReceived(username);
    }

    protected UIComponent getComponent() {
        return ((LoginActivity) context).component();
    }
}

package com.albaradocompany.jose.proyect_meme_clean.interactor;

import com.albaradocompany.jose.proyect_meme_clean.usecase.update.UpdatePermissions;

/**
 * Created by jose on 25/05/2017.
 */

public class UpdatePermissionsInteractor implements Interactor, UpdatePermissions, UpdatePermissions.Listener {

    UpdatePermissions updatePermissions;
    MainThread mainThread;
    Executor executor;

    UpdatePermissions.Listener listener = new NullListener();

    public UpdatePermissionsInteractor(UpdatePermissions updatePermissions, MainThread mainThread, Executor executor) {
        this.updatePermissions = updatePermissions;
        this.mainThread = mainThread;
        this.executor = executor;
    }

    @Override
    public void run() {
        updatePermissions.updatePermissions(listener);
    }

    @Override
    public void updatePermissions(Listener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        this.executor.run(this);
    }

    @Override
    public void onNoInternetAvailable() {
        listener.onNoInternetAvailable();
    }

    @Override
    public void onError(Exception e) {
        listener.onError(e);
    }

    @Override
    public void onSuccess() {
        listener.onSuccess();
    }

    private class NullListener implements Listener {
        @Override
        public void onNoInternetAvailable() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess() {

        }
    }
}

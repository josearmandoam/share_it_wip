package com.albaradocompany.jose.proyect_meme_clean.ui.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.App;
import com.albaradocompany.jose.proyect_meme_clean.global.di.DaggerUIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIModule;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.albaradocompany.jose.proyect_meme_clean.interactor.AvatarInteractor;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.AvatarsRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.dialog.ConfirmAvatarDialog;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.AvatarsPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsAvatarsPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.view.ShowSnackBarImp;
import com.albaradocompany.jose.proyect_meme_clean.usecase.ShowSnackBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 03/05/2017.
 */

public class AvatarsFragment extends Fragment implements AbsAvatarsPresenter.Navigator, AbsAvatarsPresenter.View {
    @BindView(R.id.avatars_pbr)
    ProgressBar pbr;
    @BindView(R.id.avatars_listAvatar)
    RecyclerView recyclerView;
    @BindString(R.string.noInternetAvailable)
    String noInternet;
    @BindString(R.string.error)
    String error;
    @Inject
    AvatarInteractor interactor;

    AvatarsRecyclerAdapter adapter;
    ShowSnackBar showSnackBar;
    Activity activity;
    UIComponent component;
    AbsAvatarsPresenter presenter;

    public AvatarsFragment(Activity activity) {
        this.activity = activity;
    }

    AvatarsRecyclerAdapter.OnAvatarClicked onAvatarClicked = new AvatarsRecyclerAdapter.OnAvatarClicked() {
        @Override
        public void onAvatarClicked(Avatar avatar) {
            presenter.onAvatarClicked(avatar);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initializeRecycler(List<Avatar> list) {
        recyclerView.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), 3));
        adapter = new AvatarsRecyclerAdapter(activity.getApplicationContext(), list, onAvatarClicked);
        recyclerView.setAdapter(adapter);
    }

    private void initialize() {
        component().inject(this);
        presenter = new AvatarsPresenter(interactor);
        presenter.setView(this);
        presenter.setNavigator(this);
        showSnackBar = new ShowSnackBarImp(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_avatar, container, false);
        ButterKnife.bind(this, view);
        presenter.initialize();
        return view;
    }

    @Override
    public void showLoading() {
        pbr.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbr.setVisibility(View.GONE);
    }

    @Override
    public void showAvatarList(List<Avatar> avatarList) {
        initializeRecycler(avatarList);
    }

    @Override
    public void showNoInternetAvailable() {
        showSnackBar.show(noInternet, Color.RED);
    }

    @Override
    public void showError(Exception e) {
        showSnackBar.show(error, Color.RED);
    }

    @Override
    public void showAvatarClicked(Avatar avatar) {
        ConfirmAvatarDialog c = new ConfirmAvatarDialog(activity, avatar);
    }

    public UIComponent component() {
        if (component == null) {
            component = DaggerUIComponent.builder()
                    .rootComponent(((App) activity.getApplication()).getComponent())
                    .uIModule(new UIModule(activity.getApplicationContext()))
                    .mainModule(((App) activity.getApplication()).getMainModule())
                    .build();
        }
        return component;
    }
}

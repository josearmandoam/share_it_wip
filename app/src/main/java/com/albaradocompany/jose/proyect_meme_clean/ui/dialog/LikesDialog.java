package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.PictureActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.ProfileActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.UserActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.LikesRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.LikesPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsLikesPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesDialog extends DialogFragment implements AbsLikesPresenter.View, AbsLikesPresenter.Navigator {
    @BindView(R.id.likes_rv)
    RecyclerView recyclerView;
    @BindView(R.id.likes_tv_empty_likes)
    TextView empty_likes;

    List<Like> listLikes;
    AlertDialog dialog;
    LikesRecyclerAdapter adapter;
    AbsLikesPresenter presenter;
    @Inject
    UserSharedImp userSharedImp;

    @OnClick(R.id.likes_ibtn_back)
    public void onBackClicked(View view) {
        presenter.onBackClicked();
    }

    LikesRecyclerAdapter.onUserClicked onUserClicked = new LikesRecyclerAdapter.onUserClicked() {
        @Override
        public void onPictureClicked(Like like) {
            presenter.onPictureClicked(like);
        }

        @Override
        public void onUserNameClicked(Like like) {
            presenter.onUsernameClicked(like);
        }
    };

    public LikesDialog(List<Like> likes) {
        this.listLikes = likes;
    }

    private void initialize() {
        getComponent().inject(this);
        if (listLikes.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty_likes.setVisibility(View.VISIBLE);
        } else {
            adapter = new LikesRecyclerAdapter(getActivity(), listLikes, onUserClicked);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            empty_likes.setVisibility(View.GONE);
        }
        presenter = new LikesPresenter(getActivity());
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_likes, null);
        dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        ButterKnife.bind(this, view);
        initialize();
        return dialog;
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void navigateToUserDetail(Like like) {
        dialog.dismiss();
        if (like.getUserId().equals(userSharedImp.getUserID()))
            openUserProfile(getActivity());
        else
            openUserDetail(getActivity(), like);
    }

    public static void openUserDetail(Context ctx, Like like) {
        Intent intent = new Intent(ctx, UserActivity.class);
        intent.putExtra("userId", like.getUserId());
        ctx.startActivity(intent);
    }

    public static void openUserProfile(Context ctx) {
        Intent intent = new Intent(ctx, ProfileActivity.class);
        ctx.startActivity(intent);
    }

    protected UIComponent getComponent() {
        if (getActivity() instanceof MainActivity)
            return ((MainActivity) getActivity()).component();
        else
            return ((PictureActivity) getActivity()).component();
    }
}

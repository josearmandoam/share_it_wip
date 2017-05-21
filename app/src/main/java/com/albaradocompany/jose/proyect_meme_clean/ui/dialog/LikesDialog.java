package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

public class LikesDialog extends AlertDialog implements AbsLikesPresenter.View, AbsLikesPresenter.Navigator {
    @BindView(R.id.likes_rv)
    RecyclerView recyclerView;
    Context context;

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

    public LikesDialog(Context context, List<Like> likes) {
        super(context);
        this.context = context;
        this.listLikes = likes;

        initialize();
    }

    private void initialize() {
        initializeDialog();
        ButterKnife.bind(this, dialog);
        getComponent().inject(this);
        adapter = new LikesRecyclerAdapter(getContext(), listLikes, onUserClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        presenter = new LikesPresenter(getContext());
        presenter.setView(this);
        presenter.setNavigator(this);
    }

    private void initializeDialog() {
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_likes)
                .create();
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void navigateToUserDetail(Like like) {
        dialog.dismiss();
        if (like.getUserId().equals(userSharedImp.getUserID()))
            openUserProfile(getContext());
        else
            openUserDetail(getContext(), like);
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
        if (context instanceof MainActivity)
            return ((MainActivity) context).component();
        else
            return ((PictureActivity) context).component();
    }
}

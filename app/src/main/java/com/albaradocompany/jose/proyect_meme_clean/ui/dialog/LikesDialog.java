package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.UserActivity;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.LikesRecyclerAdapter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.LikesPresenter;
import com.albaradocompany.jose.proyect_meme_clean.ui.presenter.abs.AbsLikesPresenter;

import java.util.List;

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
        openUserDetail(getContext(), like);
    }

    public static void openUserDetail(Context ctx, Like like) {
        Intent intent = new Intent(ctx, UserActivity.class);
        intent.putExtra("userId", like.getUserId());
        ctx.startActivity(intent);
    }
}

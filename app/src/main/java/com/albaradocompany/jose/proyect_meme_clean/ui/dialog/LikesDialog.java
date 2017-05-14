package com.albaradocompany.jose.proyect_meme_clean.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.LikesRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesDialog extends AlertDialog {
    @BindView(R.id.likes_rv)
    RecyclerView recyclerView;
    Context context;

    List<Like> listLikes;
    AlertDialog dialog;
    LikesRecyclerAdapter adapter;

    public LikesDialog(Context context, List<Like> likes) {
        super(context);
        this.context = context;
        this.listLikes = likes;
        initialize();
    }

    private void initialize() {
        initializeDialog();
        ButterKnife.bind(this, dialog);

        adapter = new LikesRecyclerAdapter(getContext(), listLikes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initializeDialog() {
        dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_likes)
                .create();
        dialog.show();
    }
}

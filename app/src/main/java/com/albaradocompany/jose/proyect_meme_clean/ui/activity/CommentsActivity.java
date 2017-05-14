package com.albaradocompany.jose.proyect_meme_clean.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.ui.adaptor.CommentsRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class CommentsActivity extends BaseActivty {
    @BindView(R.id.comments_rv_list_comments)
    RecyclerView recyclerView;

    ArrayList<Comment> comments;
    CommentsRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        getDataBundle();
        adapter = new CommentsRecyclerAdapter(this, comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected boolean hideToolbar() {
        return true;
    }

    public void getDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            comments = (ArrayList<Comment>) bundle.get("comments");
        }
    }
}

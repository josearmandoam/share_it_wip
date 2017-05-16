package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 14/05/2017.
 */

public class LikesRecyclerAdapter extends RecyclerView.Adapter<LikesRecyclerAdapter.LikesView> {
    Context context;
    List<Like> listLikes;
    LikesRecyclerAdapter.onUserClicked onClickListener = new NullListener();

    public LikesRecyclerAdapter(Context context, List<Like> listLikes, onUserClicked onClickListener) {
        this.context = context;
        this.listLikes = listLikes;
        this.onClickListener = onClickListener;
    }

    @Override
    public LikesRecyclerAdapter.LikesView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_likes, parent, false);
        return new LikesView(view);
    }

    @Override
    public void onBindViewHolder(LikesRecyclerAdapter.LikesView holder, int position) {
        Picasso.with(context).load(listLikes.get(position).getProfile()).into(holder.profile);
        holder.username.setText(listLikes.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return listLikes.size();
    }

    public class LikesView extends RecyclerView.ViewHolder {
        @BindView(R.id.likes_row_iv_profile)
        ImageView profile;
        @BindView(R.id.likes_row_tv_username)
        TextView username;

        @OnClick(R.id.likes_row_iv_profile)
        public void onProfileClicked(View view) {
            onClickListener.onPictureClicked(listLikes.get(getAdapterPosition()));
        }
        @OnClick(R.id.likes_row_tv_username)
        public void onUsernameClicked(View view) {
            onClickListener.onUserNameClicked(listLikes.get(getAdapterPosition()));
        }

        public LikesView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onUserClicked {
        void onPictureClicked(Like like);

        void onUserNameClicked(Like like);
    }

    private class NullListener implements onUserClicked {

        @Override
        public void onPictureClicked(Like like) {

        }

        @Override
        public void onUserNameClicked(Like like) {

        }
    }
}

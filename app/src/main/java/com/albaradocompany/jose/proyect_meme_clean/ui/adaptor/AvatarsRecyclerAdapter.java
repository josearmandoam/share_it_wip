package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 20/04/2017.
 */

public class AvatarsRecyclerAdapter extends RecyclerView.Adapter<AvatarsRecyclerAdapter.ListAvatarAdapter> {
    Context context;
    List<Avatar> listAvatar;
    OnAvatarClicked onAvatarClicked = new NullOnAvatarClicked();

    public AvatarsRecyclerAdapter(Context context, List<Avatar> listAvatar, OnAvatarClicked onAvatarClicked) {
        this.context = context;
        this.listAvatar = listAvatar;
        this.onAvatarClicked = onAvatarClicked;
    }

    class ListAvatarAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.row_avatar)
        ImageView photo;

        @OnClick(R.id.row_avatar)
        public void onImageClicked(View view) {
            onAvatarClicked.onAvatarClicked(listAvatar.get(getAdapterPosition()));
        }

        ListAvatarAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public AvatarsRecyclerAdapter.ListAvatarAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_recycler_row, parent, false);
        return new ListAvatarAdapter(view);
    }

    @Override
    public void onBindViewHolder(AvatarsRecyclerAdapter.ListAvatarAdapter holder, int position) {
        if (!listAvatar.get(position).getImagePath().isEmpty()) {
            Picasso.with(context.getApplicationContext())
                    .load(listAvatar.get(position).getImagePath())
                    .into(holder.photo);
        }

    }

    @Override
    public int getItemCount() {
        return listAvatar.size();
    }

    public void clearListAvatar() {
        listAvatar.clear();
    }

    public void setListAvatar(List<Avatar> listAvatar) {
        this.listAvatar = listAvatar;
    }


    public interface OnAvatarClicked {
        void onAvatarClicked(Avatar avatar);
    }

    private class NullOnAvatarClicked implements OnAvatarClicked {
        @Override
        public void onAvatarClicked(Avatar avatar) {

        }
    }
}

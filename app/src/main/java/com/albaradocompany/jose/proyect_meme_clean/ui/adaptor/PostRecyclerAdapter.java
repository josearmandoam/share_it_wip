package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeBD.GetUserBDImp;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.UserBD;
import com.albaradocompany.jose.proyect_meme_clean.datasource.sharedpreferences.UserSharedImp;
import com.albaradocompany.jose.proyect_meme_clean.global.di.UIComponent;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Like;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Post;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.albaradocompany.jose.proyect_meme_clean.global.util.ListUtil;
import com.albaradocompany.jose.proyect_meme_clean.ui.activity.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 20/05/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder> {
    Context context;
    List<Post> posts;

    PostRecyclerAdapter.Listener listener = new NullListener();

    @Inject
    GetUserBDImp getUserBDImp;
    @Inject
    UserSharedImp userSharedImp;
    UserBD userBD;

    public PostRecyclerAdapter(Context context, List<Post> posts, Listener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener;

    }

    @Override
    public PostRecyclerAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_picture, parent, false);

        getComponent().inject(this);

        userBD = getUserBDImp.getUserBD(userSharedImp.getUserID());
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostRecyclerAdapter.PostViewHolder holder, int position) {
        Picasso.with(context).load(posts.get(position).getPicture().getImagePath()).into(holder.image);
        Picasso.with(context).load(posts.get(position).getxProfile()).into(holder.profile);
        holder.description.setText(posts.get(position).getPicture().getDescription());
        holder.username.setText(posts.get(position).getxUsername());
        holder.username2.setText(posts.get(position).getxUsername());
        holder.comments.setText("" + posts.get(position).getCommentList().size() + " " + context.getString(R.string.comments));
        holder.likes.setText("" + posts.get(position).getLikeList().size() + " " + context.getString(R.string.likes));
        holder.time.setText(DateUtil.timeAgo(posts.get(position).getPicture().getDate(),posts.get(position).getPicture().getTime()));
        if (photoSaved(posts.get(position).getPicture().getImageId()))
            holder.btnSave.setImageDrawable(holder.saved);
        else
            holder.btnSave.setImageDrawable(holder.notSaved);
        if (photoLiked(posts.get(position).getLikeList(), posts.get(position).getUserId()))
            holder.btnLike.setImageDrawable(holder.heartFill);
        else
            holder.btnLike.setImageDrawable(holder.heartBorder);
    }

    private boolean photoLiked(List<Like> likeList, String userId) {
        for (Like like : likeList) {
            if (like.getUserId().equals(userId))
                return true;
            else
                return false;
        }
        return false;
    }

    private boolean photoSaved(String imageId) {
        return getUserBDImp.isPhotoSaved(imageId);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void updateListAt(int position, Post post) {
        posts.get(position).setCommentList(post.getCommentList());
    }

    public void setNewPosts(List<Post> newPosts) {
        for (Post post : newPosts) {
            posts.add(post);
            posts = ListUtil.orderList(posts);
            notifyDataSetChanged();
        }
    }

    public void updatePosts(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            this.posts.get(i).setCommentList(posts.get(i).getCommentList());
            this.posts.get(i).setLikeList(posts.get(i).getLikeList());
            notifyItemChanged(i);
        }
    }

    public interface Listener {
        void onCommentsClicked(List<Comment> commments, String imageId, Post post, int adapterPosition);

        void onLikesClicked(List<Like> likes);

        void onSaveClicked(Picture picture, String userId);

        void onUnSaveClicked(Picture picture, String userId);

        void onSaveLikeClicked(String imageId);

        void onUnSaveLikeClicked(String userId, String imageId);

        void onUserClicked(String userId);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.picture_row_iv_user_profile)
        ImageView profile;
        @BindView(R.id.picture_row_tv_description)
        TextView description;
        @BindView(R.id.picture_row_tv_user_name1)
        TextView username;
        @BindView(R.id.picture_row_tv_user_name2)
        TextView username2;
        @BindView(R.id.picture_row_iv_photo)
        ImageView image;
        @BindView(R.id.picture_row_tv_likes)
        TextView likes;
        @BindView(R.id.picture_row_tv_comments)
        TextView comments;
        @BindView(R.id.picture_row_ibtn_like)
        ImageButton btnLike;
        @BindView(R.id.picture_row_ibtn_save)
        ImageButton btnSave;
        @BindView(R.id.picture_row_tv_time)
        TextView time;

        @BindDrawable(R.drawable.heart_fill)
        Drawable heartFill;
        @BindDrawable(R.drawable.heart_border)
        Drawable heartBorder;
        @BindDrawable(R.drawable.save_clicked)
        Drawable saved;
        @BindDrawable(R.drawable.save_unclicked)
        Drawable notSaved;

        @OnClick({R.id.picture_row_ibtn_comments, R.id.picture_row_tv_comments})
        public void onCommentsClicked(View view) {
            listener.onCommentsClicked(posts.get(getAdapterPosition()).getCommentList(),
                    posts.get(getAdapterPosition()).getPicture().getImageId(), posts.get(getAdapterPosition()), getAdapterPosition());
        }

        @OnClick(R.id.picture_row_tv_likes)
        public void onLikeClicked(View view) {
            listener.onLikesClicked(posts.get(getAdapterPosition()).getLikeList());
        }

        @OnClick(R.id.picture_row_ibtn_save)
        public void onSaveClicked(View view) {
            if (btnSave.getDrawable().equals(notSaved)) {
                btnSave.setImageDrawable(saved);
                listener.onSaveClicked(posts.get(getAdapterPosition()).getPicture(), posts.get(getAdapterPosition()).getUserId());
            } else {
                btnSave.setImageDrawable(notSaved);
                listener.onUnSaveClicked(posts.get(getAdapterPosition()).getPicture(), posts.get(getAdapterPosition()).getUserId());
            }
        }

        @OnClick(R.id.picture_row_ibtn_like)
        public void onSaveLikeClicked(View view) {
            if (btnLike.getDrawable().equals(heartFill)) {
                unSaveLike();
            } else {
                saveLike();
            }
        }
        @OnClick({R.id.picture_row_tv_user_name2, R.id.picture_row_tv_user_name1, R.id.picture_row_iv_user_profile})
        public void onUserClicked(View view){
            listener.onUserClicked(posts.get(getAdapterPosition()).getxUserId());
        }

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void saveLike() {
            posts.get(getAdapterPosition()).getLikeList().add(new Like(posts.get(getAdapterPosition()).getPicture().getImageId(),
                    userBD.user_name + " " + userBD.user_lastname, userBD.user_profile, userBD.userId, userSharedImp.createLikeID()));
            likes.setText(posts.get(getAdapterPosition()).getLikeList().size() + " " + context.getString(R.string.likes));
            btnLike.setImageDrawable(heartFill);
            listener.onSaveLikeClicked(posts.get(getAdapterPosition()).getPicture().getImageId());
        }

        private void unSaveLike() {
            for (int i = 0; i < posts.get(getAdapterPosition()).getLikeList().size(); i++) {
                if (posts.get(getAdapterPosition()).getLikeList().get(i).getUserId().equals(userBD.userId))
                    posts.get(getAdapterPosition()).getLikeList().remove(i);
            }
            likes.setText(posts.get(getAdapterPosition()).getLikeList().size() + " " + context.getString(R.string.likes));
            btnLike.setImageDrawable(heartBorder);
            listener.onUnSaveLikeClicked(posts.get(getAdapterPosition()).getUserId(), posts.get(getAdapterPosition()).getPicture().getImageId());
        }
    }

    protected UIComponent getComponent() {
        return ((MainActivity) context).component();
    }

    private class NullListener implements Listener {
        @Override
        public void onCommentsClicked(List<Comment> commments, String imageId, Post post, int adapterPosition) {

        }

        @Override
        public void onLikesClicked(List<Like> likes) {

        }

        @Override
        public void onSaveClicked(Picture picture, String userId) {

        }

        @Override
        public void onUnSaveClicked(Picture picture, String userId) {

        }

        @Override
        public void onSaveLikeClicked(String imageId) {

        }

        @Override
        public void onUnSaveLikeClicked(String userId, String imageId) {

        }

        @Override
        public void onUserClicked(String userId) {

        }
    }

}

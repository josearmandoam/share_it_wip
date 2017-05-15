package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Comment;
import com.albaradocompany.jose.proyect_meme_clean.global.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by jose on 14/05/2017.
 */

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.CommentsView> {
    Context context;
    List<Comment> comments;

    CommentsRecyclerAdapter.onCommentClicked commentClicked;

    public CommentsRecyclerAdapter(Context context, List<Comment> comments, onCommentClicked commentClicked) {
        this.context = context;
        this.comments = comments;
        this.commentClicked = commentClicked;
    }

    @Override
    public CommentsRecyclerAdapter.CommentsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_comment, parent, false);
        return new CommentsView(view);
    }

    @Override
    public void onBindViewHolder(CommentsRecyclerAdapter.CommentsView holder, int position) {
        holder.comment.setText(comments.get(position).getComment());
        Picasso.with(context).load(comments.get(position).getProfile()).into(holder.profile);
        holder.username.setText(comments.get(position).getUsername());
        holder.time.setText(DateUtil.timeAgo(comments.get(position).getDate(), comments.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentsView extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_row_comment)
        TextView comment;
        @BindView(R.id.comment_row_profile)
        ImageView profile;
        @BindView(R.id.comment_row_time)
        TextView time;
        @BindView(R.id.comment_row_username)
        TextView username;

        @OnClick(R.id.comment_row_profile)
        public void onPictureCliclek(View view) {
            commentClicked.onPictureClicked();
        }

        @OnClick(R.id.comment_row_username)
        public void onUsernameClicked(View view) {
            commentClicked.onUsernameClicked();
        }

        @OnLongClick(R.id.comment_row_comment)
        public boolean onCommentLongClickd(View view) {
            commentClicked.onLongClick(comments.get(getAdapterPosition()));
            return true;
        }

        public CommentsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onCommentClicked {
        void onPictureClicked();

        void onUsernameClicked();

        void onLongClick(Comment comment);
    }
}

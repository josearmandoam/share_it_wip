package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.BuildConfig;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 25/05/2017.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.UsersViewHolder> {
    Context context;
    String userId;
    List<User> users;
    List<Feed> feeds;

    onRowClickListener listener = new NullListenerRow();
    private List<Feed> newFeeds;

    public SearchRecyclerAdapter(Context context, String userId, List<User> users, List<Feed> feeds, onRowClickListener listener) {
        this.context = context;
        this.userId = userId;
        this.users = users;
        this.feeds = feeds;
        this.listener = listener;
    }

    @Override
    public SearchRecyclerAdapter.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_search, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.UsersViewHolder holder, int position) {
        if (users.get(position).getUserId().equals(userId)) {
            holder.state.setVisibility(View.GONE);
        } else {
            holder.state.setVisibility(View.VISIBLE);
            if (isFollowing(position)) {
                holder.state.setTextColor(BuildConfig.COLOR_RED);
                holder.state.setText(R.string.unfollow);
                holder.state.setBackground(context.getDrawable(R.drawable.border_text_blue));
            } else {
                holder.state.setTextColor(BuildConfig.COLOR_WHITE);
                holder.state.setText(R.string.follow);
                holder.state.setBackground(context.getDrawable(R.drawable.roundedbutton_blue));
            }
        }
        Picasso.with(context).load(users.get(position).getProfile()).into(holder.profile);
        holder.name.setText(users.get(position).getName() + " " + users.get(position).getLastname());
        holder.username.setText("@" + users.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void clear() {
        users.clear();
        feeds.clear();
    }

    public void updateData(List<User> users, List<Feed> feeds) {
        this.users = users;
        this.feeds = feeds;
    }

    public boolean isFollowing(int position) {
        for (int i = 0; i < feeds.size(); i++) {
            if (feeds.get(i).getxUserId().equals(users.get(position).getUserId()))
                return true;
        }
        return false;
    }

    public void clearFeeds() {
        feeds.clear();
    }

    public void setNewFeeds(List<Feed> newFeeds) {
        this.feeds = newFeeds;
    }

    public interface onRowClickListener {
        void onFollowClicked(String s, String xUserId, String xUsername, String xProfile);

        void onUnFollowClicked(String feedId);

        void onUserClicked(String userId);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_row_iv_profle)
        ImageView profile;
        @BindView(R.id.search_row_tv_name)
        TextView name;
        @BindView(R.id.search_row_tv_username)
        TextView username;
        @BindView(R.id.search_row_btn_state)
        Button state;

        @OnClick({R.id.search_row_tv_name, R.id.search_row_tv_username, R.id.search_row_iv_profle, R.id.search_row_lyt_container})
        public void onUserClicked(View view) {
            listener.onUserClicked(users.get(getAdapterPosition()).getUserId());
        }

        @OnClick(R.id.search_row_btn_state)
        public void onStateClicked(View view) {
            if (state.getText().toString().equals(context.getString(R.string.follow))) {
                unFollowUser();
            } else {
                followUser();
            }
        }

        private void followUser() {
            listener.onUnFollowClicked(getFeedIdClicked());
//            state.setTextColor(BuildConfig.COLOR_WHITE);
//            state.setText(R.string.follow);
//            state.setBackground(context.getDrawable(R.drawable.roundedbutton_blue));
        }

        private void unFollowUser() {
            listener.onFollowClicked(userId, users.get(getAdapterPosition()).getUserId(),
                    users.get(getAdapterPosition()).getName() + " " + users.get(getAdapterPosition()).getLastname(),
                    users.get(getAdapterPosition()).getProfile());
//            state.setTextColor(BuildConfig.COLOR_RED);
//            state.setText(R.string.unfollow);
//            state.setBackground(context.getDrawable(R.drawable.border_text_blue));
        }

        public UsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public String getFeedIdClicked() {
            for (int i = 0; i < feeds.size(); i++) {
                if (feeds.get(i).getUserId().equals(userId) && feeds.get(i).getxUserId().equals(users.get(getAdapterPosition()).getUserId()))
                    return feeds.get(i).getFeedId();
            }
            return null;
        }
    }

    private class NullListenerRow implements onRowClickListener {

        @Override
        public void onFollowClicked(String s, String xUserId, String xUsername, String xProfile) {

        }

        @Override
        public void onUnFollowClicked(String feedId) {

        }

        @Override
        public void onUserClicked(String userId) {

        }
    }
}

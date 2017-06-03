package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 03/06/2017.
 */

public class FeedSheetRecyclerAdapter extends RecyclerView.Adapter<FeedSheetRecyclerAdapter.ViewHolder> {
    Context context;
    List<Feed> feedList;

    FeedSheetRecyclerAdapter.Listener listener = new NullListener();

    public FeedSheetRecyclerAdapter(Context context, List<Feed> feedList, Listener listener) {
        this.context = context;
        this.feedList = feedList;
        this.listener = listener;
    }

    @Override
    public FeedSheetRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_bottom_sheet_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedSheetRecyclerAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load(feedList.get(position).getxProfile()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public interface Listener {
        void onContacClicked(Feed feed);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sheet_row_profile)
        ImageView profile;

        @OnClick(R.id.sheet_row_profile)
        public void onContactClicked(View view) {
            listener.onContacClicked(feedList.get(getAdapterPosition()));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class NullListener implements Listener {
        @Override
        public void onContacClicked(Feed feed) {

        }
    }
}

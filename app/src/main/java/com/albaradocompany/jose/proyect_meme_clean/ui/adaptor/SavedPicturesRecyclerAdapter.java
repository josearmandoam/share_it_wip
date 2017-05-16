package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 16/05/2017.
 */

public class SavedPicturesRecyclerAdapter extends RecyclerView.Adapter<SavedPicturesRecyclerAdapter.SavedPictureView> {
    Context context;
    List<Picture> pictureList;
    SavedPicturesRecyclerAdapter.onClickListener onClickListener;

    public SavedPicturesRecyclerAdapter(Context context, List<Picture> pictureList, SavedPicturesRecyclerAdapter.onClickListener onClickListener) {
        this.context = context;
        this.pictureList = pictureList;
        this.onClickListener = onClickListener;
    }

    @Override
    public SavedPicturesRecyclerAdapter.SavedPictureView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_saved_pictures, parent, false);
        return new SavedPictureView(view);
    }

    @Override
    public void onBindViewHolder(SavedPicturesRecyclerAdapter.SavedPictureView holder, int position) {
        Picasso.with(context).load(pictureList.get(position).getImagePath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public class SavedPictureView extends RecyclerView.ViewHolder {
        @BindView(R.id.spictures_row_iv_image)
        ImageView image;

        @OnClick(R.id.spictures_row_iv_image)
        public void onPictureClicked(View view) {
            onClickListener.onImageClicked(pictureList.get(getAdapterPosition()));
        }

        public SavedPictureView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onClickListener {
        void onImageClicked(Picture picture);
    }
}

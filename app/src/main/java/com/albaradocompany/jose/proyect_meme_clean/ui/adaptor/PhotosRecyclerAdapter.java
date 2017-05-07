package com.albaradocompany.jose.proyect_meme_clean.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albaradocompany.jose.proyect_meme_clean.R;
import com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid.PicturesBD;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jose on 06/05/2017.
 */

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.ListPhotosAdapter> {
    Context context;
    List<PicturesBD> list;

    PhotosRecyclerAdapter.Listener listener = new NullListener();

    public PhotosRecyclerAdapter(Context context, List<PicturesBD> list, Listener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public PhotosRecyclerAdapter.ListPhotosAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photos_recycler_row, parent, false);
        return new ListPhotosAdapter(view);
    }

    @Override
    public void onBindViewHolder(final PhotosRecyclerAdapter.ListPhotosAdapter holder, int position) {
        holder.comments.setText(list.get(position).coments);
        holder.likes.setText(list.get(position).likes);
        final ImageView imageView = new ImageView(context);
        Picasso.with(context)
                .load(list.get(position).imagePath)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.picture.setBackground(imageView.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Listener {
        void onPictureClicked(PicturesBD picture);
    }

    public class ListPhotosAdapter extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_recycler_iv_picture)
        ImageView picture;
        @BindView(R.id.photo_recycler_tv_number_comments)
        TextView comments;
        @BindView(R.id.photo_recycler_tv_number_likes)
        TextView likes;

        @OnClick(R.id.photo_recycler_iv_picture)
        public void onPictureClicked(View view){
            listener.onPictureClicked(list.get(getAdapterPosition()));
        }
        public ListPhotosAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class NullListener implements Listener {
        @Override
        public void onPictureClicked(PicturesBD picture) {

        }
    }
}

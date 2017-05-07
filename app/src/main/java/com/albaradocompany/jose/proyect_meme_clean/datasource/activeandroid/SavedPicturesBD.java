package com.albaradocompany.jose.proyect_meme_clean.datasource.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jose on 06/05/2017.
 */
@Table(name = "SavedPictures")
public class SavedPicturesBD extends Model{
    @Column(name = "userId")
    public String userId;
    @Column(name = "imagePath")
    public String imagePath;
    @Column(name = "description")
    public String description;
    @Column(name = "date")
    public String date;
    @Column(name = "imageId")
    public String imageId;
    @Column(name = "coments")
    public String coments;
    @Column(name = "likes")
    public String likes;

    public SavedPicturesBD() {
        super();
    }

    public SavedPicturesBD(String userId, String imagePath, String description, String date,
                           String imageId, String coments, String likes) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
        this.imageId = imageId;
        this.coments = coments;
        this.likes = likes;
    }
}

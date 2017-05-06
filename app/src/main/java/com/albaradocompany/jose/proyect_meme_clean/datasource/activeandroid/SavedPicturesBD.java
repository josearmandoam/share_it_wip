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

    public SavedPicturesBD() {
    }

    public SavedPicturesBD(String userId, String imagePath, String description, String date) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.description = description;
        this.date = date;
    }
}

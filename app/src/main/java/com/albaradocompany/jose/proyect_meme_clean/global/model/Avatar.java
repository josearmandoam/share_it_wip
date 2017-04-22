package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 20/04/2017.
 */

public class Avatar {
    private long id;
    private String imagePath;
    private String description;

    public Avatar() {
        id = 0L;
        imagePath = "";
        description = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

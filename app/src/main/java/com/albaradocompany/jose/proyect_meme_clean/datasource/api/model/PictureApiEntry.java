package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

/**
 * Created by jose on 05/05/2017.
 */

public class PictureApiEntry {
    String userId = "";
    String imagePath = "";
    String description = "";
    String fechaSubida = "";
    String imageId = "";
    String time = "";

    public Picture parsePicture() {
        Picture obj = new Picture();
        obj.setUserId(userId);
        obj.setImagePath(imagePath);
        obj.setDescription(description);
        obj.setDate(fechaSubida);
        obj.setImageId(imageId);
        obj.setTime(time);
        return obj;
    }
}

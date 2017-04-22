package com.albaradocompany.jose.proyect_meme_clean.datasource.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;

/**
 * Created by jose on 22/04/2017.
 */

public class AvatarApiEntry {
    long idAvatar = 0L;
    String imagenPath = "";
    String descripcion = "";

    public Avatar parseAvatar() {
        Avatar obj = new Avatar();
        obj.setId(idAvatar);
        obj.setImagePath(imagenPath);
        obj.setDescription(descripcion);
        return obj;
    }

}

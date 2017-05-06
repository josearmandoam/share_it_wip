package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 05/05/2017.
 */

public class PictureResponse {
    List<PictureApiEntry> pictures;

    public List<Picture> parsePictures() {
        List<Picture> list = new ArrayList<Picture>();
        if (pictures == null)
            return list;

        for (PictureApiEntry e : pictures) {
            list.add(e.parsePicture());
        }

        return list;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public class FeedApiEntry {
    String feedId = "";
    String userId = "";
    String xUserId = "";
    String xProfile = "";
    String xUsername = "";
    String state = "";
    List<PictureApiEntry> pictures = new ArrayList<>();

    public Feed parseFeed() {
        Feed obj = new Feed();
        obj.setUserId(userId);
        obj.setFeedId(feedId);
        obj.setxUserId(xUserId);
        obj.setxProfile(xProfile);
        obj.setxUsername(xUsername);
        obj.setState(state);
        obj.setPictures(parsePictures());
        return obj;
    }

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

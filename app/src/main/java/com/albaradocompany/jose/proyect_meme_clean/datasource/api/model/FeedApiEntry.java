package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;

/**
 * Created by jose on 20/05/2017.
 */

class FeedApiEntry {
    String feedId = "";
    String userId = "";
    String xUserId = "";
    String xProfile = "";
    String xUsername = "";

    public Feed parseFeed() {
        Feed obj = new Feed();
        obj.setUserId(userId);
        obj.setFeedId(feedId);
        obj.setxUserId(xUserId);
        obj.setxProfile(xProfile);
        obj.setxUsername(xUsername);
        return obj;
    }
}

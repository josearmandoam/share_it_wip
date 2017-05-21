package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 20/05/2017.
 */

public class FeedApiResponse {
    List<FeedApiEntry> feed;

    public List<Feed> parseFeeds(){
        List<Feed> list = new ArrayList<Feed>();
        if(feed == null)
            return list;

        for(FeedApiEntry e : feed) {
            list.add(e.parseFeed());
        }

        return list;
    }
}

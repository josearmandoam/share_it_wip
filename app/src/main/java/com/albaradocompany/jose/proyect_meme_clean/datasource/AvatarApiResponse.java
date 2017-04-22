package com.albaradocompany.jose.proyect_meme_clean.datasource;

import com.albaradocompany.jose.proyect_meme_clean.datasource.model.AvatarApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Avatar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 22/04/2017.
 */

public class AvatarApiResponse {
    List<AvatarApiEntry> avatars;

    public List<Avatar> parseAvatars() {
        List<Avatar> list = new ArrayList<Avatar>();
        if (avatars == null)
            return list;

        for (AvatarApiEntry e : avatars) {
            list.add(e.parseAvatar());
        }

        return list;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.GenericApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;

/**
 * Created by jose on 24/04/2017.
 */

public class GenericApiResponse {
    GenericApiEntry response;

    public GenericResponse parseResponse() {
        return response.parseResponse();
    }

    public  boolean isSuccessful(){
        return response.parseResponse().getCode().equals("1");
    }
}

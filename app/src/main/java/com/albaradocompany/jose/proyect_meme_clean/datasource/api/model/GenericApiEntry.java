package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.GenericResponse;

/**
 * Created by jose on 24/04/2017.
 */

public class GenericApiEntry {
    String code = "";
    String response = "";

    public GenericResponse parseResponse() {
        GenericResponse obj = new GenericResponse();
        obj.setCode(code);
        obj.setResponse(response);
        return obj;
    }
}

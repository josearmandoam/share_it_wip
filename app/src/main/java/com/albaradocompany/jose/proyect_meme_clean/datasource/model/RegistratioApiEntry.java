package com.albaradocompany.jose.proyect_meme_clean.datasource.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Response;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistratioApiEntry {
    String code = "";
    String response = "";

    public Response parseResponse() {
        Response obj = new Response();
        obj.setCode(code);
        obj.setResponse(response);
        return obj;
    }
}

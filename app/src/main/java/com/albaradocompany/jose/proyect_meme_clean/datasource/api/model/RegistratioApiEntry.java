package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.RegistrationResponse;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistratioApiEntry {
    String code = "";
    String response = "";

    public RegistrationResponse parseResponse() {
        RegistrationResponse obj = new RegistrationResponse();
        obj.setCode(code);
        obj.setResponse(response);
        return obj;
    }
}

package com.albaradocompany.jose.proyect_meme_clean.datasource;

import com.albaradocompany.jose.proyect_meme_clean.datasource.model.RegistratioApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.Response;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistrationApiResponse {
    RegistratioApiEntry response;

    public Response parseResponse() {
        return response.parseResponse();
    }
}

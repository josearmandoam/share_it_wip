package com.albaradocompany.jose.proyect_meme_clean.datasource.api;

import com.albaradocompany.jose.proyect_meme_clean.datasource.api.model.RegistratioApiEntry;
import com.albaradocompany.jose.proyect_meme_clean.global.model.RegistrationResponse;

/**
 * Created by jose on 24/04/2017.
 */

public class RegistrationApiResponse {
    RegistratioApiEntry response;

    public RegistrationResponse parseResponse() {
        return response.parseResponse();
    }
}

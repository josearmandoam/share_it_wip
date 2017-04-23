package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 24/04/2017.
 */

public class Response {
    private String code;
    private String response;

    public Response() {
        this.code = "";
        this.response = "";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

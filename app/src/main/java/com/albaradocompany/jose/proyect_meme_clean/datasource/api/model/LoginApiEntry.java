package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Login;

/**
 * Created by jose on 16/04/2017.
 */

public class LoginApiEntry {
    String idUser = "";
    String username = "";
    String password = "";
    String preguntaSeguridad = "";
    String respuestaSeguridad = "";
    String respuestaSeguridad2 = "";
    String email = "";
    String fechaNacimiento = "";
    String nombre = "";
    String apellidos = "";
    String imagePath = "";
    String backgrundPath = "";
    String description = "";

    public Login parseLogin() {
        Login obj = new Login();
        obj.setIdUser(idUser);
        obj.setUsername(username);
        obj.setPassword(password);
        obj.setPreguntaSeguridad(preguntaSeguridad);
        obj.setRespuestaSeguridad(respuestaSeguridad);
        obj.setRespuestaSeguridad2(respuestaSeguridad2);
        obj.setEmail(email);
        obj.setFechaNacimiento(fechaNacimiento);
        obj.setNombre(nombre);
        obj.setApellidos(apellidos);
        obj.setImagePath(imagePath);
        obj.setBackgrundPath(backgrundPath);
        obj.setDescription(description);
        return obj;
    }
}

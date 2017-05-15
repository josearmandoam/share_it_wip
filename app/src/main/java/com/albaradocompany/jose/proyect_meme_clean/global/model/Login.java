package com.albaradocompany.jose.proyect_meme_clean.global.model;

import java.io.Serializable;

/**
 * Created by jose on 21/04/2017.
 */

public class Login implements Serializable {
    private String idUser;
    private String username;
    private String password;
    private String preguntaSeguridad;
    private String respuestaSeguridad;
    private String respuestaSeguridad2;
    private String email;
    private String fechaNacimiento;
    private String nombre;
    private String apellidos;
    private String imagePath;
    private String backgrundPath;
    private String description;
    private String socialEmail;
    private String socialFacebook;
    private String socialTwitter;
    private String socialInstagram;
    private String socialWhatsapp;
    private String socialWebsite;

    public Login(String idUser, String username, String password, String preguntaSeguridad,
                 String respuestaSeguridad, String respuestaSeguridad2, String email, String fechaNacimiento,
                 String nombre, String apellidos, String imagePath, String backgrundPath, String description,
                 String socialEmail, String socialFacebook, String socialTwitter, String socialInstagram,
                 String socialWhatsapp, String socialWebsite) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSeguridad = respuestaSeguridad;
        this.respuestaSeguridad2 = respuestaSeguridad2;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.imagePath = imagePath;
        this.backgrundPath = backgrundPath;
        this.description = description;
        this.socialEmail = socialEmail;
        this.socialFacebook = socialFacebook;
        this.socialTwitter = socialTwitter;
        this.socialInstagram = socialInstagram;
        this.socialWhatsapp = socialWhatsapp;
        this.socialWebsite = socialWebsite;
    }

    public Login() {
        idUser = "";
        username = "";
        password = "";
        preguntaSeguridad = "";
        respuestaSeguridad = "";
        respuestaSeguridad2 = "";
        email = "";
        fechaNacimiento = "";
        nombre = "";
        apellidos = "";
        imagePath = "";
        backgrundPath = "";
        description = "";
        socialEmail = "";
        socialFacebook = "";
        socialTwitter = "";
        socialInstagram = "";
        socialWhatsapp = "";
        socialWebsite = "";
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreguntaSeguridad() {
        return preguntaSeguridad;
    }

    public void setPreguntaSeguridad(String preguntaSeguridad) {
        this.preguntaSeguridad = preguntaSeguridad;
    }

    public String getRespuestaSeguridad() {
        return respuestaSeguridad;
    }

    public void setRespuestaSeguridad(String respuestaSeguridad) {
        this.respuestaSeguridad = respuestaSeguridad;
    }

    public String getRespuestaSeguridad2() {
        return respuestaSeguridad2;
    }

    public void setRespuestaSeguridad2(String respuestaSeguridad2) {
        this.respuestaSeguridad2 = respuestaSeguridad2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setAvatarPath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBackgrundPath() {
        return backgrundPath;
    }

    public void setBackgrundPath(String backgrundPath) {
        this.backgrundPath = backgrundPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSocialEmail() {
        return socialEmail;
    }

    public void setSocialEmail(String socialEmail) {
        this.socialEmail = socialEmail;
    }

    public String getSocialFacebook() {
        return socialFacebook;
    }

    public void setSocialFacebook(String socialFacebook) {
        this.socialFacebook = socialFacebook;
    }

    public String getSocialTwitter() {
        return socialTwitter;
    }

    public void setSocialTwitter(String socialTwitter) {
        this.socialTwitter = socialTwitter;
    }

    public String getSocialInstagram() {
        return socialInstagram;
    }

    public void setSocialInstagram(String socialInstagram) {
        this.socialInstagram = socialInstagram;
    }

    public String getSocialWhatsapp() {
        return socialWhatsapp;
    }

    public void setSocialWhatsapp(String socialWhatsapp) {
        this.socialWhatsapp = socialWhatsapp;
    }

    public String getSocialWebsite() {
        return socialWebsite;
    }

    public void setSocialWebsite(String socialWebsite) {
        this.socialWebsite = socialWebsite;
    }
}

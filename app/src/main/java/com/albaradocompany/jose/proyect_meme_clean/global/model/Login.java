package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 21/04/2017.
 */

public class Login {
    private long id;
    private String username;
    private String password;
    private String preguntaSeguridad;
    private String respuestaSeguridad;
    private String respuestaSeguridad2;
    private String email;
    private String fechaNacimiento;
    private String nombre;
    private String apellidos;

    public Login(long id, String username, String password, String preguntaSeguridad,
                 String respuestaSeguridad, String respuestaSeguridad2, String email, String fechaNacimiento, String nombre, String apellidos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSeguridad = respuestaSeguridad;
        this.respuestaSeguridad2 = respuestaSeguridad2;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Login() {
        id=0;
        username = "";
        password = "";
        preguntaSeguridad = "";
        respuestaSeguridad = "";
        respuestaSeguridad2 = "";
        email = "";
        fechaNacimiento = "";
        nombre = "";
        apellidos = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

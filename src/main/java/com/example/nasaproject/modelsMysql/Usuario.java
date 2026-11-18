package com.example.nasaproject.modelsMysql;

public class Usuario {
    private int idUsuario;
    private String cveUsuario;
    private String email;
    private String NombreUsuario;
    private String Apellidos;
    private String FechaNac;
    private String Nacionalidad;
    private String Username;
    private String Password;


    public Usuario() {
    }

    public Usuario(int idUsuario, String cveUsuario, String email, String nombreUsuario, String apellidos, String fechaNac, String nacionalidad , String username, String password) {
        this.idUsuario = idUsuario;
        this.cveUsuario = cveUsuario;
        this.email = email;
        NombreUsuario = nombreUsuario;
        Apellidos = apellidos;
        FechaNac = fechaNac;
        Nacionalidad = nacionalidad;
        Username = username;
        Password = password;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCveUsuario() {
        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cveUsuario='" + cveUsuario + '\'' +
                ", NombreUsuario='" + NombreUsuario + '\'' +
                '}';
    }
}

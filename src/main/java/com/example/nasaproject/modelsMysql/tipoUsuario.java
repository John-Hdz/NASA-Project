package com.example.nasaproject.modelsMysql;

public class tipoUsuario {

    private String cveUsuario;
    private String NomTipoUsuario;

    public tipoUsuario() {
    }

    public tipoUsuario(String cveUsuario, String nomTipoUsuario) {
        this.cveUsuario = cveUsuario;
        NomTipoUsuario = nomTipoUsuario;
    }

    public String getCveUsuario() {
        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }

    public String getNomTipoUsuario() {
        return NomTipoUsuario;
    }

    public void setNomTipoUsuario(String nomTipoUsuario) {
        NomTipoUsuario = nomTipoUsuario;
    }
    @Override
    public String toString() {
        return NomTipoUsuario;
    }
}

package com.example.nasaproject.baseDatos.datosCompartidos;


import com.example.nasaproject.modelsMysql.Usuario;

public class UsuarioActual
{
    private static UsuarioActual instance;
    private Usuario usuario;

    private UsuarioActual() {
        // Constructor privado para evitar la instanciación directa
    }

    public static synchronized UsuarioActual getInstance()
    {
        if (instance == null) {
            instance = new UsuarioActual();
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

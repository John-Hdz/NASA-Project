package com.example.nasaproject.modelsMysql;

import java.sql.Date;

public class Apod {
    private String titulo;
    private String descripcion;
    private String tipo;
    private String url;
    private Date fecha;

    public Apod (String titulo, String descripcion, String tipo, String url, Date fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.url = url;
        this.fecha = fecha;
    }
    public Apod(String titulo) {

    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

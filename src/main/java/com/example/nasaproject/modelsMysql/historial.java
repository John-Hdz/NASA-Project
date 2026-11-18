package com.example.nasaproject.modelsMysql;

import java.time.LocalDateTime;

public class historial {

    private int idHistorial;
    private String email;
    private String busqueda;
    private LocalDateTime fechaBusqueda;

    public historial() {
    }

    public historial(int idHistorial, String email, String busqueda, LocalDateTime fechaBusqueda) {
        this.idHistorial = idHistorial;
        this.email = email;
        this.busqueda = busqueda;
        this.fechaBusqueda = fechaBusqueda;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public LocalDateTime getFechaBusqueda() {
        return fechaBusqueda;
    }

    public void setFechaBusqueda(LocalDateTime fechaBusqueda) {
        this.fechaBusqueda = fechaBusqueda;
    }
    @Override
    public String toString() {
        return "ID: " + idHistorial + ", Descripción: " + busqueda;
    }
}

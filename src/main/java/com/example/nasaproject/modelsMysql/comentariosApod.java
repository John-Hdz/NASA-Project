package com.example.nasaproject.modelsMysql;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class comentariosApod {

    private int idComentario;
    private String email;
    private LocalDateTime fechaComentario;
    private LocalDate fecha;
    private String comentario;

    public comentariosApod() {
    }

    public comentariosApod(int idComentario, String email, LocalDateTime fechaComentario, LocalDate fecha, String comentario) {
        this.idComentario = idComentario;
        this.email = email;
        this.fechaComentario = fechaComentario;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "idComentario=" + idComentario +
                ", email='" + email + '\'' +
                ", fechaComentario=" + fechaComentario +
                ", fecha=" + fecha +
                ", comentario='" + comentario + '\'' +
                '}';
    }


}
package com.example.nasaproject.baseDatos;

import com.example.nasaproject.modelsMysql.comentariosApod;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComentarioDao extends MySQLConnection implements Dao<comentariosApod>{

    static Connection conn = getConnection();

    @Override
    public Optional<comentariosApod> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<comentariosApod> findAll() {
        List<comentariosApod> comentarios = new ArrayList<>();
        String query = "SELECT idComentario, email, fechaComentario, fecha, comentario FROM comentariosapod WHERE DATE(fecha) = CURDATE()";

        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int idComentario = rs.getInt("idComentario");
                String email = rs.getString("email");
                LocalDateTime fechaComentario = null;
                if (rs.getTimestamp("fechaComentario") != null) {
                    fechaComentario = rs.getTimestamp("fechaComentario").toLocalDateTime();
                }
                LocalDate fecha = null;
                if (rs.getDate("fecha") != null) {
                    fecha = rs.getDate("fecha").toLocalDate();
                }
                String comentario = rs.getString("comentario");

                comentarios.add(new comentariosApod(idComentario, email, fechaComentario, fecha, comentario));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comentarios;
    }




    @Override
    public boolean save(comentariosApod comentariosApod) {
        String query;
        if (comentariosApod.getIdComentario() == 0) {
            query = "INSERT INTO comentariosapod (email, fechaComentario,fecha, comentario) VALUES (?, NOW(),Now(), ?)";
        } else {
            query = "UPDATE comentariosapod SET comentario = ? WHERE idComentario = ?";
        }

        try (
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (comentariosApod.getIdComentario() == 0) {
                ps.setString(1, comentariosApod.getEmail());
                ps.setString(2, comentariosApod.getComentario());
            } else {
                ps.setString(1, comentariosApod.getComentario());
                ps.setInt(2, comentariosApod.getIdComentario());
            }

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(comentariosApod record) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query= "delete from comentariosapod where idComentario = ?";
        try{
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setInt(1,id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean emailExists(String email) {
        String query = "SELECT 1 FROM usuarios WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Retorna true si hay al menos un resultado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<comentariosApod> findAllInformation() {
        List<comentariosApod> comentarios = new ArrayList<>();
        String query = "SELECT idComentario, email, fechaComentario, fecha, comentario FROM comentariosapod";

        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int idComentario = rs.getInt("idComentario");
                String email = rs.getString("email");
                LocalDateTime fechaComentario = null;
                LocalDate fecha = null;

                try {
                    fechaComentario = rs.getTimestamp("fechaComentario").toLocalDateTime();
                } catch (SQLException | NullPointerException e) {
                    e.printStackTrace();
                }

                try {
                    Date date = rs.getDate("fecha");
                    if (date != null) {
                        fecha = date.toLocalDate();
                    }
                } catch (SQLException | NullPointerException e) {
                    e.printStackTrace();
                }

                String comentario = rs.getString("comentario");
                comentarios.add(new comentariosApod(idComentario, email, fechaComentario, fecha, comentario));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comentarios;
    }
}

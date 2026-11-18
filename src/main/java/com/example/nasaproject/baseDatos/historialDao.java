package com.example.nasaproject.baseDatos;

import com.example.nasaproject.modelsMysql.historial;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class historialDao extends MySQLConnection implements Dao<historial>{

    static Connection conn = getConnection();

    @Override
    public Optional<historial> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<historial> findAll() {

        List<historial> historialList = new ArrayList<>();
        String query = "SELECT * FROM historialbusqueda";

        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                historial historial = new historial();
                historial.setIdHistorial(rs.getInt("idHistorial"));
                historial.setEmail(rs.getString("email"));

                LocalDateTime fechaBusqueda = null;
                if (rs.getTimestamp("fechaBusqueda") != null) {
                    fechaBusqueda = rs.getTimestamp("fechaBusqueda").toLocalDateTime();
                }
                historial.setFechaBusqueda(fechaBusqueda);

                historial.setBusqueda(rs.getString("busqueda"));

                historialList.add(historial);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historialList;
    }
    public List<historial> GetHistorialByUser(String email)
    {

        List<historial> historialList = new ArrayList<>();
        String query = "SELECT * FROM historialbusqueda where email=?";

        try{
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, email);
                ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                historial historial = new historial();
                historial.setIdHistorial(rs.getInt("idHistorial"));
                historial.setEmail(rs.getString("email"));

                LocalDateTime fechaBusqueda = null;
                if (rs.getTimestamp("fechaBusqueda") != null) {
                    fechaBusqueda = rs.getTimestamp("fechaBusqueda").toLocalDateTime();
                }
                historial.setFechaBusqueda(fechaBusqueda);

                historial.setBusqueda(rs.getString("busqueda"));

                historialList.add(historial);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historialList;
    }



    @Override
    public boolean save(historial record) {

        return false;
    }

    @Override
    public boolean update(historial record) {

        return false;
    }

    @Override
    public boolean delete(int id)
    {
        String query = "DELETE\n" +
                "FROM historialbusqueda where idHistorial = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

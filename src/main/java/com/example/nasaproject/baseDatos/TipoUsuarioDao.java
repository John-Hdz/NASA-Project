package com.example.nasaproject.baseDatos;

import com.example.nasaproject.modelsMysql.tipoUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TipoUsuarioDao extends MySQLConnection implements Dao<tipoUsuario>{

    static Connection conn = getConnection();

    @Override
    public Optional<tipoUsuario> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<tipoUsuario> findAll() {
        return null;
    }

    @Override
    public boolean save(tipoUsuario record) {
        return false;
    }

    @Override
    public boolean update(tipoUsuario record) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public List<tipoUsuario> getAllTipoUsuarios() {
        ObservableList<tipoUsuario> listtipoUsuarios = FXCollections.observableArrayList();
        String query = "SELECT cveUsuario, NomTipoUsuario FROM tipoUsuario";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String cveUsuario = rs.getString("cveUsuario");
                String nomTipoUsuario = rs.getString("NomTipoUsuario");
                listtipoUsuarios.add(new tipoUsuario(cveUsuario, nomTipoUsuario));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listtipoUsuarios;
    }

    public tipoUsuario getTipoUsuarioByCve(String cveUsuario) {
        tipoUsuario tipoUsuario = null;
        String query = "SELECT cveUsuario, NomTipoUsuario FROM tipoUsuario WHERE cveUsuario = ?";

        try (   PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, cveUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String cve = rs.getString("cveUsuario");
                    String nomTipoUsuario = rs.getString("NomTipoUsuario");
                    tipoUsuario = new tipoUsuario(cve, nomTipoUsuario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoUsuario;
    }

}

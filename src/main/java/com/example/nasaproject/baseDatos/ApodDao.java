package com.example.nasaproject.baseDatos;

import com.example.nasaproject.modelsMysql.Apod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ApodDao extends MySQLConnection implements Dao<Apod> {
    Connection conn = getConnection();
    ObservableList<Apod> ListaApod = FXCollections.observableArrayList();
    @Override
    public Optional<Apod> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Apod> findAll() {
        List<Apod> apodslist = FXCollections.observableArrayList();
        String query = "SELECT\n" +
                "apod.titulo, apod.descripcion, apod.tipo, apod.fecha, imagenes.Url AS imagen_url\n" +
                "FROM  apod JOIN imagenes ON apod.url = imagenes.Url\n" +
                "ORDER BY apod.fecha";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Apod apod = new Apod(rs.getString("titulo"));
                apod.setTitulo(rs.getString("titulo"));
                apod.setDescripcion(rs.getString("descripcion"));
                apod.setTipo(rs.getString("tipo"));
                apod.setUrl(rs.getString("imagen_url"));
                apod.setFecha(rs.getDate("fecha"));
                apodslist.add(apod);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return apodslist;
    }
    public ObservableList<Apod> getReporte() {

        ListaApod.clear();

        try {
            String  query = "SELECT apod.titulo, apod.descripcion, apod.tipo, apod.fecha, imagenes.Url AS imagen_url\n" +
                    "FROM  apod JOIN imagenes ON apod.url = imagenes.Url\n" +
                    "ORDER BY apod.fecha;";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                ListaApod.add(new Apod(
                       rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getString("tipo"),
                        rs.getString("imagen_url"),
                        rs.getDate("fecha"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return ListaApod;
    }
    public void insertImage(String imagePath, Apod apod) {
        String query = "INSERT INTO imagenes (nombreImagen, imagen, Url) VALUES (?, ?, ?)";

        try {
            FileInputStream fis = new FileInputStream(imagePath);
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, apod.getTitulo());
            statement.setBinaryStream(2, fis, fis.available());
            statement.setString(3, apod.getUrl());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("La imagen se insertó correctamente.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        String query2 = "INSERT INTO apod (titulo, descripcion, tipo, url, fecha)\n" +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = conn.prepareStatement(query2);

            statement.setString(1, apod.getTitulo());
            statement.setString(2, apod.getDescripcion());
            statement.setString(3, apod.getTipo());
            statement.setString(4, apod.getUrl());
            statement.setDate(5, apod.getFecha());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("se actualizo la tabla dao");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(Apod record) {
        return false;
    }

    @Override
    public boolean update(Apod record) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}

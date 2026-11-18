package com.example.nasaproject.baseDatos;

import com.example.nasaproject.baseDatos.datosCompartidos.EnvConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the shared MySQL JDBC connection using environment configuration.
 */
public class MySQLConnection {
    private static Connection conn = null;

    public static void Connect() {
        String hostname = EnvConfig.get("DB_HOST", "localhost");
        String dbport = EnvConfig.get("DB_PORT", "3306");
        String dbname = EnvConfig.get("DB_NAME", "nasa");
        String dbuser = EnvConfig.get("DB_USER");
        String dbpass = EnvConfig.get("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://" + hostname + ":" + dbport + "/" + dbname + "?serverTimezone=UTC";
            conn = DriverManager.getConnection(jdbcUrl, dbuser, dbpass);
            System.out.println("Successful database connection.");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, "Database connection error", ex);
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            Connect();
        }
        return conn;
    }

    public static void Disconnect() {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
            conn = null;
            System.out.println("Database connection has been terminated.");
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

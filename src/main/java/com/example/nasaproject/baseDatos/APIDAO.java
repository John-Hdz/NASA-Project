package com.example.nasaproject.baseDatos;

import com.example.nasaproject.baseDatos.datosCompartidos.EnvConfig;
import com.example.nasaproject.modelsMysql.APIKey;
import com.example.nasaproject.modelsMysql.Api;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class APIDAO extends MySQLConnection implements Dao<Api>{

    static Connection conn = getConnection();

    @Override
    public Optional<Api> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Api> findAll() {
        return null;
    }

    @Override
    public boolean save(Api record) {
        return false;
    }

    @Override
    public boolean update(Api p_api)
    {

        String query = "UPDATE apis SET Api=?, TipoApi=? WHERE IdApi=?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, p_api.getApi());
            ps.setString(2, p_api.getTipoApi());
            ps.setInt(3, p_api.getIdApi());

            ps.executeUpdate();
            System.out.println(p_api.getTipoApi());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
    @Override
    public boolean delete(int id) {
        return false;
    }

    public boolean updateKey(APIKey p_api)
    {
        String query = "UPDATE apiKey SET ApiKey=?, FechaCreacion=?,FechaVencimiento=? WHERE IdApiKey=?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, p_api.getApiKey());
            ps.setString(2, p_api.getFechaCreacion());
            ps.setString(3, p_api.getFechaVencimiento());
            ps.setInt(4, p_api.getIdApi());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
    public APIKey getApiKey()
    {
        if (conn == null) {
            return null;
        }
        String query = "SELECT * from apiKey";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new APIKey(
                        resultSet.getInt("idApiKey"),
                        resultSet.getString("ApiKey"),
                        resultSet.getString("FechaCreacion"),
                        resultSet.getString("FechaVencimiento")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Api> getApis()
    {
        List<Api> apis = new ArrayList<>();
        if (conn == null) {
            return apis;
        }
        String query = "SELECT * from apis";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                Api api= new Api(
                        resultSet.getInt("idApi"),
                        resultSet.getString("Api"),
                        resultSet.getString("TipoApi"));
                apis.add(api);
            }
            return apis;

        } catch (SQLException e) {
            e.printStackTrace();
            return apis;
        }
    }

    /**
     * Resolves the NASA API key from environment/.env first, then DB, falling back to DEMO_KEY.
     */
    public String resolveApiKey() {
        String envKey = EnvConfig.get("NASA_API_KEY");
        if (!envKey.isEmpty()) {
            return envKey;
        }
        APIKey dbKey = getApiKey();
        if (dbKey != null && dbKey.getApiKey() != null && !dbKey.getApiKey().isBlank()) {
            return dbKey.getApiKey().trim();
        }
        return "DEMO_KEY";
    }

    /**
     * Resolves an API endpoint URL from environment/.env first, then DB by index, then defaultUrl.
     */
    public String resolveApiUrl(int dbIndex, String envVarName, String defaultUrl) {
        String envUrl = EnvConfig.get(envVarName);
        if (!envUrl.isEmpty()) {
            return envUrl;
        }
        List<Api> apis = getApis();
        if (dbIndex >= 0 && dbIndex < apis.size()) {
            String dbUrl = apis.get(dbIndex).getApi();
            if (dbUrl != null && !dbUrl.isBlank()) {
                return dbUrl.trim();
            }
        }
        return defaultUrl;
    }

}

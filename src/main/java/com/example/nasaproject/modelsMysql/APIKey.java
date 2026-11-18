package com.example.nasaproject.modelsMysql;

public class APIKey
{
    int idApi;
    String apiKey;
    String fechaCreacion;
    String fechaVencimiento;

    public APIKey() {
    }

    public APIKey(int idApi, String apiKey, String fechaCreacion, String fechaVencimiento) {
        this.idApi = idApi;
        this.apiKey = apiKey;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}

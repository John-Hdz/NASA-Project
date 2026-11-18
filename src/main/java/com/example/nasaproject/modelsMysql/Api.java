package com.example.nasaproject.modelsMysql;

public class Api
{
    int idApi;
    String Api;
    String TipoApi;

    public Api() {
    }

    public Api(int idApi, String api, String tipoApi) {
        this.idApi = idApi;
        Api = api;
        TipoApi = tipoApi;
    }

    public String getTipoApi() {
        return TipoApi;
    }

    public void setTipoApi(String tipoApi) {
        TipoApi = tipoApi;
    }

    public String getApi() {
        return Api;
    }

    public void setApi(String api) {
        Api = api;
    }

    public int getIdApi() {
        return idApi;
    }

    public void setIdApi(int idApi) {
        this.idApi = idApi;
    }
}

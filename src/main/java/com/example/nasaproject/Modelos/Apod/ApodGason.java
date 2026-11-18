
package com.example.nasaproject.Modelos.Apod;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ApodGason {

    @SerializedName("copyright")
    @Expose
    public String copyright;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("explanation")
    @Expose
    public String explanation;
    @SerializedName("hdurl")
    @Expose
    public String hdurl;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("service_version")
    @Expose
    public String serviceVersion;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("url")
    @Expose
    public String url;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String agregarSaltoDeLineaCadaNPalabras(String texto, int palabrasPorLinea) {
        String[] palabras = texto.split("\\s+"); // Divide el texto en palabras usando espacios como delimitador
        StringBuilder sb = new StringBuilder();
        int contadorPalabras = 0;
        for (String palabra : palabras) {
            sb.append(palabra).append(" "); // Agrega la palabra al resultado con un espacio después
            contadorPalabras++;
            // Si el número de palabras alcanza el límite por línea, agrega un salto de línea
            if (contadorPalabras >= palabrasPorLinea) {
                sb.append("\n");
                contadorPalabras = 0; // Reinicia el contador de palabras para la siguiente línea
            }
        }
        return sb.toString();
    }

    @Override
    public String toString()
    {
        explanation=agregarSaltoDeLineaCadaNPalabras(explanation,8);

        return title +"\n"+
                "\ncopyright: " + copyright+
                "\ndate: " + date +"\n"+
                "\nmediaType: " + mediaType +"\n"+
                "\nExplanation: "+explanation;
    }
}

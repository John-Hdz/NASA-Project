
package com.example.nasaproject.Modelos.LibraryImage;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Datum {

    @SerializedName("album")
    @Expose
    public List<String> album;
    @SerializedName("center")
    @Expose
    public String center;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("keywords")
    @Expose
    public List<String> keywords;
    @SerializedName("nasa_id")
    @Expose
    public String nasaId;
    @SerializedName("date_created")
    @Expose
    public String dateCreated;
    @SerializedName("media_type")
    @Expose
    public String mediaType;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("photographer")
    @Expose
    public String photographer;
    @SerializedName("location")
    @Expose
    public String location;

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        description= agregarSaltoDeLineaCadaNPalabras(description,11);
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Datum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("album");
        sb.append('=');
        sb.append(((this.album == null)?"<null>":this.album));
        sb.append(',');
        sb.append("center");
        sb.append('=');
        sb.append(((this.center == null)?"<null>":this.center));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("keywords");
        sb.append('=');
        sb.append(((this.keywords == null)?"<null>":this.keywords));
        sb.append(',');
        sb.append("nasaId");
        sb.append('=');
        sb.append(((this.nasaId == null)?"<null>":this.nasaId));
        sb.append(',');
        sb.append("dateCreated");
        sb.append('=');
        sb.append(((this.dateCreated == null)?"<null>":this.dateCreated));
        sb.append(',');
        sb.append("mediaType");
        sb.append('=');
        sb.append(((this.mediaType == null)?"<null>":this.mediaType));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("photographer");
        sb.append('=');
        sb.append(((this.photographer == null)?"<null>":this.photographer));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}

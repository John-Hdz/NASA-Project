
package com.example.nasaproject.Modelos.EPIC;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Epic {

    @SerializedName("identifier")
    @Expose
    public String identifier;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("centroid_coordinates")
    @Expose
    public CentroidCoordinates centroidCoordinates;
    @SerializedName("dscovr_j2000_position")
    @Expose
    public DscovrJ2000Position dscovrJ2000Position;
    @SerializedName("lunar_j2000_position")
    @Expose
    public LunarJ2000Position lunarJ2000Position;
    @SerializedName("sun_j2000_position")
    @Expose
    public SunJ2000Position sunJ2000Position;
    @SerializedName("attitude_quaternions")
    @Expose
    public AttitudeQuaternions attitudeQuaternions;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("coords")
    @Expose
    public Coords coords;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CentroidCoordinates getCentroidCoordinates() {
        return centroidCoordinates;
    }

    public void setCentroidCoordinates(CentroidCoordinates centroidCoordinates) {
        this.centroidCoordinates = centroidCoordinates;
    }

    public DscovrJ2000Position getDscovrJ2000Position() {
        return dscovrJ2000Position;
    }

    public void setDscovrJ2000Position(DscovrJ2000Position dscovrJ2000Position) {
        this.dscovrJ2000Position = dscovrJ2000Position;
    }

    public LunarJ2000Position getLunarJ2000Position() {
        return lunarJ2000Position;
    }

    public void setLunarJ2000Position(LunarJ2000Position lunarJ2000Position) {
        this.lunarJ2000Position = lunarJ2000Position;
    }

    public SunJ2000Position getSunJ2000Position() {
        return sunJ2000Position;
    }

    public void setSunJ2000Position(SunJ2000Position sunJ2000Position) {
        this.sunJ2000Position = sunJ2000Position;
    }

    public AttitudeQuaternions getAttitudeQuaternions() {
        return attitudeQuaternions;
    }

    public void setAttitudeQuaternions(AttitudeQuaternions attitudeQuaternions) {
        this.attitudeQuaternions = attitudeQuaternions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public String FisrtContent()
    {
        return  "centroid_coordinates:"+
                "\nLat: "+centroidCoordinates.getLat()
                +"\nLon:"+centroidCoordinates.getLon()+
                "\n\ndscovr_position:"+
                "\nx: "+dscovrJ2000Position.getX()+
                "\ny: "+dscovrJ2000Position.getY()+
                "\nz: "+dscovrJ2000Position.getZ()+
                "\n\nlunar_position:"+
                "\nx: "+lunarJ2000Position.getX()+
                "\ny: "+lunarJ2000Position.getY()+
                "\nz: "+lunarJ2000Position.getZ();
    }
    public String SecondContent()
    {
        return "sun_position:"+
                "\nx: "+sunJ2000Position.getX()+
                "\ny: "+sunJ2000Position.getY()+
                "\nz: "+sunJ2000Position.getZ()+
                "\n\nattitude_quaternions:"+
                "\nq0"+ attitudeQuaternions.getQ0()+
                "\nq1"+ attitudeQuaternions.getQ1()+
                "\nq2"+ attitudeQuaternions.getQ2()+
                "\nq3"+ attitudeQuaternions.getQ3();
    }

    @Override
    public String toString()
    {
        return  "\nCAPTION: " + caption  +
                "\n                       IMAGE: " + image+
                "   VERSION: " + version +
                "\n                             DATE: " + date;
    }
}


package com.example.nasaproject.Modelos.EPIC;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Coords {

    @SerializedName("centroid_coordinates")
    @Expose
    public CentroidCoordinates__1 centroidCoordinates;
    @SerializedName("dscovr_j2000_position")
    @Expose
    public DscovrJ2000Position__1 dscovrJ2000Position;
    @SerializedName("lunar_j2000_position")
    @Expose
    public LunarJ2000Position__1 lunarJ2000Position;
    @SerializedName("sun_j2000_position")
    @Expose
    public SunJ2000Position__1 sunJ2000Position;
    @SerializedName("attitude_quaternions")
    @Expose
    public AttitudeQuaternions__1 attitudeQuaternions;

    public CentroidCoordinates__1 getCentroidCoordinates() {
        return centroidCoordinates;
    }

    public void setCentroidCoordinates(CentroidCoordinates__1 centroidCoordinates) {
        this.centroidCoordinates = centroidCoordinates;
    }

    public DscovrJ2000Position__1 getDscovrJ2000Position() {
        return dscovrJ2000Position;
    }

    public void setDscovrJ2000Position(DscovrJ2000Position__1 dscovrJ2000Position) {
        this.dscovrJ2000Position = dscovrJ2000Position;
    }

    public LunarJ2000Position__1 getLunarJ2000Position() {
        return lunarJ2000Position;
    }

    public void setLunarJ2000Position(LunarJ2000Position__1 lunarJ2000Position) {
        this.lunarJ2000Position = lunarJ2000Position;
    }

    public SunJ2000Position__1 getSunJ2000Position() {
        return sunJ2000Position;
    }

    public void setSunJ2000Position(SunJ2000Position__1 sunJ2000Position) {
        this.sunJ2000Position = sunJ2000Position;
    }

    public AttitudeQuaternions__1 getAttitudeQuaternions() {
        return attitudeQuaternions;
    }

    public void setAttitudeQuaternions(AttitudeQuaternions__1 attitudeQuaternions) {
        this.attitudeQuaternions = attitudeQuaternions;
    }

}

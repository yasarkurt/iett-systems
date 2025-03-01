package com.example.iett_system_backend.soap.garage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GarageData", namespace = "http://tempuri.org/", propOrder = {
        "garageId",
        "garageName",
        "garageCode",
        "longitude",
        "latitude"
})
public class GarageData {

    @XmlElement(name = "garageId", required = true, namespace = "http://tempuri.org/")
    private String garageId;

    @XmlElement(name = "garageName", required = true, namespace = "http://tempuri.org/")
    private String garageName;

    @XmlElement(name = "garageCode", required = true, namespace = "http://tempuri.org/")
    private String garageCode;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private Double longitude;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private Double latitude;

    // Default constructor
    public GarageData() {
    }

    // Constructor with parameters
    public GarageData(String garageId, String garageName, String garageCode,
                      Double longitude, Double latitude) {
        this.garageId = garageId;
        this.garageName = garageName;
        this.garageCode = garageCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and Setters
    public String getGarageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getGarageCode() {
        return garageCode;
    }

    public void setGarageCode(String garageCode) {
        this.garageCode = garageCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
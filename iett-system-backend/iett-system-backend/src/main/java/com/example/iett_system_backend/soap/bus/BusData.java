package com.example.iett_system_backend.soap.bus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusData", namespace = "http://tempuri.org/", propOrder = {
        "operator",
        "garage",
        "doorNumber",
        "time",
        "longitude",
        "latitude",
        "speed",
        "plateNumber"
})
public class BusData {

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private String operator;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private String garage;

    @XmlElement(name = "doorNumber", required = true, namespace = "http://tempuri.org/")
    private String doorNumber;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private String time;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private Double longitude;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private Double latitude;

    @XmlElement(required = true, namespace = "http://tempuri.org/")
    private Double speed;

    @XmlElement(name = "plateNumber", required = true, namespace = "http://tempuri.org/")
    private String plateNumber;

    // Default constructor
    public BusData() {
    }

    // Constructor with parameters
    public BusData(String operator, String garage, String doorNumber, String time,
                   Double longitude, Double latitude, Double speed, String plateNumber) {
        this.operator = operator;
        this.garage = garage;
        this.doorNumber = doorNumber;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.plateNumber = plateNumber;
    }

    // Getters and Setters
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
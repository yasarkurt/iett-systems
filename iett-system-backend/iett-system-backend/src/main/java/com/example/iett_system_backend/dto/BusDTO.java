package com.example.iett_system_backend.dto;

import java.time.LocalDateTime;

public class BusDTO {

    private Long id;
    private String operator;
    private String garage;
    private String doorNumber;
    private LocalDateTime time;
    private Double longitude;
    private Double latitude;
    private Double speed;
    private String plateNumber;
    private String nearestGarage; // For bonus task

    // Constructors
    public BusDTO() {
    }

    public BusDTO(Long id, String operator, String garage, String doorNumber,
                  LocalDateTime time, Double longitude, Double latitude,
                  Double speed, String plateNumber) {
        this.id = id;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

    public String getNearestGarage() {
        return nearestGarage;
    }

    public void setNearestGarage(String nearestGarage) {
        this.nearestGarage = nearestGarage;
    }
}
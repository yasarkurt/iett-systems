package com.example.iett_system_backend.dto;

public class GarageDTO {

    private Long id;
    private String garageId;
    private String garageName;
    private String garageCode;
    private Double longitude;
    private Double latitude;

    // Constructors
    public GarageDTO() {
    }

    public GarageDTO(Long id, String garageId, String garageName, String garageCode,
                     Double longitude, Double latitude) {
        this.id = id;
        this.garageId = garageId;
        this.garageName = garageName;
        this.garageCode = garageCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
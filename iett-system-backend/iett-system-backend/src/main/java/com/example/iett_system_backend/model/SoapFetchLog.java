package com.example.iett_system_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "soap_fetch_logs")
public class SoapFetchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type")
    private String serviceType; // "BUS" or "GARAGE"

    @Column(name = "fetch_time")
    private LocalDateTime fetchTime;

    @Column(name = "status")
    private String status; // "SUCCESS" or "FAILED"

    @Column(name = "message", length = 1000)
    private String message;

    // Constructors
    public SoapFetchLog() {
    }

    public SoapFetchLog(String serviceType, String status, String message) {
        this.serviceType = serviceType;
        this.status = status;
        this.message = message;
        this.fetchTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(LocalDateTime fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
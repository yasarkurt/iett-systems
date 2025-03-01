package com.example.iett_system_backend.repository;

import com.example.iett_system_backend.model.SoapFetchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SoapFetchLogRepository extends JpaRepository<SoapFetchLog, Long> {

    @Query("SELECT s FROM SoapFetchLog s WHERE s.serviceType = :serviceType AND s.status = 'SUCCESS' " +
            "ORDER BY s.fetchTime DESC")
    Optional<SoapFetchLog> findLatestSuccessfulFetch(@Param("serviceType") String serviceType);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SoapFetchLog s " +
            "WHERE s.serviceType = :serviceType AND s.status = 'SUCCESS' " +
            "AND s.fetchTime > :timeThreshold")
    boolean hasRecentSuccessfulFetch(@Param("serviceType") String serviceType,
                                     @Param("timeThreshold") LocalDateTime timeThreshold);
}
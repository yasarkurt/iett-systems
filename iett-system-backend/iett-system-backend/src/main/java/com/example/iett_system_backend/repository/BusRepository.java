package com.example.iett_system_backend.repository;

import com.example.iett_system_backend.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByPlateNumberContainingIgnoreCase(String plateNumber);
    List<Bus> findByOperatorContainingIgnoreCase(String operator);
    List<Bus> findByGarageContainingIgnoreCase(String garage);
    List<Bus> findByDoorNumberContainingIgnoreCase(String doorNumber);

    @Query("SELECT b FROM Bus b WHERE " +
            "LOWER(b.plateNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.operator) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.garage) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.doorNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Bus> findBySearchTermContainingIgnoreCase(@Param("search") String search);
}
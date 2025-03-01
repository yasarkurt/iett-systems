package com.example.iett_system_backend.repository;

import com.example.iett_system_backend.model.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query("SELECT b FROM Bus b WHERE " +
            "(:operator IS NULL OR LOWER(b.operator) LIKE LOWER(CONCAT('%', :operator, '%'))) AND " +
            "(:garage IS NULL OR LOWER(b.garage) LIKE LOWER(CONCAT('%', :garage, '%'))) AND " +
            "(:doorNumber IS NULL OR LOWER(b.doorNumber) LIKE LOWER(CONCAT('%', :doorNumber, '%'))) AND " +
            "(:plateNumber IS NULL OR LOWER(b.plateNumber) LIKE LOWER(CONCAT('%', :plateNumber, '%')))")
    Page<Bus> findBusesWithFilters(
            @Param("operator") String operator,
            @Param("garage") String garage,
            @Param("doorNumber") String doorNumber,
            @Param("plateNumber") String plateNumber,
            Pageable pageable
    );

    @Query("SELECT b FROM Bus b WHERE " +
            "LOWER(b.operator) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.garage) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.doorNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.plateNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Bus> findBusesBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}
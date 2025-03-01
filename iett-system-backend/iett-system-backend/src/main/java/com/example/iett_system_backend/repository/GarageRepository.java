package com.example.iett_system_backend.repository;

import com.example.iett_system_backend.model.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

    Garage findByGarageId(String garageId);

    @Query("SELECT g FROM Garage g WHERE " +
            "(:garageId IS NULL OR LOWER(g.garageId) LIKE LOWER(CONCAT('%', :garageId, '%'))) AND " +
            "(:garageName IS NULL OR LOWER(g.garageName) LIKE LOWER(CONCAT('%', :garageName, '%'))) AND " +
            "(:garageCode IS NULL OR LOWER(g.garageCode) LIKE LOWER(CONCAT('%', :garageCode, '%')))")
    Page<Garage> findGaragesWithFilters(
            @Param("garageId") String garageId,
            @Param("garageName") String garageName,
            @Param("garageCode") String garageCode,
            Pageable pageable
    );

    @Query("SELECT g FROM Garage g WHERE " +
            "LOWER(g.garageId) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(g.garageName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(g.garageCode) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Garage> findGaragesBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}
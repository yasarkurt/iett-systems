package com.example.iett_system_backend.repository;

import com.example.iett_system_backend.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findByGarageIdContainingIgnoreCase(String garageId);
    List<Garage> findByGarageNameContainingIgnoreCase(String garageName);
    List<Garage> findByGarageCodeContainingIgnoreCase(String garageCode);

    @Query("SELECT g FROM Garage g WHERE " +
            "LOWER(g.garageId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(g.garageName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(g.garageCode) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Garage> findBySearchTermContainingIgnoreCase(@Param("search") String search);
}
package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpacesRepository extends JpaRepository<ParkingSpaces, Long> {
    Optional<ParkingSpaces> findByCode(String code);
}

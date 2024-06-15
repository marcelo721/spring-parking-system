package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpacesRepository extends JpaRepository<ParkingSpaces, Long> {
}

package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpaceClientRepository extends JpaRepository<ParkingSpaceClient, Long> {
    Optional<ParkingSpaceClient> findByReceiptAndCheckOutDateIsNull(String receipt);

    long countByClientCpfAndCheckOutDateIsNotNull(String cpf);
}

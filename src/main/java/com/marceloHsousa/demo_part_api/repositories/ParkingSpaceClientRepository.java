package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.repositories.projection.ParkingSpacesClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpaceClientRepository extends JpaRepository<ParkingSpaceClient, Long> {
    Optional<ParkingSpaceClient> findByReceiptAndCheckOutDateIsNull(String receipt);

    long countByClientCpfAndCheckOutDateIsNotNull(String cpf);

    Page<ParkingSpacesClientProjection> findAllByClientCpf(String cpf, Pageable pageable);

    Page<ParkingSpacesClientProjection> findAllByClientUserId(Long id, Pageable pageable);
}

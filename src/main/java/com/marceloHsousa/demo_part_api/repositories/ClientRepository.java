package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.repositories.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select  c from Client c")
    Page<ClientProjection> findAllClients(Pageable pageable);

    Client findByUserId(Long id);

    Optional<Client> findByCpf(String cpf);
}

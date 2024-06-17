package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.repositories.ParkingSpaceClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceClientService {

    ParkingSpaceClientRepository repository;

    @Transactional
    public ParkingSpaceClient insert (ParkingSpaceClient spaceClient){
        return repository.save(spaceClient);
    }
}

package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingSpaceClient spaceClient;
    private final ClientService clientService;
    private final ParkingSpacesService parkingSpacesService;
}

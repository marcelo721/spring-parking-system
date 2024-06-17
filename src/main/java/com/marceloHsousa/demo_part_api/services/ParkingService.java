package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import com.marceloHsousa.demo_part_api.entities.enums.ParkingSpaceStatus;
import com.marceloHsousa.demo_part_api.util.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingSpaceClientService parkingSpaceClientService;
    private final ClientService clientService;
    private final ParkingSpacesService parkingSpacesService;

    @Transactional
    public ParkingSpaceClient checkIn(ParkingSpaceClient spaceClient) {

        Client client = clientService.findByCpf(spaceClient.getClient().getCpf());
        spaceClient.setClient(client);

        ParkingSpaces parkingSpaces = parkingSpacesService.findFreeParkingSpot();
        parkingSpaces.setStatus(ParkingSpaceStatus.OCCUPED);
        spaceClient.setParkingSpaces(parkingSpaces);

        spaceClient.setCheckInDate(LocalDateTime.now());
        spaceClient.setReceipt(ParkingUtils.generateReceipt());

        return parkingSpaceClientService.insert(spaceClient);
    }
}

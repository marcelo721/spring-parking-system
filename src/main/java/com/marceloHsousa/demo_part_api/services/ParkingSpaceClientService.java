package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.repositories.ParkingSpaceClientRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceClientService {

    private final ParkingSpaceClientRepository repository;

    @Transactional
    public ParkingSpaceClient insert (ParkingSpaceClient spaceClient){
        return repository.save(spaceClient);
    }

    @Transactional(readOnly = true)
    public ParkingSpaceClient findByReceipt(String receipt) {

        return repository.findByReceiptAndCheckOutDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException("receipt  Not Found or the checkOut has already been carried out")
        );
    }

    @Transactional(readOnly = true)
    public long getNumberOfTimes(String cpf) {

        return repository.countByClientCpfAndcheckOutDateIsNotNull(cpf);
    }
}

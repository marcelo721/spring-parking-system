package com.marceloHsousa.demo_part_api.services;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import com.marceloHsousa.demo_part_api.repositories.ParkingSpacesRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.CodeUniqueViolationException;
import com.marceloHsousa.demo_part_api.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpacesService {

    private final ParkingSpacesRepository repository;

    @Transactional
    public ParkingSpaces insert(ParkingSpaces parkingSpaces){
        try {

            return repository.save(parkingSpaces);
        }catch (DataIntegrityViolationException e){
            throw new CodeUniqueViolationException(String.format("ParkingSpace is already registered '%s'", parkingSpaces.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpaces getByCode(String code){
        return repository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException("ParkingSpaces not found")
        );
    }
}

package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import com.marceloHsousa.demo_part_api.services.ParkingSpacesService;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.mapper.ParkingSpacesMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkingSpaces")
public class ParkingSpacesController {

    private final ParkingSpacesService service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> insert(@RequestBody @Valid ParkingSpacesDto dto){
        ParkingSpaces parkingSpaces = ParkingSpacesMapper.toParkingSpaces(dto);
        service.insert(parkingSpaces);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpaces.getCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpacesResponseDto> getByCode(@PathVariable String code){
        ParkingSpaces parkingSpaces = service.getByCode(code);

        return ResponseEntity.ok(ParkingSpacesMapper.toDto(parkingSpaces));
    }


}

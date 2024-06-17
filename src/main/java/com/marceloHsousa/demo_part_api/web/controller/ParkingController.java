package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.services.ParkingService;
import com.marceloHsousa.demo_part_api.web.dto.mapper.ParkingSpaceClientMapper;
import com.marceloHsousa.demo_part_api.web.dto.parking.ParkingCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.parking.ParkingResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/parkings")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService service;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid ParkingCreateDto dto){
        ParkingSpaceClient parkingSpaceClient = ParkingSpaceClientMapper.toParkingSpaceClient(dto);

        service.checkIn(parkingSpaceClient);

        ParkingResponseDto responseDto = ParkingSpaceClientMapper.toDto(parkingSpaceClient);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(parkingSpaceClient.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}

package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.services.ParkingService;
import com.marceloHsousa.demo_part_api.web.dto.clientDTO.ClientResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.ParkingSpaceClientMapper;
import com.marceloHsousa.demo_part_api.web.dto.parking.ParkingCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.parking.ParkingResponseDto;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
@Tag(name = "parkings" , description = "operations to record entry and exit of a vehicle from the parking lot," +
        " This request requires a token")
public class ParkingController {

    private final ParkingService service;

    @Operation(summary = "check-in operation", description = "resource for entering a vehicle into the parking lot",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL to access the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingResponseDto.class))),

                    @ApiResponse(responseCode = "422",
                            description = "resource not processed due to invalid input data",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This feature is not allowed for CLIENT user type",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404",
                            description = "POSSIBLE CAUSES : <br/>" +
                            "The customer's CPF is not registered in the system ; <br/>" +
                            "no free parking spaces were found;",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
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

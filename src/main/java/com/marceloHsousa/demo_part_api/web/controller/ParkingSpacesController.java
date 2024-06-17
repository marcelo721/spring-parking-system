package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaces;
import com.marceloHsousa.demo_part_api.services.ParkingSpacesService;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingSpaceDto.ParkingSpacesResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.ParkingSpacesMapper;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "ParkingSpaces", description = "resource to create a new ParkingSpaces,  This request requires a token")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkingSpaces")
public class ParkingSpacesController {

    private final ParkingSpacesService service;


    @Operation(summary = "create new Parking Spaces", description = "feature to insert a new Parking Spaces, This request requires a token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL created")),

                    @ApiResponse(responseCode = "409",
                            description = "Parking Spaces already registered in the system",
                            content =  @Content(mediaType ="application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422",
                            description = "resource not processed due to invalid input data",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This feature is not allowed for CLIENT user type",
                            content =  @Content(mediaType ="application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
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

    @Operation(summary = "Find Parking Spaces", description = "feature to find Parking Spaces by code, This request requires a token, restricted access to Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "resource found successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingSpacesResponseDto.class))),

                    @ApiResponse(responseCode = "404",
                            description = "resource Not Found",
                            content =  @Content(mediaType ="application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This feature is not allowed for CLIENT user type",
                            content =  @Content(mediaType ="application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpacesResponseDto> getByCode(@PathVariable String code){
        ParkingSpaces parkingSpaces = service.getByCode(code);

        return ResponseEntity.ok(ParkingSpacesMapper.toDto(parkingSpaces));
    }


}

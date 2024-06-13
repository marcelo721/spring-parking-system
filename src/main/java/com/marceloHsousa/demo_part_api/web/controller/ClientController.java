package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.Client;
import com.marceloHsousa.demo_part_api.jwt.JwtUserDetails;
import com.marceloHsousa.demo_part_api.services.ClientService;
import com.marceloHsousa.demo_part_api.services.UserService;
import com.marceloHsousa.demo_part_api.web.dto.clientDTO.ClientCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.clientDTO.ClientResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.ClientMapper;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clients", description = "this class contains all the resources to deal with Clients, This request requires a token")
@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final UserService userService;

    @Operation(summary = "create new Client", description = "feature to insert a new client, This request requires a token",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "resource created successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ClientResponseDto.class))),

                    @ApiResponse(responseCode = "409",
                            description = "Client already registered in the system",
                            content =  @Content(mediaType ="application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422",
                            description = "resource not processed due to invalid input data",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This feature is not allowed for ADMIN user type",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class)))
            }


    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> insert(@RequestBody @Valid ClientCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findById(userDetails.getId()));
        service.insert(client);

        return ResponseEntity.status(201).body(ClientMapper.toDto(client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> findClientById(@PathVariable Long id){

        Client client = service.findClientById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }
}

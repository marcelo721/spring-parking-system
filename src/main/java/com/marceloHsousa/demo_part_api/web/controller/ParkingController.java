package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.ParkingSpaceClient;
import com.marceloHsousa.demo_part_api.jwt.JwtUserDetails;
import com.marceloHsousa.demo_part_api.repositories.projection.ParkingSpacesClientProjection;
import com.marceloHsousa.demo_part_api.services.ClientService;
import com.marceloHsousa.demo_part_api.services.JasperService;
import com.marceloHsousa.demo_part_api.services.ParkingService;
import com.marceloHsousa.demo_part_api.services.ParkingSpaceClientService;
import com.marceloHsousa.demo_part_api.web.dto.PageableDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.PageableMappper;
import com.marceloHsousa.demo_part_api.web.dto.mapper.ParkingSpaceClientMapper;
import com.marceloHsousa.demo_part_api.web.dto.parkingDto.ParkingCreateDto;
import com.marceloHsousa.demo_part_api.web.dto.parkingDto.ParkingResponseDto;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
@Slf4j
@RestController
@RequestMapping("api/v1/parkings")
@RequiredArgsConstructor
@Tag(name = "parkings" , description = "operations to record entry and exit of a vehicle from the parking lot," +
        " This request requires a token")
public class ParkingController {

    private final ParkingService service;
    private final ParkingSpaceClientService spaceClientService;

    private final ClientService clientService;
    private final JasperService jasperService;


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




    @Operation(summary = "locate a parked vehicle", description = "resource to locate a parked vehicle",
            security = @SecurityRequirement(name = "security"),
            parameters = {
            @Parameter(in = PATH, name = "receipt", description = "number generated by check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "resource created successfully",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL to access the created resource"),
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ParkingResponseDto.class))),

                    @ApiResponse(responseCode = "404",
                            description = "Receipt Not Found",
                            content =  @Content(mediaType = "application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/check-in/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    public ResponseEntity<ParkingResponseDto> getByReceipt(@PathVariable String receipt){
        ParkingSpaceClient spaceClient = spaceClientService.findByReceipt(receipt);

        ParkingResponseDto dto = ParkingSpaceClientMapper.toDto(spaceClient);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/check-out/{receipt}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkOut(@PathVariable String receipt){
        ParkingSpaceClient spaceClient = service.checkOut(receipt);

        ParkingResponseDto dto = ParkingSpaceClientMapper.toDto(spaceClient);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Find customer parking records by CPF", description = "Locate the" +
            "Customer parking records by CPF. Request requires use of a Bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = PATH, name = "cpf", description = "CPF No. refers to the customer to be consulted",
                            required = true
                    ),
                    @Parameter(in = QUERY, name = "page", description = "Represents the returned page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))
                    ),
                    @Parameter(in = QUERY, name = "size", description ="Represents the total of elements per page" ,
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))
                    ),
                    @Parameter(in = QUERY, name = "sort", description = " Data -dated, ASC 'standard field.",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "checkOutDate,asc")),
                            hidden = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful located",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "403", description = "Appeal I do not allow the CLIENT profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PageableDto> getAllParkingByCpf(@PathVariable String cpf,
                                                           @PageableDefault(size = 5, sort = "checkInDate",
                                                                   direction = Sort.Direction.ASC)Pageable pageable){
        Page<ParkingSpacesClientProjection> projection = spaceClientService.findAllByClientCpf( cpf, pageable);

        PageableDto dto = PageableMappper.toDto(projection);

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Find customer parking records by Id", description = "Locate the" +
            "Customer parking records by Id. Request requires use of a Bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {

                    @Parameter(in = QUERY, name = "page", description = "Represents the returned page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))
                    ),
                    @Parameter(in = QUERY, name = "size", description ="Represents the total of elements per page" ,
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))
                    ),
                    @Parameter(in = QUERY, name = "sort", description = " Data -dated, ASC 'standard field.",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "checkOutDate,asc")),
                            hidden = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful located",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "403", description = "Appeal I do not allow the ADMIN profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<PageableDto> getAllParkingClient(@AuthenticationPrincipal JwtUserDetails user,
                                                           @PageableDefault(size = 5, sort = "checkInDate",
                                                                  direction = Sort.Direction.ASC)Pageable pageable){
        Page<ParkingSpacesClientProjection> projection = spaceClientService.findAllByUserId( user.getId(), pageable);
        PageableDto dto = PageableMappper.toDto(projection);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/relatorio")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> getRelatorio(HttpServletResponse response, @AuthenticationPrincipal JwtUserDetails user) throws IOException {
        String cpf = clientService.findUserByid(user.getId()).getCpf();
        jasperService.addParams("CPF", cpf);

        byte[] bytes = jasperService.gerarPdf();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=" + System.currentTimeMillis() + ".pdf");
        response.getOutputStream().write(bytes);

        return ResponseEntity.ok().build();
    }

}

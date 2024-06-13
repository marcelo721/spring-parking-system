package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.jwt.JwtToken;
import com.marceloHsousa.demo_part_api.jwt.JwtUserDetailsService;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserLonginDto;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserResponseDto;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Tag(name = "Authentication", description = "resource to proceed with API authentication")
public class ControllerAuthentication {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "authenticate to api",
            description = "authentication feature",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = " authentication created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),

                    @ApiResponse(responseCode = "400",
                            description = "Invalid credentials",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422",
                            description = "invalid  data",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<?> authenticated(@RequestBody @Valid UserLonginDto dto, HttpServletRequest request){

        log.info("login authentication process {}" , dto.getUsername());

        try {

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);

        } catch (AuthenticationException e){
            log.warn("bad credentials from username '{}'", dto.getUsername());
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, " Invalid credentials"));
    }
}

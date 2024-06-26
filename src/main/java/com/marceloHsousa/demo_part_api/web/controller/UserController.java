package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.services.UserService;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserDto;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserPasswordDto;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.UserMapper;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "users", description = "contains all operations related to resources for registering, editing and reading a user")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(
            summary = "create a new user",
            description = "Resource create a new user",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),

                    @ApiResponse(responseCode = "409",
                            description = "username and email already registered in the system",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422",
                            description = "resource not processed due to invalid input data",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@Valid @RequestBody UserDto userDto){

        User obj = service.insert(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(obj));
    }


    @Operation(
            summary = "find a user by id",
            security = @SecurityRequirement(name = "security"),
            description = "resource to find a user by id, this request requires a token, Access restricted to ADMIN/CLIENT",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "user found successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),

                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){

        User obj = service.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(obj));
    }

    @Operation(
            summary = "update password",
            security = @SecurityRequirement(name = "security"),
            description = "resource to update password, this request requires a token, Access restricted to ADMIN/CLIENT",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "password updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "400",
                            description = "password is wrong",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422",
                            description = "Invalid data",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto user){

        User obj = service.updatePassword(id,user.getCurrentPassword(), user.getNewPassword(), user.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "find all users",
            security = @SecurityRequirement(name = "security"),
            description = "resource to find all users, this request requires a token, Access restricted to CLIENT",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of all registered users",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<User> users = service.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

}

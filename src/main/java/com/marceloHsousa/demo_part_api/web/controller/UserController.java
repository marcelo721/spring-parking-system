package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.services.UserService;
import com.marceloHsousa.demo_part_api.web.dto.UserDto;
import com.marceloHsousa.demo_part_api.web.dto.UserPasswordDto;
import com.marceloHsousa.demo_part_api.web.dto.UserResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.UserMapper;
import com.marceloHsousa.demo_part_api.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            description = "feature create a new user",
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
            description = "resource to find a user by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "user found successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),

                    @ApiResponse(responseCode = "404",
                            description = "User not found",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){

        User obj = service.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(obj));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto user){

        User obj = service.updatePassword(id,user.getCurrentPassword(), user.getNewPassword(), user.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<User> users = service.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }
}

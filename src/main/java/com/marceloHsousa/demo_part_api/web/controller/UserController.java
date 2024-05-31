package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.services.UserService;
import com.marceloHsousa.demo_part_api.web.dto.UserDto;
import com.marceloHsousa.demo_part_api.web.dto.UserResponseDto;
import com.marceloHsousa.demo_part_api.web.dto.mapper.UserMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody UserDto userDto){

        User obj = service.insert(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){

        User obj = service.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(obj));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){

        User obj = service.updatePassword(id,user.getPassword());
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){

        List<User> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }
}

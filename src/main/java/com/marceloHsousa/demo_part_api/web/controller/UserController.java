package com.marceloHsousa.demo_part_api.web.controller;

import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.services.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user){

        User obj = service.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){

        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){

        User obj = service.updatePassword(id,user.getPassword());
        return ResponseEntity.ok().body(obj);
    }
}

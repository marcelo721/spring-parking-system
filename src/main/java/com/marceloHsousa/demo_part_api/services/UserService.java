package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}

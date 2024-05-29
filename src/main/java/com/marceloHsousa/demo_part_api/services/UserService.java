package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User insert(User user){
        return userRepository.save(user);
    }
}

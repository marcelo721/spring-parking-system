package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.ServiceUI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User insert(User user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id){

        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow((()-> new ResourcesNotFoundException(id)));
    }

    @Transactional
    public User updatePassword(Long id, String newPassword){
        User user = findById(id);
        user.setPassword(newPassword);

        return user;
    }
}

package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.ResourcesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public User updatePassword(Long id, String CurrentPassword, String newPassword, String confirmPassword){

        if (!newPassword.equals(confirmPassword)){
            throw new RuntimeException("new password and password confirmation are different");
        }

        User user = findById(id);

        if (!user.getPassword().equals(CurrentPassword)){

            throw new RuntimeException("YOUR CURRENT PASSWORD IS WRONG");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
       return userRepository.findAll();
    }
}

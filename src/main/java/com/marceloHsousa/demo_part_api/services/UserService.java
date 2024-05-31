package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.exceptions.UsernameUniqueViolationException;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.EntityNotFoundException;
import com.marceloHsousa.demo_part_api.services.exceptions.PasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

        try {
            return userRepository.save(user);

        }catch (DataIntegrityViolationException e){

            throw new UsernameUniqueViolationException(String.format("Username {%s} already registered ", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id){

        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow((()-> new EntityNotFoundException(id)));
    }

    @Transactional
    public User updatePassword(Long id, String CurrentPassword, String newPassword, String confirmPassword){

        if (!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("new password and password confirmation are different");
        }

        User user = findById(id);

        if (!user.getPassword().equals(CurrentPassword)){
            throw new PasswordInvalidException("YOUR CURRENT PASSWORD IS WRONG");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
       return userRepository.findAll();
    }
}

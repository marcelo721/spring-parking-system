package com.marceloHsousa.demo_part_api.services;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.entities.enums.Role;
import com.marceloHsousa.demo_part_api.services.exceptions.UsernameUniqueViolationException;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import com.marceloHsousa.demo_part_api.services.exceptions.EntityNotFoundException;
import com.marceloHsousa.demo_part_api.services.exceptions.PasswordInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow((()-> new EntityNotFoundException("Entity not found")));
    }

    @Transactional
    public User insert(User user){

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        if (!passwordEncoder.matches(CurrentPassword, user.getPassword())){
            throw new PasswordInvalidException("YOUR CURRENT PASSWORD IS WRONG");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
       return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role findRoleByUsername(String username) {
       return userRepository.findRoleByUsername(username);
    }
}

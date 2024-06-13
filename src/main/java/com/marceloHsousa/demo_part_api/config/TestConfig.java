package com.marceloHsousa.demo_part_api.config;


import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import com.marceloHsousa.demo_part_api.web.controller.UserController;
import com.marceloHsousa.demo_part_api.web.dto.userDTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;



    @Override
    public void run(String... args) throws Exception {

        //userRepository.deleteAll();

        List<UserDto> userDtos = new ArrayList<>();

        userDtos.add(new UserDto("lucas@gmail.com", "123456"));
        userDtos.add(new UserDto("JOAO@gmail.com", "123456"));
        userDtos.add(new UserDto("DAVI@gmail.com", "123456"));
        userDtos.add(new UserDto("CLEBER@gmail.com", "123456"));
        userDtos.add(new UserDto("PEDRO@gmail.com", "123456"));

        //userController.insert(userDtos.get(0));
        //userController.insert(userDtos.get(1));
        //userController.insert(userDtos.get(2));
        //userController.insert(userDtos.get(3));
        //userController.insert(userDtos.get(4));

        System.out.println("done !");
    }
}

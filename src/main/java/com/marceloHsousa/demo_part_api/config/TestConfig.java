package com.marceloHsousa.demo_part_api.config;


import com.marceloHsousa.demo_part_api.entities.User;
import com.marceloHsousa.demo_part_api.entities.enums.Role;
import com.marceloHsousa.demo_part_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

    }
}

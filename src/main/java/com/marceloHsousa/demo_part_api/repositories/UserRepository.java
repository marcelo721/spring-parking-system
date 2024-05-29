package com.marceloHsousa.demo_part_api.repositories;

import com.marceloHsousa.demo_part_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

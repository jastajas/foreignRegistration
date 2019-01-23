package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

}

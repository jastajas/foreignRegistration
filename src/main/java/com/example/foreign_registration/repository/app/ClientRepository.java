package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

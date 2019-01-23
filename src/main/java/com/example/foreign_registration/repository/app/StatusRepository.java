package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {

    public List<Status> getByCategoryIs(String category);
}

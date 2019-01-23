package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}

package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findByUnit(String unit);

}

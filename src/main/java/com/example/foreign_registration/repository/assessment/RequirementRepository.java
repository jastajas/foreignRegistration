package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.assessment.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}

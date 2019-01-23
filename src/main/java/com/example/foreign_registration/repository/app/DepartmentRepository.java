package com.example.foreign_registration.repository.app;

import com.example.foreign_registration.model.app.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

package com.example.foreign_registration.repository.app;

import java.util.List;

import com.example.foreign_registration.model.app.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	List<UserRole> findAllByUsername(String username);
	
		
}
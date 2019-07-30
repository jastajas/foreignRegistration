package com.example.foreign_registration.repository.process;

import com.example.foreign_registration.model.process.TaskProgressRegister;
import com.example.foreign_registration.model.process.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TaskProgressRegisterRepository extends JpaRepository<TaskProgressRegister, Long> {

    Optional<TaskProgressRegister> getByProcessAndRegisterDate(Process process, Date registerDate);

}

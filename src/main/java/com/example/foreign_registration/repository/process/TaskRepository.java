package com.example.foreign_registration.repository.process;

import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.model.process.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProcess(Process process);

    @Query("SELECT COUNT(t) FROM Task t LEFT JOIN t.process p LEFT JOIN t.status s WHERE p.id = :processId AND s.name = :statusName")
    public Long getCountTasksForAssessment(@Param("processId") long processId, @Param("statusName") String statusName);

    @Query("SELECT MAX(t.id) FROM Task t")
    public Long getMaxId();

}

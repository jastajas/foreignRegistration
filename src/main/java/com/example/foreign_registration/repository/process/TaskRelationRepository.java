package com.example.foreign_registration.repository.process;

import com.example.foreign_registration.model.process.Task;
import com.example.foreign_registration.model.process.TaskRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRelationRepository extends JpaRepository<TaskRelation, Long> {

    @Query("SELECT tr FROM TaskRelation tr WHERE tr.mainTask IN(:tasks) or tr.relatedTask IN(:tasks)")
    List<TaskRelation> getAllRelationsByTasks(@Param("tasks") List<Task> tasks);

    @Query("SELECT tr FROM TaskRelation tr WHERE tr.mainTask = :task")
    List<TaskRelation> getAllRelationsByMainTask(@Param("task") Task task);

    @Query("SELECT tr FROM TaskRelation tr WHERE tr.mainTask = :task OR tr.relatedTask =:task")
    List<TaskRelation> getAllRelationsByTask(@Param("task") Task task);

}

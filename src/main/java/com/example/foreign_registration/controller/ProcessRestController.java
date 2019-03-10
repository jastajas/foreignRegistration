package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.Client;
import com.example.foreign_registration.model.app.Status;
import com.example.foreign_registration.model.app.User;
import com.example.foreign_registration.model.process.*;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.app.ClientRepository;
import com.example.foreign_registration.repository.app.StatusRepository;
import com.example.foreign_registration.repository.app.UserRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.repository.process.TaskRelationRepository;
import com.example.foreign_registration.repository.process.TaskRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class ProcessRestController {

    private ProcessRepository processRepository;
    private ClientRepository clientRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private TaskRelationRepository trRepository;

    @Autowired
    public ProcessRestController(ProcessRepository processRepository, ClientRepository clientRepository, TaskRepository taskRepository,
                                 UserRepository userRepository, StatusRepository statusRepository, TaskRelationRepository trRepository) {
        this.processRepository = processRepository;
        this.clientRepository = clientRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.trRepository = trRepository;
    }

    @PutMapping("/api/process/{id}/{paramName}/{paramValue}")
    public ResponseEntity<Process> changeProcessDetail(@PathVariable Long id, @PathVariable String paramName, @PathVariable String paramValue) {


        Optional<Process> processOptional = processRepository.findById(id);


        if (processOptional.get() == null || !processOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        switch (paramName) {
            case "clientID":
                Optional<Client> client = clientRepository.findById(Long.valueOf(paramValue));
                if (client.get() != null && client.isPresent()) {
                    processOptional.get().setClient(client.get());
                    processRepository.save(processOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
                break;
            case "clientProductName":
                processOptional.get().setDestined_product_name(paramValue);
                processRepository.save(processOptional.get());
                break;
            case "cooperationName":
                processOptional.get().setModel_cooperation(ModelCooperation.valueOf(paramValue));
                processRepository.save(processOptional.get());
                break;
            default:
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(processOptional.get());
    }

    @PostMapping("/api/process/saveTask")
    public ResponseEntity<Task> saveKanbanTask(@RequestBody Task task, Principal principal) {

        Optional<Status> statusOptional = null;
        Optional<User> userOptional = null;
        Optional<Process> processOptional = null;
        Optional<User> orderingPerson = userRepository.findByUsername(principal.getName());

        if (task.getStatus() == null || task.getTaskOwner() == null || task.getProcess() == null) {
            return ResponseEntity.notFound().build();
        }

        statusOptional = statusRepository.findById(task.getStatus().getId());
        userOptional = userRepository.findById(task.getTaskOwner().getId());
        processOptional = processRepository.findById(task.getProcess().getId());

        if (statusOptional.isPresent() && userOptional.isPresent() && processOptional.isPresent() && orderingPerson.isPresent()) {
            task.setProcess(processOptional.get());
            task.setStatus(statusOptional.get());
            task.setTaskOwner(userOptional.get());
            task.setOrderingPerson(orderingPerson.get());
        }

        DateGenerator dateGenerator = new DateGenerator();
        task.setOrderingDate(dateGenerator.getCurrentDate());

        taskRepository.save(task);

        Optional<Task> optionalTask = taskRepository.findById(taskRepository.getMaxId());

        if (!optionalTask.isPresent()) {
            ResponseEntity.notFound().build();
        }

        if (null != task.getRelatedTaskRelations()) {
            for (TaskRelation relatedTaskRelation : task.getRelatedTaskRelations()) {
                Optional<Task> relatedTaskOptional = taskRepository.findById(relatedTaskRelation.getRelatedTask().getId());
                if (!relatedTaskOptional.isPresent()) {
                    continue;
                }
                trRepository.save(new TaskRelation(optionalTask.get(), relatedTaskOptional.get(), relatedTaskRelation.getRelationType()));
            }
        }
        return ResponseEntity.ok(optionalTask.get());
    }


    @GetMapping("api/process/getRelationByTask/{id}")
    public ResponseEntity<List<TaskRelation>> getRelationsTaskByTask(@PathVariable Long id) {

        Optional<Task> taskOptional = taskRepository.findById(id);

        if (!taskOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<TaskRelation> taskRelationList = trRepository.getAllRelationsByMainTask(taskOptional.get());

        if (taskRelationList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskRelationList);
    }

    @PutMapping("api/process/changeTaskStatus")
    public ResponseEntity<Task> changeTaskStatus(@RequestParam Long id, @RequestParam Long statusId) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<Status> optionalStatus = statusRepository.findById(statusId);

        if (!optionalTask.isPresent() || !optionalStatus.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        optionalTask.get().setStatus(optionalStatus.get());

        taskRepository.save(optionalTask.get());

        return ResponseEntity.ok(optionalTask.get());
    }

    @DeleteMapping("api/process/deleteTask")
    public void deleteTask(@RequestParam Long id) {

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            List<TaskRelation> taskRelationList = trRepository.getAllRelationsByTask(optionalTask.get());
            if (!taskRelationList.isEmpty()) {
                for (TaskRelation relation : taskRelationList) {
                    trRepository.delete(relation);
                }
            }
            taskRepository.delete(optionalTask.get());
        }
    }

    @PutMapping("api/process/updateTask")
    public ResponseEntity<Task> updateTaskDetails(@RequestBody Task task) {

        if (task.getId() == 0 || task.getTaskOwner() == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        Optional<User> reponsiblePersonOptional = userRepository.findById(task.getTaskOwner().getId());

        if (!optionalTask.isPresent() || !reponsiblePersonOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<TaskRelation> taskRelationList = trRepository.getAllRelationsByTask(optionalTask.get());
        try {
            if (taskRelationList.size() > 0 && task.getRelatedTaskRelations() != null) {
                int maxIterator = taskRelationList.size() >= task.getRelatedTaskRelations().size() ? taskRelationList.size() : task.getRelatedTaskRelations().size();
                int minIterator = taskRelationList.size() >= task.getRelatedTaskRelations().size() ? task.getRelatedTaskRelations().size() : taskRelationList.size();

                for (int i = 0; i < maxIterator; i++) {
                    if (i < minIterator) {
                        TaskRelation taskRelation = taskRelationList.get(i);
                        taskRelation.setMainTask(optionalTask.get());

                        Optional<Task> relatedTaskOpt = taskRepository.findById(task.getRelatedTaskRelations().get(i).getRelatedTask().getId());
                        if (!relatedTaskOpt.isPresent()) {
                            continue;
                        }
                        taskRelation.setRelatedTask(relatedTaskOpt.get());
                        taskRelation.setRelationType(task.getRelatedTaskRelations().get(i).getRelationType());

                        trRepository.save(taskRelation);
                    } else if (taskRelationList.size() > task.getRelatedTaskRelations().size()) {
                        trRepository.delete(taskRelationList.get(i));
                    } else if (task.getRelatedTaskRelations().size() > taskRelationList.size()) {
                        Optional<Task> optionalRelatedTask = taskRepository.findById(task.getRelatedTaskRelations().get(i).getRelatedTask().getId());
                        if (!optionalRelatedTask.isPresent()) {
                            continue;
                        }
                        trRepository.save(new TaskRelation(optionalTask.get(), optionalRelatedTask.get(), task.getRelatedTaskRelations().get(i).getRelationType()));
                    }
                }
            } else if (!taskRelationList.isEmpty() && task.getRelatedTaskRelations() == null) {
                taskRelationList.stream().forEach(taskRelation -> trRepository.delete(taskRelation));
            } else if (taskRelationList.isEmpty() && task.getRelatedTaskRelations() != null) {
                for (int i = 0; i < task.getRelatedTaskRelations().size(); i++) {
                    if (null == task.getRelatedTaskRelations().get(i).getRelatedTask()) {
                        continue;
                    }
                    Optional<Task> relatedTaskOptional = taskRepository.findById(task.getRelatedTaskRelations().get(i).getRelatedTask().getId());

                    if (relatedTaskOptional.isPresent() && null != task.getRelatedTaskRelations().get(i).getRelationType()) {
                        trRepository.save(new TaskRelation(optionalTask.get(), relatedTaskOptional.get(), task.getRelatedTaskRelations().get(i).getRelationType()));
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("JS buider task exception.");
        }
        optionalTask.get().setName(task.getName());
        optionalTask.get().setDescription(task.getDescription());
        optionalTask.get().setDeadline(task.getDeadline());
        optionalTask.get().setTaskOwner(reponsiblePersonOptional.get());
        optionalTask.get().setManEffort(task.getManEffort());

        taskRepository.save(optionalTask.get());

        return ResponseEntity.ok(optionalTask.get());
    }

}
package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.process.*;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.app.ClientRepository;
import com.example.foreign_registration.repository.app.StatusRepository;
import com.example.foreign_registration.repository.app.UserRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.assessment.DepartmentAssessmentRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.repository.process.TaskRelationRepository;
import com.example.foreign_registration.repository.process.TaskRepository;
import com.example.foreign_registration.tools.calculation.MhCostCalculator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ProcessController {

    private ProcessRepository processRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private ClientRepository clientRepository;
    private TaskRepository taskRepository;
    private TaskRelationRepository trRepository;
    private AssessmentRepository assessmentRepository;
    private MhCostCalculator mhCostCalculator;

    @Autowired
    public ProcessController(ProcessRepository processRepository, UserRepository userRepository, StatusRepository statusRepository,
                             ClientRepository clientRepository, TaskRepository taskRepository, TaskRelationRepository trRepository,
                             AssessmentRepository assessmentRepository, MhCostCalculator mhCostCalculator) {
        this.processRepository = processRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.clientRepository = clientRepository;
        this.taskRepository = taskRepository;
        this.trRepository = trRepository;
        this.assessmentRepository = assessmentRepository;
        this.mhCostCalculator = mhCostCalculator;
    }

    @GetMapping("/processList")
    public String showProcessList(Model model, HttpServletRequest request) {

        List<Process> processList = processRepository.findAll();

        model.addAttribute("order", processList);

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        return "ordersList";
    }

    @GetMapping("/processDetails")
    public String showProcessDetails(Model model, HttpServletRequest request, @RequestParam Long processId) {

        Optional<Process> optionalProcess = processRepository.findById(processId);
        List<User> userList = userRepository.findAll();
        List<Status> statusList = statusRepository.getByCategoryIs("process");
        List<Client> clientList = clientRepository.findAll();

        if (!optionalProcess.isPresent() || userList.isEmpty() || statusList.isEmpty() || clientList.isEmpty()) {
            return "errorPage";
        }

        List<Task> taskList = taskRepository.findByProcess(optionalProcess.get());
        List<Assessment> assessments = assessmentRepository.getAllBySelectedProcess(optionalProcess.get());

        List<TaskRelation> taskRelationList = null;
        if (!taskList.isEmpty()) {
            taskRelationList = trRepository.getAllRelationsByTasks(taskList);
        } else {
            taskRelationList = new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        String tasksJson = null;
        String taskRelationsJson = null;
        try {
            tasksJson = mapper.writeValueAsString(taskList);
            taskRelationsJson = mapper.writeValueAsString(taskRelationList);
        } catch (JsonProcessingException e) {
            return "errorPage";
        }

        model.addAttribute("tasksJSON", tasksJson);
        model.addAttribute("taskRelationsJSON", taskRelationsJson);
        model.addAttribute("oneProcess", optionalProcess.get());
        model.addAttribute("employees", userList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("clientList", clientList);
        model.addAttribute("businessModels", ModelCooperation.values());
        model.addAttribute("taskList", taskList);
        model.addAttribute("countToDoTasks", taskRepository.getCountTasksForAssessment(processId, "ToDo"));
        model.addAttribute("countInprogressTasks", taskRepository.getCountTasksForAssessment(processId, "In progress"));
        model.addAttribute("countDoneTasks", taskRepository.getCountTasksForAssessment(processId, "Done"));
        model.addAttribute("relationTypes", RelationType.values());
        model.addAttribute("assessmentList", assessments);
        model.addAttribute("mhCostCalculator", mhCostCalculator);

        model.addAttribute("isRoleHZ", request.isUserInRole("ROLE_HZ"));

        return "orderDetails";
    }


}

package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.process.*;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.app.ClientRepository;
import com.example.foreign_registration.repository.app.StatusRepository;
import com.example.foreign_registration.repository.app.UserRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.process.ProcessDAO;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.repository.process.TaskRelationRepository;
import com.example.foreign_registration.repository.process.TaskRepository;
import com.example.foreign_registration.tools.calculation.MhCostCalculator;
import com.example.foreign_registration.tools.general.DynamicSqlSyntaxBuilder;
import com.example.foreign_registration.tools.general.FilterManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("processFilterParameter")
public class ProcessController {

    private ProcessRepository processRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private ClientRepository clientRepository;
    private TaskRepository taskRepository;
    private TaskRelationRepository trRepository;
    private AssessmentRepository assessmentRepository;
    private MhCostCalculator mhCostCalculator;
    private FilterManager filterManager;
    private ProcessDAO processDAO;
    private DynamicSqlSyntaxBuilder dynamicSqlSyntaxBuilder;

    @Autowired
    public ProcessController(ProcessRepository processRepository, UserRepository userRepository, StatusRepository statusRepository,
                             ClientRepository clientRepository, TaskRepository taskRepository, TaskRelationRepository trRepository,
                             AssessmentRepository assessmentRepository, MhCostCalculator mhCostCalculator, FilterManager filterManager,
                             ProcessDAO processDAO, DynamicSqlSyntaxBuilder dynamicSqlSyntaxBuilder) {
        this.processRepository = processRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.clientRepository = clientRepository;
        this.taskRepository = taskRepository;
        this.trRepository = trRepository;
        this.assessmentRepository = assessmentRepository;
        this.mhCostCalculator = mhCostCalculator;
        this.filterManager = filterManager;
        this.processDAO = processDAO;
        this.dynamicSqlSyntaxBuilder = dynamicSqlSyntaxBuilder;
    }

    @ModelAttribute("processFilterParameter")
    public FilterParameter setFilterParameter() {
        return new FilterParameter();
    }

    @GetMapping("processList")
    public String showProcessList(@ModelAttribute("processFilterParameter") FilterParameter processFilterParameter, Model model, HttpServletRequest request) {

        List<Process> processList = null;
        if (processFilterParameter.getFiltersList().isEmpty() || processFilterParameter.getFiltersList() == null) {
            processList = processRepository.findAll();
        } else if (processFilterParameter.getFiltersList().containsKey("byKeyword")) {
            processList = processRepository.getAllByKeyWord(processFilterParameter.getFiltersList().get("byKeyword"));
        } else if (filterManager.isOtherThenKeyWord(processFilterParameter.getFiltersList())) {
            processList = processDAO.getFilteredProcess(dynamicSqlSyntaxBuilder.buildFilterQuery(processFilterParameter.getFiltersList(), AppObjectType.ORDER));
        }

        List<Status> statusList = statusRepository.getByCategoryIs("process");


        if (statusList.isEmpty()){
            return "errorPage";
        }

        model.addAttribute("order", processList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("modelCoopList", ModelCooperation.values());
        model.addAttribute("filterList", processFilterParameter.getFiltersList());

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        return "ordersList";
    }
    @GetMapping("processList/addFilter")
    public String removeFilterParam(@ModelAttribute("processFilterParameter") FilterParameter processFilterParameter,
                                    @RequestParam(required = false) String byKeyword,
                                    @RequestParam(required = false) String byClient,
                                    @RequestParam(required = false) String byProductHL,
                                    @RequestParam(required = false) String byProductClient,
                                    @RequestParam(required = false) String byOrder,
                                    @RequestParam(required = false) String byOrderDateStart,
                                    @RequestParam(required = false) String byOrderDateEnd,
                                    @RequestParam(required = false) String byCooperationModel,
                                    @RequestParam(required = false) String byAssessment,
                                    @RequestParam(defaultValue = "0") long byStatus
    ) {


        String paramName = "";
        String paramValue = "";
        if (byKeyword != null && !"".equals(byKeyword)) {
            processFilterParameter.addKeyWord(byKeyword);
            return "redirect:/processList";
        } else if (byClient != null && !"".equals(byClient)) {
            paramName = "byClient";
            paramValue = byClient;
        } else if (byProductHL != null && !"".equals(byProductHL)) {
            paramName = "byProductHL";
            paramValue = byProductHL;
        } else if (byProductClient != null && !"".equals(byProductClient)) {
            paramName = "byProductClient";
            paramValue = byProductClient;
        } else if (byOrder != null && !"".equals(byOrder)) {
            paramName = "byOrder";
            paramValue = byOrder;
        } else if (byAssessment != null && !"".equals(byAssessment)) {
            paramName = "byAssessment";
            paramValue = byAssessment;
        } else if (byOrderDateStart != null && !"".equals(byOrderDateStart)) {
            paramName = "byOrderDateStart";
            paramValue = byOrderDateStart;
        } else if (byCooperationModel != null && !"".equals(byCooperationModel)) {
            paramName = "byCooperationModel";
            paramValue = byCooperationModel;
        } else if (byStatus != 0) {
            paramName = "byStatus";
            paramValue = String.valueOf(byStatus);
        }

        if ((byKeyword == null || "".equals(byKeyword)) && !"".equals(paramName) && !"".equals(paramValue)) {
            processFilterParameter.addFilterParameter(paramName, paramValue);
            paramName = "";
            paramValue = "";
        }

        if (byOrderDateEnd != null && !"".equals(byOrderDateEnd)) {
            paramName = "byOrderDateEnd";
            paramValue = byOrderDateEnd;
        }
        if ((byKeyword == null || "".equals(byKeyword)) && !"".equals(paramName) && !"".equals(paramValue)) {
            processFilterParameter.addFilterParameter(paramName, paramValue);
        }

        return "redirect:/processList";
    }

    @GetMapping("processList/removeFilter")
    public String removeFilterParam(@ModelAttribute("processFilterParameter") FilterParameter processFilterParameter, @RequestParam() String filterName) {

        if ("".equals(filterName) || filterName == null) {
            return "redirect: /processList";
        } else if (!"all".equals(filterName) && processFilterParameter.getFiltersList().containsKey(filterName)) {
            processFilterParameter.removeFilterParameters(false, filterName);
        } else {
            processFilterParameter.removeFilterParameters(true, null);
        }
        return "redirect:/processList";
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

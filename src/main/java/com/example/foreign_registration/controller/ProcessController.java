package com.example.foreign_registration.controller;

import com.example.foreign_registration.repository.process.ProcessRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.foreign_registration.model.process.Process;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Controller
public class ProcessController {

     private ProcessRepository processRepository;

    public ProcessController(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @GetMapping("/processList")
    public String showProcessList(Model model, HttpServletRequest request){

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
    public String showProcessDetails(Model model, HttpServletRequest request, @RequestParam Long processId){

        Optional<Process> optionalProcess = processRepository.findById(processId);

        if(!optionalProcess.isPresent()){
            return "errorPage";
        }
        model.addAttribute("oneProcess", optionalProcess.get());

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        return "orderDetails";
    }



}

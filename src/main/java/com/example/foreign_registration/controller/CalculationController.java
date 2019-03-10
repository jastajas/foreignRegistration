package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.process.ModelCooperation;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.calculation.CalculationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class CalculationController {

    private CalculationRepository calculationRepository;
    private AssessmentRepository ar;

    public CalculationController(CalculationRepository calculationRepository, AssessmentRepository ar) {
        this.calculationRepository = calculationRepository;
        this.ar = ar;
    }

    @GetMapping("calculations/calcDetails")
    public String showCalcDetails(@RequestParam Long idCalculation, Model model, HttpServletRequest request){

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        Optional<Calculation> calculationOptional = calculationRepository.findById(idCalculation);
        if(!calculationOptional.isPresent()){
            //todo error Page
        }
        model.addAttribute("calculation", calculationOptional.get());


        return "calculationDetails";
    }

    @GetMapping("calculations/newCalculation")
    public String createNewCalculation(@RequestParam Long idAssessment){

        Optional<Assessment> assessmentOptional = ar.findById(idAssessment);

        if(!assessmentOptional.isPresent()){
          //todo error page
        }

        Calculation calculation = new Calculation();

        calculation.setAssessment(assessmentOptional.get());



//        Calculation calculation = new Calculation((maxCurrentProcessNo.isPresent() && procR.getCountAllRows() != 0) ? maxCurrentProcessNo.get() : "00000",
//                prodR.findById(productID), destinedProdName, pqr.findById(productQualificationID), sr.findById((long) 1), cr.findById(clientID),
//                ur.findByUsername(principal.getName()), ModelCooperation.valueOf(cooperationModel), dateGenerator, procR);


        return "";
    }



}

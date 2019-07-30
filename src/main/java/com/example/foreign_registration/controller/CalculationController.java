package com.example.foreign_registration.controller;


import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;
import com.example.foreign_registration.model.calculation.CalculationForm;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import com.example.foreign_registration.repository.app.StatusRepository;
import com.example.foreign_registration.repository.app.UserRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.assessment.PackageSizeRepository;
import com.example.foreign_registration.repository.calculation.CalculationAssumptionsRepository;
import com.example.foreign_registration.repository.calculation.CalculationRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.tools.calculation.CalculationFactory;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.ObjectsBinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class CalculationController {

    private CalculationRepository calculationRepository;
    private AssessmentRepository ar;
    private PackageSizeRepository psr;
    private DateGenerator dateGenerator;
    private CalculationAssumptionsRepository car;
    private StatusRepository sr;
    private ProcessRepository pr;
    private CalculationFactory calculationFactory;
    private UserRepository userRepository;
    private ObjectsBinder objectsBinder;

    public CalculationController(CalculationRepository calculationRepository, AssessmentRepository ar, PackageSizeRepository psr,
                                 DateGenerator dateGenerator, CalculationAssumptionsRepository car, StatusRepository sr, ProcessRepository pr,
                                 CalculationFactory calculationFactory, UserRepository userRepository, ObjectsBinder objectsBinder) {
        this.calculationRepository = calculationRepository;
        this.ar = ar;
        this.psr = psr;
        this.dateGenerator = dateGenerator;
        this.car = car;
        this.sr = sr;
        this.pr = pr;
        this.calculationFactory = calculationFactory;
        this.userRepository = userRepository;
        this.objectsBinder = objectsBinder;
    }

    @GetMapping("/calculationList")
    public String showCalcList(Model model, HttpServletRequest request) {

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        model.addAttribute("calcList", calculationRepository.findAll());
        model.addAttribute("processList", pr.findAll());


        return "calculationList";
    }

    @GetMapping("calculations/calcDetails")
    public String showCalcDetails(@RequestParam Long id, Model model, HttpServletRequest request) {

        if (request.isUserInRole("ROLE_HZ")) {
            model.addAttribute("isRoleHZ", true);
        } else {
            model.addAttribute("isRoleHZ", false);
        }

        Optional<Calculation> calculationOptional = calculationRepository.findById(id);

        //List<CalculationAssumptions> calculationAssumptionsList = car.getCalcAssumtionsByCalculation(calculationOptional.get());




        model.addAttribute("statusList", sr.getByCategoryIs("calculation"));
        model.addAttribute("calculation", calculationOptional.get());
        model.addAttribute("newCalculationForm", new CalculationForm());
        //model.addAttribute("calculationAssumptions", calculationAssumptionsList.stream().filter(calculationAssumptions -> null == calculationAssumptions.getPackageSize()).collect(Collectors.toList()));

        return "calculationDetails";
    }

    @GetMapping("calculations/newCalculation")
    public String createNewCalculation(@RequestParam Long idAssessment, @RequestParam Long idProcess, @RequestParam List<Long> packageSizeIds, CalculationForm calculationForm, Principal principal) {

        calculationForm.setCreatorUsername(principal.getName());
        calculationForm.setPackSizesID(packageSizeIds);
        Calculation calculation = null;

        try {
            calculation = (Calculation) calculationFactory.create(calculationForm);
            objectsBinder.bindAssessAndProcessToCalculation(idAssessment, idProcess, calculation);
        } catch (OversizeNumberException e) {
            e.printStackTrace();
        } catch (BuildingAppObjectException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return "redirect:/calculations/calcDetails?id=" + calculation.getId();
    }
}

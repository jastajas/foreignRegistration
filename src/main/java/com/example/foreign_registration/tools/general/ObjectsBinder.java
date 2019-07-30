package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.calculation.CalculationRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ObjectsBinder {

    private ProcessRepository processRepo;
    private AssessmentRepository assessmentRepo;
    private CalculationRepository calculationRepo;

    @Autowired
    public ObjectsBinder(ProcessRepository processRepo, AssessmentRepository assessmentRepo, CalculationRepository calculationRepo) {
        this.processRepo = processRepo;
        this.assessmentRepo = assessmentRepo;
        this.calculationRepo = calculationRepo;
    }

    public static void createAssessmentProcessRelation(Process process, Assessment assessment, ProcessRepository processRepository, AssessmentRepository assessmentRepository) {

        List<Assessment> assessmentList = process.getAssessments();

        if (assessmentList == null || assessmentList.isEmpty()) {
            assessmentList = new ArrayList<>();
        }

        assessmentList.add(assessment);
        process.setAssessments(assessmentList);

        List<Process> processList = assessment.getProcesses();

        if (processList == null || processList.isEmpty()) {
            processList = new ArrayList<>();
        }

        processList.add(process);
        assessment.setProcesses(processList);

        processRepository.save(process);
        assessmentRepository.save(assessment);
    }

    public void bindAssessAndProcessToCalculation (long idAssessment, long idProcess, Calculation calculation) throws NoSuchElementException {

        Assessment assessment = assessmentRepo
                .findById(idAssessment)
                .orElseThrow(() -> new NoSuchElementException("Selected assessment didn't found"));

        Process process = processRepo
                .findById(idProcess)
                .orElseThrow(() -> new NoSuchElementException("Selected process didn't found"));

        Optional
                .ofNullable(calculation)
                .ifPresentOrElse(calc -> {
                  calc.setAssessment(assessment);
                  calc.setProcess(process);
                  calculationRepo.save(calc);
                }, () -> new NoSuchElementException("Calculation not provided"));

    }


}

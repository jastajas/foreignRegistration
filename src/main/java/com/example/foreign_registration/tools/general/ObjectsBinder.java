package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;


import java.util.ArrayList;
import java.util.List;

public class ObjectsBinder {

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

}

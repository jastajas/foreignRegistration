package com.example.foreign_registration.tools.assessment;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.AssessDepartConfirmation;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.AssessmentPattern;
import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import com.example.foreign_registration.repository.assessment.AssessDepartConfirmationRepository;
import com.example.foreign_registration.repository.assessment.AssessmentPatternRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.assessment.DepartmentAssessmentRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.ObjectNumberCreator;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

public class AssessmentFactory{

	private AssessmentRepository assessmentRepository;

	@Autowired
	public AssessmentFactory(AssessmentRepository assessmentRepository) {
		this.assessmentRepository = assessmentRepository;
	}

		public Optional<Assessment> createNewAssesssment(String maxCurrentNo, DateGenerator dateGenerator, Optional<Country> registration_country,
                                                         Optional<ProductStatus> destined_product_status, AvailabilityStatus availability_status,
                                                         Optional<ProductQualification> required_prod_qualification, Optional<User> ordering_person,
                                                         Optional<Status> status, String[] dossierTypes, AssessmentRepository assessmentRepository) {

        String assessNo = ObjectNumberCreator.createObjectNo(maxCurrentNo, dateGenerator.getCurrentYearText(), AppObject.ASSESSMENT);
        if (assessNo.substring(0,5).equals("99999")){
            return null;
        }
        Assessment createdNewAssess = null;

        if (registration_country.get() != null && registration_country.isPresent() && destined_product_status.get() != null && destined_product_status.isPresent() &&
                required_prod_qualification.get() != null && required_prod_qualification.isPresent() && ordering_person.get() != null && ordering_person.isPresent() &&
                status.get() != null && status.isPresent()) {
            createdNewAssess = new Assessment(assessNo, registration_country.get(), destined_product_status.get(), availability_status, required_prod_qualification.get(), ordering_person.get(), status.get(), dateGenerator.getCurrentDate());

        }

        if (createdNewAssess != null && dossierTypes != null && dossierTypes.length > 0) {
            for (String dossierType : dossierTypes) {

                switch (dossierType) {
                    case "sModule":
                        createdNewAssess.setS_module(true);
                        break;
                    case "quality":
                        createdNewAssess.setQuality_module(true);
                        break;
                    case "clinic":
                        createdNewAssess.setClinical_module(true);
                        break;
                    case "nonClinic":
                        createdNewAssess.setNonclinical_module(true);
                        break;
                }

            }
        }

        if (createdNewAssess != null) {
            assessmentRepository.save(createdNewAssess);
            return assessmentRepository.findByAssessmentNo(assessNo);
        }

        return null;
    }

	public void prepareElementsToAssessment(DepartmentAssessmentRepository departmentAssessmentRepository, Assessment assessment, AssessmentPatternRepository apr) {

		List<AssessmentPattern> assessmentPatterns = apr.findByProductStatus(assessment.getDestined_product_status());

		if (!assessmentPatterns.isEmpty()) {

			for (AssessmentPattern assessmentPattern : assessmentPatterns) {

				departmentAssessmentRepository.save(new DepartmentAssessment(assessmentPattern, assessment));

			}
		}
	}

	public void prepareConfirmationDepartmentList(AssessmentPatternRepository apr, AssessDepartConfirmationRepository adcr, ProductStatus productStatus, Assessment assessment) {
		List<Department> departments = apr.getAllDepartmentsByStatu(productStatus);
		for (Department department : departments) {
			adcr.save(new AssessDepartConfirmation(assessment, department, false));
		}
	}



}
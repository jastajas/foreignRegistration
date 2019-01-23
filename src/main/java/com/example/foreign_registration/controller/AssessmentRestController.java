package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.AvailabilityStatus;
import com.example.foreign_registration.model.app.Country;
import com.example.foreign_registration.model.app.ProductQualification;
import com.example.foreign_registration.model.app.ProductStatus;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.repository.app.CountryRepository;
import com.example.foreign_registration.repository.app.ProductQualificationRepository;
import com.example.foreign_registration.repository.app.ProductStatusRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class AssessmentRestController {

    private AssessmentRepository assessmentRepository;
    private CountryRepository countryRepository;
    private ProductStatusRepository productStatusRepository;
    private ProductQualificationRepository prodQualRepository;

    @Autowired
    public AssessmentRestController(AssessmentRepository assessmentRepository, CountryRepository countryRepository,
                                    ProductStatusRepository productStatusRepository, ProductQualificationRepository prodQualRepository) {
        this.assessmentRepository = assessmentRepository;
        this.countryRepository = countryRepository;
        this.productStatusRepository = productStatusRepository;
        this.prodQualRepository = prodQualRepository;
    }


    @PutMapping("/api/assessment/{id}/{paramName}/{paramValue}")
    public ResponseEntity<Assessment> changeAssessmentDetail(@PathVariable Long id, @PathVariable String paramName, @PathVariable String paramValue) {

        Optional<Assessment> assessmentOptional = assessmentRepository.findById(id);

        if (assessmentOptional.get() == null || !assessmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        switch (paramName) {
            case "countryID":
                Optional<Country> country = countryRepository.findById(Long.valueOf(paramValue));
                if (country.get() != null && country.isPresent()) {
                    assessmentOptional.get().setRegistration_country(country.get());
                    assessmentRepository.save(assessmentOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
                break;
            case "availabilityStatus":
                assessmentOptional.get().setAvailability_status(AvailabilityStatus.valueOf(paramValue));
                assessmentRepository.save(assessmentOptional.get());
                break;
            case "productStatusID":
                Optional<ProductStatus> productStatus = productStatusRepository.findById(Long.valueOf(paramValue));
                if (productStatus.get() != null && productStatus.isPresent()) {
                    assessmentOptional.get().setDestined_product_status(productStatus.get());
                    assessmentRepository.save(assessmentOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
                break;
            case "productQualificationID":
                Optional<ProductQualification> productQualification = prodQualRepository.findById(Long.valueOf(paramValue));
                if (productQualification.get() != null && productQualification.isPresent()) {
                    assessmentOptional.get().setRequired_prod_qualification(productQualification.get());
                    assessmentRepository.save(assessmentOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
                break;
                //Todo dodac asesment status change
            default:
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assessmentOptional.get());
    }


    @PutMapping("/api/assessment/ctdType/{id}")
    public ResponseEntity<Assessment> changeAssessmentDossier(@PathVariable Long id, @RequestBody List<String> paramValues) {

        Optional<Assessment> assessmentOptional = assessmentRepository.findById(id);

        if (!assessmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        assessmentOptional.get().setS_module(paramValues.contains("sModule"));
        assessmentOptional.get().setQuality_module(paramValues.contains("quality"));
        assessmentOptional.get().setClinical_module(paramValues.contains("clinic"));
        assessmentOptional.get().setNonclinical_module(paramValues.contains("nonClinic"));

        assessmentRepository.save(assessmentOptional.get());

        return ResponseEntity.ok(assessmentOptional.get());
    }

    @GetMapping("/api/assessment")
    public ResponseEntity<List<Assessment>> getAllValidAssessments() {

        //TODO add conditions for assessment filtering
        List<Assessment> assessmentList = assessmentRepository.findAll();

        if (assessmentList.size() == 0 || assessmentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assessmentList);
    }

}

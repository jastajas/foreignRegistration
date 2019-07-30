package com.example.foreign_registration.tools.assessment;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.*;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import com.example.foreign_registration.repository.app.*;
import com.example.foreign_registration.repository.assessment.AssessDepartConfirmationRepository;
import com.example.foreign_registration.repository.assessment.AssessmentPatternRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.assessment.DepartmentAssessmentRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.Factory;
import com.example.foreign_registration.tools.general.ObjectNumberCreator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;


@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AssessmentFactory extends Factory {

    private AssessmentRepository assessmentRepo;
    private CountryRepository countryRepo;
    private ProductQualificationRepository prodQualificationRepo;
    private ProductStatusRepository prodStatusRepo;
    private StatusRepository statusRepo;
    private UserRepository userRepo;
    private DepartmentAssessmentRepository departmentAssessmentRepo;
    private AssessmentPatternRepository assessmentPatternRepo;
    private AssessDepartConfirmationRepository assessDepartConfirmationRepo;


    public AssessmentFactory(DateGenerator dateGenerator, AssessmentRepository assessmentRepo, CountryRepository countryRepo,
                             ProductQualificationRepository prodQualificationRepo, ProductStatusRepository prodStatusRepo,
                             StatusRepository statusRepo, UserRepository userRepo, DepartmentAssessmentRepository departmentAssessmentRepo,
                             AssessmentPatternRepository assessmentPatternRepo, AssessDepartConfirmationRepository assessDepartConfirmationRepo) {
        super(dateGenerator);
        this.assessmentRepo = assessmentRepo;
        this.countryRepo = countryRepo;
        this.prodQualificationRepo = prodQualificationRepo;
        this.prodStatusRepo = prodStatusRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
        this.departmentAssessmentRepo = departmentAssessmentRepo;
        this.assessmentPatternRepo = assessmentPatternRepo;
        this.assessDepartConfirmationRepo = assessDepartConfirmationRepo;
    }


    @Override
    public String createNumber() throws OversizeNumberException {

        String maxCurrentAssessNumber = assessmentRepo
                .getMaxAssessmentNoForCurrentYear(getDateGenerator().getFistDateOfCurrentYear(), getDateGenerator().getCurrentDate())
                .orElse("00000")
                .substring(0, 5);

        return ObjectNumberCreator.createObjectNo(maxCurrentAssessNumber, getDateGenerator().getCurrentYearText(), AppObjectType.ASSESSMENT);
    }

    @Override
    public AppObject create(AppObjectForm appObjectForm) throws BuildingAppObjectException, OversizeNumberException {
        if (!(appObjectForm instanceof AssessmentForm)) {
            throw new BuildingAppObjectException("Assessment Form Exception");
        }
        AssessmentForm assessmentForm = (AssessmentForm) appObjectForm;
        if (!assessmentForm.isValid()) {
            throw new BuildingAppObjectException("Assessment Form Validation Error");
        }

        Assessment assessment = new Assessment();
        assessment.setNumber(createNumber());

        countryRepo
                .findById(assessmentForm.getIdCountry())
                .ifPresentOrElse(country -> assessment.setRegistration_country(country),
                        () -> new BuildingAppObjectException("Country not found"));

        prodStatusRepo
                .findById(assessmentForm.getIdDestinedProdStatus())
                .ifPresentOrElse(productStatus -> assessment.setDestined_product_status(productStatus),
                        () -> new BuildingAppObjectException("Product Status not found"));

        prodQualificationRepo
                .findById(assessmentForm.getIdRequiredProdQualif())
                .ifPresent(productQualification -> assessment.setRequired_prod_qualification(productQualification));

        userRepo
                .findByUsername(assessmentForm.getCreatorUsername())
                .ifPresentOrElse(creator -> assessment.setCreator(creator),
                        () -> new BuildingAppObjectException("User/Creator not found"));

        statusRepo
                .findById(Long.valueOf(7))
                .ifPresentOrElse(status -> assessment.setStatus(status), () -> new BuildingAppObjectException("Status not found"));

        assessment.setAvailability_status(AvailabilityStatus.valueOf(assessmentForm.getAvaliabilityStatus()));
        assessment.setS_module(assessmentForm.issModule());
        assessment.setClinical_module(assessmentForm.isQuality());
        assessment.setNonclinical_module(assessmentForm.isClinic());
        assessment.setQuality_module(assessmentForm.isNonClinic());
        assessment.setCreationDate(getDateGenerator().getCurrentDate());

        assessmentRepo.save(assessment);

        return assessmentRepo
                .findByNumber(assessment.getNumber())
                .orElseThrow(() -> new BuildingAppObjectException("Object assessment not found"));
    }


    public void prepareElementsToAssessment(Assessment assessment) {

        if (null != assessment) {
            assessmentPatternRepo
                    .findByProductStatus(assessment.getDestined_product_status())
                    .forEach(assessmentPattern -> departmentAssessmentRepo.save(new DepartmentAssessment(assessmentPattern, assessment)));

        }
    }

    public void prepareConfirmationDepartmentList(long destinedProdStatusId, Assessment assessment) throws BuildingAppObjectException {

        if (null != assessment) {

            ProductStatus destinedProductStatus = prodStatusRepo
                    .findById(destinedProdStatusId)
                    .orElseThrow(() -> new BuildingAppObjectException("Destined Product not found"));

            assessmentPatternRepo
                    .getAllDepartmentsByStatu(destinedProductStatus)
                    .forEach(department -> assessDepartConfirmationRepo.save(new AssessDepartConfirmation(assessment, department, false)));

        }
    }


}

package com.example.foreign_registration.tools.calculation;

import com.example.foreign_registration.model.app.*;

import com.example.foreign_registration.model.calculation.*;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import com.example.foreign_registration.repository.app.StatusRepository;
import com.example.foreign_registration.repository.app.UserRepository;
import com.example.foreign_registration.repository.assessment.AssessmentRepository;
import com.example.foreign_registration.repository.assessment.PackageSizeRepository;
import com.example.foreign_registration.repository.calculation.CalculationRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import com.example.foreign_registration.tools.general.DateGenerator;
import com.example.foreign_registration.tools.general.Factory;
import com.example.foreign_registration.tools.general.ObjectNumberCreator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CalculationFactory extends Factory {

    CalculationRepository calculationRepo;
    PackageSizeRepository packageSizeRepo;
    StatusRepository statusRepo;
    UserRepository userRepo;
    AssessmentRepository assessmentRepo;
    ProcessRepository processRepository;

    public CalculationFactory(CalculationRepository calculationRepo, PackageSizeRepository packageSizeRepo, StatusRepository statusRepo,
                              UserRepository userRepo, AssessmentRepository assessmentRepo, ProcessRepository processRepository) {
        this.calculationRepo = calculationRepo;
        this.packageSizeRepo = packageSizeRepo;
        this.statusRepo = statusRepo;
        this.userRepo = userRepo;
        this.assessmentRepo = assessmentRepo;
        this.processRepository = processRepository;
    }

    @Override
    public String createNumber() throws OversizeNumberException {
        String maxCurrentCalcNumber = calculationRepo
                .getMaxCalcNoForCurrentYear(getDateGenerator().getFistDateOfCurrentYear(), getDateGenerator().getCurrentDate())
                .orElse("00000")
                .substring(0, 5);

        return ObjectNumberCreator.createObjectNo(maxCurrentCalcNumber, getDateGenerator().getCurrentYearText(), AppObjectType.CALCULATION);
    }

    @Override
    public AppObject create(AppObjectForm appObjectForm) throws OversizeNumberException, BuildingAppObjectException {
        if (!(appObjectForm instanceof CalculationForm)) {
            throw new BuildingAppObjectException("Calculation Form Exception");
        }
        CalculationForm calcForm = (CalculationForm) appObjectForm;
        if (calcForm.isValid()) {
            throw new BuildingAppObjectException("Calculation Form Validation Error");
        }

        Calculation calculation = new Calculation();
        calculation.setNumber(createNumber());

        Optional.of(packageSizeRepo.getAllByIds(calcForm.getPackSizesID()))
                .filter(packageSizes -> !packageSizes.isEmpty())
                .orElseThrow(() -> new BuildingAppObjectException("Packages List not found"))
                .stream()
                .forEach(packageSize -> calculation.addItemToPackageSizes(packageSize));

        Optional.ofNullable(calcForm.getChargeType())
                .ifPresent(chargeType -> calculation.setChargeType(ChargeType.valueOf(chargeType)));

        Optional.ofNullable(calcForm.getChargeSubject())
                .ifPresent(chargeSubject -> calculation.setChargeSubject(ChargeSubject.valueOf(chargeSubject)));

        Optional.ofNullable(calcForm.getPeriodsNo())
                .filter(periodsNo->periodsNo<0)
                .ifPresentOrElse(periodsNo -> calculation.setPeriodsNo(periodsNo), () -> new BuildingAppObjectException("No periods number provided"));

        userRepo
                .findByUsername(calcForm.getCreatorUsername())
                .ifPresentOrElse(user -> calculation.setCreator(user), () -> new BuildingAppObjectException("User/Creator not found"));
        statusRepo
                .findById((long) 20)
                .ifPresentOrElse(status -> calculation.setStatus(status), () -> new BuildingAppObjectException("Status not found"));

        calculation.setRoyalitiesRequired(calcForm.isRoyalitiesRequired());
        calculation.setCreationDate(DateGenerator.getCurrentDate());

        return null;
    }






    /*public Optional<Calculation> createNewCalculation(DateGenerator dateGenerator, Assessment assessment, Status status, Process process, User calculatinPerson) {



        Calculation calculation = new Calculation(destinedCalcNo, dateGenerator.getCurrentDate(),
                assessment, status, process, calculatinPerson);
        calculationRepository.save(calculation);

        return calculationRepository.findByNumber(destinedCalcNo);
    }

    public void prepareCalcAssumptionsBaseForPack(CalculationAssumptionsRepository calcAssumptionsRepo, PackageSize packageSize, Calculation calculation) {

        for (int i = 0; i < AssumptionCalcType.values().length; i++) {
            if (AssumptionCalcType.values()[i] == AssumptionCalcType.MarginOnManHours || AssumptionCalcType.values()[i] == AssumptionCalcType.MarginOnRowMaterials ||
                    AssumptionCalcType.values()[i] == AssumptionCalcType.InitialOutlay || AssumptionCalcType.values()[i] == AssumptionCalcType.ExternalCost ||
                    AssumptionCalcType.values()[i] == AssumptionCalcType.ManEffort || AssumptionCalcType.values()[i] == AssumptionCalcType.DiscountRate) {
                continue;
            }
            calcAssumptionsRepo.save(new CalculationAssumptions(AssumptionCalcType.values()[i], calculation, packageSize));
        }
    }

    public void prepareCalcAssumptionsBase(CalculationAssumptionsRepository calcAssumptionsRepo, Calculation calculation) {
        for (int i = 0; i < AssumptionCalcType.values().length; i++) {
            if (AssumptionCalcType.values()[i] == AssumptionCalcType.MarginOnManHours || AssumptionCalcType.values()[i] == AssumptionCalcType.MarginOnRowMaterials ||
                    AssumptionCalcType.values()[i] == AssumptionCalcType.InitialOutlay || AssumptionCalcType.values()[i] == AssumptionCalcType.DiscountRate) {
                calcAssumptionsRepo.save(new CalculationAssumptions(AssumptionCalcType.values()[i], calculation));
            }

        }

    }*/
}

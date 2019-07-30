package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.AppObject;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.PackageSize;
import com.example.foreign_registration.model.process.Process;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Calculation extends AppObject {

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean royalitiesRequired;

    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @Enumerated(EnumType.STRING)
    private ChargeSubject chargeSubject;

    @ElementCollection
    private List<PackageSize> packageSizes;

    private int periodsNo;

    @ManyToOne
    private Assessment assessment;

    @ManyToOne
    private Process process;

    private Date calculationDate;

    @OneToMany(mappedBy = "calculation")
    private List<CalculationAssumptions> calculationAssumptions;

    public Calculation() {
    }

    public List<CalculationAssumptions> getCalculationAssumptions() {
        return calculationAssumptions;
    }

    public void setCalculationAssumptions(List<CalculationAssumptions> calculationAssumptions) {
        this.calculationAssumptions = calculationAssumptions;
    }

    public boolean isRoyalitiesRequired() {
        return royalitiesRequired;
    }

    public void setRoyalitiesRequired(boolean royalitiesRequired) {
        this.royalitiesRequired = royalitiesRequired;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public ChargeSubject getChargeSubject() {
        return chargeSubject;
    }

    public void setChargeSubject(ChargeSubject chargeSubject) {
        this.chargeSubject = chargeSubject;
    }

    public List<PackageSize> getPackageSizes() {
        return packageSizes;
    }

    public void setPackageSizes(List<PackageSize> packageSizes) {
        this.packageSizes = packageSizes;
    }

    public void addItemToPackageSizes(PackageSize packageSize){
        this.packageSizes.add(packageSize);
    }

    public int getPeriodsNo() {
        return periodsNo;
    }

    public void setPeriodsNo(int periodsNo) {
        this.periodsNo = periodsNo;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }
}

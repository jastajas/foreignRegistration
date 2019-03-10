package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.app.Department;
import com.example.foreign_registration.model.assessment.AssessmentCostMH;
import com.example.foreign_registration.model.assessment.PackageSize;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class CalculationAssumptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssumptionCalcType assumptionCalcType;

    private String subject;
    @ColumnDefault("0")
    private double singleNoValue;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    //@Column (columnDefinition="Decimal(10,0) default '0'")
    @ColumnDefault("0")
    private int mh;

    @ManyToOne
    private Department department;

    @ColumnDefault("0")
    private double firstYear;
    @ColumnDefault("0")
    private double secondYear;
    @ColumnDefault("0")
    private double thirdYear;
    @ColumnDefault("0")
    private double fourthYear;
    @ColumnDefault("0")
    private double fifthYear;

    @ManyToOne
    private AssessmentCostMH assessmentCostMh;

    @ManyToOne
    private Calculation calculation;

    @ManyToOne
    private PackageSize packageSize;

    public CalculationAssumptions() {
    }

    public CalculationAssumptions(AssumptionCalcType assumptionCalcType, String subject, double singleNoValue, Currency currency, int mh, Department department, double firstYear, double secondYear, double thirdYear, double fourthYear, double fifthYear, AssessmentCostMH assessmentCostMh, Calculation calculation, PackageSize packageSize) {
        this.assumptionCalcType = assumptionCalcType;
        this.subject = subject;
        this.singleNoValue = singleNoValue;
        this.currency = currency;
        this.mh = mh;
        this.department = department;
        this.firstYear = firstYear;
        this.secondYear = secondYear;
        this.thirdYear = thirdYear;
        this.fourthYear = fourthYear;
        this.fifthYear = fifthYear;
        this.assessmentCostMh = assessmentCostMh;
        this.calculation = calculation;
        this.packageSize = packageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssumptionCalcType getAssumptionCalcType() {
        return assumptionCalcType;
    }

    public void setAssumptionCalcType(AssumptionCalcType assumptionCalcType) {
        this.assumptionCalcType = assumptionCalcType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getSingleNoValue() {
        return singleNoValue;
    }

    public void setSingleNoValue(double singleNoValue) {
        this.singleNoValue = singleNoValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getMh() {
        return mh;
    }

    public void setMh(int mh) {
        this.mh = mh;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(double firstYear) {
        this.firstYear = firstYear;
    }

    public double getSecondYear() {
        return secondYear;
    }

    public void setSecondYear(double secondYear) {
        this.secondYear = secondYear;
    }

    public double getThirdYear() {
        return thirdYear;
    }

    public void setThirdYear(double thirdYear) {
        this.thirdYear = thirdYear;
    }

    public double getFourthYear() {
        return fourthYear;
    }

    public void setFourthYear(double fourthYear) {
        this.fourthYear = fourthYear;
    }

    public double getFifthYear() {
        return fifthYear;
    }

    public void setFifthYear(double fifthYear) {
        this.fifthYear = fifthYear;
    }

    public AssessmentCostMH getAssessmentCostMh() {
        return assessmentCostMh;
    }

    public void setAssessmentCostMh(AssessmentCostMH assessmentCostMh) {
        this.assessmentCostMh = assessmentCostMh;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public PackageSize getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(PackageSize packageSize) {
        this.packageSize = packageSize;
    }
}

package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.assessment.PackageSize;

import javax.persistence.*;
import java.util.List;

@Entity
public class CalculationAssumptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**  */
    @Enumerated(EnumType.STRING)
    private AssumptionCalcType assumptionCalcType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ElementCollection
    private List<Double> values;

    @ManyToOne
    private Calculation calculation;

    @ManyToOne
    private PackageSize packageSize;

    public CalculationAssumptions() {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
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

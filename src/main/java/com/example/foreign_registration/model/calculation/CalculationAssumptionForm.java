package com.example.foreign_registration.model.calculation;

import java.util.List;

public class CalculationAssumptionForm {

    private double discountRate;

    private List<Double> additionalInflows;
    private String inflowsCurrency;

    private List<Double> additionalOutflows;
    private String outflowsCurrency;

    private List<PackageCalcAssumption> packageCalcAssumption;

    public CalculationAssumptionForm() {
    }

    public CalculationAssumptionForm(double discountRate, List<Double> additionalInflows, String inflowsCurrency, List<Double> additionalOutflows, String outflowsCurrency, List<PackageCalcAssumption> packageCalcAssumption) {
        this.discountRate = discountRate;
        this.additionalInflows = additionalInflows;
        this.inflowsCurrency = inflowsCurrency;
        this.additionalOutflows = additionalOutflows;
        this.outflowsCurrency = outflowsCurrency;
        this.packageCalcAssumption = packageCalcAssumption;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public List<Double> getAdditionalInflows() {
        return additionalInflows;
    }

    public void setAdditionalInflows(List<Double> additionalInflows) {
        this.additionalInflows = additionalInflows;
    }

    public String getInflowsCurrency() {
        return inflowsCurrency;
    }

    public void setInflowsCurrency(String inflowsCurrency) {
        this.inflowsCurrency = inflowsCurrency;
    }

    public List<Double> getAdditionalOutflows() {
        return additionalOutflows;
    }

    public void setAdditionalOutflows(List<Double> additionalOutflows) {
        this.additionalOutflows = additionalOutflows;
    }

    public String getOutflowsCurrency() {
        return outflowsCurrency;
    }

    public void setOutflowsCurrency(String outflowsCurrency) {
        this.outflowsCurrency = outflowsCurrency;
    }

    public List<PackageCalcAssumption> getPackageCalcAssumption() {
        return packageCalcAssumption;
    }

    public void setPackageCalcAssumption(List<PackageCalcAssumption> packageCalcAssumption) {
        this.packageCalcAssumption = packageCalcAssumption;
    }
}

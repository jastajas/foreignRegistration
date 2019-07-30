package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;

import java.util.Arrays;

public class ProductCostForm {

    private double[] totalManufacturingCost;
    private Currency totalMCurrency;

    private double[] technManufacturingCost;
    private Currency technicalMCurrency;

    private double[] materialsCost;
    private Currency materialCostCurrency;

    private double[] effortCost;
    private Currency effortCostCurrency;

    public ProductCostForm() {
    }

    public ProductCostForm(double[] totalManufacturingCost, Currency totalMCurrency, double[] technManufacturingCost, Currency technicalMCurrency, double[] materialsCost, Currency materialCostCurrency, double[] effortCost, Currency effortCostCurrency) {
        this.totalManufacturingCost = totalManufacturingCost;
        this.totalMCurrency = totalMCurrency;
        this.technManufacturingCost = technManufacturingCost;
        this.technicalMCurrency = technicalMCurrency;
        this.materialsCost = materialsCost;
        this.materialCostCurrency = materialCostCurrency;
        this.effortCost = effortCost;
        this.effortCostCurrency = effortCostCurrency;
    }

    public double[] getTotalManufacturingCost() {
        return totalManufacturingCost;
    }

    public void setTotalManufacturingCost(double[] totalManufacturingCost) {
        this.totalManufacturingCost = totalManufacturingCost;
    }

    public Currency getTotalMCurrency() {
        return totalMCurrency;
    }

    public void setTotalMCurrency(Currency totalMCurrency) {
        this.totalMCurrency = totalMCurrency;
    }

    public double[] getTechnManufacturingCost() {
        return technManufacturingCost;
    }

    public void setTechnManufacturingCost(double[] technManufacturingCost) {
        this.technManufacturingCost = technManufacturingCost;
    }

    public Currency getTechnicalMCurrency() {
        return technicalMCurrency;
    }

    public void setTechnicalMCurrency(Currency technicalMCurrency) {
        this.technicalMCurrency = technicalMCurrency;
    }

    public double[] getMaterialsCost() {
        return materialsCost;
    }

    public void setMaterialsCost(double[] materialsCost) {
        this.materialsCost = materialsCost;
    }

    public Currency getMaterialCostCurrency() {
        return materialCostCurrency;
    }

    public void setMaterialCostCurrency(Currency materialCostCurrency) {
        this.materialCostCurrency = materialCostCurrency;
    }

    public double[] getEffortCost() {
        return effortCost;
    }

    public void setEffortCost(double[] effortCost) {
        this.effortCost = effortCost;
    }

    public Currency getEffortCostCurrency() {
        return effortCostCurrency;
    }

    public void setEffortCostCurrency(Currency effortCostCurrency) {
        this.effortCostCurrency = effortCostCurrency;
    }

    @Override
    public String toString() {
        return "ProductCostForm{" +
                "totalManufacturingCost=" + Arrays.toString(totalManufacturingCost) +
                ", totalMCurrency=" + totalMCurrency +
                ", technManufacturingCost=" + Arrays.toString(technManufacturingCost) +
                ", technicalMCurrency=" + technicalMCurrency +
                ", materialsCost=" + Arrays.toString(materialsCost) +
                ", materialCostCurrency=" + materialCostCurrency +
                ", effortCost=" + Arrays.toString(effortCost) +
                ", effortCostCurrency=" + effortCostCurrency +
                '}';
    }
}

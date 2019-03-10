package com.example.foreign_registration.model.calculation;

public class CalculationAssumptionForm {
    private long packSize;
    private int[] forecast;
    private double[] technicalManufacturingCost;
    private double[] totalManufacturingCost;
    private double[] salesPrice;
    private double[] inflow;
    private double[] outflow;
    private double initialOutlay;
    private double marginOnRowMaterials;
    private double marginOnManHours;
    private double discountRate;
    private long calculation;

    public CalculationAssumptionForm() {
    }

    public CalculationAssumptionForm(long packSize, int[] forecast, double[] technicalManufacturingCost, double[] totalManufacturingCost, double[] salesPrice, double[] inflow, double[] outflow, double initialOutlay, double marginOnRowMaterials, double marginOnManHours, double discountRate, long calculation) {
        this.packSize = packSize;
        this.forecast = forecast;
        this.technicalManufacturingCost = technicalManufacturingCost;
        this.totalManufacturingCost = totalManufacturingCost;
        this.salesPrice = salesPrice;
        this.inflow = inflow;
        this.outflow = outflow;
        this.initialOutlay = initialOutlay;
        this.marginOnRowMaterials = marginOnRowMaterials;
        this.marginOnManHours = marginOnManHours;
        this.discountRate = discountRate;
        this.calculation = calculation;
    }

    public long getPackSize() {
        return packSize;
    }

    public void setPackSize(long packSize) {
        this.packSize = packSize;
    }

    public int[] getForecast() {
        return forecast;
    }

    public void setForecast(int[] forecast) {
        this.forecast = forecast;
    }

    public double[] getTechnicalManufacturingCost() {
        return technicalManufacturingCost;
    }

    public void setTechnicalManufacturingCost(double[] technicalManufacturingCost) {
        this.technicalManufacturingCost = technicalManufacturingCost;
    }

    public double[] getTotalManufacturingCost() {
        return totalManufacturingCost;
    }

    public void setTotalManufacturingCost(double[] totalManufacturingCost) {
        this.totalManufacturingCost = totalManufacturingCost;
    }

    public double[] getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double[] salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double[] getInflow() {
        return inflow;
    }

    public void setInflow(double[] inflow) {
        this.inflow = inflow;
    }

    public double[] getOutflow() {
        return outflow;
    }

    public void setOutflow(double[] outflow) {
        this.outflow = outflow;
    }

    public double getInitialOutlay() {
        return initialOutlay;
    }

    public void setInitialOutlay(double initialOutlay) {
        this.initialOutlay = initialOutlay;
    }

    public double getMarginOnRowMaterials() {
        return marginOnRowMaterials;
    }

    public void setMarginOnRowMaterials(double marginOnRowMaterials) {
        this.marginOnRowMaterials = marginOnRowMaterials;
    }

    public double getMarginOnManHours() {
        return marginOnManHours;
    }

    public void setMarginOnManHours(double marginOnManHours) {
        this.marginOnManHours = marginOnManHours;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public long getCalculation() {
        return calculation;
    }

    public void setCalculation(long calculation) {
        this.calculation = calculation;
    }
}

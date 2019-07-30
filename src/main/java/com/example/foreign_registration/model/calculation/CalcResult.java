package com.example.foreign_registration.model.calculation;

public class CalcResult {

    private double[] npv;
    private double[] pi;
    private double[] roi;
    private double[] roe;
    private double[] costFlow;
    private double[] irr;
    private double[] returnPeriod;

    public CalcResult(double[] npv, double[] pi, double[] roi, double[] roe, double[] costFlow, double[] irr, double[] returnPeriod) {
        this.npv = npv;
        this.pi = pi;
        this.roi = roi;
        this.roe = roe;
        this.costFlow = costFlow;
        this.irr = irr;
        this.returnPeriod = returnPeriod;
    }

    public CalcResult(final int periodsNo) {
        this.npv = new double[periodsNo];
        this.pi = new double[periodsNo];
        this.roi = new double[periodsNo];
        this.roe = new double[periodsNo];
        this.costFlow = new double[periodsNo];
        this.irr = new double[periodsNo];
        this.returnPeriod = new double[periodsNo];
    }

    public double[] getNpv() {
        return npv;
    }

    public void setNpv(double[] npv) {
        this.npv = npv;
    }

    public double[] getPi() {
        return pi;
    }

    public void setPi(double[] pi) {
        this.pi = pi;
    }

    public double[] getRoi() {
        return roi;
    }

    public void setRoi(double[] roi) {
        this.roi = roi;
    }

    public double[] getRoe() {
        return roe;
    }

    public void setRoe(double[] roe) {
        this.roe = roe;
    }

    public double[] getCostFlow() {
        return costFlow;
    }

    public void setCostFlow(double[] costFlow) {
        this.costFlow = costFlow;
    }

    public double[] getIrr() {
        return irr;
    }

    public void setIrr(double[] irr) {
        this.irr = irr;
    }

    public double[] getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double[] returnPeriod) {
        this.returnPeriod = returnPeriod;
    }
}

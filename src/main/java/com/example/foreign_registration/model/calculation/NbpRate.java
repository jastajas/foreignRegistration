package com.example.foreign_registration.model.calculation;

import java.sql.Date;

public class NbpRate {
     private String no;
     private Date effectiveDate;
     private double mid;

    public NbpRate() {
    }

    public NbpRate(String no, Date effectiveDate, double mid) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "NbpRate{" +
                "no='" + no + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", mid=" + mid +
                '}';
    }
}

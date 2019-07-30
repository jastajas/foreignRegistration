package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Entity
public class AssessmentCostMH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(scale = 2)
    private double cost;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String costSubject;

    private int mh;
// todo scalić dla costów i mh => effortSubject
    private String mhSubject;

    @ManyToOne
    private DepartmentAssessment department_assessment;

    @Transient
    private BigDecimal bd;

    public AssessmentCostMH() {
    }

    public AssessmentCostMH(double cost, Currency currency, String costSubject, int mh, String mhSubject, DepartmentAssessment department_assessment) {
        this.bd = new BigDecimal(cost);
        this.bd = this.bd.setScale(2, RoundingMode.HALF_UP);
        this.cost = this.bd.doubleValue();
        this.currency = currency;
        this.costSubject = costSubject;
        this.mh = mh;
        this.mhSubject = mhSubject;
        this.department_assessment = department_assessment;
    }

    public AssessmentCostMH(double cost, Currency currency, String costSubject, DepartmentAssessment department_assessment) {
        this.bd = new BigDecimal(cost);
        this.bd = this.bd.setScale(2, RoundingMode.HALF_UP);
        this.cost = this.bd.doubleValue();
        this.currency = currency;
        this.costSubject = costSubject;
        this.department_assessment = department_assessment;
    }

    public AssessmentCostMH(int mh, String mhSubject, DepartmentAssessment department_assessment) {
        this.mh = mh;
        this.mhSubject = mhSubject;
        this.department_assessment = department_assessment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.bd = new BigDecimal(cost);
        this.bd = this.bd.setScale(2, RoundingMode.HALF_UP);
        this.cost = this.bd.doubleValue();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCostSubject() {
        return costSubject;
    }

    public void setCostSubject(String costSubject) {
        this.costSubject = costSubject;
    }

    public int getMh() {
        return mh;
    }

    public void setMh(int mh) {
        this.mh = mh;
    }

    public String getMhSubject() {
        return mhSubject;
    }

    public void setMhSubject(String mhSubject) {
        this.mhSubject = mhSubject;
    }

    public DepartmentAssessment getDepartment_assessment() {
        return department_assessment;
    }

    public void setDepartment_assessment(DepartmentAssessment department_assessment) {
        this.department_assessment = department_assessment;
    }

}

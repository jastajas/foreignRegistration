package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.app.Department;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class CalculationCostMh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    private double effort;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String effortSubject;

    @ManyToOne
    private Department effortSource;

    @Transient
    private BigDecimal bd;

    @ManyToOne
    private Calculation calculation;

    public CalculationCostMh() {
    }

    public CalculationCostMh(double effort, Currency currency, String effortSubject, Department effortSource, Calculation calculation) {
        this.bd = new BigDecimal(effort);
        this.bd = this.bd.setScale(2, RoundingMode.HALF_UP);
        this.effort = this.bd.doubleValue();
        this.currency = currency;
        this.effortSubject = effortSubject;
        this.effortSource = effortSource;
        this.calculation = calculation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getEffort() {
        return effort;
    }

    public void setEffort(double effort) {
        this.effort = effort;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getEffortSubject() {
        return effortSubject;
    }

    public void setEffortSubject(String effortSubject) {
        this.effortSubject = effortSubject;
    }

    public Department getEffortSource() {
        return effortSource;
    }

    public void setEffortSource(Department effortSource) {
        this.effortSource = effortSource;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }
}

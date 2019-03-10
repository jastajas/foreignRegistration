package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Status;
import com.example.foreign_registration.model.assessment.Assessment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calculationNo;

    private Date calculationDate;

    @OneToMany(mappedBy = "calculation")
    private List<CalculationAssumptions> calculationAssumptions;

    @ManyToOne
    private Assessment assessment;

    @ManyToOne
    private Status status;

    public Calculation() {
    }

    public Calculation(String calculationNo, Date calculationDate, List<CalculationAssumptions> calculationAssumptions, Assessment assessment, Status status) {
        this.calculationNo = calculationNo;
        this.calculationDate = calculationDate;
        this.calculationAssumptions = calculationAssumptions;
        this.assessment = assessment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalculationNo() {
        return calculationNo;
    }

    public void setCalculationNo(String calculationNo) {
        this.calculationNo = calculationNo;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public List<CalculationAssumptions> getCalculationAssumptions() {
        return calculationAssumptions;
    }

    public void setCalculationAssumptions(List<CalculationAssumptions> calculationAssumptions) {
        this.calculationAssumptions = calculationAssumptions;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

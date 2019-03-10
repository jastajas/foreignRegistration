package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.Unit;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;

import javax.persistence.*;
import java.util.List;

@Entity
public class PackageSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(scale = 2)
    private double size;

    @ManyToOne
    private Unit unit;

    @ManyToOne
    private Assessment assessment;

    @OneToMany(mappedBy = "packageSize")
    private List<CalculationAssumptions> calculationAssumptions;

    public PackageSize(double size, Unit unit, Assessment assessment) {
        this.size = size;
        this.unit = unit;
        this.assessment = assessment;
    }

    public PackageSize() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}

package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.Unit;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageSize that = (PackageSize) o;
        return id == that.id &&
                Double.compare(that.size, size) == 0 &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, unit);
    }
}

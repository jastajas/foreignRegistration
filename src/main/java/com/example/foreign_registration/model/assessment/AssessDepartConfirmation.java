package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.Department;

import javax.persistence.*;

@Entity
public class AssessDepartConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Assessment assessment;

    @ManyToOne
    private Department department;

    private boolean isConfirmed;

    public AssessDepartConfirmation(Assessment assessment, Department department, boolean isConfirmed) {
        this.assessment = assessment;
        this.department = department;
        this.isConfirmed = isConfirmed;
    }

    public AssessDepartConfirmation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}

package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.AssessDepartConfirmation;
import com.example.foreign_registration.model.assessment.AssessmentPattern;
import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;
import com.example.foreign_registration.model.calculation.DepartmentRate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<AssessmentPattern> assessmentPatterns;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<AssessDepartConfirmation> assessDepartConfirmations;

    @OneToMany(mappedBy = "other_subject_department")
    @JsonIgnore
    private List<DepartmentAssessment> departmentAssessments;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<DepartmentRate> departmentRates;

    public Department() {
    }

    public Department(String name, List<User> users, List<AssessmentPattern> assessmentPatterns,
                      List<AssessDepartConfirmation> assessDepartConfirmations, List<DepartmentAssessment> departmentAssessments,
                      List<DepartmentRate> departmentRates) {
        this.name = name;
        this.users = users;
        this.assessmentPatterns = assessmentPatterns;
        this.assessDepartConfirmations = assessDepartConfirmations;
        this.departmentAssessments = departmentAssessments;
        this.departmentRates = departmentRates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<AssessmentPattern> getAssessmentPatterns() {
        return assessmentPatterns;
    }

    public void setAssessmentPatterns(List<AssessmentPattern> assessmentPatterns) {
        this.assessmentPatterns = assessmentPatterns;
    }

    public List<AssessDepartConfirmation> getAssessDepartConfirmations() {
        return assessDepartConfirmations;
    }

    public void setAssessDepartConfirmations(List<AssessDepartConfirmation> assessDepartConfirmations) {
        this.assessDepartConfirmations = assessDepartConfirmations;
    }

    public List<DepartmentAssessment> getDepartmentAssessments() {
        return departmentAssessments;
    }

    public void setDepartmentAssessments(List<DepartmentAssessment> departmentAssessments) {
        this.departmentAssessments = departmentAssessments;
    }

    public List<DepartmentRate> getDepartmentRates() {
        return departmentRates;
    }

    public void setDepartmentRates(List<DepartmentRate> departmentRates) {
        this.departmentRates = departmentRates;
    }


    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", assessmentPatterns=" + assessmentPatterns +
                ", assessDepartConfirmations=" + assessDepartConfirmations +
                ", departmentAssessments=" + departmentAssessments +
                ", departmentRates=" + departmentRates +
                '}';
    }
}

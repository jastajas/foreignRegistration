package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.AssessDepartConfirmation;
import com.example.foreign_registration.model.assessment.AssessmentPattern;
import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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

    public Department(String name, List<User> users, List<AssessmentPattern> assessmentPatterns, List<AssessDepartConfirmation> assessDepartConfirmations, List<DepartmentAssessment> departmentAssessments) {
        this.name = name;
        this.users = users;
        this.assessmentPatterns = assessmentPatterns;
        this.assessDepartConfirmations = assessDepartConfirmations;
        this.departmentAssessments = departmentAssessments;
    }

    public Department() {
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
    
    
}
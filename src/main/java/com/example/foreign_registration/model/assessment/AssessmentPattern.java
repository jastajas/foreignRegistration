package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.Department;
import com.example.foreign_registration.model.app.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class AssessmentPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dossier_module;

    @Column(length = 400)
    private String assessment_subject;


    @ManyToOne
    private Department department;

    @ManyToOne
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "assessment_pattern")
    @JsonIgnore
    private List<DepartmentAssessment> departmentAssessments;

    //private Date assessment_date;


    public AssessmentPattern(String dossier_module, String assessment_subject, Department department, ProductStatus productStatus, List<DepartmentAssessment> departmentAssessments) {
        this.dossier_module = dossier_module;
        this.assessment_subject = assessment_subject;
        this.department = department;
        this.productStatus = productStatus;
        this.departmentAssessments = departmentAssessments;
    }

    public AssessmentPattern() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDossier_module() {
        return dossier_module;
    }

    public void setDossier_module(String dossier_module) {
        this.dossier_module = dossier_module;
    }

    public String getAssessment_subject() {
        return assessment_subject;
    }

    public void setAssessment_subject(String assessment_subject) {
        this.assessment_subject = assessment_subject;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public List<DepartmentAssessment> getDepartmentAssessments() {
        return departmentAssessments;
    }

    public void setDepartmentAssessments(List<DepartmentAssessment> departmentAssessments) {
        this.departmentAssessments = departmentAssessments;
    }
}
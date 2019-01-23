package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.process.Process;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProductQualification {
    // dotyczy produktów leczniczych: generyk, WUE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category_name;

    @OneToMany(mappedBy = "product_qualification")
    @JsonIgnore
    private List<Process> processes;

    @OneToMany(mappedBy = "required_prod_qualification")
    @JsonIgnore
    private List<Assessment> assessments_req;

    public ProductQualification(String category_name, List<Process> processes, List<Assessment> assessments_req) {
        this.category_name = category_name;
        this.processes = processes;
        this.assessments_req = assessments_req;
    }

    public ProductQualification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<Assessment> getAssessments_req() {
        return assessments_req;
    }

    public void setAssessments_req(List<Assessment> assessments_req) {
        this.assessments_req = assessments_req;
    }
}

package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.Assessment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {
// tabela - lista krajów z ich kodami.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country_name;

    private String code;

    //łączniki tabel
    @OneToMany(mappedBy = "registration_country")
    @JsonIgnore
    private List<Assessment> assessments;

    public Country() {
    }

    public Country(String country_name, String code, List<Assessment> assessments) {
        this.country_name = country_name;
        this.code = code;
        this.assessments = assessments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }
}

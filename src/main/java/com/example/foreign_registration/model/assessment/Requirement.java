package com.example.foreign_registration.model.assessment;

import javax.persistence.*;

@Entity
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Assessment assessment;

    private String requirement;

    public Requirement(Assessment assessment, String requirement) {
        this.assessment = assessment;
        this.requirement = requirement;
    }

    public Requirement() {
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

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}

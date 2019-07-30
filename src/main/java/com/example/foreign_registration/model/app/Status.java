package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.dossier.Dossier;
import com.example.foreign_registration.model.process.Process;
import com.example.foreign_registration.model.process.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Assessment> assessments;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Dossier> dossiers;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Process> processes;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private List<Calculation> calculations;

    public Status() {
    }

    public Status(String name, String category, List<Assessment> assessments, List<Dossier> dossiers, List<Process> processes, List<Task> tasks, List<Calculation> calculations) {
        this.name = name;
        this.category = category;
        this.assessments = assessments;
        this.dossiers = dossiers;
        this.processes = processes;
        this.tasks = tasks;
        this.calculations = calculations;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public List<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", assessments=" + assessments +
                ", dossiers=" + dossiers +
                ", processes=" + processes +
                ", tasks=" + tasks +
                ", calculations=" + calculations +
                '}';
    }
}

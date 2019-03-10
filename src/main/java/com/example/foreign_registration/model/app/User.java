package com.example.foreign_registration.model.app;


import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.foreign_registration.model.process.Process;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    // email adress == username
    private String username;

    private String password;
    private Boolean enabled;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "ordering_person")
    @JsonIgnore
    private List<Assessment> assessments;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<DepartmentAssessment> departmentAssessments;

    @OneToMany(mappedBy = "order_owner")
    @JsonIgnore
    private List<Process> processes;

    //todo zakotwiczyć taski i json ignore

    public User(String name, String surname, String username, String password, Boolean enabled, Position position, Department department, List<Assessment> assessments, List<DepartmentAssessment> departmentAssessments, List<Process> processes) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.position = position;
        this.department = department;
        this.assessments = assessments;
        this.departmentAssessments = departmentAssessments;
        this.processes = processes;
    }

    public User() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public List<DepartmentAssessment> getDepartmentAssessments() {
        return departmentAssessments;
    }

    public void setDepartmentAssessments(List<DepartmentAssessment> departmentAssessments) {
        this.departmentAssessments = departmentAssessments;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(enabled, user.enabled) &&
                Objects.equals(position, user.position) &&
                Objects.equals(department, user.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, username, password, enabled, position, department);
    }
}

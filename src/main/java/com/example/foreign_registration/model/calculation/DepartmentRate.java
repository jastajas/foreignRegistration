package com.example.foreign_registration.model.calculation;


import com.example.foreign_registration.model.app.Department;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class DepartmentRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 4)
    private double rate;

    @ManyToOne
    private Department department;

    private Date date;

    public DepartmentRate() {
    }

    public DepartmentRate(double rate, Department department, Date date) {
        this.rate = rate;
        this.department = department;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

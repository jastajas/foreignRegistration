package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.PackageSize;
import com.example.foreign_registration.model.process.Process;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String unit;

    @OneToMany(mappedBy = "one_order_unit")
    @JsonIgnore
    private List<Process> processes;

    @OneToMany(mappedBy = "annual_order_unit")
    @JsonIgnore
    private List<Process> processList;

    @OneToMany(mappedBy = "unit")
    @JsonIgnore
    private List<PackageSize> packageSizeList;

    public Unit(String unit, List<Process> processes, List<Process> processList, List<PackageSize> packageSizeList) {
        this.unit = unit;
        this.processes = processes;
        this.processList = processList;
        this.packageSizeList = packageSizeList;
    }

    public Unit() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    public List<PackageSize> getPackageSizeList() {
        return packageSizeList;
    }

    public void setPackageSizeList(List<PackageSize> packageSizeList) {
        this.packageSizeList = packageSizeList;
    }
}

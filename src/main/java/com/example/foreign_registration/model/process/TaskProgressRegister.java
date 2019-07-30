package com.example.foreign_registration.model.process;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class TaskProgressRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sumMhToDo;

    private Date registerDate;

    @ManyToOne
    private Process process;

    public TaskProgressRegister(int sumMhToDo, Date registerDate, Process process) {
        this.sumMhToDo = sumMhToDo;
        this.registerDate = registerDate;
        this.process = process;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSumMhToDo() {
        return sumMhToDo;
    }

    public void setSumMhToDo(int sumMhToDo) {
        this.sumMhToDo = sumMhToDo;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}

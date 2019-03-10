package com.example.foreign_registration.model.process;

import com.example.foreign_registration.model.app.Status;
import com.example.foreign_registration.model.app.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deadline;
    private int manEffort;

    @ManyToOne
    private User taskOwner;
    @ManyToOne
    private User orderingPerson;

    private Date orderingDate;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Process process;

    @JsonIgnore
    @OneToMany(mappedBy = "mainTask")
    private List<TaskRelation> relatedTaskRelations;

    @JsonIgnore
    @OneToMany(mappedBy = "relatedTask")
    private List<TaskRelation> mainTaskRelations;

    public Task() {
    }

    public Task(String name, String description, Date deadline, int manEffort, User taskOwner, User orderingPerson, Date orderingDate, Status status, Process process, List<TaskRelation> relatedTaskRelations, List<TaskRelation> mainTaskRelations) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.manEffort = manEffort;
        this.taskOwner = taskOwner;
        this.orderingPerson = orderingPerson;
        this.orderingDate = orderingDate;
        this.status = status;
        this.process = process;
        this.relatedTaskRelations = relatedTaskRelations;
        this.mainTaskRelations = mainTaskRelations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getManEffort() {
        return manEffort;
    }

    public void setManEffort(int manEffort) {
        this.manEffort = manEffort;
    }

    public User getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(User taskOwner) {
        this.taskOwner = taskOwner;
    }

    public User getOrderingPerson() {
        return orderingPerson;
    }

    public void setOrderingPerson(User orderingPerson) {
        this.orderingPerson = orderingPerson;
    }

    public Date getOrderingDate() {
        return orderingDate;
    }

    public void setOrderingDate(Date orderingDate) {
        this.orderingDate = orderingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @JsonIgnore
    public List<TaskRelation> getRelatedTaskRelations() {
        return relatedTaskRelations;
    }

    @JsonProperty
    public void setRelatedTaskRelations(List<TaskRelation> relatedTaskRelations) {
        this.relatedTaskRelations = relatedTaskRelations;
    }

    @JsonIgnore
    public List<TaskRelation> getMainTaskRelations() {
        return mainTaskRelations;
    }

    @JsonProperty
    public void setMainTaskRelations(List<TaskRelation> mainTaskRelations) {
        this.mainTaskRelations = mainTaskRelations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                manEffort == task.manEffort &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(taskOwner, task.taskOwner) &&
                Objects.equals(orderingPerson, task.orderingPerson) &&
                Objects.equals(orderingDate, task.orderingDate) &&
                Objects.equals(status, task.status) &&
                Objects.equals(process, task.process);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, deadline, manEffort, taskOwner, orderingPerson, orderingDate, status, process);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", manEffort=" + manEffort +
                ", taskOwner=" + taskOwner +
                ", orderingPerson=" + orderingPerson +
                ", orderingDate=" + orderingDate +
                ", status=" + status +
                ", process=" + process +
                ", relatedTaskRelations=" + relatedTaskRelations +
                ", mainTaskRelations=" + mainTaskRelations +
                '}';
    }
}

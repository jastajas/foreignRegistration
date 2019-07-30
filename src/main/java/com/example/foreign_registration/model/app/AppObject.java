package com.example.foreign_registration.model.app;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class AppObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @ManyToOne
    private Status status;

    private Date creationDate;

    @ManyToOne
    private User creator;

    public AppObject() {
    }

    public AppObject(String number, Status status, Date creationDate, User creator) {
        this.number = number;
        this.status = status;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppObject appObject = (AppObject) o;
        return Objects.equals(id, appObject.id) &&
                Objects.equals(number, appObject.number) &&
                Objects.equals(status, appObject.status) &&
                Objects.equals(creationDate, appObject.creationDate) &&
                Objects.equals(creator, appObject.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, status, creationDate, creator);
    }

    @Override
    public String toString() {
        return "AppObject{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", creator=" + creator +
                '}';
    }
}

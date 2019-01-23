package com.example.foreign_registration.model.dossier;

import com.example.foreign_registration.model.app.Status;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Dossier {

    @Id
    private long id;

    private String name;

    @ManyToOne
    private Status status;

    public Dossier(long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Dossier() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

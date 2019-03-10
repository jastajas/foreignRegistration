package com.example.foreign_registration.model.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.foreign_registration.model.process.Process;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String adress;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Process> processes;

    public Client(String name, String adress, List<Process> processes) {
        this.name = name;
        this.adress = adress;
        this.processes = processes;
    }

    public Client() {
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(name, client.name) &&
                Objects.equals(adress, client.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress);
    }
}

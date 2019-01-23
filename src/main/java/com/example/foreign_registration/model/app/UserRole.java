package com.example.foreign_registration.model.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String username;
    private String role;

    public UserRole() {
    }

    public UserRole(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
package com.example.foreign_registration.model.app;

import java.util.Objects;

public abstract class AppObjectForm {

    private long idStatus;
    private String creatorUsername;

    public void setIdStatus(long idStatus) {
        this.idStatus = idStatus;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppObjectForm that = (AppObjectForm) o;
        return idStatus == that.idStatus &&
                Objects.equals(creatorUsername, that.creatorUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStatus, creatorUsername);
    }

    @Override
    public String toString() {
        return "AppObjectForm{" +
                "idStatus=" + idStatus +
                ", creatorUsername='" + creatorUsername + '\'' +
                '}';
    }

    public abstract boolean isValid();

}

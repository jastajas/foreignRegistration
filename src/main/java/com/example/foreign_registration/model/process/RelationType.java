package com.example.foreign_registration.model.process;

public enum RelationType {

    SF("Start-Finish"),
    SS("Start-Start"),
    FS("Finish-Start"),
    FF("Finish-Finish");

    public String fullName;

    RelationType(String fullName){
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

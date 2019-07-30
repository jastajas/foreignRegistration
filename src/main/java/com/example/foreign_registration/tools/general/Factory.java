package com.example.foreign_registration.tools.general;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class Factory {

    private DateGenerator dateGenerator;

    @Autowired
    public Factory(DateGenerator dateGenerator){
        this.dateGenerator = dateGenerator;
    }

    public Factory() {
    }

    public DateGenerator getDateGenerator() {
        return dateGenerator;
    }

    public void setDateGenerator(DateGenerator dateGenerator) {
        this.dateGenerator = dateGenerator;
    }

    public abstract String createNumber() throws OversizeNumberException;

    public abstract AppObject create(AppObjectForm appObjectForm) throws OversizeNumberException, BuildingAppObjectException;

}

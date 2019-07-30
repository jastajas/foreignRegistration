package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.AppObjectForm;

import java.util.List;

public class CalculationForm extends AppObjectForm {

    private List<Long> packSizesID;
    private boolean royalitiesRequired;
    private String chargeType;
    private String chargeSubject;
    private int periodsNo;

    public List<Long> getPackSizesID() {
        return packSizesID;
    }

    public void setPackSizesID(List<Long> packSizesID) {
        this.packSizesID = packSizesID;
    }

    public boolean isRoyalitiesRequired() {
        return royalitiesRequired;
    }

    public void setRoyalitiesRequired(boolean royalitiesRequired) {
        this.royalitiesRequired = royalitiesRequired;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeSubject() {
        return chargeSubject;
    }

    public void setChargeSubject(String chargeSubject) {
        this.chargeSubject = chargeSubject;
    }

    public int getPeriodsNo() {
        return periodsNo;
    }

    public void setPeriodsNo(int periodsNo) {
        this.periodsNo = periodsNo;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}

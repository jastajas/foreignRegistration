package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;

public class LicenceCostForm {

    private double licence;
    private Currency licenceCurrency;

    private double[] royalities;
    private Currency royalitiesCurrency;

    public LicenceCostForm() {
    }

    public LicenceCostForm(double licence, Currency licenceCurrency, double[] royalities, Currency royalitiesCurrency) {
        this.licence = licence;
        this.licenceCurrency = licenceCurrency;
        this.royalities = royalities;
        this.royalitiesCurrency = royalitiesCurrency;
    }

    public double getLicence() {
        return licence;
    }

    public void setLicence(double licence) {
        this.licence = licence;
    }

    public Currency getLicenceCurrency() {
        return licenceCurrency;
    }

    public void setLicenceCurrency(Currency licenceCurrency) {
        this.licenceCurrency = licenceCurrency;
    }

    public double[] getRoyalities() {
        return royalities;
    }

    public void setRoyalities(double[] royalities) {
        this.royalities = royalities;
    }

    public Currency getRoyalitiesCurrency() {
        return royalitiesCurrency;
    }

    public void setRoyalitiesCurrency(Currency royalitiesCurrency) {
        this.royalitiesCurrency = royalitiesCurrency;
    }
}

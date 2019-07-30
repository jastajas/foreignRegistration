package com.example.foreign_registration.model.calculation;

import java.util.List;

public class NbpRatesTable {
    private String table;
    private String currency;
    private String code;
    private List<NbpRate> rates;

    public NbpRatesTable() {
    }

    public NbpRatesTable(String table, String currency, String code, List<NbpRate> rates) {
        this.table = table;
        this.currency = currency;
        this.code = code;
        this.rates = rates;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NbpRate> getRates() {
        return rates;
    }

    public void setRates(List<NbpRate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "NbpRatesTable{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}

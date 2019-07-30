package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double rate;

    private Date updateDate;

    public CurrencyRate() {
    }

    public CurrencyRate(Currency currency, double rate, Date updateDate) {
        this.currency = currency;
        this.rate = rate;
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

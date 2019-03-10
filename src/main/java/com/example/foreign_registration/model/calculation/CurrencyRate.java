package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.app.Currency;

import javax.persistence.*;

@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double rate;

    public CurrencyRate() {
    }

    public CurrencyRate(Currency currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id=" + id +
                ", currency=" + currency +
                ", rate=" + rate +
                '}';
    }
}

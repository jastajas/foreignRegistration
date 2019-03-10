package com.example.foreign_registration.model.calculation;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class DiscountRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    private double rate;

    //stopa procentowa
    @Column(scale = 2)
    private double interestRate;

    @Column(scale = 2)
    private double inflation;

    private Date date;

    public DiscountRate() {
    }

    public DiscountRate(double rate, double interestRate, double inflation, Date date) {
        this.rate = rate;
        this.interestRate = interestRate;
        this.inflation = inflation;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInflation() {
        return inflation;
    }

    public void setInflation(double inflation) {
        this.inflation = inflation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

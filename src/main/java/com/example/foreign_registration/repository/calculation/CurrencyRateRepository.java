package com.example.foreign_registration.repository.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.calculation.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    public Optional<CurrencyRate> getCurrencyRateByCurrency(Currency currency);
}

package com.example.foreign_registration.repository.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.calculation.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    //public Optional<CurrencyRate> getCurrencyRateByCurrency(Currency currency);

    @Query("SELECT cr FROM CurrencyRate cr WHERE cr.id = (SELECT MAX(c.id) FROM CurrencyRate c where c.currency = :currency and c.updateDate <= :referenceDate)")
    public Optional<CurrencyRate>  getCurrencyRateByDate(@Param("currency")Currency currency,@Param("referenceDate") Date referenceDate);

}

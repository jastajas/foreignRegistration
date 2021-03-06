package com.example.foreign_registration.repository.calculation;

import com.example.foreign_registration.model.calculation.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    @Query("SELECT cl.number FROM Calculation cl WHERE cl.id = (SELECT MAX(c.id) FROM Calculation c where c.creationDate between :firstDateOfThisYear and :lastDateOfThisYear)")
    Optional<String> getMaxCalcNoForCurrentYear(@Param("firstDateOfThisYear") Date firstDate, @Param("lastDateOfThisYear") Date lastDate);

    Optional<Calculation> findByNumber(String number);

}

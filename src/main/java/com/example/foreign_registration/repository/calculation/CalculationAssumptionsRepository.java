package com.example.foreign_registration.repository.calculation;

import com.example.foreign_registration.model.assessment.PackageSize;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalculationAssumptionsRepository extends JpaRepository<CalculationAssumptions, Long> {


    @Query("SELECT ca FROM CalculationAssumptions ca WHERE ca.calculation = :calculation ORDER BY :package")
    public List<CalculationAssumptions> getCalcAssumtionsByCalculation(@Param("calculation") Calculation calculation,
                                                                       @Param("package")PackageSize packageSize);



}

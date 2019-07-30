package com.example.foreign_registration.repository.calculation;

import com.example.foreign_registration.model.assessment.PackageSize;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.calculation.CalculationAssumptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalculationAssumptionsRepository extends JpaRepository<CalculationAssumptions, Long> {


    @Query("SELECT ca FROM CalculationAssumptions ca WHERE ca.calculation = :calculation")
    public List<CalculationAssumptions> getAllCalcAssumtionsByCalculation(@Param("calculation") Calculation calculation);

    @Query("SELECT ca FROM CalculationAssumptions ca WHERE ca.calculation = :calculation AND ca.packageSize IS NULL")
    public List<CalculationAssumptions> getMainCalcAssumtionsByCalculation(@Param("calculation") Calculation calculation);

    @Query("SELECT ca FROM CalculationAssumptions ca WHERE ca.calculation = :calculation AND ca.packageSize IS NOT NULL ORDER BY ca.packageSize")
    public List<CalculationAssumptions> getCalcAssumtionsForPackagesByCalculation(@Param("calculation") Calculation calculation);

    @Query("SELECT DISTINCT(ca.packageSize) FROM CalculationAssumptions ca WHERE ca.calculation = :calculation ORDER BY ca.packageSize")
    public List<PackageSize> getPackSizesForCalculation(@Param("calculation") Calculation calculation);

}

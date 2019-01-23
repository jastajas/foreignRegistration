package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.app.Department;
import com.example.foreign_registration.model.app.ProductStatus;
import com.example.foreign_registration.model.assessment.AssessmentPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentPatternRepository extends JpaRepository<AssessmentPattern, Long> {

    List<AssessmentPattern> findByProductStatus(ProductStatus productStatus);

    @Query("SELECT DISTINCT ap.department FROM AssessmentPattern ap WHERE ap.productStatus = :selectedStatus")
    public List<Department> getAllDepartmentsByStatu(@Param("selectedStatus") ProductStatus productStatus);

}

package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.assessment.AssessmentCostMH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AssessmentCostMHRepository extends JpaRepository<AssessmentCostMH, Long> {

    @Query("SELECT SUM(acm.cost) FROM DepartmentAssessment da LEFT JOIN da.assessment a LEFT JOIN da.assessmentCostMHs acm WHERE a.id = :assessmentID AND acm.currency = :currency GROUP BY a.id")
    public Optional<Double> getSumCostAssessment(@Param("assessmentID") long assessmentID, @Param("currency") Currency currency);


    @Query("SELECT SUM(acm.mh) FROM DepartmentAssessment da LEFT JOIN da.assessment a LEFT JOIN da.assessmentCostMHs acm WHERE a.id = :assessmentID GROUP BY a.id")
    public Optional<Integer> getSumMhAssessment(@Param("assessmentID") long assessmentID);

}

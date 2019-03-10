package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.DepartmentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DepartmentAssessmentRepository extends JpaRepository<DepartmentAssessment, Long> {

    public List<DepartmentAssessment> findByAssessment(Assessment id);

    @Query("SELECT da FROM DepartmentAssessment da " +
            "LEFT JOIN  da.assessment_pattern ap " +
            "LEFT JOIN ap.department dp " +
            "LEFT JOIN da.other_subject_department osd " +
            "WHERE da.id = :idDepartmentAssessment " +
            "AND da.assessment_description is null " +
            "AND (dp.id = :idLogedUserDepart " +
            "OR osd.id = :idLogedUserDepart)")
    public List<DepartmentAssessment> checkIfExistDepartmentAssessment(@Param("idDepartmentAssessment") Long idDepartmentAssessment, @Param("idLogedUserDepart") Long idLogedUserDepart);


}

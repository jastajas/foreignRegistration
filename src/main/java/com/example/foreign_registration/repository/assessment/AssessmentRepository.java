package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.assessment.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @Query("SELECT a FROM Assessment a " +
            "LEFT JOIN  a.processes p " +
            "LEFT JOIN p.product pr " +
            "LEFT JOIN pr.product_status ps " +
            "LEFT JOIN a.destined_product_status dps " +
            "LEFT JOIN a.registration_country rc " +
            "LEFT JOIN a.required_prod_qualification rpq " +
            "LEFT JOIN p.product_qualification pq " +
            "LEFT JOIN p.client cl " +
            "LEFT JOIN a.status s " +
            "WHERE pr.name LIKE CONCAT('%',:keyWord, '%') " +
            "OR pr.drug_form LIKE CONCAT('%',:keyWord, '%') " +
            "OR ps.product_status LIKE CONCAT('%',:keyWord, '%') " +
            "OR dps.product_status LIKE CONCAT('%',:keyWord, '%') " +
            "OR p.destined_product_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR rc.country_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR pq.category_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR rpq.category_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR cl.name LIKE CONCAT('%',:keyWord, '%') " +
            "OR s.name LIKE CONCAT('%',:keyWord, '%')")
    public List<Assessment> listAllByKeyWord(@Param("keyWord") String keyWord);


    @Query("SELECT p FROM Assessment a LEFT JOIN  a.processes p WHERE a.id = :assessmentId AND p.id = :processId")
    public Process getProcessForAssessment(@Param("processId") long processId, @Param("assessmentId") long assessmentId);

    @Query("SELECT MAX(a.id) FROM Assessment a")
    public Optional<Long> getMaxAssessmentId();

    Optional<Assessment> findByAssessmentNo(String assessmentNo);

    @Query("SELECT ass.assessmentNo FROM Assessment ass WHERE ass.id = (SELECT MAX(a.id) FROM Assessment a where a.order_date between :firstDateOfThisYear and :lastDateOfThisYear)")
    public Optional<String> getMaxAssessmentNoForCurrentYear(@Param("firstDateOfThisYear") Date firstDate, @Param("lastDateOfThisYear") Date lastDate);

    @Query("SELECT COUNT(a) FROM Assessment a")
    public Long getCountAllRows();

}
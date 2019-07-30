package com.example.foreign_registration.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.foreign_registration.model.process.Process;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    @Query("SELECT DISTINCT p FROM Process p " +
            "LEFT JOIN p.assessments a " +
            "LEFT JOIN p.client c " +
            "LEFT JOIN p.product pr " +
            "LEFT JOIN pr.product_status ps " +
            "LEFT JOIN a.destined_product_status dps " +
            "LEFT JOIN a.registration_country rc " +
            "LEFT JOIN a.required_prod_qualification rpq " +
            "LEFT JOIN p.product_qualification pq " +
            "LEFT JOIN p.status s " +
            "WHERE p.model_cooperation LIKE CONCAT('%',:keyWord,'%') " +
            "OR p.destined_product_name LIKE CONCAT('%',:keyWord,'%') " +
            "OR s.name LIKE CONCAT('%',:keyWord,'%') " +
            "OR pr.name LIKE CONCAT('%',:keyWord,'%') " +
            "OR pr.drug_form LIKE CONCAT('%',:keyWord, '%') " +
            "OR ps.product_status LIKE CONCAT('%',:keyWord,'%') " +
            "OR c.name LIKE CONCAT('%',:keyWord, '%') " +
            "OR rc.country_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR pq.category_name LIKE CONCAT('%',:keyWord, '%') " +
            "OR rpq.category_name LIKE CONCAT('%',:keyWord, '%') " +
            "ORDER BY p.id DESC")
    public List<Process> getAllByKeyWord(@Param("keyWord") String keyWord);

    @Query("SELECT MIN(p.id) FROM Process p LEFT JOIN  p.assessments a WHERE a.id = :thisAssessmentId")
    public long getMinLongOfProcessForAssessment(@Param("thisAssessmentId") long assessmentId);

    @Query("SELECT MAX(p.id) FROM Process p")
    public Optional<Long> getMaxProcessId();

    Optional<Process> findByNumber(String number);

    @Query("SELECT pr.number FROM Process pr WHERE pr.id = (SELECT MAX(p.id) FROM Process p where p.creationDate between :firstDateOfCurYear and :lastDateOfCurYear)")
    public Optional<String> getMaxProcessNoForCurrentYear(@Param("firstDateOfCurYear") Date firstDate, @Param("lastDateOfCurYear") Date lastDate);

    @Query("SELECT COUNT(p) FROM Process p")
    public Long getCountAllRows();

}

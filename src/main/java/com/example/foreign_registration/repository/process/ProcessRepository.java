package com.example.foreign_registration.repository.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.foreign_registration.model.process.Process;

import java.sql.Date;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    @Query("SELECT MIN(p.id) FROM Process p LEFT JOIN  p.assessments a WHERE a.id = :thisAssessmentId")
    public long getMinLongOfProcessForAssessment(@Param("thisAssessmentId") long assessmentId);

    @Query("SELECT MAX(p.id) FROM Process p")
    public Optional<Long> getMaxProcessId();

    Optional<Process> findByOrderNo(String orderNo);

    @Query("SELECT pr.orderNo FROM Process pr WHERE pr.id = (SELECT MAX(p.id) FROM Process p where p.order_date between :firstDateOfCurYear and :lastDateOfCurYear)")
    public Optional<String> getMaxProcessNoForCurrentYear(@Param("firstDateOfCurYear") Date firstDate, @Param("lastDateOfCurYear") Date lastDate);

    @Query("SELECT COUNT(p) FROM Process p")
    public Long getCountAllRows();


}

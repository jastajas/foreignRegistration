package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.assessment.AssessDepartConfirmation;
import com.example.foreign_registration.model.assessment.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AssessDepartConfirmationRepository extends JpaRepository<AssessDepartConfirmation, Long> {

    List<AssessDepartConfirmation> findByAssessment(Assessment assessment);

}

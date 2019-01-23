package com.example.foreign_registration.repository.assessment;

import com.example.foreign_registration.model.assessment.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
public class AssessmentDAO {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory emFactory;


//TODO zweryfikować błędy zamknięcia połączeń
    public List<Assessment> getFilteredAssessments(String preparedQuery) {
        System.out.println(preparedQuery);
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Assessment> typedQueryAssessmets = em.createQuery(preparedQuery, Assessment.class);
        System.out.println(typedQueryAssessmets.getResultList().size());
       // em.close();
       // emFactory.close();
        return typedQueryAssessmets.getResultList();
    }

}

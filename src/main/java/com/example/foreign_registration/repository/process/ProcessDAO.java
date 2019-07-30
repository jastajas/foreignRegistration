package com.example.foreign_registration.repository.process;

import com.example.foreign_registration.model.process.Process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class ProcessDAO {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory emFactory;

    //TODO zweryfikować błędy zamknięcia połączeń
    public List<Process> getFilteredProcess(String preparedQuery) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Process> typedQueryProcesses = em.createQuery(preparedQuery, Process.class);

        // em.close();
        // emFactory.close();
        return typedQueryProcesses.getResultList();
    }
}

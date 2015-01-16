package ch.heigvd.amt_project.services.fact;

import ch.heigvd.amt_project.model.FactTiedToDate;
import ch.heigvd.amt_project.model.FactType;
import java.sql.Date;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class FactTiedToDateManager implements FactTiedToDateManagerLocal {
    @PersistenceContext
    public EntityManager em;
    
    public FactTiedToDateManager() {
    }

    @Override
    public long create(FactTiedToDate fact) {
        em.persist(fact);
        em.flush();
        
        return fact.getId();
    }

    @Override
    public List<FactTiedToDate> readAllTiedToDate() {
        return em.createNamedQuery("FactTiedToDate.findAll")
                .setParameter("type", FactType.FACT_TIED_TO_SENSOR_BY_DATE)
                .getResultList();
    }

    @Override
    public List<FactTiedToDate> readByDate(Date date) {
        return em.createNamedQuery("FactTiedToDate.findByDateRange")
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<FactTiedToDate> readByDateRange(Date startDate, Date endDate) {
        return em.createNamedQuery("FactTiedToDate.findByDateRange")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public FactTiedToDate update(FactTiedToDate fact) {
        FactTiedToDate f = em.merge(fact);
        em.flush();
        
        return f;
    }
}

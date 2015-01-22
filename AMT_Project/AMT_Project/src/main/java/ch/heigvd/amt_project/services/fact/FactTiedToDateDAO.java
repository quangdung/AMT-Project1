package ch.heigvd.amt_project.services.fact;

import ch.heigvd.amt_project.model.FactTiedToDate;
import ch.heigvd.amt_project.model.FactType;
import java.sql.Date;
import java.util.*;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class FactTiedToDateDAO implements FactTiedToDateDAOLocal {

    @PersistenceContext
    public EntityManager em;

    public FactTiedToDateDAO() {
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long create(FactTiedToDate fact) {
        em.persist(fact);
        em.flush();

        return fact.getId();
    }

    @Override
    public List<FactTiedToDate> readAllTiedToDate() {
        Query q = em.createNamedQuery("FactTiedToDate.findAll")
                .setParameter("type", FactType.FACT_TIED_TO_SENSOR_BY_DATE);
//        q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return q.getResultList();

    }

    @Override
    public FactTiedToDate readFactBySensorByDate(long id) {
        Query q = em.createNamedQuery("FactTiedToDate.findById")
                .setParameter("id", id);

        return (FactTiedToDate) q.getSingleResult();
    }
    
    

    @Override
    public List<FactTiedToDate> readBySensorId(long sensorId) {
        Query q = em.createNamedQuery("FactTiedToDate.findBySensorId")
                .setParameter("sensorId", sensorId);

//        try {
            q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
//            q.setHint("javax.persistence.query.timeout", 100);
//        }
//        catch (PessimisticLockException e) {
//            em.getTransaction().rollback();
//        }
//        catch (LockTimeoutException e) {
//
//        }

        return q.getResultList();

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

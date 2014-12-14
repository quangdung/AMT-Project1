package ch.heigvd.amt_project.services.observation;

import ch.heigvd.amt_project.model.Observation;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class ObservationManager implements ObservationManagerLocal {

    @PersistenceContext
    public EntityManager em;
    
    public ObservationManager() {
    }

    @Override
    public long create(Observation observation) {
        em.persist(observation);
        em.flush();
        
        return observation.getId();
    }
    
    @Override
    public List<Observation> read() {
        return em.createNamedQuery("findAllObservations")
                .getResultList();
    }
    
    @Override
    public Observation read(long obsId) {
        return (Observation) em.createNamedQuery("findObservationById")
                .setParameter("id", obsId)
                .getSingleResult();
    }
    
    @Override
    public List<Observation> readByName(String obsName) {
        return em.createNamedQuery("findObservationsByName")
                .setParameter("name", obsName)
                .getResultList();
    }

    @Override
    public List<Observation> readByCreationDate(java.sql.Date date) {
        return em.createNamedQuery("findObservationsByCreationDate")
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Observation> readBySensorId(long sensorId) {
        return em.createNamedQuery("findObservationsBySensorId")
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    @Override
    public Observation update(Observation observation) {
        Observation o = em.merge(observation);
        em.flush();
        
        return o;
    }

    @Override
    public void delete(Observation observation) {
        em.remove(observation);
        em.flush();
    }
}

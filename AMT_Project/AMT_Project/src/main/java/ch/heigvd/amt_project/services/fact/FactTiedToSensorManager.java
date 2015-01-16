package ch.heigvd.amt_project.services.fact;

import ch.heigvd.amt_project.model.FactTiedToSensor;
import ch.heigvd.amt_project.model.FactType;
import java.util.*;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class FactTiedToSensorManager implements FactTiedToSensorManagerLocal {
    @PersistenceContext
    public EntityManager em;
    
    public FactTiedToSensorManager() {
    }

    @Override
    public long create(FactTiedToSensor fact) {
        em.persist(fact);
        em.flush();
        
        return fact.getId();
    }

    @Override
    public List<FactTiedToSensor> readAllTiedToSensor() {
        return em.createNamedQuery("FactTiedToSensor.findAll")
                .setParameter("type", FactType.FACT_TIED_TO_SENSOR)
                .getResultList();
    }

    @Override
    public List<FactTiedToSensor> readBySensorId(long sensorId) {
        return em.createNamedQuery("FactTiedToSensor.findBySensorId")
                .setParameter("sensorId", sensorId)
                .getResultList();
    }

    @Override
    public List<FactTiedToSensor> readBySensorName(String sensorName) {
        return em.createNamedQuery("FactTiedToSensor.findBySensorName")
                .setParameter("sensorName", sensorName)
                .getResultList();
    }

    @Override
    public List<FactTiedToSensor> readBySensorType(String sensorType) {
        return em.createNamedQuery("FactTiedToSensor.findBySensorType")
                .setParameter("sensorType", sensorType)
                .getResultList();
    }

    @Override
    public List<FactTiedToSensor> readByTotalNbObsAbove(long threshold) {
        return em.createNamedQuery("FactTiedToSensor.findByTotNbObsAbove")
                .setParameter("threshold", threshold)
                .getResultList();
    }

    @Override
    public List<FactTiedToSensor> readByTotalNbObsBelow(long threshold) {
        return em.createNamedQuery("FactTiedToSensor.findByTotNbObsBelow")
                .setParameter("threshold", threshold)
                .getResultList();
    }

    @Override
    public FactTiedToSensor update(FactTiedToSensor fact) {
        FactTiedToSensor f = em.merge(fact);
        em.flush();
        
        return f;
    }
}

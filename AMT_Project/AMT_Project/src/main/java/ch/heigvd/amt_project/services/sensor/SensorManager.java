
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.*;

/**
 *
 * @author
 */
@Singleton
public class SensorManager implements SensorManagerLocal {

    @PersistenceContext
    public EntityManager em;
    
    // constructor
    public SensorManager() {}

    @Override
    public Sensor create(Sensor sensor) {
        em.persist(sensor);
        em.flush();
        
        return sensor;
    }
    
    @Override
    public long create(long id, String name, String description, String type, long orgId, boolean visible) {
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setName(name);
        sensor.setDescription(description);
        sensor.setType(type);
        sensor.setOrganizationId(orgId);
        sensor.setPublicSensor(visible);
        
        em.persist(sensor);
        em.flush();

        return sensor.getId();
    }
    
    @Override
    public List<Sensor> read() {
        return em.createNamedQuery("findAllSensors")
                .getResultList();
    }
    
    @Override
    public Sensor read(long sensorId) {
        return (Sensor) em.createNamedQuery("findSensorById")
                .setParameter("id", sensorId)
                .getSingleResult();
    }
    
    @Override
    public List<Sensor> readByName(String sensorName) {
        return em.createNamedQuery("findSensorsByName")
                .setParameter("name", sensorName)
                .getResultList();
    }

    @Override
    public List<Sensor> readByType(String type) {
        return em.createNamedQuery("findSensorsByType")
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Sensor> readByOrgId(long orgId) {
        return em.createNamedQuery("findSensorsByOrganizationId")
                .setParameter("organizationId", orgId)
                .getResultList();
    }

    @Override
    public List<Sensor> readPublicSensor() {
        return em.createNamedQuery("findSensorsByPublicSensor")
                .getResultList();
    }

    @Override
    public Sensor update(Sensor sensor) {
        Sensor s = em.merge(sensor);
        em.flush();
        
        return s;
    }

    @Override
    public void delete(Sensor sensor) {
        em.remove(sensor);
        em.flush();
    }
}

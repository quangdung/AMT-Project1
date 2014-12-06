
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public long create(Sensor sensor) {
        em.persist(sensor);
        em.flush();
        
        return sensor.getId();
    }
    
    @Override
    public long create(String description, String type) {
        Sensor sensor = new Sensor();
        sensor.setDescription(description);
        sensor.setType(type);

        em.persist(sensor);
        em.flush();

        return sensor.getId();
    }
    
    @Override
    public List<Sensor> read() {
        return em.createNamedQuery("findAll")
                .getResultList();
    }
    
    @Override
    public Sensor read(long sensorId) {
        return (Sensor) em.createNamedQuery("findById")
                .setParameter("id", sensorId)
                .getSingleResult();
    }
    
    @Override
    public List<Sensor> readByName(String sensorName) {
        return em.createNamedQuery("findByName")
                .setParameter("name", sensorName)
                .getResultList();
    }

    @Override
    public List<Sensor> readByType(String type) {
        return em.createNamedQuery("findByType")
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Sensor> readByOrgId(long orgId) {
        return em.createNamedQuery("findByOrganizationId")
                .setParameter("organizationId", orgId)
                .getResultList();
    }

    @Override
    public List<Sensor> readIsPublic() {
        return em.createNamedQuery("findByPublicSensor")
                .setParameter("publicSensor", true)
                .getResultList();
    }

    @Override
    public Sensor update(Sensor sensor) {
        return em.merge(sensor);
    }

    @Override
    public void delete(Sensor sensor) {
        em.remove(sensor);
        em.flush();
    }
}

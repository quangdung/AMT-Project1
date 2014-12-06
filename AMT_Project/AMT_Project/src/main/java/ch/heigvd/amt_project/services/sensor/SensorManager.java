/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author
 */
@Singleton
public class SensorManager implements SensorManagerLocal {

    @PersistenceContext
    public EntityManager em;

//    private Map<Long, Sensor> sensors = new HashMap<>();
    public SensorManager() {
    }

    @Override
    public Sensor findById(long id) {

        /**
         * ancien code
         */
//        List<Sensor> sensors = em.createNamedQuery("findById").getResultList();
//        Sensor sensor = sensors.get((int) id);
//        return sensor;
        /**
         * fin ancien code
         */
        Sensor result = null;

        try {
            result = (Sensor) em.createNamedQuery("findById")
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public List<Sensor> findAll() {

        /**
         * ancien code
         */
//        List<Sensor> sensors = em.createNamedQuery("findAll").getResultList();
//
//        return new ArrayList(sensors);
        /**
         * fin ancien code
         */

        return em.createNamedQuery("findAll")
                .getResultList();

    }

    @Override
    public long create(Sensor sensor) {
        em.persist(sensor);
        em.flush();
        
        return sensor.getId();
    }

    
    

    @Override
    public long create(String description, String type) {
        Sensor s = new Sensor();
        s.setDescription(description);
        s.setType(type);

        em.persist(s);
        em.flush();

        return s.getId();
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


    @Override
    public List<Sensor> findByName(String name) {
        return em.createNamedQuery("findByName")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Sensor> findByType(String type) {
        return em.createNamedQuery("findByType")
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Sensor> findByOrganizationId(long id) {
        return em.createNamedQuery("findByOrganizationId")
                .setParameter("organizationId", id)
                .getResultList();
    }

    @Override
    public List<Sensor> findByPublicSensor() {
        return em.createNamedQuery("findByPublicSensor")
                .setParameter("publicSensor", true)
                .getResultList();
    }
    
//    @Override
//    public List<Sensor> findSensorByParameters(long id, String type) {
//        
//        List<Sensor> sensorsByParam = new ArrayList<>();
//        Iterator it = sensors.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pairs = (Map.Entry) it.next();
//            Sensor current = (Sensor) pairs.getValue();
//            if ((id != 0) && !type.equals("null")) {
//                if(current.getOrganizationId() == id && current.getType().equals(type)) {
//                    sensorsByParam.add(current);
//                }
//            } else if((id != 0) && type.equals("null")) {
//                if(current.getOrganizationId() == id) {
//                    sensorsByParam.add(current);
//                }
//            } else if((id == 0) && !type.equals("null")) {
//                if(current.getType().equals(type)) {
//                    sensorsByParam.add(current);
//                }
//            }
//        }
//        return sensorsByParam;
//    }    
    
    //    @Override
//    public List<Sensor> findByParameters(long organizationId, String type) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    //    @Override
//    public long addSensor(Sensor sensor) {
//        sensor.setId(sensors.size() + 1);
//        sensors.put(sensor.getId(), sensor);
//        
//        return sensor.getId();
//    }
//
//    @Override
//    public void updateSensor(Sensor sensor) {
//        sensors.put(sensor.getId(), sensor);
//    }
//
//    @Override
//    public void deleteSensor(long id) {
//        sensors.remove(id);
//    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author
 */
@Stateless
public class SensorJpaDao implements ISensorDao {
    @PersistenceContext 
    public EntityManager em; 

    @Override
    public Sensor create(String description, String type) {
        Sensor s = new Sensor();
        s.setDescription(description);
        s.setType(type);
        
        em.persist(s);
        em.flush();
        
        return s;
    }

    @Override
    public Sensor update(Sensor sensor) {
        return em.merge(sensor);
    }

    @Override
    public void delete(Sensor sensor) {
        em.remove(sensor);
    }

    @Override
    public Sensor findById(long id) {
        List results = em.createNamedQuery("findById").setParameter("id", id).getResultList();
        return (Sensor) results.get(0);
    }

    @Override
    public List<Sensor> findAll() {
        List results = em.createNamedQuery("findAll").getResultList();
        return results;

    }

    @Override
    public List<Sensor> findByParameters(long organizationId, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author
 */
@Local
public interface ISensorDao {
    
    public Sensor create(String description, String type);
    
    public Sensor update(Sensor sensor);
    
    public void delete(Sensor sensor);
    
    public Sensor findById(long id);
    public List<Sensor> findAll();
    public List<Sensor> findByParameters(long organizationId, String type);
}
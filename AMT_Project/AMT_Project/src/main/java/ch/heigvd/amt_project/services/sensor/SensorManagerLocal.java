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
public interface SensorManagerLocal {

    public long create(Sensor sensor);

    public long create(String description, String type);

    public Sensor update(Sensor sensor);

    public void delete(Sensor sensor);

    public List<Sensor> findByName(String name);

    public List<Sensor> findByType(String type);

    public List<Sensor> findByOrganizationId(long id);

    public List<Sensor> findByPublicSensor();

    public Sensor findById(long id);

    public List<Sensor> findAll();

    //    public List<Sensor> findSensorByParameters(long id, String type);
//    public long addSensor(Sensor sensor);
//    
//    public void updateSensor(Sensor sensor);
//    
//    public void deleteSensor(long id);
}

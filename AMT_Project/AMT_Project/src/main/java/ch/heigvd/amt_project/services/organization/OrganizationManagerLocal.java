/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.organization;

import ch.heigvd.amt_project.services.sensor.*;
import ch.heigvd.amt_project.model.Sensor;
import java.util.List;

/**
 *
 * @author
 */
public interface OrganizationManagerLocal {
    
    public Sensor findSensorById(long id);
    
    public List<Sensor> findSensorByParameters(long id, String type);
    
    public List<Sensor> findAllSensors();
    
    public long addSensor(Sensor sensor);
    
    public void updateSensor(Sensor sensor);
    
    public void deleteSensor(long id);
}

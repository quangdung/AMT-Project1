/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.services.user;

import ch.heigvd.amt_project.services.sensor.*;
import ch.heigvd.amt_project.model.Sensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author
 */
@Singleton
public class UserManager implements SensorManagerLocal {

    private Map<Long, Sensor> sensors = new HashMap<>();

    public UserManager() {
    }

    @Override
    public Sensor findSensorById(long id) {
        Sensor sensor = sensors.get(id);
        return sensor;
    }

    @Override
    public List<Sensor> findSensorByParameters(long id, String type) {
        List<Sensor> sensorsByParam = new LinkedList<>();
        Iterator it = sensors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            Sensor current = (Sensor) pairs.getValue();
            if ((id != 0) && !type.equals("null")) {
                if(current.getOrganizationId() == id && current.getType().equals(type)) {
                    sensorsByParam.add(current);
                }
            } else if((id != 0) && type.equals("null")) {
                if(current.getOrganizationId() == id) {
                    sensorsByParam.add(current);
                }
            } else if((id == 0) && !type.equals("null")) {
                if(current.getType().equals(type)) {
                    sensorsByParam.add(current);
                }
            }
        }
        return sensorsByParam;
    }

    @Override
    public List<Sensor> findAllSensors() {
        return new ArrayList(sensors.values());
    }

    @Override
    public long addSensor(Sensor sensor) {
        sensor.setId(sensors.size() + 1);
        sensors.put(sensor.getId(), sensor);
        
        return sensor.getId();
    }

    @Override
    public void updateSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
    }

    @Override
    public void deleteSensor(long id) {
        sensors.remove(id);
    }

}

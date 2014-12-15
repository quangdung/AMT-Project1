/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.dto;

import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.model.Sensor;

/**
 *
 * @author
 */
public class FactTiedToSensorDTO extends FactDTO{

    private Sensor sensor;
    private long totNbObs;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public long getTotNbObs() {
        return totNbObs;
    }

    public void setTotNbObs(long totNbObs) {
        this.totNbObs = totNbObs;
    }
    
    
}

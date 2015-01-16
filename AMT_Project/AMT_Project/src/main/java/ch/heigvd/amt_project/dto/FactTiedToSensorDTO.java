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
public class FactTiedToSensorDTO extends FactDTO {

    private long sensorId;
    private long totNbObs;

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getTotNbObs() {
        return totNbObs;
    }

    public void setTotNbObs(long totNbObs) {
        this.totNbObs = totNbObs;
    }
}

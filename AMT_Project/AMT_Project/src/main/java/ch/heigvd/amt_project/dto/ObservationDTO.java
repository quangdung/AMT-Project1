/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.dto;

import ch.heigvd.amt_project.model.Sensor;
import java.sql.Date;

/**
 *
 * @author
 */

public class ObservationDTO {

    private long id;
    private String name;
    private float obsValue;
    private Date creationDate;
    private long sensorId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getObsValue() {
        return obsValue;
    }

    public void setObsValue(float obsValue) {
        this.obsValue = obsValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    
}

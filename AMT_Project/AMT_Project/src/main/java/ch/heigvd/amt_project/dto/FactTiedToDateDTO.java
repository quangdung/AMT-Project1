/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.dto;

import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.model.Sensor;
import java.sql.Date;

/**
 *
 * @author
 */
public class FactTiedToDateDTO extends FactTiedToSensorDTO {

    private Date date;
    private long nbVal;
    private double sumVal;
    private float minVal;
    private float maxVal;
    private float avVal;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNbVal() {
        return nbVal;
    }

    public void setNbVal(long nbVal) {
        this.nbVal = nbVal;
    }

    public double getSumVal() {
        return sumVal;
    }

    public void setSumVal(double sumVal) {
        this.sumVal = sumVal;
    }

    public float getMinVal() {
        return minVal;
    }

    public void setMinVal(float minVal) {
        this.minVal = minVal;
    }

    public float getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(float maxVal) {
        this.maxVal = maxVal;
    }

    public float getAvVal() {
        return avVal;
    }

    public void setAvVal(float avVal) {
        this.avVal = avVal;
    }
    
    
}

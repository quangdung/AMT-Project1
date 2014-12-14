package ch.heigvd.amt_project.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
        name = "FactTiedToDate.findAll",
        query = "SELECT f FROM Fact f WHERE f.type = :type"
    ),
    @NamedQuery(
        name = "FactTiedToDate.findByDate",
        query = "SELECT f FROM FactTiedToDate f WHERE f.date = :date"
    ),
    @NamedQuery(
        name = "FactTiedToDate.findByDateRange",
        query = "SELECT f FROM FactTiedToDate f WHERE f.date BETWEEN :startDate AND :endDate"
    )  
})

@Entity
@Table(name="factstiedtodate")
@DiscriminatorValue("tiedToDate")
public class FactTiedToDate extends FactTiedToSensor implements Serializable {

    @Column(name = "dateConcerned")
    private Date date;
    
    @Column(name = "nbOfValues")
    private long nbVal;
    
    @Column(name = "sumOfValues")
    private double sumVal;
    
    @Column(name = "minVal")
    private float minVal;

    @Column(name = "maxVal")
    private float maxVal;

    @Column(name = "averageValue")
    private float avVal;
            
    public FactTiedToDate() { 
    }
    
    public FactTiedToDate(Organization org, boolean visible, Sensor sensor,
            long totNbObs, Date date, long nbVal, double sumVal, float minVal,
            float maxVal, float avVal)
    {
        super(org, "date", visible, sensor, totNbObs);
        this.date = date;
        this.nbVal = nbVal;
        this.sumVal = sumVal;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.avVal = avVal;
    }

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

    public void setSumVal(double totVal) {
        this.sumVal = totVal;
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
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
            name = "FactTiedToDate.findById",
            query = "SELECT f FROM FactTiedToDate f WHERE f.id = :id"
    ),
    @NamedQuery(
            name = "FactTiedToDate.findByDate",
            query = "SELECT f FROM FactTiedToDate f WHERE f.date = :date"
    ),
    @NamedQuery(
            name = "FactTiedToDate.findByDateRange",
            query = "SELECT f FROM FactTiedToDate f WHERE f.date BETWEEN :startDate AND :endDate"
    ),
    @NamedQuery(
            name = "FactTiedToDate.findBySensorId",
            query = "SELECT f FROM FactTiedToDate f WHERE f.sensor.id = :sensorId AND f.type = :type"
    ),
    @NamedQuery(
            name = "FactTiedToDate.findBySensorIdByDate",
            query = "SELECT f FROM FactTiedToDate f WHERE f.sensor.id = :sensorId AND f.type = :type AND f.date = :date"
    )
})

@Entity
@Table(name = "factstiedtodate")
@DiscriminatorValue(FactType.FACT_TIED_TO_SENSOR_BY_DATE)
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

    public FactTiedToDate(Organization org,
                          boolean visible,
                          Sensor sensor,
                          long totNbObs,
                          Date date, long nbVal, double sumVal, float minVal,
                          float maxVal, float avVal) {
        super(org, visible, sensor, totNbObs);
        this.type = FactType.FACT_TIED_TO_SENSOR_BY_DATE;
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

    @Override
    public String getType() {
        return FactType.FACT_TIED_TO_SENSOR_BY_DATE;
    }
}

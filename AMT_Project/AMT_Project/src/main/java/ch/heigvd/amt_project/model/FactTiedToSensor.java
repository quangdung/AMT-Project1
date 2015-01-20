package ch.heigvd.amt_project.model;

import java.io.Serializable;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
            name = "FactTiedToSensor.findAll",
            query = "SELECT f FROM Fact f WHERE f.type = :type"
    ),
    @NamedQuery(
            name = "FactTiedToSensor.findBySensorId",
            query = "SELECT f FROM FactTiedToSensor f WHERE f.sensor.id = :sensorId"
    ),
    @NamedQuery(
            name = "FactTiedToSensor.findBySensorName",
            query = "SELECT f FROM FactTiedToSensor f WHERE f.sensor.name = :sensorName"
    ),
    @NamedQuery(
            name = "FactTiedToSensor.findBySensorType",
            query = "SELECT f FROM FactTiedToSensor f WHERE f.sensor.type = :sensorType"
    ),
    @NamedQuery(
            name = "FactTiedToSensor.findByTotNbObsAbove",
            query = "SELECT f FROM FactTiedToSensor f WHERE f.totNbObs > :threshold"
    ),
    @NamedQuery(
            name = "FactTiedToSensor.findByTotNbObsBelow",
            query = "SELECT f FROM FactTiedToSensor f WHERE f.totNbObs < :threshold"
    )
})

@Entity
@Table(name = "factstiedtosensor")
@DiscriminatorValue(FactType.FACT_TIED_TO_SENSOR)
public class FactTiedToSensor extends Fact implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensorId")
    private Sensor sensor;

    @Column(name = "totNbObs")
    private long totNbObs;

    public FactTiedToSensor() {
    }

    public FactTiedToSensor(Organization organization,
                            boolean visible,
                            Sensor sensor, final long totNbObs) {
        super(organization, visible);
        this.type = FactType.FACT_TIED_TO_SENSOR;
        this.sensor = sensor;
        this.totNbObs = totNbObs;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public long getNbObs() {
        return totNbObs;
    }

    public void setNbObs(long totNbObs) {
        this.totNbObs = totNbObs;
    }

    @Override
    public String getType() {
        return FactType.FACT_TIED_TO_SENSOR;
    }
    
    
}

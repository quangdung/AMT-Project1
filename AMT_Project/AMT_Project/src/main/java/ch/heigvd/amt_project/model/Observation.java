package ch.heigvd.amt_project.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

/**
 *
 * @author
 */
@NamedQueries({
    @NamedQuery(
            name = "findObservationById",
            query = "SELECT o FROM Observation o WHERE o.id = :id"
    ),
    @NamedQuery(
            name = "findAllObservations",
            query = "SELECT o FROM Observation o"
    ),
    @NamedQuery(
            name = "findObservationsByName",
            query = "SELECT o FROM Observation o WHERE o.name LIKE :name"
    ),
    @NamedQuery(
            name = "findObservationsByCreationDate",
            query = "SELECT o FROM Observation o WHERE o.creationDate = :date"
    ),
    @NamedQuery(
            name = "findObservationsBySensorId",
            query = "SELECT o FROM Observation o WHERE o.sensor.id = :sensorId"
    )
})

@Entity
@Table(name = "observations")
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    
    @Column(name = "obs_value")
    private float obsValue;
    
    @Column(name = "creation_date")
    private Date creationDate;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensorId")
    private Sensor sensor;
    

    public Observation() {
    }

    public Observation(long id, String name, float value, Date creationDate, Sensor sensor) {
        this.id = id;
        this.name = name;
        this.obsValue = value;
        this.creationDate = creationDate;
        this.sensor = sensor;
    }

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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    
}

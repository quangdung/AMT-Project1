package ch.heigvd.amt_project.model;

import java.io.Serializable;
import java.util.Date;
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
            query = "SELECT o FROM Observation o WHERE o.sensorId = :sensorId"
    )
})

@Entity
@Table(name = "observations")
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    
    @Column(name = "obs_value")
    private int obsValue;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Column(name = "sensor_id")
    private long sensorId;
    

    public Observation() {
    }

    public Observation(long id, String name, int value, Date creationDate, long sensorId) {
        this.id = id;
        this.name = name;
        this.obsValue = value;
        this.creationDate = creationDate;
        this.sensorId = sensorId;
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

    public int getObsValue() {
        return obsValue;
    }

    public void setObsValue(int obsValue) {
        this.obsValue = obsValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getObservationId() {
        return sensorId;
    }

    public void setObservationId(long sensorId) {
        this.sensorId = sensorId;
        
        
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    
}

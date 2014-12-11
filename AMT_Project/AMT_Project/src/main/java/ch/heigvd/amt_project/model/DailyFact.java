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
            name = "findDailyFactById",
            query = "SELECT f FROM Fact f WHERE f.id = :id"
    ),
    @NamedQuery(
            name = "findAllDailyFacts",
            query = "SELECT f FROM Fact f"
    ),
    @NamedQuery(
            name = "findDailyFactsByName",
            query = "SELECT f FROM Fact f WHERE f.name LIKE :name"
    ),
    @NamedQuery(
            name = "findDailyFactsByType",
            query = "SELECT f FROM Fact f WHERE f.type LIKE :type"
    ),
    @NamedQuery(
            name = "findDailyFactsByOrganizationId",
            query = "SELECT f FROM Fact f WHERE f.organizationId = :orgId"
    ),
    @NamedQuery(
            name = "findDailyFactsByPublicFact",
            query = "SELECT f FROM Fact f WHERE f.publicFact = true"
    )
})

@Entity
@Table(name="dailyfacts")
public class DailyFact extends Fact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date date;
    private String name;
    private String type;
    private String description;
    
    @Column(name = "organization_id")
    private long organizationId;
    
    @Column(name = "public_fact")
    private boolean publicFact;
    
    @Column(name = "nb_obs")
    private long nbObs;
    
    @Column(name = "sensor_id")
    private long sensorId;
    
    
    public DailyFact() { 
    }

    public DailyFact(String name, String type, String description, long organizationId, boolean publicFact, long nbObs, long sensorId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.organizationId = organizationId;
        this.publicFact = publicFact;
        this.nbObs = nbObs;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public boolean isPublicFact() {
        return publicFact;
    }

    public void setPublicFact(boolean publicFact) {
        this.publicFact = publicFact;
    }
    
    @Override
    public String toString() {
        return "Fact " + id + ": " 
                + name + ", " + type + ", " + description + ", " + organizationId
                + (publicFact ? ", public" : "");
    }

    public long getNbObs() {
        return nbObs;
    }

    public void setNbObs(long nbObs) {
        this.nbObs = nbObs;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
    
    
}

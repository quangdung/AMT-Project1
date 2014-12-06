package ch.heigvd.amt_project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author
 */
////@NamedQueries({
////    @NamedQuery(
////            name = "findById",
////            query = "SELECT o FROM Observation o WHERE o.id = :id"
////    ),
////    @NamedQuery(
////            name = "findByName",
////            query = "SELECT o FROM Observation o WHERE o.name = :name"
////    ),
////    @NamedQuery(
////            name = "findBySensorId",
////            query = "SELECT o FROM Observation o WHERE o.sensorId = :sensorId"
////    )
////})
//
@Entity
@Table(name = "observations")
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int value;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;
    
    @Column(name = "sensor_id")
    private long sensorId;
    

    public Observation() {
    }

    
}

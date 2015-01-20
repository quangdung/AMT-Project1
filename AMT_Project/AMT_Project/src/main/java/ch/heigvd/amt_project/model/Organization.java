package ch.heigvd.amt_project.model;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author
 */
@NamedQueries({
    @NamedQuery(
        name="findAllOrganizations",
        query="SELECT o FROM Organization o"
    ),
    @NamedQuery(
        name="findOrganizationById",
        query="SELECT o FROM Organization o WHERE o.id = :id"
    )
})

@Entity
@Table(name = "organizations")
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    
    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization(long id, String name) {
        this.id = id;
        this.name = name;
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
    
    
}

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
    ),
    @NamedQuery(
        name="findOrganizationsByName",
        query="SELECT o FROM Organization o WHERE o.name = :name"
    )
})

@Entity
@Table(name = "organizations")
public class Organization implements Serializable {

    @Id
    @SequenceGenerator(name="org_seq", allocationSize=10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    
    public Organization() {
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

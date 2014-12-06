package ch.heigvd.amt_project.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author
 */

@NamedQueries({
    @NamedQuery(
            name = "findFactById",
            query = "SELECT f FROM Fact f WHERE f.id = :id"
    ),
    @NamedQuery(
            name = "findAllFacts",
            query = "SELECT f FROM Fact f"
    ),
    @NamedQuery(
            name = "findFactsByName",
            query = "SELECT f FROM Fact f WHERE f.name LIKE :name"
    ),
    @NamedQuery(
            name = "findFactsByType",
            query = "SELECT f FROM Fact f WHERE f.type LIKE :type"
    ),
    @NamedQuery(
            name = "findFactsByOrganizationId",
            query = "SELECT f FROM Fact f WHERE f.organizationId = :orgId"
    ),
    @NamedQuery(
            name = "findFactsByPublicFact",
            query = "SELECT f FROM Fact f WHERE f.publicFact = true"
    )
})

@Entity
@Table(name="facts")
public class Fact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String type;
    private String description;
    
    @Column(name = "organization_id")
    private long organizationId;
    
    @Column(name = "public_fact")
    private boolean publicFact;
    
    
    public Fact() { 
    }

    public Fact(long id, String name, String type, String description, long organizationId, boolean publicFact) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.organizationId = organizationId;
        this.publicFact = publicFact;
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
}

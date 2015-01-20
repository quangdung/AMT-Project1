package ch.heigvd.amt_project.model;

import java.io.Serializable;
import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
            name = "findAllFacts",
            query = "SELECT f FROM Fact f"
    ),
    @NamedQuery(
            name = "findFactById",
            query = "SELECT f FROM Fact f WHERE f.id = :id"
    ),
    @NamedQuery(
            name = "findFactsByOrganizationId",
            query = "SELECT f FROM Fact f WHERE f.organization.id = :orgId"
    ),
    @NamedQuery(
            name = "findFactsByVisibility",
            query = "SELECT f FROM Fact f WHERE f.visible = :visible"
    )
})

@Entity
@Table(name = "facts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "typeOfFact",
        discriminatorType = DiscriminatorType.STRING
)
public class Fact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orgId")
    private Organization organization;

    @Column(name = "type")
    String type;

    @Column(name = "visible")
    private boolean visible;

    public Fact() {
    }

    public Fact(Organization organization, boolean visible) {
        this.organization = organization;
        this.visible = visible;
        this.type = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

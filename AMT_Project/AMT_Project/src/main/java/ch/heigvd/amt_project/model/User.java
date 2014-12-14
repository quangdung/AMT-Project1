package ch.heigvd.amt_project.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author
 */
@NamedQueries({
    @NamedQuery(
            name = "findAllUsers",
            query = "SELECT u FROM User u"
    ),
    @NamedQuery(
            name = "findUserById",
            query = "SELECT u FROM User u WHERE u.id = :id"
    ),
    @NamedQuery(
            name = "findUsersByFirstName",
            query = "SELECT u FROM User u WHERE u.firstName LIKE :firstName"
    ),
    @NamedQuery(
            name = "findUsersByLastName",
            query = "SELECT u FROM User u WHERE u.lastName LIKE :lastName"
    ),
    @NamedQuery(
            name = "findUsersByEmail",
            query = "SELECT u FROM User u WHERE u.email LIKE :email"
    ),
    @NamedQuery(
            name = "findUsersByOrgId",
            query = "SELECT u FROM User u WHERE u.organization.id = :orgId"
    ),
    @NamedQuery(
            name = "findContactByOrgId",
            query = "SELECT u FROM User u WHERE u.organization.id = :orgId "
            + "AND u.mainContact = true"
    ),})

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @SequenceGenerator(name="user_seq", allocationSize=10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orgId")
    private Organization organization;
    

    @Column(name = "main_contact")
    private boolean mainContact;

    public User() {
    }

    public User(long id, String firstName, String lastName, String email, String password, Organization organization, boolean mainContact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.organization = organization;
        this.mainContact = mainContact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isMainContact() {
        return mainContact;
    }

    public void setMainContact(boolean mainContact) {
        this.mainContact = mainContact;
    }

    @Override
    public String toString() {
        return "User : " + id + ", " + firstName + " " + lastName
                + " (" + email + ") "
                + (mainContact ? ", Main contact" : "");
    }
}

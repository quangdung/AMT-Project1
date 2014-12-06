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
            query = "SELECT u FROM User u WHERE u.fisrtName LIKE :firstName"
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
            query = "SELECT u FROM User u WHERE u.organizationId = :orgId"
    ),
    @NamedQuery(
            name = "findContactByOrgId",
            query = "SELECT u FROM User u WHERE u.organizationId = :orgId "
            + "AND u.mainContact = true"
    ),})

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Column(name = "organization_id")
    private long organizationId;

    @Column(name = "main_contact")
    private boolean mainContact;

    public User() {
    }

    public User(long id, String firstName, String lastName, String email, String password, long organizationId, boolean mainContact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.organizationId = organizationId;
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

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
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

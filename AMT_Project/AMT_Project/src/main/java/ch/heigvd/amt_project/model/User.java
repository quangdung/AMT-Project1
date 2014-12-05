/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author
 */
@NamedQueries({
    @NamedQuery(
            name = "findById",
            query = "SELECT u FROM User u WHERE u.id = :id"
    ),
    @NamedQuery(
            name = "findByFirstName",
            query = "SELECT u FROM User u WHERE u.fisrtName = :firstName"
    ),
    @NamedQuery(
            name = "findByLastName",
            query = "SELECT u FROM User u WHERE u.lastName = :lastName"
    ),
    @NamedQuery(
            name = "findByEmail",
            query = "SELECT u FROM User u WHERE u.email = :email"
    ),
    @NamedQuery(
            name = "findByOrganizationId",
            query = "SELECT u FROM User u WHERE u.organizationId = :organizationId"
    ),
    @NamedQuery(
            name = "findByIsMainContact",
            query = "SELECT u FROM User u WHERE u.isMainContact = true AND u.organizationId = :organizationId"
    ),
    @NamedQuery(
            name = "findAll",
            query = "SELECT u FROM User u"
    )
})

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long organizationId;
    private boolean isMainContact;

    public User() {
    }

    public User(long id, String firstName, String lastName, String email, String password, long organizationId, boolean isMainContact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.organizationId = organizationId;
        this.isMainContact = isMainContact;
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

    public boolean isIsMainContact() {
        return isMainContact;
    }

    public void setIsMainContact(boolean isMainContact) {
        this.isMainContact = isMainContact;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "User : " + id + ", " + firstName + " " + lastName
                + " (" + email + ") "
                + (isMainContact ? ", Main contact" : "");
    }
}

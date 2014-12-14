/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author
 */
@NamedQueries({
    @NamedQuery(
            name = "findSensorById",
            query = "SELECT s FROM Sensor s WHERE s.id = :id"
    ),
    @NamedQuery(
            name = "findAllSensors",
            query = "SELECT s FROM Sensor s"
    ),
    @NamedQuery(
            name = "findSensorsByName",
            query = "SELECT s FROM Sensor s WHERE s.name LIKE :name"
    ),
    @NamedQuery(
            name = "findSensorsByType",
            query = "SELECT s FROM Sensor s WHERE s.type LIKE :type"
    ),
    @NamedQuery(
            name = "findSensorsByOrganizationId",
            query = "SELECT s FROM Sensor s WHERE s.organization.id = :orgId"
    ),
    @NamedQuery(
            name = "findSensorsByPublicSensor",
            query = "SELECT s FROM Sensor s WHERE s.visible = true"
    )
})

@Entity
@Table(name = "sensors")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orgId")
    private Organization organization;

    @Column(name = "visible")
    private boolean visible;

    public Sensor() {
    }
    
    public Sensor(Sensor s)
    {
        this.id = s.id;
        this.name = s.name;
        this.description = s.description;
        this.type = s.type;
        this.organization = s.organization;
        this.visible = s.visible;
    }

    public Sensor(long id, String name, String description, String type, Organization organization, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.organization = organization;
        this.visible = isPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Sensor #" + id + ", " + name + ", " + description + ", " + type + ", "
                + ", " + organization.getName()
                + (visible ? ", public" : "");
    }
}

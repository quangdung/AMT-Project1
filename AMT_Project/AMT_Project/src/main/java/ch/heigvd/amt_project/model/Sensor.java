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
            query = "SELECT s FROM Sensor s WHERE s.organizationId = :orgId"
    ),
    @NamedQuery(
            name = "findSensorsByPublicSensor",
            query = "SELECT s FROM Sensor s WHERE s.publicSensor = true"
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

    @Column(name = "organization_id")
    private long organizationId;

    @Column(name = "public_sensor")
    private boolean publicSensor;

    public Sensor() {
    }

    public Sensor(long id, String name, String description, String type, long organizationId, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.organizationId = organizationId;
        this.publicSensor = isPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublicSensor() {
        return publicSensor;
    }

    public void setPublicSensor(boolean publicSensor) {
        this.publicSensor = publicSensor;
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

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "Sensor #" + id + ", " + name + ", " + description + ", " + type + ", "
                + ", " + organizationId
                + (publicSensor ? ", public" : "");
    }
}

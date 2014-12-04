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
        name="findById",
        query="SELECT s FROM Sensor s WHERE s.id = :id"
    ),
    @NamedQuery(
        name="findByOrganizationId",
        query="SELECT s FROM Sensor s WHERE s.organizationId = :organizationId"
    ),
    @NamedQuery(
        name="findByType",
        query="SELECT s FROM Sensor s WHERE s.type = :type"
    ),
    @NamedQuery(
        name="findAll",
        query="SELECT s FROM Sensor s"
    ),
})

@Entity
@Table(name="sensors")
public class Observation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String description;
    private String type;
    private long organizationId;
    
    public Observation() { 
    }
    
    public Observation(String description, String type, long organizationId) {
        this.description = description;
        this.type = type;
        this.organizationId = organizationId;
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
        return "Sensor #" + id + ", " + description + ": " + type;
    }
}

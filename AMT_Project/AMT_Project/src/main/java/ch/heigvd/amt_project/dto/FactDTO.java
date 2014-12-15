/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.dto;

import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.model.Sensor;

/**
 *
 * @author
 */
public class FactDTO {

    private long id;

    private Organization organization;
    private boolean visible;
    private Sensor sensor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganizationId(Organization organization) {
        this.organization = organization;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean publicFact) {
        this.visible = publicFact;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

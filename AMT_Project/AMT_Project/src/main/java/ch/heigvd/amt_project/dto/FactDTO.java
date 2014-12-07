/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_project.dto;

/**
 *
 * @author
 */
public class FactDTO {

    private long id;

    private String name;
    private String type;
    private String description;
    private long organizationId;
    private boolean publicFact;

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
    
    
}

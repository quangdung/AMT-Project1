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
public class UserDTO {

    private long id;
    private String name;
    private String description;
    private String type;
    public boolean visibility;
    private long organizationId;
    
   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
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

    public void setOrganizationId(long orgId) {
        this.organizationId = orgId;
    }
}

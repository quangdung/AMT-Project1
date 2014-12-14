
package ch.heigvd.amt_project.dto;

import ch.heigvd.amt_project.model.Organization;

public class SensorDTO {

    private long id;
    private String name;
    private String description;
    private String type;
    private boolean visible;
    private Organization organization;
    
   
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
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganizationId(Organization organization) {
        this.organization = organization;
    }
}

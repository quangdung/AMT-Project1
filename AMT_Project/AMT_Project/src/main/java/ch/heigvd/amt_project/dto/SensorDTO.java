
package ch.heigvd.amt_project.dto;

public class SensorDTO {

    private long id;
    private String name;
    private String description;
    private String type;
    private boolean publicSensor;
    private long organizationId;
    
   
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
    
    public boolean isPublicSensor() {
        return publicSensor;
    }

    public void setPublicSensor(boolean visibility) {
        this.publicSensor = visibility;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long orgId) {
        this.organizationId = orgId;
    }
}

package ch.heigvd.amt_project.services.organization;

import ch.heigvd.amt_project.model.Organization;

import java.util.List;
import javax.ejb.Local;

@Local
public interface OrganizationManagerLocal {

    public long create(Organization org);

    public List<Organization> read();
    public Organization read(long id);
    
//    public Organization update(Organization org);
    public void update(Organization org);

    public void delete(Organization org);
}
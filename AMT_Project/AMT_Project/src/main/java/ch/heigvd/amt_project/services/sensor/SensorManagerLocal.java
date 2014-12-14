
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Organization;
import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SensorManagerLocal {

    public long create(Sensor sensor);

    public long create(long id, String name, String description, String type, Organization organization, boolean visible);

    public List<Sensor> read();
    public Sensor read(long id);
    public List<Sensor> readByName(String name);
    public List<Sensor> readByType(String type);
    public List<Sensor> readByOrgId(long id);
    public List<Sensor> readPublicSensor();
    
    public Sensor update(Sensor sensor);

    public void delete(Sensor sensor);
}

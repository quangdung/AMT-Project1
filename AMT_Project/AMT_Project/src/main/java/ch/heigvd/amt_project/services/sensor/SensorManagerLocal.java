
package ch.heigvd.amt_project.services.sensor;

import ch.heigvd.amt_project.model.Sensor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SensorManagerLocal {

    public long create(Sensor sensor);

    public long create(String description, String type);

    public List<Sensor> read();
    public Sensor read(long id);
    public List<Sensor> readByName(String name);
    public List<Sensor> readByType(String type);
    public List<Sensor> readByOrgId(long id);
    public List<Sensor> readPublicSensor();
    
    public Sensor update(Sensor sensor);

    public void delete(Sensor sensor);
}

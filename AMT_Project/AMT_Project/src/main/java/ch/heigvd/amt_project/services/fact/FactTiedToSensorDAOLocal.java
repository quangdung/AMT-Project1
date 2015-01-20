package ch.heigvd.amt_project.services.fact;
import ch.heigvd.amt_project.model.FactTiedToSensor;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author
 */
@Local
public interface FactTiedToSensorDAOLocal {

    public long create(FactTiedToSensor fact);

    public List<FactTiedToSensor> readAllTiedToSensor();
    public List<FactTiedToSensor> readBySensorId(long sensorId);
    public List<FactTiedToSensor> readBySensorName(String sensorName);
    public List<FactTiedToSensor> readBySensorType(String sensorType);
    public List<FactTiedToSensor> readByTotalNbObsAbove(long threshold);
    public List<FactTiedToSensor> readByTotalNbObsBelow(long threshold);
    
    public FactTiedToSensor update(FactTiedToSensor fact);
}

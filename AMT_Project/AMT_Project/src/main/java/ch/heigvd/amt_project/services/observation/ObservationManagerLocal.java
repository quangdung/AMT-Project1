package ch.heigvd.amt_project.services.observation;
import ch.heigvd.amt_project.model.Observation;
import java.util.Date;
import java.util.List;

/**
 *
 * @author
 */
public interface ObservationManagerLocal {
    
    public long create(Observation observation);

    public List<Observation> read();
    public Observation read(long id);
    public List<Observation> readByName(String name);
    public List<Observation> readByCreationDate(Date date);
    public List<Observation> readBySensorId(long id);
    
    public Observation update(Observation observation);

    public void delete(Observation observation);
}

package ch.heigvd.amt_project.services.fact;
import ch.heigvd.amt_project.model.Fact;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author
 */
@Local
public interface FactManagerLocal {

    public long create(Fact fact);

    public List<Fact> read();
    public Fact read(long id);
    public List<Fact> readByOrgId(long id);
    public List<Fact> readByVisibility(boolean visible);
    
    public Fact update(Fact fact);

    public void delete(long factId);
    public void deleteAll();
}

package ch.heigvd.amt_project.services.fact;

import ch.heigvd.amt_project.model.FactTiedToDate;
import java.sql.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FactTiedToDateManagerLocal {

    public long create(FactTiedToDate fact);

    public List<FactTiedToDate> readAllTiedToDate();
    public List<FactTiedToDate> readByDate(Date date);
    public List<FactTiedToDate> readByDateRange(Date startDate, Date endDate);
    
    public FactTiedToDate update(FactTiedToDate fact);
}
package ch.heigvd.amt_project.api;

import ch.heigvd.amt_project.services.fact.FactDAOLocal;
import ch.heigvd.amt_project.services.observation.ObservationDAOLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Stateless
@Path("operations")
public class AdminOperationResource {
 
    @EJB
    FactDAOLocal factDAO;
    
    @EJB
    ObservationDAOLocal obsDAO;
    
    public AdminOperationResource() {}
    
    @Path("/reset")
    @POST
    @Consumes("application/json")
    public void postResetOperation(Object operation) {
        factDAO.deleteAll();
        obsDAO.deleteAll();
    }
    
    @Path("/resetFact")
    @DELETE
    public void postResetFactOperation() {
        factDAO.deleteAll();
    }
    
    @Path("/resetObservation")
    @DELETE
    public void postResetObservationOperation() {
        obsDAO.deleteAll();
    }
}
